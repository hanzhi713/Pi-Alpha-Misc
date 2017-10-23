package alpha;

import com.pi4j.io.gpio.*;
import face.FaceDetection;
import sensing.I2CLCDExtended;

/**
 * @author Hanzhi Zhou
 * */
public class Main {
    private static final AlphaProtocol ap = new PCProtocol(0x483, 0x5750);

    public static void main(String[] args) {
//        ap.moveMotor(new int[]{54, 20, 67, 40, 162, 111, 75, 108, 104, 120, 103, 80, 90, 72, 78, 102}, 500, 0);
        Controls control = new Controls(ap);
        control.initializeFrontDetector(RaspiPin.GPIO_00);
        control.initializeBackDetector(RaspiPin.GPIO_01);
        control.initMovementHandler();
        control.initFrameGrabber("http://localhost:9090/stream/video.mjpeg",720, 560, 3, "mjpeg");
        control.initFaceDetection(FaceDetection.LBP_FRONTAL_FACE);
        control.initEmotionHandler(I2CLCDExtended.Default_LCD1602());
    }
}
