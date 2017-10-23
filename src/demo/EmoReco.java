package demo;


import face.FaceDetection;
import face.FaceRecognition;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_videoio.*;
import static face.Conversions.*;

import javax.swing.*;
import java.util.LinkedHashMap;

public class EmoReco {

    private static String emotion = "";
    private static Mat frame = new Mat();

    public static void main(String... args) {
        FaceRecognition fr = new FaceRecognition();
        FaceDetection fd = new FaceDetection(FaceDetection.LBP_FRONTAL_FACE);
        JFrame gui = new JFrame("Form");
        Form form = new Form();
        gui.setContentPane(form.Main);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.pack();
        gui.setVisible(true);

        VideoCapture camera = new VideoCapture(0);
        new Thread(() -> {
            while (true){
                camera.read(frame);
                String text = emotion.contains(":") ? emotion.substring(6, emotion.indexOf(":")) : "";
                fd.detect(frame, true, text);
                form.setPicture(frameToBuff(matToFrame(frame)));
                form.setText(emotion);
            }

        }).start();
        new Thread(() -> {
            while (true){
                LinkedHashMap<String, Double> result = FaceRecognition.getTopEmotions(fr.getEmotion(buffedImgToBytes(frameToBuff(matToFrame(frame.clone())))), 4);
                StringBuilder box_text = new StringBuilder("<html>");
                for (String key : result.keySet())
                    box_text.append(key).append(": ").append(result.get(key)).append("<br/>");
                box_text.append("</html>");
                emotion = box_text.toString();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
