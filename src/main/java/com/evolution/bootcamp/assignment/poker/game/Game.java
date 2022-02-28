package com.evolution.bootcamp.assignment.poker.game;

import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.game.sorter.Sorter;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public abstract class Game {
    private final String name;
    private final Board board;
    private final List<Hand> handsStr;
    private final Sorter sorter;

    public Game(String name, Board board, List<Hand> hands, Sorter sorter) {
        this.name = name;
        this.board = board;
        this.handsStr = hands;
        this.sorter = sorter;
    }

    public List<Hand> sortHandsByStrength() {
        return getSorter().sort(getBoard(), getHandsStr());
    }

    public String getSortedHandString() {
        List<Hand> hands = new ArrayList<>(sortHandsByStrength());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hands.size(); i++) {
            Hand hand1 = hands.get(i);
            if (i + 1 < hands.size()) {
                Hand hand2 = hands.get(i + 1);
                int compare = hand1.getStrength().compareTo(hand2.getStrength());
                if (compare == 0) {
                    if (hand1.getChars().compareTo(hand2.getChars()) < 0) {
                        sb.append(hand1.getChars()).append("=");
                    } else {
                        sb.append(hand2.getChars()).append("=");
                        hands.set(i + 1, hand1);
                    }
                } else {
                    sb.append(hand1.getChars()).append(" ");
                }
            } else {
                sb.append(hand1.getChars());
            }
        }
        return sb.toString().trim();
    }
}
