package com.evolution.bootcamp.assignment.poker;

import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.game.Game;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.game.GameFactory;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solver {

    static String process(String line) {

        Objects.requireNonNull(line, "invalid inout");
        String[] parts = line.split(" ");
        if (parts.length < 3) {
            throw new RuntimeException("invalid input");
        }
        Game game = GameFactory.getGame(line);
        return game.getSortedHandString();
    }
}
