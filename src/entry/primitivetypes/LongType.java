package entry.primitivetypes;

public class LongType {
    public static void main(String[] args) throws Exception {

        long minLong = Long.MIN_VALUE;
        long maxLong = Long.MAX_VALUE;
        long smallLong = -42332200000L;

        System.out.printf("long 型の最小値 = %d%n", minLong);
        System.out.printf("long 型の最大値 =  %d%n", maxLong);
        System.out.println();
        System.out.printf("long 型の負数 %d = %d%n", smallLong, smallLong);
    }
}
