package alpha;

import java.util.Arrays;

/**
 * @author Hanzhi Zhou
 */
public class AlphaActions {
    private final AlphaProtocol ap;
    private boolean acting = false;

    public AlphaActions(AlphaProtocol ap) {
        this.ap = ap;
    }

    public boolean isActing(){
        return acting;
    }

    public synchronized void caller(String emotion) {
        if (emotion == null)
            return;
        acting = true;
        switch (emotion) {
            case "surprise":
                surprise();
                break;
            case "anger":
                anger();
                break;
            case "happiness":
                happiness();
                break;
            case "neutral":
                neutral();
                break;
            case "disgust":
                disgust();
                break;
            case "fear":
                fear();
                break;
            case "sadness":
                sadness();
                break;
        }
        if (!Arrays.equals(ap.currentMotorRotations, AlphaProtocol.initialMotorPositions))
            ap.restoreMotorDefaultState();
        acting = false;
    }

    public synchronized void surprise() {
        ap.moveBackward(2);
        ap.moveMotor(new int[]{88, 18, 71, 87, 157, 104, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{88, 160, 165, 89, 26, 2, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{76, 153, 173, 75, 25, 5, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{103, 154, 173, 99, 25, 5, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{76, 153, 173, 75, 25, 5, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{103, 154, 173, 99, 25, 5, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{86, 21, 63, 91, 167, 104, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
    }

    public synchronized void anger() {
        ap.moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 91, 90, 116, 104, 70, 90}, 200, 0);
        ap.moveMotor(new int[]{90, 111, 90, 90, 99, 145, 86, 62, 44, 141, 89, 118, 96, 91, 65, 59}, 300, 460);
        ap.moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90}, 300, 460);
        ap.moveMotor(new int[]{90, 54, 39, 90, 62, 90, 67, 50, 69, 112, 118, 106, 156, 148, 56, 79}, 300, 460);
        ap.moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90}, 300, 460);
        ap.moveMotor(new int[]{0, 65, 67, 180, 111, 122, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90}, 500, 300);
        ap.moveMotor(new int[]{103, 164, 106, 105, 145, 114, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90}, 500, 200);
        ap.moveMotor(new int[]{180, 164, 122, 105, 145, 114, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90},500,0);
        ap.moveMotor(new int[]{130, 165, 123, 104, 145, 114, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{180, 165, 123, 104, 145, 114, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90},500, 200);
        ap.moveMotor(new int[]{94, 20, 65, 78, 155, 115, 90, 63, 76, 110, 90, 91, 116, 104, 70, 90},500, 0);
    }

    public synchronized void happiness() {
        ap.moveMotor(new int[]{88, 180, 90, 90, 0, 90, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 700, 0);
        ap.moveMotor(new int[]{88, 180, 90, 90, 0, 90, 92, 60, 74, 111, 91, 114, 118, 102, 70, 69}, 400, 0);
        ap.moveMotor(new int[]{88, 180, 90, 90, 0, 90, 88, 59, 69, 116, 89, 88, 124, 110, 68, 88}, 200, 400);
        ap.moveMotor(new int[]{88, 154, 90, 90, 0, 58, 88, 59, 69, 116, 89, 88, 124, 110, 68, 88}, 200, 0);
        ap.moveMotor(new int[]{85, 18, 122, 88, 15, 99, 88, 59, 69, 116, 89, 88, 124, 110, 68, 88}, 200, 0);
        ap.moveMotor(new int[]{88, 154, 90, 90, 0, 58, 88, 59, 69, 116, 89, 88, 124, 110, 68, 88}, 200, 0);
        ap.moveMotor(new int[]{88, 154, 90, 90, 0, 58, 63, 71, 83, 110, 113, 94, 126, 117, 66, 82}, 400, 0);
        ap.moveMotor(new int[]{88, 154, 90, 90, 0, 58, 90, 69, 89, 107, 87, 91, 114, 92, 76, 88}, 200, 400);
        ap.moveMotor(new int[]{85, 180, 122, 88, 15, 99, 88, 59, 69, 116, 89, 88, 124, 110, 68, 88}, 200, 0);
        ap.moveMotor(new int[]{88, 154, 90, 90, 0, 58, 88, 59, 69, 116, 89, 88, 124, 110, 68, 88}, 200, 0);
        ap.moveMotor(new int[]{85, 180, 122, 88, 15, 99, 88, 59, 69, 116, 89, 88, 124, 110, 68, 88}, 200, 0);
    }

    public synchronized void neutral() {
        ap.moveMotor(new int[]{85, 31, 132, 92, 148, 36, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{83, 16, 136, 86, 106, 36, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{88, 54, 70, 90, 150, 71, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{91, 69, 121, 90, 157, 108, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{91, 18, 158, 87, 91, 145, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{81, 26, 53, 86, 98, 22, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{75, 39, 23, 96, 131, 167, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
    }

    public synchronized void disgust() {
        // implementation not shown
    }

    public synchronized void fear() {
        // implementation not shown
    }

    public synchronized void sadness() {
        ap.moveForward(2);
        ap.moveMotor(new int[]{37, 67, 58, 90, 135, 89, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90},500,0);
        ap.moveMotor(new int[]{1, 86, 45, 180, 86, 129, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90},500,0);
        ap.moveMotor(new int[]{31, 20, 35, 172, 150, 146, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90},500,1000);
        ap.moveMotor(new int[]{70, 40, 60, 180, 150, 160, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90},500,0);
        ap.moveMotor(new int[]{70, 40, 60, 180, 135, 101, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90},200, 500);
        ap.moveMotor(new int[]{78, 40, 71, 90, 44, 78, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 500, 0);
        ap.moveMotor(new int[]{78, 40, 71, 91, 2, 48, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 200, 0);
        ap.moveMotor(new int[]{78, 40, 71, 98, 61, 48, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90},200, 0);
        ap.moveMotor(new int[]{78, 40, 71, 94, 1, 48, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 200, 0);
        ap.moveMotor(new int[]{80, 40, 71, 96, 160, 114, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90}, 200, 0);
    }
}
