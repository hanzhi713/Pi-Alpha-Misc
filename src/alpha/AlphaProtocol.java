package alpha;

import java.util.Map;
/**
 * @author Hanzhi Zhou
 * */
public abstract class AlphaProtocol {

    // Default motor rotational values
    // used to reset the robot
    public static final int[] initialMotorPositions = new int[]{
            90, 90, 90, 90, 90, 90, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90
    };

    // used to record the current motor rotational values
    public int[] currentMotorRotations = new int[]{
            90, 90, 90, 90, 90, 90, 90, 60, 76, 110, 90, 90, 120, 104, 70, 90
    };

    // the command tail of the bluetooth serial
    public static final int COMMAND_TAIL = 0xed;


    /**
     * reset motors' rotational values
     */
    public void restoreMotorDefaultState() {
        moveMotor(initialMotorPositions, 1000, 0);
        sleep(1020);
    }

    /**
     * @param command The array of hexadecimal commands
     *                Add the head and tail for this command
     */
    public abstract void addHeadAndTail(int[] command);

    /**
     * @param commands The array of hexadecimal commands
     *                 Output commands to the serial
     */
    public abstract void outputSerial(int[] commands);


    public synchronized void moveForward(int steps) {
        for (int i = 0; i < steps; i++) {
            moveMotor(new int[]{54, 20, 67, 40, 162, 111, 75, 108, 104, 120, 103, 80, 90, 72, 78, 102}, 500, 0);
            moveMotor(new int[]{105, 31, 67, 65, 162, 111, 75, 58, 55, 122, 100, 80, 90, 72, 78, 102}, 240, 0);
            moveMotor(new int[]{105, 31, 67, 65, 162, 111, 75, 58, 55, 122, 100, 80, 90, 72, 78, 102}, 40, 0);
            moveMotor(new int[]{135, 20, 66, 120, 162, 123, 75, 61, 115, 68, 100, 80, 90, 72, 78, 102}, 240, 0);
            moveMotor(new int[]{135, 20, 66, 120, 162, 123, 75, 61, 115, 68, 100, 80, 90, 72, 78, 102}, 40, 0);
            moveMotor(new int[]{121, 20, 67, 85, 151, 110, 100, 90, 108, 100, 79, 105, 74, 69, 66, 79}, 500, 0);
            moveMotor(new int[]{105, 20, 67, 65, 162, 111, 98, 87, 108, 100, 79, 105, 123, 125, 57, 80}, 240, 0);
            moveMotor(new int[]{105, 20, 67, 65, 162, 111, 98, 87, 108, 100, 79, 105, 123, 125, 57, 80}, 40, 0);
            moveMotor(new int[]{105, 20, 67, 65, 162, 111, 100, 87, 108, 100, 79, 104, 114, 72, 105, 80}, 240, 0);
            moveMotor(new int[]{105, 20, 67, 65, 162, 111, 100, 87, 108, 100, 79, 104, 114, 72, 105, 80}, 40, 0);
        }
    }

    public synchronized void moveBackward(int steps) {
        for (int i = 0; i < steps; i++) {
            moveMotor(new int[]{105, 20, 67, 65, 162, 111, 100, 87, 108, 100, 78, 107, 114, 65, 108, 76}, 500, 0);
            moveMotor(new int[]{105, 20, 67, 65, 162, 111, 100, 87, 108, 100, 78, 104, 110, 125, 50, 80}, 240, 0);
            moveMotor(new int[]{121, 20, 67, 85, 162, 111, 100, 90, 108, 102, 78, 105, 84, 94, 53, 76}, 240, 0);
            moveMotor(new int[]{135, 35, 66, 120, 162, 123, 75, 68, 123, 70, 102, 80, 90, 72, 78, 104}, 500, 0);
            moveMotor(new int[]{105, 30, 67, 65, 162, 111, 75, 70, 55, 135, 100, 80, 90, 72, 78, 104}, 240, 0);
            moveMotor(new int[]{54, 32, 67, 40, 162 ,111, 75, 96, 90, 123, 106, 80, 90, 72, 78, 104}, 240, 0);
        }
    }

    public synchronized void turnLeft(int steps) {
        for (int i = 0; i < steps; i++) {
            moveMotor(new int[]{90, 38, 40, 90, 144, 150, 87, 57, 74, 110, 90, 90, 123, 106, 72, 90}, 100, 0);
            moveMotor(new int[]{103, 55, 55, 67, 124, 125, 72, 66, 102, 84, 107, 110, 68, 71, 60, 69}, 100, 0);
            moveMotor(new int[]{90, 39, 40, 90, 144, 150, 88, 57, 72, 110, 90, 90, 123, 110, 70, 88}, 200, 0);
            moveMotor(new int[]{103, 55, 55, 67, 124, 125, 72, 66, 102, 84, 107, 110, 68, 71, 60, 69}, 100, 0);
            moveMotor(new int[]{90, 39, 40, 90, 144, 150, 88, 57, 72, 110, 90, 90, 123, 110, 70, 88}, 200, 0);
        }
    }

    public synchronized void turnRight(int steps) {
        for (int i = 0; i < steps; i++) {
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 87, 57, 72, 110, 90, 90, 121, 106, 72, 90}, 100, 0);
            moveMotor(new int[]{103, 54, 55, 67, 124, 125, 69, 92, 93, 120, 109, 105, 122, 72, 108, 72}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 87, 57, 72, 110, 90, 90, 123, 106, 72, 90}, 200, 0);
            moveMotor(new int[]{103, 54, 55, 67, 124, 125, 69, 92, 93, 120, 109, 105, 122, 72, 108, 72}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 87, 57, 72, 110, 90, 90, 123, 106, 72, 90}, 200, 0);
        }
    }

    public synchronized void moveLeft(int steps) {
        for (int i = 0; i < steps; i++) {
            moveMotor(new int[]{90, 52, 40, 90, 144, 150, 64, 63, 72, 112, 104, 89, 127, 125, 63, 90}, 100, 0);
            moveMotor(new int[]{90, 52, 40, 90, 144, 150, 64, 63, 72, 112, 104, 89, 127, 125, 63, 90}, 200, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 200, 0);
            moveMotor(new int[]{90, 52, 40, 90, 144, 150, 64, 63, 72, 112, 104, 89, 127, 125, 63, 90}, 100, 0);
            moveMotor(new int[]{90, 52, 40, 90, 144, 150, 64, 63, 72, 112, 104, 89, 127, 125, 63, 90}, 200, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 200, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 200, 0);
        }
    }

    public synchronized void moveRight(int steps) {
        for (int i = 0; i < steps; i++) {
            moveMotor(new int[]{90, 40, 40, 90, 136, 150, 90, 53, 58, 117, 90, 116, 116, 98, 76, 76}, 100, 0);
            moveMotor(new int[]{90, 40, 40, 90, 136, 150, 90, 53, 58, 117, 90, 116, 116, 98, 76, 76}, 200, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 200, 0);
            moveMotor(new int[]{90, 40, 40, 90, 136, 150, 90, 53, 58, 117, 90, 116, 116, 98, 76, 76}, 100, 0);
            moveMotor(new int[]{90, 40, 40, 90, 136, 150, 90, 53, 58, 117, 90, 116, 116, 98, 76, 76}, 200, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 200, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 100, 0);
            moveMotor(new int[]{90, 30, 40, 90, 144, 150, 90, 63, 76, 110, 90, 91, 117, 104, 70, 90}, 200, 0);
        }
    }

    /**
     * set the degree of the motor with key (motorID) A to its value B
     * remain other motors' rotational value unchanged
     *
     * @param degrees The motorID → degree map.
     * @param time    the rotation time
     * @param delay   delay for the next action
     */
    public synchronized void moveMotor(Map<Integer, Integer> degrees, int time, int delay) {
        for (int key : degrees.keySet())
            currentMotorRotations[key - 1] = degrees.get(key);
        moveMotor(currentMotorRotations, time, delay);
    }

    /**
     * set the rotational values of all the motors
     *
     * @param degrees The degrees for motors numbering from 1 to 16.
     * @param time    the rotation time
     * @param delay   delay for the next action
     */
    public  abstract void moveMotor(int[] degrees, int time, int delay);

    /**
     * set the rotational value for a given motor
     *
     * @param motorID the id of the motor to be controlled
     * @param degree  the degree of rotation, between 0 to 180
     * @param time    the time of rotation, unit: milliseconds
     * @param delay   the delay after the action, unit: milliseconds
     */
    public abstract void moveMotor(int motorID, int degree, int time, int delay);

    /**
     * Add the check code for the bluetooth serial command
     *
     * @param command The serial command
     */
    public void check(int[] command) {
        int sum = 0;
        for (int i = 2; i < command.length - 2; i++)
            sum += command[i];
        command[command.length - 2] = sum % 0x100;
    }

    /**
     * sleep the main thread
     *
     * @param ms time in milliseconds
     */
    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * output the hexadecimal code for a command sent via serial
     *
     * @param command the command which takes integer form
     */
    public void outputHexCode(int[] command) {
        for (int a : command)
            System.out.printf("%02X ", a);
        System.out.println();
    }
}
