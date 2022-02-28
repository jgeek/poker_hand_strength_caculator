package com.evolution.bootcamp.assignment.poker;

import com.evolution.bootcamp.assignment.poker.entity.Hand;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TexasHoldemSolverTest {

    @Test
    public void testTh5c6dAcAsQs() {
        assertEquals(
                "2cJc Kh4h=Ks4c Kc7h KdJs 6h7d 2hAh",
                Solver.process("texas-holdem 5c6dAcAsQs Ks4c KdJs 2hAh Kh4h Kc7h 6h7d 2cJc"));
    }

    @Test
    public void testTh2h5c8sAsKc() {
        assertEquals(
                "Jc6s Qs9h 3cKh KdQh",
                Solver.process("texas-holdem 2h5c8sAsKc Qs9h KdQh 3cKh Jc6s"));
    }

    @Test
    public void testTh3d4s5dJsQd() {
        assertEquals(
                "9h7h 2dTc KcAs 7sJd TsJc Qh8c 5c4h",
                Solver.process("texas-holdem 3d4s5dJsQd 5c4h 7sJd KcAs 9h7h 2dTc Qh8c TsJc"));
    }

    @Test
    public void checkIsStraightAce() {
        Hand hand = new Hand("5d3s2h4cAc");
        int straight = hand.straight();
        assertEquals(straight, 5);
    }

    @Test
    public void checkIsStraight() {
        Hand hand = new Hand("5d3s2h4c6c");
        int straight = hand.straight();
        assertEquals(straight, 6);
    }

    @Test
    public void checkTwoPair() {
        int[] twoPair = (new Hand("7d3h7h3c4d")).twoPair();
        var min = Math.min(twoPair[0], twoPair[1]);
        var max = Math.max(twoPair[0], twoPair[1]);
        assertEquals(min, 3);
        assertEquals(max, 7);
    }

    @Test
    public void checkThreeKind() {
        int kind3Value = (new Hand("7d3h7hTc7d")).kind(3, false);
        assertEquals(kind3Value, 7);
    }

    @Test
    public void checkFourKind() {
        var fourKindValue = (new Hand("7d3h7h7s7c")).kind(4, false);
        assertEquals(fourKindValue, 7);
    }

    @Test
    public void checkFullHouse() {
        Hand fullHouseHand = new Hand("7d3h7h7s3c");
        var pair = fullHouseHand.kind(2, false);
        var threeKind = fullHouseHand.kind(3, false);
        assertEquals(pair, 3);
        assertEquals(threeKind, 7);
    }
}
