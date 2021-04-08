package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.NakedSingle;
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
        NakedSingle nakedSingle = nakedSingleFinder.getNakedSingle();
        assertEquals(nakedSingle.row, 0);
        assertEquals(nakedSingle.col, 8);
    }
}