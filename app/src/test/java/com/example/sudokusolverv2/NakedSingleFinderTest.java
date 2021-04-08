package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.NakedSingleFinder;

import junit.framework.TestCase;

public class NakedSingleFinderTest extends TestCase {


    public void testGetNakedSingleLocation() {
        Solver solver = new Solver();
        NakedSingleFinder nakedSingleFinder = new NakedSingleFinder();
        nakedSingleFinder.setSolver(solver);
        solver.board = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        solver.calculateInitialCandidates();
        int[] pos = nakedSingleFinder.getNakedSingle();
        assertEquals(pos[0], 0);
        assertEquals(pos[1], 8);
    }
}