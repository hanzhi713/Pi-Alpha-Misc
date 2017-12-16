package music;

import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import sensing.I2CLCDExtended;

import java.util.Arrays;

public class Test {
    public static void main(String[] s) throws Exception {
        final I2CLCDExtended display = new I2CLCDExtended(4, 20, 1, 0x27);
        PlayMultiTracks.setScriptLength(3, 4);
        PlayMultiTracks.setBeatLength(7, 8);
        PlayMultiTracks p;
        p = new PlayMultiTracks(new Pin[]{RaspiPin.GPIO_00, RaspiPin.GPIO_01});
        p.play(new Music[]{Music.WHAT_ARE_WORDS_HIGH, Music.WHAT_ARE_WORDS_LOW}, display);
        p.dispose();
        Thread.sleep(1000);
        display.clear();
    }
}
