package music;

import com.pi4j.io.gpio.*;
import sensing.I2CLCDExtended;

import static com.pi4j.wiringpi.Gpio.delay;
import static com.pi4j.wiringpi.Gpio.delayMicroseconds;

/**
 * @author Hanzhi (Tom) Zhou
 */
public class PlayMultiTracks {
    private static int scriptDenominator = 2;
    private static int scriptNumerator = 1;
    private static int beatNumerator = 8;
    private static int beatDenominator = 9;

    /**
     * set the length of sound within a beat
     *
     * @param beatNumerator   numerator
     * @param beatDenominator denominator
     */
    public static void setBeatLength(int beatNumerator, int beatDenominator) {
        PlayMultiTracks.beatNumerator = beatNumerator;
        PlayMultiTracks.beatDenominator = beatDenominator;
    }

    /**
     * set the duration of script animation within a beat
     *
     * @param scriptNumerator   numerator
     * @param scriptDenominator denominator
     */
    public static void setScriptLength(int scriptNumerator, int scriptDenominator) {
        PlayMultiTracks.scriptNumerator = scriptNumerator;
        PlayMultiTracks.scriptDenominator = scriptDenominator;
    }

    private static int[] getCumulativeBeats(int[] beats) {
        int[] result = new int[beats.length];
        result[0] = beats[0];
        for (int i = 1; i < beats.length; i++) {
            result[i] = beats[i] + result[i - 1];
        }
        return result;
    }

    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalOutput buzzers[];
    private final int trackNumber;

    public PlayMultiTracks(Pin[] pins) {
        trackNumber = pins.length;
        buzzers = new GpioPinDigitalOutput[trackNumber];
        for (int i = 0; i < trackNumber; i++) {
            buzzers[i] = gpio.provisionDigitalOutputPin(pins[i]);
            buzzers[i].setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        }
    }

    /**
     * @param tracks  Music tracks whose numbers of beats shall be equal
     * @param display the display used to show the script
     */
    public void play(Music[] tracks, I2CLCDExtended display) {
        // check if the number of tracks provided is the same as the track number preset
        if (tracks.length != trackNumber)
            throw new IllegalArgumentException("Inconsistent number of tracks!");

        // initialize the stopping flag for each track
        final boolean[] flag = new boolean[trackNumber];

        // initialize the positions of current notes among all tracks
        final int[] pos = new int[trackNumber];

        // get the notes for each track
        final int[][] notes = new int[trackNumber][];
        for (int i = 0; i < trackNumber; i++)
            notes[i] = tracks[i].getNotes();

        // get the beats for each track
        final int[][] beats = new int[trackNumber][];
        for (int i = 0; i < trackNumber; i++)
            beats[i] = tracks[i].getBeats();

        // sum up these beats for later use
        final int[][] cumulativeBeats = new int[trackNumber][];
        for (int i = 0; i < trackNumber; i++)
            cumulativeBeats[i] = getCumulativeBeats(beats[i]);

        // acquire tempo and check consistency
        final int[] tempos = new int[trackNumber];
        for (int i = 0; i < trackNumber; i++)
            tempos[i] = tracks[i].getTempo();
        if (!checkConsistency(tempos[0], tempos))
            throw new IllegalArgumentException("Warning: Inconsistent tempos!");

        // check consistency of beats among these tracks
        int[] sumBeats = new int[trackNumber];
        for (int i = 0; i < trackNumber; i++)
            sumBeats[i] = Music.sum(beats[i]);
        if (!checkConsistency(sumBeats[0], sumBeats))
            throw new IllegalArgumentException("Warning: Inconsistent number of beats!");

        // initialize threads for each track
        Thread[] trackThreads = new Thread[trackNumber];
        for (int i = 0; i < trackNumber; i++) {
            final int idx = i;
            trackThreads[i] = new Thread(() -> {
                if (!flag[idx])
                    playTone(buzzers[idx], notes[idx][pos[idx]], beats[idx][pos[idx]] * tempos[idx] * beatNumerator / beatDenominator);
            });
        }

        // initialize the thread for displaying the script, if needed
        // first thread for first track and this thread are synchronous
        Thread scriptThread = null;
        if (display != null) {
            final String[] script = tracks[0].getScript();
            scriptThread = new Thread(() -> {
                if (!flag[0])
                    display.animatedWrite(script[pos[0]], beats[0][pos[0]] * tempos[0] * scriptNumerator / scriptDenominator);
            });
        }

        // start the script thread and all other tracks (for the very first note)
        if (display != null)
            new Thread(scriptThread).start();
        for (int i = 0; i < trackNumber; i++) {
            new Thread(trackThreads[i]).start();
        }
        delay(tempos[0]);

        // if script needs to be displayed:
        if (display != null) {
            for (int i = 1; i < sumBeats[0]; i++) {

                // script is bonded with the first track
                // if next beat is met
                if (cumulativeBeats[0][pos[0]] == i) {
                    // if there are still beats remaining
                    if (pos[0] < beats[0].length - 1)
                        // shift the beat pointer
                        pos[0]++;
                    else
                        // else: raise the stopping flag for this track
                        flag[0] = true;
                    // play this node and show its script
                    new Thread(scriptThread).start();
                    new Thread(trackThreads[0]).start();
                }
                // same for all other threads
                for (int j = 1; j < trackNumber; j++) {
                    if (cumulativeBeats[j][pos[j]] == i) {
                        if (pos[j] < beats[j].length - 1)
                            pos[j]++;
                        else
                            flag[j] = true;
                        new Thread(trackThreads[j]).start();
                    }
                }
                delay(tempos[0]);
            }
            //case: no need to display script
        } else {
            for (int i = 1; i < sumBeats[0]; i++) {
                for (int j = 0; j < trackNumber; j++) {
                    if (cumulativeBeats[j][pos[j]] == i) {
                        if (pos[j] < beats[j].length - 1)
                            pos[j]++;
                        else
                            flag[j] = true;
                        new Thread(trackThreads[j]).start();
                    }
                }
                delay(tempos[0]);
            }
        }
    }

    /**
     * @param buzzer   a particular buzzer
     * @param tone     Frequency to play
     * @param duration unit: ms
     */
    public final void playTone(GpioPinDigitalOutput buzzer, int tone, int duration) {
        if (tone == 0) {
            delay(duration);
            return;
        }
        // get the interval
        // interval = speed of sound divided by the frequency
        tone = 345000 / tone;

        //set the duration
        duration = duration * 1000 / (2 * tone);

        //output the wave
        for (int i = 0; i < duration; i++) {
            buzzer.high();
            delayMicroseconds(tone);
            buzzer.low();
            delayMicroseconds(tone);
        }
    }

    public void dispose() {
        for (GpioPinDigitalOutput p : buzzers)
            gpio.unprovisionPin(p);
        gpio.shutdown();

    }

    /**
     * @param one  The first
     * @param many Other tracks
     */
    private boolean checkConsistency(int one, int[] many) {
        for (int aMany : many)
            if (aMany != one)
                return false;
        return true;
    }

    /**
     * @param beats The array of integers that represent the beats
     */
    private int getLastBeat(int[][] beats) {
        int[] arr = new int[beats.length];
        for (int i = 0; i < beats.length; i++) arr[i] = beats[i][beats[i].length - 1];
        return getMin(arr);
    }

    /**
     * @param arr The array of integer
     */
    private int getMin(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int i : arr) {
            if (i < min)
                min = i;
        }
        return min;
    }
}
