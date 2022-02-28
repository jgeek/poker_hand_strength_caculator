package com.evolution.bootcamp.assignment.poker.game;

import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.game.sorter.Sorter;
import java.util.ArrayList;
import java.util.List;

public class TexasHoldem extends Game {
    public TexasHoldem(String name, Board board, List<Hand> handsStr, Sorter sorter) {
        super(name, board, handsStr, sorter);
        // List<Hand> hands = new ArrayList<>();
        // for (String str : getHandsStr()) {
        //     hands.add(new Hand(str));
        // }
        // setHands(hands);
    }

    public static void main(String[] args) {
        // texas-holdem 5c6dAcAsQs Ks4c KdJs 2hAh Kh4h Kc7h 6h7d 2cJc
        Board board = new Board("5c6dAcAsQs");
        Hand hand1 = new Hand("Ks4c");
        Hand hand2 = new Hand("KdJs");
        Hand hand3 = new Hand("2hAh");
        Hand hand4 = new Hand("Kh4h");
        Hand hand5 = new Hand("Kc7h");
        Hand hand6 = new Hand("6h7d");
        Hand hand7 = new Hand("2cJc");
        var hands = List.of(hand1, hand2, hand3, hand4, hand5, hand6, hand7);

        // Game game = GameFactory.getGame("texas-holdem", board, hands);
        // var sortedHands = game.sortHandsByStrength();
        // System.out.println();
    }
}
