package com.example.sudokusolverv2;

import junit.framework.TestCase;

import org.apache.commons.lang3.SerializationUtils;

import java.util.HashSet;

public class NakedSingleTest extends TestCase {


    public void testNakedSingle() {
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
        assertTrue(nakedSingle.EnterNakedSingle());
    }
}