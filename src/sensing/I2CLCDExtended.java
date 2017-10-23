package sensing;

import com.pi4j.component.lcd.impl.I2CLcdDisplay;

import static com.pi4j.wiringpi.Gpio.delay;

/**
 * @author Hanzhi (Tom) Zhou
 */
public class I2CLCDExtended extends I2CLcdDisplay {

    public static I2CLCDExtended Default_LCD2004(){
        try {
            return new I2CLCDExtended(4, 20, 1, 0x27);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static I2CLCDExtended Default_LCD1602(){
        try {
            return new I2CLCDExtended(2, 16, 1, 0x3f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int cursorCol = 0;
    private int cursorRow = 0;

    public I2CLCDExtended(int rows, int cols, int bus, int addr) throws Exception {
        super(rows, cols, bus, addr, 3, 0, 1, 2, 7, 6, 5, 4);
    }

    @Override
    public void clear() {
        super.clear();
        cursorCol = 0;
        cursorRow = 0;
    }

    @Override
    public void setCursorPosition(int r, int c) {
        super.setCursorPosition(r, c);
        cursorRow = r;
        cursorCol = c;
    }

    @Override
    public void setCursorHome() {
        super.setCursorHome();
        cursorCol = 0;
        cursorRow = 0;
    }

    private void shiftCursor() {
        cursorCol++;
        if (cursorCol >= columns) {
            cursorCol = 0;
            cursorRow++;
            if (cursorRow >= rows) {
                cursorRow = 0;
            }
            clear(cursorRow);
        }
    }

    public void write(String[] lines){
        for (int i = 0; i < lines.length; i++) {
            write(i, lines[i]);
        }
    }

    public void animatedWrite(String[] lines, int charDelay, int lineDelay) {
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                write(i, j, lines[i].charAt(j));
                delay(charDelay);
            }
            delay(lineDelay);
        }
    }

    public void writeln() {
        cursorRow++;
        cursorCol = 0;
        if (cursorRow >= rows)
            cursorRow = 0;
    }

    public void animatedWrite(String word, int duration) {
        int delay = duration / (word.length() + 1);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == '\n') {
                writeln();
                clear(cursorRow);
            } else {
                write(cursorRow, cursorCol, word.charAt(i));
                shiftCursor();
            }
            delay(delay);
        }
    }

}
