package music;

public class LengthCheck {
    public static void main(String[] args) {
        int[] a = new int[]{
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
        };
        System.out.println(Music.sum(a));
    }
}
