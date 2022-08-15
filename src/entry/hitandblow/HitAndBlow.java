package entry.hitandblow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HitAndBlow {
    public static void main(String[] args) throws Exception {
        List<Integer> numbers = new ArrayList<>(Arrays.asList( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ));
        Collections.shuffle(numbers);

        List<String> correct = new ArrayList<>();
        correct.add(String.valueOf(numbers.remove(0)));
        correct.add(String.valueOf(numbers.remove(0)));
        correct.add(String.valueOf(numbers.remove(0)));
        correct.add(String.valueOf(numbers.remove(0)));

        for (String hoge : correct) {
            System.out.print(hoge);
        }
        System.out.println();

        try (Scanner scanner = new Scanner(System.in)) {
            int count = 0;

            while (true) {
                count++;
                int hit = 0;
                int blow = 0;
                System.out.print("4桁の数字を入力して下さい。");
                String[] a = scanner.nextLine().split("");
                for (int i = 0; i < a.length; i++) {
                    String b = a[i];
                    hit += isHit(i, b, correct) ? 1 : 0;
                    blow += isBlow(b, correct) ? 1 : 0;
                }

                System.out.printf("ヒット：%s個、ブロー：%s個%n", hit, blow - hit);

                if (hit == 4) {
                    System.out.printf("おめでとう！%s回目で成功♪%n", count);
                    break;
                }
            }
        }
    }

    static boolean isHit(int i, String b, List<String> correct) {
        return correct.get(i).equals(b);
    }

    static boolean isBlow(String b, List<String> correct) {
        return correct.contains(b);
    }
}
