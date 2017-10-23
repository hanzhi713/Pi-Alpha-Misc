package face;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Hanzhi Zhou
 */
public class FaceRecognition {
    public static final String[] emotions = {
            "sadness", "neutral", "disgust", "anger", "surprise", "fear", "happiness"
    };
    public static final int SIMULTANEOUS_MODE = 0;
    public static final int ALTERNATE_MODE = 1;
    public static final int AZURE_ONLY = 2;
    public static final int FACEPP_ONLY = 3;
    private final Map<String, Double> emotionMap = new HashMap<>();
    private int requestCounter = 0;
    private int requestMode = FACEPP_ONLY;

    public FaceRecognition() {
        for (String emotion : emotions)
            emotionMap.put(emotion, 0.0);
    }

    public Map<String, Double> getEmotion(byte[] img) {
        return getEmotion(img, requestMode);
    }

    private Map<String, Double> getEmotion(byte[] img, int requestMode) {
        if (requestMode == ALTERNATE_MODE) {
            requestCounter++;
            EmotionRecognitionThread r;
            if (requestCounter % 2 == 0) {
                r = new FacePPRequest(img);
            } else {
                r = new AzureRequest(img);
            }
            getResult(r);
        } else if (requestMode == SIMULTANEOUS_MODE) {
            for (String emotion : FaceRecognition.emotions)
                emotionMap.replace(emotion, 0.0);
            FacePPRequest r1 = new FacePPRequest(img);
            AzureRequest r2 = new AzureRequest(img);
            r1.start();
            r2.start();
            try {
                r1.join(3000);
                r2.join(3000);
                Map<String, Double> faceppResult = r1.getEmotion();
                Map<String, Double> azureResult = r2.getEmotion();
                for (String emotion : emotions)
                    emotionMap.replace(emotion, (faceppResult.getOrDefault(emotion, 0.0) + azureResult.getOrDefault(emotion, 0.0)) / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            EmotionRecognitionThread ert;
            if (requestMode == FACEPP_ONLY)
                ert = new FacePPRequest(img);
            else
                ert = new AzureRequest(img);
            for (String emotion : FaceRecognition.emotions)
                emotionMap.replace(emotion, 0.0);
            getResult(ert);
        }
        return emotionMap;
    }

    private void getResult(EmotionRecognitionThread r){
        r.start();
        try {
            r.join(3000);
            Map<String, Double> result = r.getEmotion();
            for (String emotion : emotions)
                emotionMap.replace(emotion, result.getOrDefault(emotion, 0.0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getTopEmotion(Map<String, Double> emotions){
        double maxValue = -1.0;
        String maxEmo = null;
        for (String emotion : emotions.keySet()){
            double v = emotions.get(emotion);
            if (v > maxValue){
                maxValue = v;
                maxEmo = emotion;
            }
        }
        return maxEmo;
    }

    public static LinkedHashMap<String, Double> getTopEmotions(Map<String, Double> emotions, int number) {
        if (emotions == null)
            return null;
        LinkedHashMap<String, Double> result = new LinkedHashMap<>();
        Set<Entry<String, Double>> emotionSet = emotions.entrySet();
        ArrayList<Double> values = new ArrayList<>();
        for (Entry<String, Double> emotion : emotionSet)
            values.add(emotion.getValue());
        values.sort((o1, o2) -> o1 > o2 ? -1 : o1.equals(o2) ? 0 : 1);
        for (int i = 0; i < number; i++)
            for (Entry<String, Double> emotion : emotionSet)
                if (emotion.getValue().equals(values.get(i)))
                    result.put(emotion.getKey(), values.get(i));
        return result;
    }

    public void setRequestMode(int requestMode) {
        this.requestMode = requestMode;
    }
}

abstract class EmotionRecognitionThread extends Thread{
    abstract Map<String, Double> getEmotion();
}

class FacePPRequest extends EmotionRecognitionThread{
    private static final String apiKey = "";
    private static final String apiSecret = "";
    private static final JsonParser jp = new JsonParser();
    private final CommonOperate co = new CommonOperate(apiKey, apiSecret, false);
    private final byte[] img;
    private final Map<String, Double> emotions = new HashMap<>();

    FacePPRequest(byte[] img) {
        this.img = img;
        for (String emotion : FaceRecognition.emotions)
            emotions.put(emotion, 0.0);
    }

    public void run() {
        clearEmotion();
        Response rp = null;
        try {
            rp = co.detectByte(img, 0, "emotion");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rp != null) {
            int status = rp.getStatus();
            //System.out.println(status);
            String jsonResult = new String(rp.getContent());
            //System.out.println(jsonResult);
            JsonObject jo = null;
            if (status == 200) {
                JsonArray ja = jp.parse(jsonResult)
                        .getAsJsonObject().get("faces").getAsJsonArray();
                if (ja.size() > 0)
                    jo = ja.get(0).getAsJsonObject().get("attributes")
                            .getAsJsonObject().get("emotion").getAsJsonObject();
            }
            if (jo != null) {
                for (Entry<String, JsonElement> emotion : jo.entrySet())
                    emotions.replace(emotion.getKey(), emotion.getValue().getAsDouble());
            }
        }
    }

    private void clearEmotion() {
        for (String emotion : FaceRecognition.emotions)
            emotions.put(emotion, 0.0);
    }

    public Map<String, Double> getEmotion() {
        return emotions;
    }
}

class AzureRequest extends EmotionRecognitionThread{
    private static final String apiKey = "";
    private static final JsonParser jp = new JsonParser();
    private static final HttpClient httpclient = HttpClients.createDefault();
    private static URI requestURI;

    static {
        try {
            requestURI = new URIBuilder("https://api.cognitive.azure.cn/emotion/v1.0/recognize").build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private final byte[] img;
    private final Map<String, Double> emotions = new HashMap<>();

    AzureRequest(byte[] img) {
        this.img = img;
        for (String emotion : FaceRecognition.emotions)
            emotions.put(emotion, 0.0);
    }

    public void run() {
        clearEmotion();
        HttpPost request = new HttpPost(requestURI);
        request.setHeader("Content-Type", "application/octet-stream");
        request.setHeader("Ocp-Apim-Subscription-Key", apiKey);
        ByteArrayEntity img = new ByteArrayEntity(this.img);
        request.setEntity(img);
        try {
            HttpResponse response = httpclient.execute(request);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            if (!result.contains("error")) {
                JsonArray faceRectangles = jp.parse(result).getAsJsonArray();
                if (faceRectangles.size() > 0) {
                    JsonObject scores = faceRectangles.get(0).getAsJsonObject().get("scores").getAsJsonObject();
                    for (Entry<String, JsonElement> emotion : scores.entrySet())
                        if (emotions.containsKey(emotion.getKey()))
                            emotions.replace(emotion.getKey(), emotion.getValue().getAsDouble() * 100);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearEmotion() {
        for (String emotion : FaceRecognition.emotions)
            emotions.replace(emotion, 0.0);
    }

    public Map<String, Double> getEmotion() {
        return emotions;
    }

}