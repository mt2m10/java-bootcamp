package entry.cast;

public class AutoUpcasting {
    public static void main(String[] args) throws Exception {
        char myChar = 'a';
        int ascii = myChar;

        System.out.println("char -> int への自動キャスト");
        System.out.printf("%s -> %d %n", myChar, ascii);

        int castAscii = (int) myChar;

        System.out.println("char -> int へのアップキャスト");
        System.out.printf("%s -> %d %n", myChar, castAscii);
    }
}
