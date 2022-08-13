package entry.overflow;

public class Overflow {
    public static void main(String[] args) throws Exception {
        int maxInt = Integer.MAX_VALUE;
        System.out.printf("int 型の最大値 = %d%n", maxInt);

        maxInt += 1;
        System.out.printf("int 型の最大値 + 1 = %d%n", maxInt);
    }
}
