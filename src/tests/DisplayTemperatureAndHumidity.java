package tests;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import sensing.*;

import java.util.Arrays;

import static com.pi4j.wiringpi.Gpio.delay;

public class DisplayTemperatureAndHumidity {
    public static void main(String[] arg) {
        F3461BH f = new F3461BH(
                new Pin[]{RaspiPin.GPIO_00, RaspiPin.GPIO_01, RaspiPin.GPIO_02, RaspiPin.GPIO_03},
                new Pin[]{RaspiPin.GPIO_21, RaspiPin.GPIO_22, RaspiPin.GPIO_23, RaspiPin.GPIO_24,
                        RaspiPin.GPIO_25, RaspiPin.GPIO_26, RaspiPin.GPIO_27, RaspiPin.GPIO_28}
        );
        f.showAll();
        delay(1000);
        f.clear();
        byte[] data = null;
        int temperature;
        int humidity;
        DHT11 sensor = new DHT11(RaspiPin.GPIO_29);
        while (true) {
            sensor.update();
            data = sensor.getData();
            System.out.println(Arrays.toString(data));
            temperature = (int) data[0];
            humidity = (int) data[1];
            f.displayDigits(new int[]{temperature / 10, temperature % 10, humidity / 10, humidity % 10});
            delay(5000);
        }
    }
}
