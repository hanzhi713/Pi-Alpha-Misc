package alpha;

import org.hid4java.*;

import java.io.IOException;

/**
 * @author Hanzhi Zhou
 * */
public class PCProtocol extends AlphaProtocol {

    private static final int HEAD1 = 0xfb;
    private static final int HEAD2 = 0xbf;
    private HidDevice robot = null;

    public PCProtocol(int vendorID, int productID) {

        // HID Service configuration
        HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
        hidServicesSpecification.setAutoShutdown(true);
        hidServicesSpecification.setScanInterval(250);
        hidServicesSpecification.setPauseInterval(1000);
        hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

        // Get HID services using custom specification
        HidServices hidServices = HidManager.getHidServices(hidServicesSpecification);

        // Start the services
        System.out.println("Starting HID services.");
        hidServices.start();

        // Provide a list of attached devices
        System.out.println("Enumerating attached devices...");
        for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
            System.out.println(hidDevice);
            if (hidDevice.getProductId() == productID && hidDevice.getVendorId() == vendorID) {
                robot = hidDevice;
                break;
            }
        }

        if (robot == null)
            throw new IllegalArgumentException("Robot not found! Please make sure that the vendor and product id that you entered are correct!");
        else{
            if (!robot.isOpen())
                robot.open();
        }
    }

    public synchronized void moveMotor(int[] degrees, int time, int delay) {
        int len = 17 + degrees.length;
        int[] command = new int[len];// 2+1+1+1+2+1+1
        addHeadAndTail(command);
        command[2] = 0x01;
        System.arraycopy(degrees, 0, command, 8, degrees.length);
        command[len - 5] = time / 20;
        delay = delay / 20;
        if (delay > 0xff) {
            command[len - 3] = 0xff;
            command[len - 4] = delay - 0xff;
        } else {
            command[len - 3] = delay;
        }
        check(command);
        outputSerial(command);
//        outputHexCode(command);
        currentMotorRotations = degrees.clone();
        sleep(time + delay * 20);
    }

    public synchronized void moveMotor(int motorID, int degree, int time, int delay) {
        currentMotorRotations[motorID - 1] = degree;
        moveMotor(currentMotorRotations, time, delay);
    }

    public void outputSerial(int[] command) {
        byte[] msg = new byte[command.length];
        for (int i = 0; i < command.length; i++)
            msg[i] = (byte) command[i];
        int val = robot.write(msg, 64, (byte) 0x00);

//        if (val >= 0) {
//            System.out.println("> [" + val + "]");
//        } else {
//            System.err.println(robot.getLastErrorMessage());
//        }

//        // Prepare to read a single data packet
//        boolean moreData = true;
//        while (moreData) {
//            byte data[] = new byte[64];
//            // This method will now block for 500ms or until data is read
//            val = robot.read(data, 500);
//            switch (val) {
//                case -1:
//                    System.err.println(robot.getLastErrorMessage());
//                    break;
//                case 0:
//                    moreData = false;
//                    break;
//                default:
//                    System.out.print("< [");
//                    for (byte b : data) {
//                        System.out.printf(" %02x", b);
//                    }
//                    System.out.println("]");
//                    break;
//            }
//        }
    }

    public void addHeadAndTail(int[] command) {
        command[0] = HEAD1;
        command[1] = HEAD2;
        command[command.length - 1] = COMMAND_TAIL;
    }


}
