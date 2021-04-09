package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.HiddenPairFinder;

import junit.framework.TestCase;

public class HiddenPairFinderTest extends TestCase {

    public void testGetHiddenPairInRow() {
        Solver solver = new Solver();
        HiddenPairFinder hiddenPairFinder = new HiddenPairFinder();
        hiddenPairFinder.setSolver(solver);
        solver.board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 7, 0, 6, 0, 0},
                {0, 7, 0, 2, 0, 4, 3, 0, 0},
                {0, 0, 0, 9, 0, 0, 0, 8, 4},
                {0, 6, 0, 0, 0, 0, 5, 9, 2},
                {5, 9, 0, 0, 0, 0, 0, 0, 0},
                {0, 4, 0, 8, 2, 0, 9, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {6, 5, 1, 7, 0, 0, 0, 0, 0}
        };
        solver.calculateInitialCandidates();
        hiddenPairFinder.getHiddenPairInRow();
    }
}
