package com.example.sudokusolverv2;

import junit.framework.TestCase;

public class NakedSingleTest extends TestCase {


    public void testGetNakedSingleLocation() {
        Solver solver = new Solver();
        com.example.sudokusolverv2.solutionStrategies.NakedSingle nakedSingle = new com.example.sudokusolverv2.solutionStrategies.NakedSingle();
        nakedSingle.setSolver(solver);
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
        int[] pos = nakedSingle.getNakedSingleLocation();
        assertEquals(pos[0], 0);
        assertEquals(pos[1], 8);
    }
}