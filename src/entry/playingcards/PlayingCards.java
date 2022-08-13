
package entry.playingcards;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayingCards {

    // チャレンジ 2. ->
    private final static int MARK_SELECTED_MAX = 2;
    private final static int NUMBER_SELECTED_MAX = 4;
    // <- チャレンジ 2.

    public static void main(String[] args) throws Exception {
        // トランプの52枚
        List<Card> cards = bundle();

        // 52枚の中からカードを選ぶ
        Card correct = chooseOne(cards);

        System.out.println("トランプを選んだよ");
        System.out.println("トランプの図柄を当ててね");

        // 図柄とインデックスの組み合わせを表示
        Mark.printCombinationOfIndexAndMark();

        try (Scanner scanner = new Scanner(System.in)) {
            // チャレンジ 2. ->
            // while (true) {
                for (int i = 1; i <= MARK_SELECTED_MAX; i++) {
            // <- チャレンジ 2.
                try {
                    System.out.print("どれだと思う？：");
                    String answer = scanner.nextLine();
                    Mark selectedMark = Mark.of(answer);
                    if (correct.equalsMark(selectedMark)) {
                        System.out.printf("正解！図柄は%sだよ%n", selectedMark.name);
                        break;
                    }
                    System.out.printf("残念！%sじゃないよ%n", selectedMark.name);
                } catch (IllegalArgumentException iae) {
                    System.out.println("0、1、2、3の中から選んでね");
                }

                // チャレンジ 2. ->
                if (i == MARK_SELECTED_MAX) {
                    System.out.printf("%s回までに正解できませんでした！終わりです！%n", MARK_SELECTED_MAX);
                    System.exit(0);
                }
                // <- チャレンジ 2.
            }

            System.out.println("次は数字を当ててね");

            // チャレンジ 2. ->
            // while (true) {
            for (int i = 1; i <= NUMBER_SELECTED_MAX; i++) {
            // <- チャレンジ 2.
                try {
                    System.out.print("どれだと思う？：");
                    String answer = scanner.nextLine();
                    Number selectedNumber = Number.ofName(answer);
                    if (correct.equalsNumber(selectedNumber)) {
                        System.out.printf("正解！%sだよ%n", correct.toString());
                        break;
                    }
                    System.out.printf("残念！%sじゃないよ%n", selectedNumber.name);

                    // チャレンジ 1. ->
                    if (correct.compareToNumber(selectedNumber) < 0) {
                        System.out.println("選んだ数字よりも小さいよ");
                    } else {
                        System.out.println("選んだ数字よりも大きいよ");
                    }
                    // <- チャレンジ 1.
                } catch (IllegalArgumentException iae) {
                    System.out.println("A、2、3、4、5、6、7、8、9、10、J、Q、Kの中から選んでね");
                }

                // チャレンジ 2. ->
                if (i == NUMBER_SELECTED_MAX) {
                    System.out.printf("%s回までに正解できませんでした！終わりです！%n", NUMBER_SELECTED_MAX);
                    System.exit(0);
                }
                // <- チャレンジ 2.
            }
        }
    }

    static List<Card> bundle() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 13; j++) {
                Mark mark = Mark.of(Integer.toString(i));
                Number number = Number.ofIndex(j);
                cards.add(new Card(mark, number));
            }
        }
        return cards;
    }

    static Card chooseOne(List<Card> cards) {
        Random random = new Random();
        return cards.get(random.nextInt(cards.size()));
    }
}

class Card {
    final Mark mark;
    final Number number;

    Card(Mark mark, Number number) {
        this.mark = mark;
        this.number = number;
    }

    boolean equalsMark(Mark mark) {
        return this.mark == mark;
    }

    boolean equalsNumber(Number number) {
        return this.number == number;
    }

    boolean equals(Card card) {
        return this.mark == card.mark &&
                this.number == card.number;
    }

    public String toString() {
        return String.format("%sの%s", this.mark.name, this.number.name);
    }

    int compareToNumber(Number number) {
        return this.number.compareTo(number);
    }
}

enum Mark {
    HEART("0", "ハート"),
    DIAMOND("1", "ダイヤ"),
    SPADE("2", "スペード"),
    CLOVER("3", "クローバー");

    final String index;
    final String name;

    private Mark(String index, String name) {
        this.index = index;
        this.name = name;
    }

    // 逆引きMap
    private static final Map<String, Mark> reverseMap = Stream.of(Mark.values())
            .collect(Collectors.toMap(a -> a.index, a -> a));

    public static Mark of(String index) {
        return Optional.ofNullable(reverseMap.get(index))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static void printCombinationOfIndexAndMark() {
        for (Mark mark : Mark.values()) {
            System.out.printf("%s:%s%n", mark.index, mark.name);
        }
    }
}

enum Number {
    ACE(0, "A"),
    TWO(1, "2"),
    THREE(2, "3"),
    FOUR(3, "4"),
    FIVE(4, "5"),
    SIX(5, "6"),
    SEVEN(6, "7"),
    EIGHT(7, "8"),
    NINE(8, "9"),
    TEN(9, "10"),
    JACK(10, "J"),
    QUEEN(11, "Q"),
    KING(12, "K");

    final int index;
    final String name;

    private Number(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static Number ofIndex(int index) {
        Map<Integer, Number> reverseMap = Stream.of(Number.values())
                .collect(Collectors.toMap(a -> a.index, a -> a));
        return Optional.ofNullable(reverseMap.get(index))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Number ofName(String name) {
        Map<String, Number> reverseMap = Stream.of(Number.values())
                .collect(Collectors.toMap(a -> a.name, a -> a));
        return Optional.ofNullable(reverseMap.get(name))
                .orElseThrow(IllegalArgumentException::new);
    }
}