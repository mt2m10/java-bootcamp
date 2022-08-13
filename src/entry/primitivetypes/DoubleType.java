package entry.primitivetypes;

public class DoubleType {
    public static void main(String[] args) throws Exception {

        double minDouble = Double.MIN_VALUE;
        double maxDouble = Double.MAX_VALUE;

        System.out.printf("double 型の最小値 = %.1E%n", minDouble);
        System.out.printf("double 型の最大値 = %s%n", maxDouble);
    }
}
