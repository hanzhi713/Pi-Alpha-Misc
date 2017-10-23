package sensing;

import com.pi4j.io.gpio.*;

import java.util.HashMap;

import static com.pi4j.wiringpi.Gpio.delay;

public class F3461BH implements DisposableGpioDevice {
    public static final String NAME = "4 Digits 8 Segments Display";
    private static final HashMap<Integer, boolean[]> Digit_Pin = new HashMap<>();
    private static final HashMap<Character, boolean[]> Char_Pin = new HashMap<>();
    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalOutput[] anodes = new GpioPinDigitalOutput[4];
    private final GpioPinDigitalOutput[] cathodes = new GpioPinDigitalOutput[8];
    private volatile boolean flag = false;

    static {
        Digit_Pin.put(0, new boolean[]{true, true, true, true, true, true, false});
        Digit_Pin.put(1, new boolean[]{false, false, false, false, true, true, false});
        Digit_Pin.put(2, new boolean[]{true, true, false, true, true, false, true});
        Digit_Pin.put(3, new boolean[]{true, true, true, true, false, false, true});
        Digit_Pin.put(4, new boolean[]{false, true, true, false, false, true, true});
        Digit_Pin.put(5, new boolean[]{true, false, true, true, false, true, true});
        Digit_Pin.put(6, new boolean[]{true, false, true, true, true, true, true});
        Digit_Pin.put(7, new boolean[]{true, true, true, false, false, false, false});
        Digit_Pin.put(8, new boolean[]{true, true, true, true, true, true, true});
        Char_Pin.put('A', new boolean[]{true, true, true, false, true, true, true});
        Char_Pin.put('B', new boolean[]{false, false, true, true, true, true, true});
        Char_Pin.put('C', new boolean[]{true, false, false, true, true, true, false});
        Char_Pin.put('D', new boolean[]{false, true, true, true, true, false, true});
        Char_Pin.put('E', new boolean[]{true, false, false, true, true, true, true});
        Char_Pin.put('F', new boolean[]{true, false, false, false, true, true, true});
        Char_Pin.put('G', new boolean[]{true, false, true, true, true, true, true});
        Char_Pin.put('H', new boolean[]{false, true, true, false, true, true, true});
        Char_Pin.put('I', new boolean[]{true, true, true, true, false, false, false});
        Char_Pin.put('J', new boolean[]{false, true, true, true, false, false, false});
        Char_Pin.put('K', new boolean[]{false, false, false, true, true, true, true});
        Char_Pin.put('L', new boolean[]{false, false, false, true, true, true, false});
        Char_Pin.put('M', new boolean[]{});
        Char_Pin.put('N', new boolean[]{true, true, true, false, true, true, false});
        Char_Pin.put('O', new boolean[]{false, false, true, true, true, false, true});
        Char_Pin.put('P', new boolean[]{true, true, false, false, true, true, true});
        Char_Pin.put('Q', new boolean[]{true, true, true, false, false, true, true});
        Char_Pin.put('R', new boolean[]{true, true, false, true, true, true, true});
        Char_Pin.put('S', new boolean[]{true, false, true, true, false, true, true});
        Char_Pin.put('T', new boolean[]{true, true, true, false, false, false, false});
        Char_Pin.put('U', new boolean[]{false, true, true, true, true, true, false});
        Char_Pin.put('V', new boolean[]{false, false, true, true, true, false, false});
        Char_Pin.put('W', new boolean[]{});
        Char_Pin.put('X', new boolean[]{});
        Char_Pin.put('Y', new boolean[]{false, true, true, false, false, true, true});
        Char_Pin.put('Z', new boolean[]{true, true, false, true, true, false, true});
    }

    public F3461BH(Pin[] anodes, Pin[] cathodes) {
        if (anodes.length != 4 || cathodes.length != 8)
            throw new IllegalArgumentException("Incorrect pin number!");
        for (int i = 0; i < anodes.length; i++) {
            this.anodes[i] = gpio.provisionDigitalOutputPin(anodes[i], PinState.LOW);
        }
        for (int i = 0; i < cathodes.length; i++) {
            this.cathodes[i] = gpio.provisionDigitalOutputPin(cathodes[i], PinState.HIGH);
        }
    }

    public void showAll() {
        for (GpioPinDigitalOutput anode : anodes)
            anode.high();
        for (GpioPinDigitalOutput cathode : cathodes)
            cathode.low();
    }

    public void clear() {
        flag = false;
        clearAnodes();
        clearCathodes();
    }

    public void clearAnodes() {
        for (GpioPinDigitalOutput anode : anodes)
            anode.low();
    }

    public void clearCathodes() {
        for (GpioPinDigitalOutput cathode : cathodes)
            cathode.high();
    }

    public void displayDigit(int digit, int number) {
        clearAnodes();
        anodes[digit].high();
        boolean[] v = Digit_Pin.get(number);
        for (int i = 0; i < v.length; i++) {
            if (v[i])
                cathodes[i].low();
            else
                cathodes[i].high();
        }
    }

    public void displayDigit(int digit, char chr) {
        clearAnodes();
        anodes[digit].high();
        boolean[] v = Char_Pin.get(chr);
        for (int i = 0; i < v.length; i++) {
            if (v[i])
                cathodes[i].low();
            else
                cathodes[i].high();
        }
    }

    public void displayDigits(int[] digits) {
        if (digits.length != 4)
            throw new IllegalArgumentException("Should be exactly 4 digits!");
        flag = false;
        delay(50);
        new Thread(() -> {
            flag = true;
            while (flag) {
                for (int i = 0; i < digits.length; i++) {
                    displayDigit(i, digits[i]);
                    delay(1);
                }
            }
        }).start();
    }

    public void displayDigits(char[] chrs) {
        if (chrs.length != 4)
            throw new IllegalArgumentException("Should be exactly 4 digits!");
        flag = false;
        delay(50);
        new Thread(() -> {
            flag = true;
            while (flag) {
                for (int i = 0; i < chrs.length; i++) {
                    displayDigit(i, chrs[i]);
                    delay(1);
                }
            }
        }).start();
    }

    @Override
    public void dispose() {
        for (GpioPinDigitalOutput gpioPort : cathodes)
            gpio.unprovisionPin(gpioPort);
        for (GpioPinDigitalOutput gpioPort : anodes)
            gpio.unprovisionPin(gpioPort);
        gpio.shutdown();
    }
}
