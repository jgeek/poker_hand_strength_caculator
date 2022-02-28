package com.evolution.bootcamp.assignment.poker.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card implements Comparable<Card> {

    public enum Suit {
        SPADE,
        DIAMOND,
        HEART,
        CLUB
    }

    private char suitChar;
    private Suit suit;
    private char value;

    public Card(char suitChar, char value) {
        this.value = value;
        setSuitChar(suitChar);
    }

    @Override
    public String toString() {
        return value + "" + suitChar;
    }

    public int getIntVal() {
        switch (value) {
            case 'A': return 14;
            case 'K': return 13;
            case 'Q': return 12;
            case 'J': return 11;
            case 'T': return 10;
        }
        return Integer.parseInt(value + "");
    }

    public void setSuitChar(char suitChar) {
        this.suitChar = suitChar;
        switch (suitChar) {
            case 's': suit = Suit.SPADE; break;
            case 'd': suit = Suit.DIAMOND; break;
            case 'h': suit = Suit.HEART; break;
            case 'c': suit = Suit.CLUB; break;
        }
    }

    /**
     * Cards with lower value comes first
     *
     * @param other other card to compare.
     * @return -1/+1 if this card has lower/higher value. 0 if cards have the same value.
     */
    @Override
    public int compareTo(Card other) {
        return other.getIntVal() - this.getIntVal();
        // return this.getIntVal() - other.getIntVal();
    }
}
