package com.evolution.bootcamp.assignment.poker.entity;

import com.evolution.bootcamp.assignment.poker.strenght.HandStrength;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hand extends HasCard {

    private HandStrength strength;

    public Hand(String chars) {
        super(chars);
    }

    public static void main(String[] args) {
        // Hand hand = new Hand("5d7s9h8c");
        Hand hand = new Hand("5d3s2h4cAc");
        List<Card> sorted = hand.sortedCards();
        int straight = hand.straight();
        int flush = (new Hand("1d2d5d3d4d")).flush();
        int isPairValue = (new Hand("7d3h5d7c8d")).kind(2, false);
        int notPairValue = (new Hand("7d3h5dTc8d")).kind(2, false);
        int kind3Value = (new Hand("7d3h7hTc7d")).kind(3, false);
        var twoPair = (new Hand("7d3h7h3c4d")).twoPair();
        var kind4Value = (new Hand("7d3h7h7s7c")).kind(4, false);
        Hand fullHouseHand = new Hand("7d3h7h7s3c");
        var fullHouse2 = fullHouseHand.kind(2, false);
        var fullHouse3 = fullHouseHand.kind(3, false);
        System.out.println();
    }
}
