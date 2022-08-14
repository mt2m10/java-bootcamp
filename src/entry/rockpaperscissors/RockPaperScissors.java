package entry.rockpaperscissors;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RockPaperScissors {

    public static void main(String[] args) throws Exception {
        Player player = new Player();
        Com com = new Com();

        System.out.println("じゃんけん勝負");
        System.out.println("グーチョキパーを数字で入力してね");

        Hand.printCombinationOfIndexAndMark();

        System.out.printf("最初はぐー、じゃんけん：");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    Hand playerHand = player.strategy(scanner);
                    Hand comHand = com.strategy();

                    RESULT playerResult = playerHand.buttle(comHand);
                    System.out.printf("%s(COM)と%s(Player)で…%n", comHand.name, playerHand.name);
                    switch (playerResult) {
                        case WIN:
                            System.out.println("あなたの勝ち");
                            System.exit(0);
                        case DRAW:
                            System.out.println("あいこだよ！");
                            break;
                        case LOSE:
                            System.out.println("あなたの負け");
                            System.exit(0);
                    }

                    System.out.println();
                    System.out.print("あいこで：");
                } catch (IllegalArgumentException iae) {
                    System.out.print("0、1、2の中から選んでね：");
                }
            }
        }
    }
}

class Player {
    Hand strategy(Scanner scanner) {
        return Hand.of(scanner.nextLine());
    }
}

class Com {
    Hand strategy() {
        Random random = new Random();
        return Hand.of(Integer.toString(random.nextInt(3)));
    }
}

enum Hand {
    ROCK("0", "グー"),
    PAPER("1", "チョキ"),
    SCISSORS("2", "パー");

    final String index;
    final String name;

    private Hand(String index, String name) {
        this.index = index;
        this.name = name;
    }

    RESULT buttle(Hand hand) throws Exception {
        if (this == hand) {
            return RESULT.DRAW;
        }

        switch (this) {
            case ROCK:
                if (hand == Hand.PAPER) {
                    return RESULT.WIN;
                }
                return RESULT.LOSE;
            case PAPER:
                if (hand == Hand.SCISSORS) {
                    return RESULT.WIN;
                }
                return RESULT.LOSE;
            case SCISSORS:
                if (hand == Hand.ROCK) {
                    return RESULT.WIN;
                }
                return RESULT.LOSE;
        }

        throw new Exception();
    }

    public static void printCombinationOfIndexAndMark() {
        for (Hand hand : Hand.values()) {
            System.out.printf("%s:%s%n", hand.index, hand.name);
        }
    }

    // 逆引きMap
    private static final Map<String, Hand> reverseMap = Stream.of(Hand.values())
            .collect(Collectors.toMap(a -> a.index, a -> a));

    public static Hand of(String index) {
        return Optional.ofNullable(reverseMap.get(index))
                .orElseThrow(IllegalArgumentException::new);
    }
}

enum RESULT {
    WIN,
    DRAW,
    LOSE;
}