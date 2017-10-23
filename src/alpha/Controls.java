package alpha;

import com.pi4j.io.gpio.*;
import face.FaceDetection;
import face.FaceRecognition;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import sensing.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedHashMap;

import static face.Conversions.buffedImgToBytes;
import static face.Conversions.frameToBuff;

/**
 * @author Hanzhi Zhou
 */
public class Controls {
    private final AlphaProtocol ap;
    private final FaceRecognition fr = new FaceRecognition();
    private final AlphaActions actions;
    private FFmpegFrameGrabber grabber;
    private Frame frame;
    private FaceDetection face;

    private BarrierDetector frontDetector;
    private BarrierDetector backDetector;
    private HC_SR04 ultrasonicSensor;

    public Controls(AlphaProtocol p) {
        this.ap = p;
        actions = new AlphaActions(p);
    }

    public void initializeFrontDetector(Pin p) {
        frontDetector = new BarrierDetector(p);
    }

    public void initializeBackDetector(Pin p) {
        backDetector = new BarrierDetector(p);
    }

    public void initializeUltrasonicSensor(Pin control, Pin data) {
        ultrasonicSensor = new HC_SR04(control, data);
    }

    public void initFaceDetection(String cascade) {
        face = new FaceDetection(cascade);
    }

    public void initMovementHandler() {
        new Thread(() -> {
            while (true) {
                if (!actions.isActing()) {
                    if (frontDetector.detected() && !backDetector.detected()) {
                        ap.moveBackward(1);
                    } else if (!frontDetector.detected() && backDetector.detected()) {
                        ap.moveForward(1);
                    } else {
                        if (!Arrays.equals(ap.currentMotorRotations, AlphaProtocol.initialMotorPositions))
                            ap.restoreMotorDefaultState();
                    }
                }
            }
        }).start();
    }

    public void initFrameGrabber(String address, int frameWidth, int frameHeight, int frameRate, String format) {
        new Thread(() -> {
            grabber = new FFmpegFrameGrabber(address);
            grabber.setImageWidth(frameWidth);
            grabber.setImageHeight(frameHeight);
            grabber.setFrameRate(frameRate);
            grabber.setFormat(format);
            try {
                grabber.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            try {
                while (true) {
                    this.frame = grabber.grabFrame(false, true, true, false).clone();
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void initEmotionHandler(final I2CLCDExtended display) {
        new Thread(() -> {
            BufferedImage bf;
            String emotion = null;
            if (display == null) {
                while (true) {
                    if (this.frame != null) {
                        if (face.detect(frame, false)) {
                            bf = frameToBuff(this.frame);
                            emotion = FaceRecognition.getTopEmotion(fr.getEmotion(buffedImgToBytes(bf)));
                        }
                        if (!actions.isActing())
                            actions.caller(emotion);
                    }
                }
            } else {
                final int rowNumber = display.getRowCount();
                String[] text = new String[rowNumber];
                int i;
                while (true) {
                    if (this.frame != null) {
                        if (face.detect(frame, false)) {
                            bf = frameToBuff(this.frame);
                            LinkedHashMap<String, Double> result = FaceRecognition.getTopEmotions(fr.getEmotion(buffedImgToBytes(bf)), rowNumber);
                            if (result != null) {
                                i = 0;
                                for (String key : result.keySet()) {
                                    if (i == 0)
                                        emotion = key;
                                    text[i++] = key + ": " + result.get(key);
                                }
                                display.write(text);
                            }
                            if (!actions.isActing())
                                actions.caller(emotion);
                        }
                    }
                }
            }
        }).start();
    }

    public void initSoundSensorHandler() {
        new Thread(() -> {
            String emotion;
            byte[] img;
            double d;
            while (true) {
                d = ultrasonicSensor.getDistance();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (d < 50 && d > 20) {
                    System.out.println(d);
//                        emotion = fr.getEmotion(img);
//                        System.out.println(emotion);
//                        actions.caller(emotion);
                }
            }
        }).start();
    }
}