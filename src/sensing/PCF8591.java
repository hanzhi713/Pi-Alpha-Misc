package sensing;


import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

/**
 * @author Hanzhi Zhou
 * */
public class PCF8591 {
    public static final String NAME = "AD/DA Converter";
    private I2CDevice dev = null;
    private double voltage;

    public PCF8591(double voltage){
        this.voltage = voltage;
        I2CBus b = null;
        try {
            b = I2CFactory.getInstance(1);
            dev = b.getDevice(0x48);
        } catch (I2CFactory.UnsupportedBusNumberException | IOException e) {
            e.printStackTrace();
        }
    }

    public PCF8591(int address, int bus, double voltage) {
        this.voltage = voltage;
        I2CBus b = null;
        try {
            b = I2CFactory.getInstance(bus);
            dev = b.getDevice(address);
        } catch (I2CFactory.UnsupportedBusNumberException | IOException e) {
            e.printStackTrace();
        }
    }

    public int getData(int i) {
        int result = 0;
        try {
            byte currentRegister = (byte) (0x40 + i);
            dev.read(currentRegister);
            result = dev.read(currentRegister);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int[] getData() {
        if (dev != null) {
            int[] values = new int[4];
            byte currentRegister;
            for (int i = 0; i < 4; i++) {
                try {
                    currentRegister = (byte) (0x40 + i);
                    dev.read(currentRegister);
                    values[i] = dev.read(currentRegister);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return values;
        }
        return new int[]{0, 0, 0, 0};
    }

    public double getVoltage(int data){
        return data * voltage / 255.0;
    }

    public static double getTemperature(double voltage){
        return voltage * (5/10.24);
    }

    public void write(byte data){
        if (dev != null){
            try {
                dev.write(0x40, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
