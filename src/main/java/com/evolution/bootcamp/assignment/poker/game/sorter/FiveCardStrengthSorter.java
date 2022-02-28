package com.evolution.bootcamp.assignment.poker.game.sorter;

import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.game.StrengthCalculator;
import com.evolution.bootcamp.assignment.poker.strenght.HandStrength;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FiveCardStrengthSorter implements Sorter {

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
     * @return The hand as it's one permutation for each hands.
     */
    private Map<Hand, List<Hand>> calculatePermutations(Board board, List<Hand> hands) {
        Map<Hand, List<Hand>> handCardMap = new HashMap<>();
        hands.forEach(h -> handCardMap.put(h, List.of(h)));
        return handCardMap;
    }
}
