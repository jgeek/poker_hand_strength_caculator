package com.evolution.bootcamp.assignment.poker.game;

import com.evolution.bootcamp.assignment.poker.entity.Hand;
import com.evolution.bootcamp.assignment.poker.game.sorter.Sorter;
import java.util.List;

public class FiveCardDraw extends Game {
    public FiveCardDraw(String name, List<Hand> hands, Sorter sorter) {
        super(name, null, hands, sorter);
    }
}
