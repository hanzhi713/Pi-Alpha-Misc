package sensing;

import com.pi4j.component.Component;
import com.pi4j.component.sensor.DistanceSensor;
import com.pi4j.component.sensor.impl.GpioSensorComponent;
import com.pi4j.io.gpio.*;

import static com.pi4j.wiringpi.Gpio.delay;
import static com.pi4j.wiringpi.Gpio.delayMicroseconds;

/**
 * @author Hanzhi Zhou
 * Adapted from the C library
 * */
public class DHT11 implements DisposableGpioDevice{
    public static final String NAME = "Temperature and Humidity Sensor";
    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalMultipurpose sensor;
    private static final int TIMEOUT = 1000000;
    private byte[] bits = new byte[5];

    private byte temperature, humidity;

    public DHT11(Pin p) {
        sensor = gpio.provisionDigitalMultipurposePin(p, PinMode.DIGITAL_OUTPUT, PinPullResistance.PULL_DOWN);
    }

    public boolean update() {
        // INIT BUFFERVAR TO RECEIVE DATA
        short cnt = 7;
        short idx = 0;

        // EMPTY BUFFER  
        for (int i = 0; i < 5; i++)
            bits[i] = 0;

        // REQUEST SAMPLE
        sensor.setMode(PinMode.DIGITAL_OUTPUT);
        sensor.low();
        delay(20);
        sensor.high();
        delayMicroseconds(40);
        sensor.setMode(PinMode.DIGITAL_INPUT);

        // GET ACKNOWLEDGE or TIMEOUT  
        int loopCnt = TIMEOUT;
        while (sensor.isState(PinState.LOW))
            if (loopCnt-- == 0)
                return false; // Timeout

        loopCnt = TIMEOUT;
        while (sensor.isState(PinState.HIGH))
            if (loopCnt-- == 0)
                return false; // Time out

        // READ THE OUTPUT - 40 BITS => 5 BYTES  
        for (int i = 0; i < 40; i++) {
            loopCnt = TIMEOUT;
            while (sensor.isState(PinState.LOW))
                if (loopCnt-- == 0)
                    return false; // Time out

            long t = System.nanoTime() / 1000;

            loopCnt = TIMEOUT;
            while (sensor.isState(PinState.HIGH))
                if (loopCnt-- == 0)
                    return false; // Time out

            if ((System.nanoTime() / 1000 - t) > 40)
                bits[idx] |= (1 << cnt);
            if (cnt == 0)   // next byte?  
            {
                cnt = 7;
                idx++;
            } else
                cnt--;
        }
        byte sum = (byte) (bits[0] + bits[2]); // bits[1] && bits[3] both 0
        if (bits[4] != sum)
            return false; // Check sum error
        temperature = bits[2];
        humidity = bits[0];
        return true;
    }

    public byte getTemperature() {
        return temperature;
    }

    public byte getHumidity(){
        return humidity;
    }

    public byte[] getData(){
        return new byte[]{temperature, humidity};
    }

    @Override
    public void dispose() {
        gpio.unprovisionPin(sensor);
        gpio.shutdown();
    }
}
