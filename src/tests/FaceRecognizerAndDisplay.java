package tests;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import face.FaceDetection;
import face.FaceRecognition;
import sensing.I2CLCDExtended;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

import static face.Conversions.*;

public class FaceRecognizerAndDisplay {
    private static Frame frame = null;
    private static FaceRecognition fr = new FaceRecognition();
    private static FaceDetection face = new FaceDetection(FaceDetection.LBP_FRONTAL_FACE);
    private static FFmpegFrameGrabber f = new FFmpegFrameGrabber("http://localhost:9090/stream/video.mjpeg");
    private static I2CLCDExtended display = I2CLCDExtended.Default_LCD2004();


    public static void startGrabbing() {
        new Thread(() -> {
            try {
                while (true) {
                    frame = f.grabFrame(false, true, true, false).clone();
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void display(String[] lines) {
        new Thread(() -> {
            display.write(lines);
        }).start();
    }

    public static void main(String[] args) throws Exception {
        int rows = display.getRowCount();
        ImShow im = new ImShow(720, 560);
        f.setFrameRate(4);
        f.setImageWidth(720);
        f.setImageHeight(560);
        f.setFormat("mjpeg");
        f.start();
        startGrabbing();
        new Thread() {
            public void run() {
                String[] text = new String[rows];
                BufferedImage bf;
                int i;
                while (true) {
                    if (frame != null) {
                        if (face.detect(frame, true)) {
                            bf = frameToBuff(frame);
                            LinkedHashMap<String, Double> result = FaceRecognition.getTopEmotions(fr.getEmotion(buffedImgToBytes(bf)), rows);
                            if (result != null) {
                                i = 0;
                                for (String key : result.keySet()) {
                                    text[i] = key + ": " + result.get(key);
                                    if (i >= rows - 1)
                                        break;
                                    i++;
                                }
                                display.write(text);
                            }
                        }
                    }
                }
            }
        }.start();
    }
}
