package music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hanzhi (Tom) Zhou
 */
public class Music {
    // the frequency table calculated according to the principle of twelve-tone temperament
    // the base tone, A4, is set as 440Hz
    public static final int[][] TONES = new int[][]{
            {16, 17, 18, 19, 21, 22, 23, 24, 26, 28, 29, 31},
            {33, 35, 37, 39, 41, 44, 46, 49, 52, 55, 58, 62},
            {65, 69, 73, 78, 82, 87, 92, 98, 104, 110, 117, 123},
            {131, 139, 147, 156, 165, 175, 185, 196, 208, 220, 233, 247},
            {262, 277, 294, 311, 330, 349, 370, 392, 415, 440, 466, 494},
            {523, 554, 587, 622, 659, 698, 740, 784, 831, 880, 932, 988},
            {1046, 1109, 1175, 1245, 1319, 1397, 1480, 1568, 1661, 1760, 1865, 1976},
            {2093, 2217, 2349, 2489, 2637, 2794, 2960, 3136, 3322, 3520, 3729, 3951},
            {4186, 4435, 4699, 4978, 5274, 5588, 5920, 6272, 6645, 7040, 7459, 7902}
    };
    public static final Map<Integer, Integer> TEMPERAMENT = new HashMap<>();
    public static final Map<String, Integer> MAJORS = new HashMap<>();
    private static String major = "C";

    public static void setMajor(String major) {
        Music.major = major;
    }

    static {
        // map the twelve-tone temperament to the octave
        TEMPERAMENT.put(1, 0);
        TEMPERAMENT.put(2, 2);
        TEMPERAMENT.put(3, 4);
        TEMPERAMENT.put(4, 5);
        TEMPERAMENT.put(5, 7);
        TEMPERAMENT.put(6, 9);
        TEMPERAMENT.put(7, 11);

        MAJORS.put("C", 0);
        MAJORS.put("D", 1);
        MAJORS.put("E", 2);
        MAJORS.put("F", 3);
        MAJORS.put("G", 4);
        MAJORS.put("A", 5);
        MAJORS.put("B", 6);
    }

    /**
     * @param note The index of note at the fifth octave
     * @return Frequency
     */
    public static int t(int note) {
        return t(note, 0, 0);
    }

    /**
     * @param note   The index of note at the given octave
     * @param octave +1 means the sixth, -1 means the fourth, 0 is the fifth octave
     */
    public static int t(int note, int octave) {
        return t(note, octave, 0);
    }


    public static int t(int note, int octave, int highLow) {
        octave = octave + 4 + (MAJORS.get(major) + note - 1) / 7;
        note = (MAJORS.get(major) + note) % 7;
        return TONES[octave][TEMPERAMENT.get(note == 0 ? 7 : note) + highLow];
    }

    public static int sum(int[] a) {
        int result = 0;
        for (int b : a)
            result += b;
        return result;
    }

    public static final Music HAPPY_BIRTHDAY;
    public static final Music SONG_OF_JOY;
    public static final Music MARCH_OF_THE_VOLUNTEERS;
    public static final Music WHAT_ARE_WORDS_HIGH;
    public static final Music WHAT_ARE_WORDS_NOTES;
    public static final Music WHAT_ARE_WORDS_LOW;

    static {
        setMajor("C");
        HAPPY_BIRTHDAY = new Music(
                "Happy Birthday", 150,
                new int[]{
                        t(5), t(5), t(6), t(5), t(1, 1, 0), t(7), 0,
                        t(5), t(5), t(6), t(5), t(2, 1, 0), t(1, 1, 0), 0,
                        t(5), t(5), t(5, 1), t(3, 1), t(1, 1), t(7), t(6),
                        t(4, 1, 0), t(4, 1), t(3, 1), t(1, 1), t(2, 1), t(1, 1),

                        t(5), t(5), t(6), t(5), t(1, 1, 0), t(7), 0,
                        t(5), t(5), t(6), t(5), t(2, 1, 0), t(1, 1, 0), 0,
                        t(5), t(5), t(5, 1), t(3, 1), t(1, 1), t(7), t(6),
                        t(4, 1, 0), t(4, 1), t(3, 1), t(1, 1), t(2, 1), t(1, 1),
                },
                new int[]{
                        2, 2, 4, 4, 4, 4, 4,
                        2, 2, 4, 4, 4, 4, 4,
                        2, 2, 4, 4, 4, 4, 4,
                        2, 2, 4, 4, 4, 8,

                        2, 2, 4, 4, 4, 4, 4,
                        2, 2, 4, 4, 4, 4, 4,
                        2, 2, 4, 4, 4, 4, 4,
                        2, 2, 4, 4, 4, 8,
                },
                new String[]{
                        "Ha", "ppy ", "birth", "day ", "to\n", "you", "!\n",
                        "Ha", "ppy ", "birth", "day ", "to\n", "you", "!\n",
                        "Ha", "ppy ", "birth", "day ", "to\n", "you", "!\n",
                        "Ha", "ppy ", "birth", "day ", "to\n", "you!\n",

                        "Ha", "ppy ", "birth", "day ", "to\n", "you", "!\n",
                        "Ha", "ppy ", "birth", "day ", "to\n", "you", "!\n",
                        "Ha", "ppy ", "birth", "day ", "to\n", "you", "!\n",
                        "Ha", "ppy ", "birth", "day ", "to\n", "you!",
                }
        );
        setMajor("D");
        SONG_OF_JOY = new Music(
                "Song of Joy", 60,
                new int[]{
                        t(3), t(3), t(4), t(5), t(5), t(4), t(3), t(2), t(1), t(1), t(2), t(3), t(3), t(2), t(2), 0,
                        t(3), t(3), t(4), t(5), t(5), t(4), t(3), t(2), t(1), t(1), t(2), t(3), t(2), t(1), t(1), 0,
                        //x1
                        t(2), t(2), t(3), t(1), t(2), t(3), t(4), t(3), t(1), t(2), t(3), t(4), t(3), t(2), t(1), t(2), t(5), t(3),
                        t(3), t(3), t(4), t(5), t(5), t(4), t(3), t(4), t(2), t(1), t(1), t(2), t(3), t(2), t(1), t(1), 0,
                        //x2
                        t(2), t(2), t(3), t(1), t(2), t(3), t(4), t(3), t(1), t(2), t(3), t(4), t(3), t(2), t(1), t(2), t(5), t(3),
                        t(3), t(3), t(4), t(5), t(5), t(4), t(3), t(4), t(2), t(1), t(1), t(2), t(3), t(2), t(1), t(1), 0
                },
                new int[]{
                        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 2, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 2, 4, 4,

                        4, 4, 4, 4, 4, 2, 2, 4, 4, 4, 2, 2, 4, 4, 4, 4, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 2, 2, 4, 4, 4, 4, 6, 2, 4, 4,

                        4, 4, 4, 4, 4, 2, 2, 4, 4, 4, 2, 2, 4, 4, 4, 4, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 2, 2, 4, 4, 4, 4, 6, 2, 4, 4
                }
        );
        setMajor("G");
        MARCH_OF_THE_VOLUNTEERS = new Music(
                "March of the Volunteers", 50,
                new int[]{
                        t(1), t(3), t(5), t(5), t(6), t(5), t(3), t(1), t(5), t(5), t(5), t(3), t(1), t(5, -1), t(5, -1), t(5, -1), t(5, -1), t(5, -1), t(5, -1), t(1), 0, t(5, -1),
                        t(1), t(1), t(1), t(1), t(5, -1), t(6, -1), t(7, -1), t(1), t(1), 0, t(3), t(1), t(2), t(3), t(5), t(5),
                        t(3), t(3), t(1), t(3), t(5), t(3), t(2), t(2), 0, t(6), 0, t(5), 0, t(2), 0, t(3), 0,
                        t(5), t(3), 0, t(5), t(3), t(2), t(3), t(1), t(3), 0, t(5, -1), t(6, -1), t(1), t(1), t(3), t(3), t(5), t(5),
                        t(2), t(2), t(2), t(6, -1, 0), t(2), t(5, -1, 0), t(1), t(1), t(3), t(3), t(5),
                        t(1), t(3), t(5), t(5), t(6), t(5), t(3), t(1), t(5), t(5), t(5), t(3), 0, t(1), 0, t(5, -1), 0, t(1), 0,
                        t(3), t(1), t(5), t(5), t(5), t(3), 0, t(1), 0, t(5, -1), 0, t(1), 0, t(5, -1), 0, t(1), 0, t(5, -1), 0, t(1), 0, t(1), 0, 0
                },
                new int[]{
                        9, 3, 6, 6, 12, 12, 9, 3, 4, 4, 4, 12, 12, 4, 4, 4, 4, 4, 4, 12, 6, 6,
                        18, 6, 9, 3, 6, 3, 3, 12, 12, 6, 6, 6, 3, 3, 12, 12,
                        9, 3, 9, 3, 9, 3, 12, 18, 6, 9, 3, 9, 3, 9, 3, 9, 3,
                        6, 6, 6, 6, 6, 3, 3, 12, 12, 12, 9, 3, 6, 6, 9, 3, 6, 6,
                        6, 3, 3, 12, 18, 6, 18, 6, 18, 6, 24,
                        9, 3, 6, 6, 12, 12, 9, 3, 4, 4, 4, 6, 6, 6, 6, 9, 3, 9, 3,
                        9, 3, 4, 4, 4, 6, 6, 6, 6, 9, 3, 9, 3, 9, 3, 9, 3, 9, 3, 9, 3, 9, 3, 12
                }
        );
        setMajor("C");
        WHAT_ARE_WORDS_HIGH = new Music(
                "What Are Words", 240,
                new int[]{
                        // intro
                        t(2, 1), t(3, 1), t(2, 1), t(1, 1), t(6), t(1, 1), t(3, 1),
                        t(2, 1), t(2, 1), t(3, 1), t(2, 1), t(1, 1), t(5), t(3, 1), t(5, 1),
                        t(6), t(2, 1), t(3, 1), t(2, 1), t(1, 1), t(6), t(1, 1), t(3, 1),
                        t(2, 1), t(2, 1), t(1, 1), t(1, 1),
                        //verse
                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(2), t(3), t(2), t(1), t(5, -1), t(1), t(7, -1), t(6, -1), t(7, -1),
                        t(6, -1), t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(3),

                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(2), t(3), t(2), t(1), t(5, -1), t(1), t(7, -1),
                        t(6, -1), t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(1),

                        //chorus & repeat x1
                        0, t(3), t(2), t(1), t(1), t(7, -1), t(1), t(7, -1), t(1),
                        t(1), t(7, -1), t(7, -1), t(4, -1), t(5, -1), t(7, -1),
                        0, t(3), t(2), t(1), t(1), t(7, -1), t(1), t(7, -1), t(1),
                        t(1), t(7, -1), t(7, -1), t(1), t(5, -1),

                        0, t(3), t(2), t(1), t(1), t(7, -1), t(1), t(7, -1), t(1),
                        t(2), t(2), t(2), t(2), t(3), t(3), t(7, -1), t(5, -1),
                        t(1), t(5, -1), t(5, -1), t(6, -1),
                        t(5, -1), t(5, -1), t(5, -1), t(6, -1), t(4, -1),

                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(2), t(3), t(2), t(1), t(5, -1), t(1), t(7, -1), t(6, -1), t(7, -1),
                        t(6, -1), t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(3), t(2), t(3),

                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(3), t(2), t(3), t(2), t(3), t(5), t(6), t(3), t(4), t(2),
                        t(1), 0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(1),
                        // end repeat x1

                        //chorus & repeat x2
                        0, t(3), t(2), t(1), t(1), t(7, -1), t(1), t(7, -1), t(1),
                        t(1), t(7, -1), t(7, -1), t(4, -1), t(5, -1), t(7, -1),
                        0, t(3), t(2), t(1), t(1), t(7, -1), t(1), t(7, -1), t(1),
                        t(1), t(7, -1), t(7, -1), t(1), t(5, -1),

                        0, t(3), t(2), t(1), t(1), t(7, -1), t(1), t(7, -1), t(1),
                        t(2), t(2), t(2), t(2), t(3), t(3), t(7, -1), t(5, -1),
                        t(1), t(5, -1), t(5, -1), t(6, -1),
                        t(5, -1), t(5, -1), t(5, -1), t(6, -1), t(4, -1),

                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(2), t(3), t(2), t(1), t(5, -1), t(1), t(7, -1), t(6, -1), t(7, -1),
                        t(6, -1), t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(3), t(2), t(3),

                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(3), t(2), t(3), t(2), t(3), t(5), t(6), t(3), t(4), t(2),
                        t(1), 0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(1),
                        // end repeat x2

                        // the ending
                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(2), t(3), t(2), t(1), t(5, -1), t(1), t(7, -1), t(6, -1), t(7, -1),
                        t(6, -1), t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(3), t(2), t(3),

                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(2), t(3), t(2), t(1), t(5, -1), t(1), t(7, -1),
                        t(6, -1), 0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(1), t(1),

                        0, t(2), t(3), t(2), t(1), t(6, -1), t(1), t(3),
                        t(2), t(3), t(2), t(1), t(6, -1), t(5, -1), 0, t(1)
                },
                new int[]{
                        1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 2,
                        5, 2, 1, 9,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 1, 1, 1,
                        4, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 9,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 9,
                        // repeat x1
                        4, 2, 2, 2, 1, 1, 1, 1, 2,
                        2, 2, 2, 1, 2, 7,
                        4, 2, 2, 2, 1, 1, 1, 1, 2,
                        2, 2, 2, 1, 9,

                        4, 2, 2, 2, 1, 1, 1, 1, 2,
                        2, 2, 3, 1, 1, 3, 2, 1,
                        5, 2, 1, 9,
                        2, 1, 3, 1, 9,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 1, 1, 1,
                        4, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 2, 1, 6,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        2, 3, 1, 1, 1, 1, 3, 2, 1, 1, 1,
                        2, 2, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 9,
                        // end repeat x1
                        // repeat x2
                        4, 2, 2, 2, 1, 1, 1, 1, 2,
                        2, 2, 2, 1, 2, 7,
                        4, 2, 2, 2, 1, 1, 1, 1, 2,
                        2, 2, 2, 1, 9,

                        4, 2, 2, 2, 1, 1, 1, 1, 2,
                        2, 2, 3, 1, 1, 3, 2, 1,
                        5, 2, 1, 9,
                        2, 1, 3, 1, 9,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 1, 1, 1,
                        4, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 2, 1, 6,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        2, 3, 1, 1, 1, 1, 3, 2, 1, 1, 1,
                        2, 2, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 9,
                        // end repeat x2
                        // the end
                        4, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 1, 1, 1,
                        4, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 2, 1, 6,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        5, 1, 1, 1, 1, 3, 2, 2,
                        3, 2, 1, 1, 1, 1, 3, 2, 2,
                        7, 1, 9,

                        4, 1, 1, 1, 1, 3, 2, 2,
                        2, 1, 2, 2, 1, 1, 4, 12
                },
                new String[]{
                        "What ", "Are ", "Words", "\n",
                        "By ", "Chris ", "Medina", "\n",
                        "Rewritten ", "as ", "Java\n", "By ", "Tom ", "Zhou", "", "", "", "", "",
                        "\n", "", "\n", "", "\n", "", "\n", "\n",

                        "", "A", "ny", "where ", "you ", "are ", "I ", "am ",
                        "near, ", "a", "ny", "where ", "you ", "go ", "I'll ", "be ", "", "there. ",
                        "", "A", "ny", "time ", "you, ", "whis", "per ", "my ",
                        "name ", "you'll ", "see. ",

                        "", "E", "very ", "sin", "gle ", "pro", "mise ", "I'll ",
                        "keep, ", "'cause ", "what ", "kind ", "of ", "guy ", "would ", "I ",
                        "be? ", "If ", "I ", "was ", "to ", "leave ", "when ", "you ",
                        "need ", "me ", "most. ",

                        // repeat x1
                        "", "What ", "are ", "words ", "if ", "you ", "rea", "lly ", "don't ",
                        "mean ", "them ", "when ", "you ", "say ", "them? ",
                        "", "What ", "are ", "words ", "if ", "they're ", "on", "ly ", "for ",
                        "good ", "times ", "then ", "they're ", "torn? ",

                        "", "When ", "it's ", "love, ", "yeah, ", "you ", "say ", "them ", "out ",
                        "loud, ", "those ", "words ", "they ", "ne", "ver ", "go ", "a",
                        "way. ", "They ", "live ", "on. ",
                        "E", "ven ", "when ", "we're ", "gone. ",

                        "", "And ", "I ", "know ", "an ", "an", "gel ", "was ",
                        "sent ", "just ", "for ", "me ", "and ", "I ", "know ", "I'm ", "", "m",
                        "eant. ", "To ", "be ", "where ", "I ", "am ", "and ", "I'm ",
                        "gon", "na ", "b", "e.", " ",

                        "", "Stan", "ding ", "right ", "be", "side ", "her ", "to",
                        "night ", "", "and ", "I'm ", "gon", "na ", "be ", "by ", "your ", "side.", " ",
                        "", "", "I ", "would ", "ne", "ver ", "leave ", "when ", "she ",
                        "needs ", "me ", "most. ",
//                        // end repeat x1
//                        // repeat x2
                        "", "What ", "are ", "words ", "if ", "you ", "rea", "lly ", "don't ",
                        "mean ", "them ", "when ", "you ", "say ", "them? ",
                        "", "What ", "are ", "words ", "if ", "they're ", "on", "ly ", "for ",
                        "good ", "times ", "then ", "they're ", "torn? ",

                        "", "When ", "it's ", "love, ", "yeah, ", "you ", "say ", "them ", "out ",
                        "loud, ", "those ", "words ", "they ", "ne", "ver ", "go ", "a",
                        "way. ", "They ", "live ", "on. ",
                        "E", "ven ", "when ", "we're ", "gone. ",

                        "", "And ", "I ", "know ", "an ", "an", "gel ", "was ",
                        "sent ", "just ", "for ", "me ", "and ", "I ", "know ", "I'm ", "", "m",
                        "eant. ", "To ", "be ", "where ", "I ", "am ", "and ", "I'm ",
                        "gon", "na ", "b", "e.", " ",

                        "", "Stan", "ding ", "right ", "be", "side ", "her ", "to",
                        "night ", "", "and ", "I'm ", "gon", "na ", "be ", "by ", "your ", "side.", " ",
                        "", "", "I ", "would ", "ne", "ver ", "leave ", "when ", "she ",
                        "needs ", "me ", "most. ",
                        // end repeat x2
                        // the end
                        "", "A", "ny", "where ", "you ", "are ", "I ", "am ",
                        "near, ", "a", "ny", "where ", "you ", "go ", "I'll ", "be ", "", "there. ",
                        "", "And ", "I'm ", "gon", "na ", "be ", "here ", "for",
                        "e", "ver ", "mor", "e", ". ",

                        "", "E", "very ", "sin", "gle ", "pro", "mise ", "I'll ",
                        "keep, ", "'cause ", "what ", "kind ", "of ", "guy ", "would ", "I ",
                        "be, ", "", "if ", "I ", "was ", "to ", "leave ", "when ", "you ",
                        "need ", "me ", "most. ",

                        "", "I'm ", "for", "e", "ver ", "kee", "ping ", "my ",
                        "an", "", "", "gel ", "", "", "", "close."
                }
        );
        WHAT_ARE_WORDS_NOTES = new Music(
                "What Are Words", 240,
                new int[]{
                        0,

                        0, t(4), t(4), 0, t(4), 0,
                        t(5), t(5), 0, t(5), 0, t(5), t(5), 0, t(5), 0,
                        t(5), t(5), 0, t(5), 0, t(4), t(4), 0, t(4), 0,
                        t(5), t(5), 0, t(5), 0, t(6), t(6), 0, t(6), 0,

                        t(6), t(6), 0, t(6), 0, t(4), t(4), 0, t(4), 0,
                        t(5), t(5), 0, t(5), 0, t(5), t(5),
                        t(6), t(3), 0,
                        0,

                        t(5), 0,

                        0, t(4), t(4), 0, t(4), 0,
                        t(5), t(5), 0, t(5), 0, t(5), t(5), 0, t(5), 0,
                        t(5), t(5), 0, t(5), 0, t(4), t(4), 0, t(4), 0,
                        t(5), t(5), 0, t(5), 0, t(6), t(6), 0, t(6), 0,

                        t(6), t(6), 0, t(6), 0, t(4), t(4), 0, t(4), 0,
                        t(5), t(5), 0, t(5), 0, t(5), t(5),
                        t(6), t(3), 0,
                        0,

                        t(5), 0,

                        0

                },
                new int[]{
                        188,

                        8, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,

                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 4, 4,
                        4, 4, 8,
                        16,

                        4, 124,

                        8, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,

                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 4, 4,
                        4, 4, 8,
                        16,

                        4, 124,

                        168
                }
        );
        WHAT_ARE_WORDS_LOW = new Music(
                "What Are Words", 240,
                new int[]{
                        0, t(4), t(1, 1), t(3, 1), 0,
                        t(5), t(2, 1), t(4, 1), 0, t(1), t(5), t(1, 1), 0,
                        t(4), t(1, 1), t(3, 1), 0, t(4), t(1, 1), t(3, 1), 0,
                        t(5), t(2, 1), t(5, 1), 0, t(1), t(5), t(1, 1), t(4, 1), t(5, 1),

                        t(3, 1), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(5, -1), t(1), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), 0, t(6, -1), t(3), t(6), t(3), t(1, 1), t(3), t(6), t(3),

                        t(6, -1), t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(5, -1), t(1), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), 0, t(1, -1), t(5, -1), t(1), t(5), t(1, 1), t(2, 1), t(3, 1), t(2, 1),

                        // repeat x1
                        t(1, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(2, 1), t(2, 1), 0, t(2, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,

                        t(1, 1), t(1, 1), 0, t(1, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(2, 1), t(2, 1),
                        t(1, 1), t(7), t(4, -1), t(1), t(4), t(1), t(6), t(1), t(4), t(1),
                        t(4, -1), t(1), t(4), t(1), t(6), t(1), t(4), t(1), t(5, -1), t(2), t(5), t(2), t(7), t(2), t(5), t(6),

                        t(2, 1), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(4, -1), t(1), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), 0, t(6, -1), t(3), t(6), t(3), t(1, 1), t(3), t(6), t(3),

                        t(6, -1), t(3), t(6), t(1, 1), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(5, -1), t(1), t(3), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), 0, t(1, -1), t(5, -1), t(1), t(5, -1), t(3), t(5, -1), t(1), t(5, -1),
                        //end repeat x1
                        // repeat x2
                        t(1, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(2, 1), t(2, 1), 0, t(2, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,

                        t(1, 1), t(1, 1), 0, t(1, 1), 0, t(1, 1), t(1, 1), 0, t(1, 1), 0,
                        t(2, 1), t(2, 1), 0, t(2, 1), 0, t(2, 1), t(2, 1),
                        t(1, 1), t(7), t(4, -1), t(1), t(4), t(1), t(6), t(1), t(4), t(1),
                        t(4, -1), t(1), t(4), t(1), t(6), t(1), t(4), t(1), t(5, -1), t(2), t(5), t(2), t(7), t(2), t(5), t(6),

                        t(2, 1), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(4, -1), t(1), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), 0, t(6, -1), t(3), t(6), t(3), t(1, 1), t(3), t(6), t(3),

                        t(6, -1), t(3), t(6), t(1, 1), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(5, -1), t(1), t(3), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), 0, t(1, -1), t(5, -1), t(1), t(5, -1), t(3), t(5, -1), t(1), t(5, -1),
                        //end repeat x2
                        //ending
                        0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(5, -1), t(1), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), t(5), t(2), t(6, -1), t(3), t(6), t(3), t(1, 1), t(3), t(6), t(3),

                        t(6, -1), t(3), t(6), t(1, 1), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(7), 0, t(1, -1), t(5, -1), t(1), 0,
                        t(4, -1), t(1), t(4), t(6), 0, t(4, -1), t(1), t(4), 0,
                        t(5, -1), t(2), t(5), t(2), t(7), t(2), t(5), t(2), t(1, -1), t(5, -1), t(1), t(2), t(3), t(5), t(1, 1), t(2, 1),

                        t(3, 1), 0, t(4),
                        t(5), 0, t(3), t(5), t(1), t(5, -1), t(1, -1)

                },
                new int[]{
                        4, 1, 1, 1, 5,
                        1, 1, 1, 5, 1, 1, 1, 5,
                        1, 1, 1, 5, 1, 1, 1, 5,
                        1, 1, 1, 5, 1, 1, 1, 2, 2,

                        5, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1,

                        8, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1,

                        //repeat x1
                        1, 7, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,

                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 4, 4,
                        4, 4, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        4, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 1, 4,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1,
                        // end repeat x1
                        //repeat x2
                        1, 7, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,

                        3, 1, 1, 1, 2, 3, 1, 1, 1, 2,
                        3, 1, 1, 1, 2, 4, 4,
                        4, 4, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        4, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 1, 4,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1,
                        // end repeat x2

                        // the ending..
                        8, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 4, 1, 1, 1, 5,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        4, 4, 8,
                        8, 8, 1, 1, 1, 1, 4
                }
        );
    }

    private String name;
    private int tempo = 200;
    private ArrayList<Integer> notes = new ArrayList<>();
    private ArrayList<Integer> beats = new ArrayList<>();
    private ArrayList<String> script = new ArrayList<>();

    public Music(String name) {
        this.name = name;
    }

    public Music(String name, int tempo) {
        this.name = name;
        this.tempo = tempo;
    }

    public Music(String name, int tempo, int[] notes, int[] beats) {
        this.name = name;
        this.tempo = tempo;
        add(notes, beats);
    }

    public Music(String name, int tempo, int[] notes, int[] beats, String[] script) {
        this.name = name;
        this.tempo = tempo;
        add(notes, beats, script);
    }

    public void add(int note, int beat) {
        notes.add(note);
        beats.add(beat);
    }

    public void add(int[] notes, int[] beats) {
        if (notes.length != beats.length)
            throw new IllegalArgumentException("Inconsistent length!");
        for (int i = 0; i < notes.length; i++) {
            this.notes.add(notes[i]);
            this.beats.add(beats[i]);
        }
    }

    public void add(int[] notes, int[] beats, String[] script) {
        if (notes.length != beats.length || notes.length != script.length)
            throw new IllegalArgumentException("Inconsistent length!");
        for (int i = 0; i < notes.length; i++) {
            this.notes.add(notes[i]);
            this.beats.add(beats[i]);
            this.script.add(script[i]);
        }
    }

    public void set(int pos, int note, int beat) {
        this.notes.set(pos, note);
        this.beats.set(pos, beat);
    }

    public void remove(int pos) {
        this.notes.remove(pos);
        this.beats.remove(pos);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int[] getNotes() {
        return notes.stream().mapToInt(i -> i).toArray();
    }

    public int[] getBeats() {
        return beats.stream().mapToInt(i -> i).toArray();
    }

    public String[] getScript() {
        return script.toArray(new String[]{});
    }

}
