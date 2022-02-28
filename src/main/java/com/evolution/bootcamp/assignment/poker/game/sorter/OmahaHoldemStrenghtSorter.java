package com.evolution.bootcamp.assignment.poker.game.sorter;

import static com.evolution.bootcamp.assignment.poker.game.OmahaHoldem.*;
import static java.util.stream.Collectors.*;

import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.entity.Card;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.game.StrengthCalculator;
import com.evolution.bootcamp.assignment.poker.strenght.HandStrength;
import com.evolution.bootcamp.assignment.poker.util.Combinations;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OmahaHoldemStrenghtSorter implements Sorter {

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
     * @return all permutation for a hand and cards provided by 2 cards of hand and 3 from board
     * A map of hand and all permutations hands of it.
     */
    private Map<Hand, List<Hand>> calculatePermutations(Board board, List<Hand> hands) {

        List<Card> boarCards = board.getCards();
        List<List<String>> boardCardCombinations = new ArrayList<>();
        List<int[]> boardCombinations = new ArrayList<>();
        Combinations.generate(boardCombinations, new int[3], 0, 4, 0);
        // calculating all permutations of 3 cards from 5 board cards.
        for (int[] indices : boardCombinations) {
            List<String> cards = IntStream.range(0, BOARD_SELECTED_CARDS_NUM)
                    .mapToObj(i -> boarCards.get(indices[i]).getValue() + "" + boarCards.get(indices[i]).getSuitChar())
                    .collect(toList());
            boardCardCombinations.add(cards);
        }

        List<int[]> handsCardCombinations = new ArrayList<>();
        Combinations.generate(handsCardCombinations, new int[HAND_SELECTED_CARDS_NUM], 0, 3, 0);
        Map<Hand, List<List<Card>>> handCardStrMap = new HashMap<>();
        for (Hand hand : hands) {
            List<List<Card>> handCardStrings = new ArrayList<>();
            handCardStrMap.put(hand, handCardStrings);
            for (int[] combination : handsCardCombinations) {
                List<Card> cards = IntStream.range(0, 2)
                        .mapToObj(i -> hand.getCards().get(combination[i]))
                        .collect(toList());
                handCardStrings.add(cards);
            }
        }

        // cartesian product of hand permutations and board permutations
        Map<Hand, List<Hand>> handCardsMap = new HashMap<>();
        for (Map.Entry<Hand, List<List<Card>>> e : handCardStrMap.entrySet()) {
            List<Hand> finalHands = new ArrayList<>();
            handCardsMap.put(e.getKey(), finalHands);
            for (List<String> boardCards : boardCardCombinations) {
                String threeBoardCardsStr = boardCards.stream().collect(joining(""));
                for (List<Card> cards : e.getValue()) {
                    String twoCardsStr = cards.stream().map(Card::toString).collect(joining());
                    Hand hand = new Hand(twoCardsStr + threeBoardCardsStr);
                    finalHands.add(hand);
                }
            }
        }
        return handCardsMap;
    }
}