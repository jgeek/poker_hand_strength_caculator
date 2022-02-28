package com.evolution.bootcamp.assignment.poker.util;

import java.util.List;

/**
 * Recursively creates all permutation of 5 cards from number of all provided cards(7).
 * Actually it calculates C(n,r)
 *
 * @param combinations an empty list. in the end it contains all permutations of indices array.
 * @param indices      indices of cards from 0 to number of cards minus one.
 * @param start        start index which the chosen value is selected by this index.
 * @param end          number of cards minus one
 * @param index        index of start point for creating a combination.
 */
public class Combinations {
    public static void generate(List<int[]> combinations, int[] indices, int start, int end, int index) {
        if (index == indices.length) {
            int[] combination = indices.clone();
            combinations.add(combination);
        } else if (start <= end) {
            indices[index] = start;
            generate(combinations, indices, start + 1, end, index + 1);
            generate(combinations, indices, start + 1, end, index);
        }
    }
}
