package com.evolution.bootcamp.assignment.poker.game;

import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.game.sorter.FiveCardStrengthSorter;
import com.evolution.bootcamp.assignment.poker.game.sorter.OmahaHoldemStrenghtSorter;
import com.evolution.bootcamp.assignment.poker.game.sorter.Sorter;
import com.evolution.bootcamp.assignment.poker.game.sorter.TexasHoldemStrengthSorter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameFactory {
    public static Game getGame(String inputLine) {
        Objects.requireNonNull(inputLine, "name of the game is null");

        Objects.requireNonNull(inputLine, "invalid inout");
        String[] parts = inputLine.split(" ");
        if (parts.length < 3) {
            throw new RuntimeException("invalid input");
        }
        var gameName = parts[0];
        switch (gameName) {
            case "texas-holdem": {
                Board board = new Board(parts[1]);
                List<Hand> hands = createHands(parts, 2);
                Sorter sorter = new TexasHoldemStrengthSorter();
                return new TexasHoldem(gameName, board, hands, sorter);
            }
            case "omaha-holdem": {
                Board board = new Board(parts[1]);
                Sorter sorter = new OmahaHoldemStrenghtSorter();
                List<Hand> hands = createHands(parts, 2);
                return new OmahaHoldem(gameName, board, hands, sorter);
            }
            case "five-card-draw": {
                Sorter sorter = new FiveCardStrengthSorter();
                List<Hand> hands = createHands(parts, 1);
                return new FiveCardDraw(gameName, hands, sorter);
            }
        }
        throw new RuntimeException("no game found!!!");
    }

    private static List<Hand> createHands(String[] parts, int from) {
        return IntStream.range(from, parts.length).mapToObj(i -> new Hand(parts[i]))
                .collect(Collectors.toList());
    }
}
