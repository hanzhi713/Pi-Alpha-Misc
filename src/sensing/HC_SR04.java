package sensing;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import static com.pi4j.wiringpi.Gpio.delayMicroseconds;
/**
 * @author Hanzhi Zhou
 * */
public class HC_SR04 implements DisposableGpioDevice {
    public static final String NAME = "Ultrasonic Sensor";
    private final GpioPinDigitalOutput control;
    private final GpioPinDigitalInput data;
    private final GpioController gpio = GpioFactory.getInstance();

    private long t0, t1;

    public HC_SR04(Pin control, Pin input) {
        this.control = gpio.provisionDigitalOutputPin(control, "control pin", PinState.LOW);
        this.data = gpio.provisionDigitalInputPin(input, PinPullResistance.PULL_DOWN);
        data.addListener((GpioPinListenerDigital) e -> {
            if (e.getState().equals(PinState.HIGH))
                t0 = System.nanoTime();
            else
                t1 = System.nanoTime();
        });
    }

    public double getDistance() {

        // send signal to sensor
        // waiting for its response
        control.high();
        delayMicroseconds(50);
        control.low();

        // calculate distance
        return (t1 - t0) / 1000000.0 * 17;
    }

    @Override
    public void dispose() {
        gpio.unprovisionPin(control);
        gpio.unprovisionPin(data);
        gpio.shutdown();
    }
}
