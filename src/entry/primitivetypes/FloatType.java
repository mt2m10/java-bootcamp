package entry.primitivetypes;

public class FloatType {
    public static void main(String[] args) throws Exception {

        float minFloat = Float.MIN_VALUE;
        float maxFloat = Float.MAX_VALUE;
        float smallFloat = -42.3f;

        System.out.printf("float 型の最小値 = %s%n", minFloat);
        System.out.printf("float 型の最大値 = %s%n", maxFloat);
        System.out.println();
        System.out.printf("float 型の負数 %s = %s%n", smallFloat, smallFloat);
    }
}
