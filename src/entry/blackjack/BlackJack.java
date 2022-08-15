package entry.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlackJack {
    public static void main(String[] args) throws Exception {
        Dealer dealer = new Dealer(new Cards());
        Player player = new Player();
        for (int i = 0; i < 2; i++) {
            dealer.deal(player);
            dealer.deal(dealer);
        }

        System.out.println();
        dealer.sayCurrentPoint();
        player.sayCurrentPoint();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("もう1枚カードを引きますか？(Y/N)：");
                String yesOrNo = scanner.nextLine();

                System.out.println();

                // y,n以外の考慮はしない。めんどくさくなったので。
                if (yesOrNo.toLowerCase().equals("y")) {
                    dealer.deal(player);
                    player.sayCurrentPoint();

                    if (Rule.canDeal(player.currentPoint())) {
                        continue;
                    }
                }

                break;
            }

            if (Rule.isBurst(player)) {
                Rule.result(player, dealer);
                System.exit(0);
            }

            while (true) {
                if (dealer.currentPoint() >= 18) {
                    break;
                }

                dealer.deal(dealer);
                dealer.sayCurrentPoint();
                System.out.println();
            }
        }

        Rule.result(player, dealer);
    }
}

class Rule {
    static boolean canDeal(int point) {
        return point <= 21;
    }

    static void result(Player player, Dealer dealer) {
        if (isBurst(player)) {
            System.out.printf("%sの負けです。%n", player.playerName);
            return;
        }
        if (isBurst(dealer)) {
            System.out.printf("%sの勝ちです。%n", player.playerName);
            return;
        }

        if (player.currentPoint() == dealer.currentPoint()) {
            System.out.println("引き分けです。");
            return;
        }
        if (player.currentPoint() > dealer.currentPoint()) {
            System.out.printf("%sの勝ちです。%n", player.playerName);
            return;
        }
        if (player.currentPoint() < dealer.currentPoint()) {
            System.out.printf("%sの負けです。%n", player.playerName);
            return;
        }
    }

    static boolean isBurst(Player player) {
        return player.currentPoint() > 21;
    }
}

class Player {
    protected List<Card> cardsInHand;
    protected final String playerName;

    Player() {
        this("あなた");
    }

    Player(String playerName) {
        this.cardsInHand = new ArrayList<>();
        this.playerName = playerName;
    }

    void draw(Card card) {
        System.out.printf("%sに「%s」が配られました。%n", this.playerName, card.number.name);
        cardsInHand.add(card);
    }

    int currentPoint() {
        List<Card> cardsExcludeAce = new ArrayList<>();
        List<Card> cardsOnlyAce = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card.number == Number.ACE) {
                cardsOnlyAce.add(card);
            } else {
                cardsExcludeAce.add(card);
            }
        }

        int tempSum = cardsExcludeAce.stream().mapToInt(card -> card.number.point).sum();
        int[] sums = { tempSum, tempSum };
        if (!cardsOnlyAce.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    sums[0] += 1 * cardsOnlyAce.size();
                } else {
                    sums[1] += 11 + (1 * cardsOnlyAce.size() - 1);
                }
            }
        }

        return sums[1] <= 21 ? sums[1] : sums[0];
    }

    void sayCurrentPoint() {
        System.out.printf("現在の合計は %s です。%n", currentPoint());
    }
}

class Dealer extends Player {
    private Cards cards;

    Dealer(Cards cards) {
        super("ディーラー");
        this.cards = cards;
    }

    void deal(Player player) {
        Card card = this.cards.takeTopCard();
        player.draw(card);
    }

    void sayCurrentPoint() {
        System.out.printf("ディーラーの合計は %s です。%n", currentPoint());
    }
}

class Cards {
    private final List<Card> cards;

    Cards() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 13; j++) {
                Mark mark = Mark.of(Integer.toString(i));
                Number number = Number.of(j);
                cards.add(new Card(mark, number));
            }
        }
        this.cards = cards;
        shuffle();
    }

    void shuffle() {
        Collections.shuffle(this.cards);
    }

    Card takeTopCard() {
        return this.cards.remove(0);
    }
}

class Card {
    final Mark mark;
    final Number number;

    Card(Mark mark, Number number) {
        this.mark = mark;
        this.number = number;
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
    ACE(0, "A", 1),
    TWO(1, "2", 2),
    THREE(2, "3", 3),
    FOUR(3, "4", 4),
    FIVE(4, "5", 5),
    SIX(5, "6", 6),
    SEVEN(6, "7", 7),
    EIGHT(7, "8", 8),
    NINE(8, "9", 9),
    TEN(9, "10", 10),
    JACK(10, "J", 10),
    QUEEN(11, "Q", 10),
    KING(12, "K", 10);

    final int index;
    final String name;
    final int point;

    private Number(int index, String name, int point) {
        this.index = index;
        this.name = name;
        this.point = point;
    }

    public static Number of(int index) {
        Map<Integer, Number> reverseMap = Stream.of(Number.values())
                .collect(Collectors.toMap(a -> a.index, a -> a));
        return Optional.ofNullable(reverseMap.get(index))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Number of(String name) {
        Map<String, Number> reverseMap = Stream.of(Number.values())
                .collect(Collectors.toMap(a -> a.name, a -> a));
        return Optional.ofNullable(reverseMap.get(name))
                .orElseThrow(IllegalArgumentException::new);
    }
}