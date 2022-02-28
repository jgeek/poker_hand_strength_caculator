package com.evolution.bootcamp.assignment.poker.game.sorter;

import com.evolution.bootcamp.assignment.poker.entity.Board;
import com.evolution.bootcamp.assignment.poker.entity.Hand;
import java.util.List;

public interface Sorter {
    List<Hand> sort(Board board, List<Hand> hands);
}
