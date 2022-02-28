package com.evolution.bootcamp.assignment.poker.game.sorter;

import static java.util.stream.Collectors.*;

import com.evolution.bootcamp.assignment.poker.entity.Card;
import com.evolution.bootcamp.assignment.poker.game.StrengthCalculator;
import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.strenght.HandStrength;
import com.evolution.bootcamp.assignment.poker.util.Combinations;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TexasHoldemStrengthSorter implements Sorter {

    private static final int HAND_SIZE = 5;

    public List<Hand> sort(Board board, List<Hand> hands) {

        Map<Hand, List<Hand>> possiblePermutations = calculatePermutations(board, hands);
        Map<Hand, List<HandStrength>> handsPossiblePermutationsMap = new HashMap<>();
        for (Map.Entry<Hand, List<Hand>> e : possiblePermutations.entrySet()) {
            List<HandStrength> strengths = new ArrayList<>();
            handsPossiblePermutationsMap.put(e.getKey(), strengths);
            for (Hand hand : e.getValue()) {
                StrengthCalculator cal = new StrengthCalculator(hand);
                HandStrength strength = cal.calculate();
                strengths.add(strength);
            }
        }
        // calculating the strongest permutation for a hand gy sorting and getting the highest strength.
        Map<Hand, HandStrength> handsMap = handsPossiblePermutationsMap.entrySet().stream()
                .map(e -> {
                    Collections.sort(e.getValue());
                    return Map.of(e.getKey(), e.getValue().get(0));
                })
                .collect(Collectors.toMap(e -> e.keySet().iterator().next(), e -> e.values().iterator().next()));
        List<Hand> sortedHands = new ArrayList<>();
        // soring hands by theirs selected strength
        handsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(e -> {
                    e.getKey().setStrength(e.getValue());
                    sortedHands.add(e.getKey());
                });
        // ascending order for hands from weaker to stronger
        Collections.reverse(sortedHands);
        return sortedHands;
    }

    /**
     * @param board board of the game
     * @param hands hands
     * @return all permutation for a hand and cards provided by the board(5 card of 7) for each hand.
     * A map of hand and all permutations hands of it.
     */
    private Map<Hand, List<Hand>> calculatePermutations(Board board, List<Hand> hands) {
        Map<Hand, List<Hand>> handCardMap = new HashMap<>();
        List<int[]> combinations = new ArrayList<>();
        Combinations.generate(combinations, new int[HAND_SIZE], 0, 7 - 1, 0);
        for (Hand hand : hands) {
            List<Hand> handCards = new ArrayList<>();
            handCardMap.put(hand, handCards);
            List<Card> sevenCards = new ArrayList<>(hand.getCards());
            sevenCards.addAll(board.getCards());
            for (int[] combination : combinations) {
                String chars = IntStream.range(0, HAND_SIZE)
                        .mapToObj(i -> new char[] { sevenCards.get(combination[i]).getValue(),
                                sevenCards.get(combination[i]).getSuitChar() })
                        .map(String::new)
                        .collect(Collectors.joining());
                Hand h = new Hand(chars);
                handCards.add(h);
            }
        }
        return handCardMap;
    }
}
