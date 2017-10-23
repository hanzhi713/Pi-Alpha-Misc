package alpha;

import com.pi4j.io.serial.*;
import com.pi4j.util.Console;

import java.io.IOException;

/**
 * @author Hanzhi Zhou
 * */
public class BluetoothProtocol extends AlphaProtocol {

    // the command head of the bluetooth serial
    private static final int HEAD1 = 0xfb;
    private static final int HEAD2 = 0xbf;

    public final Serial serial;

    /**
     * @param portName the serial port to be used. i.e. "COM3"
     */
    public BluetoothProtocol(String portName){
        final Console console = new Console();
        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // create an instance of the serial communications class
        serial = SerialFactory.createInstance();

        // create and register the serial data listener
        serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {

                // NOTE! - It is extremely important to read the data received from the
                // serial port.  If it does not get read from the receive buffer, the
                // buffer will continue to grow and consume memory.

                // print out the data received to the console
                try {
                    console.println("[HEX DATA]   " + event.getHexByteString());
                    console.println("[ASCII DATA] " + event.getAsciiString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // create serial config object
            SerialConfig config = new SerialConfig();

            // set default serial settings (device, baud rate, flow control, etc)
            //
            // by default, use the DEFAULT com port on the Raspberry Pi (exposed on GPIO header)
            // NOTE: this utility method will determine the default serial port for the
            //       detected platform and board/model.  For all Raspberry Pi models
            //       except the 3B, it will return "/dev/ttyAMA0".  For Raspberry Pi
            //       model 3B may return "/dev/ttyS0" or "/dev/ttyAMA0" depending on
            //       environment configuration.
            config.device("/dev/rfcomm0")
                    .baud(Baud._9600)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);

            // open the default serial device/port with the configuration settings
            serial.open(config);

        }
        catch(IOException ex) {
            console.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
        }
    }

    public void handShake(){
        sendCommand(0x01, new int[]{0x00});
    }
    
    public void heartBeat(){
        sendCommand(0x08, new int[]{0x00});
    }
    
    public synchronized void moveMotor(int[] degrees, int time, int delay) {
        int len = 9 + degrees.length;
        int[] command = new int[len];// 2+1+1+1+2+1+1
        addHeadAndTail(command);
        command[2] = len - 1;
        command[3] = 0x23;
        System.arraycopy(degrees, 0, command, 4, degrees.length);
        command[len - 5] = time / 20;
        delay = delay / 20;
        if (delay > 0xff) {
            command[len - 3] = 0xff;
            command[len - 4] = delay - 0xff;
        } else {
            command[len - 3] = delay;
        }
        check(command);
        currentMotorRotations = degrees.clone();
        outputSerial(command);
        outputHexCode(command);
        sleep(time + delay * 20 + 20);
    }

    public synchronized void moveMotor(int motorID, int degree, int time, int delay) {
        int[] command = new int[11];
        addHeadAndTail(command);
        command[2] = 10; // 2 + 1 + 1 + 5 + 1;
        command[3] = 0x22;
        command[4] = motorID;
        command[5] = degree;
        command[6] = time / 20;
        delay = delay / 20;
        if (delay > 0xff) {
            command[7] = 0xff;
            command[8] = delay - 0xff;
        } else {
            command[7] = delay;
        }
        check(command);
        currentMotorRotations[motorID - 1] = degree;
        outputSerial(command);
        outputHexCode(command);
        sleep(time + delay * 20 + 20);
    }

    public void outputSerial(int[] command){
        try {
            for (int b : command) serial.write((byte) b);
            serial.writeln();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeadAndTail(int[] command) {
        command[0] = HEAD1;
        command[1] = HEAD2;
        command[command.length - 1] = COMMAND_TAIL;
    }

    /**
     * @param cmd The command in hexadecimal format
     * @param params The corresponding parameters for the command code cmd
     * send a command to the robot
     */
    public void sendCommand(int cmd, int[] params){
        int[] command = new int[6 + params.length];
        command[2] = command.length - 1;
        command[3] = cmd;
        addHeadAndTail(command);
        System.arraycopy(params, 0, command, 4, params.length);
        check(command);
        outputSerial(command);
        outputHexCode(command);
    }

//    public void moveForward(int steps){
//        for (int i = 0; i < steps ; i++){
//            sendCommand(0x03, new int[]{0x6d, 0x66});
//            sleep(2200);
//        }
//    }
//
//    public void moveBackward(int steps){
//        for (int i = 0; i < steps ; i++){
//            sendCommand(0x03, new int[]{0x6d, 0x62});
//            sleep(2200);
//        }
//
//    }
//
//    public void moveLeft(int steps){
//        for (int i = 0; i < steps ; i++){
//            sendCommand(0x03, new int[]{0x6d, 0x6c});
//            sleep(700);
//        }
//
//    }
//
//    public void moveRight(int steps){
//        for (int i = 0; i < steps ; i++){
//            sendCommand(0x03, new int[]{0x6d, 0x72});
//            sleep(700);
//        }
//
//    }
//
//    public void turnLeft(int steps){
//        for (int i = 0; i < steps ; i++){
//            sendCommand(0x03, new int[]{0x74, 0x6c});
//            sleep(1000);
//        }
//    }
//
//    public void turnRight(int steps){
//        for (int i = 0; i < steps ; i++){
//            sendCommand(0x03, new int[]{0x74, 0x72});
//            sleep(1000);
//        }
//    }

}
