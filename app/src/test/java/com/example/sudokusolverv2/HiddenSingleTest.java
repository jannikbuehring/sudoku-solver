package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.HiddenSingle;

import junit.framework.TestCase;

public class HiddenSingleTest extends TestCase {

    public void testEnterHiddenSingleRow() {
        Solver solver = new Solver();
        HiddenSingle hiddenSingle = new HiddenSingle();
        hiddenSingle.setSolver(solver);
        solver.board = new int[][]{
                {0, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 5, 8, 0, 0, 6, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 8, 5},
                {0, 1, 0, 4, 7, 5, 6, 0, 0},
                {0, 0, 6, 0, 0, 0, 5, 0, 7},
                {5, 0, 7, 6, 3, 9, 1, 4, 0},
                {7, 6, 0, 0, 0, 8, 0, 0, 0},
                {0, 0, 0, 9, 0, 0, 8, 1, 0},
                {0, 0, 9, 0, 0, 0, 0, 0, 0}
        };
        assertTrue(hiddenSingle.EnterHiddenSingleRow(2));
    }
}