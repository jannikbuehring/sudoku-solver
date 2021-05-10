package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.Swordfish;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.SwordfishFinder;

import junit.framework.TestCase;

public class SwordfishFinderTest extends TestCase {

    public void testGetSwordfishInRow() {
        Solver solver = new Solver();
        SwordfishFinder swordfishFinder = new SwordfishFinder();
        swordfishFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-2/
        solver.board = new int[][]{
                {0, 0, 2, 8, 0, 5, 0, 0, 7},
                {0, 9, 0, 2, 4, 0, 0, 5, 0},
                {4, 0, 0, 9, 0, 0, 2, 0, 0},
                {0, 0, 6, 0, 0, 2, 0, 0, 1},
                {0, 0, 0, 0, 8, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 3, 0, 0},
                {0, 0, 7, 0, 0, 8, 0, 0, 5},
                {0, 0, 0, 0, 9, 0, 0, 6, 0},
                {1, 0, 0, 4, 0, 0, 7, 0, 0}
        };
        solver.calculateInitialCandidates();
        Swordfish swordfish = swordfishFinder.getSwordfishInColumn();

        assertEquals(swordfish.triplet1.field1.row, 4);
        assertEquals(swordfish.triplet1.field2.row, 4);
        assertEquals(swordfish.triplet1.field3.row, 4);

        assertEquals(swordfish.triplet2.field1.row, 5);
        assertEquals(swordfish.triplet2.field2.row, 5);
        assertEquals(swordfish.triplet2.field3.row, 5);

        assertEquals(swordfish.triplet3.field1.row, 8);
        assertEquals(swordfish.triplet3.field2.row, 8);
        assertEquals(swordfish.triplet3.field3.row, 8);

        assertEquals(swordfish.triplet1.field1.column, 2);
        assertEquals(swordfish.triplet1.field2.column, 5);
        assertEquals(swordfish.triplet1.field3.column, 8);

        assertEquals(swordfish.triplet2.field1.column, 2);
        assertEquals(swordfish.triplet2.field2.column, 5);
        assertEquals(swordfish.triplet2.field3.column, 8);

        assertEquals(swordfish.triplet3.field1.column, 2);
        assertEquals(swordfish.triplet3.field2.column, 5);
        assertEquals(swordfish.triplet3.field3.column, 8);

        assertEquals(swordfish.value, 9);

    }
}
