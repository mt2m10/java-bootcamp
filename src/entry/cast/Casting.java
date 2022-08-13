package entry.cast;

public class Casting {
    public static void main(String[] args) throws Exception {
        double myDouble = 1.9;
        int downToInt = (int) myDouble;

        System.out.println("ダウンキャスト double -> int");
        System.out.printf("%s -> %d %n", myDouble, downToInt);

        float upToInt = (float) downToInt;

        System.out.println("アップキャスト int -> float");
        System.out.printf("%d -> %s %n", downToInt, upToInt);
    }
}
