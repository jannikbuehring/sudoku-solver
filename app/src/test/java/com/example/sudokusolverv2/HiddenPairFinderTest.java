package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenPair;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenPairFinder;

import junit.framework.TestCase;

public class HiddenPairFinderTest extends TestCase {

    public void testGetHiddenPairInRow() {
        Solver solver = new Solver();
        HiddenPairFinder hiddenPairFinder = new HiddenPairFinder();
        hiddenPairFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
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
        HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInRow();
        assertEquals(hiddenPair.field1.row, 3);
        assertEquals(hiddenPair.field2.row, 3);
        assertEquals(hiddenPair.field1.column, 4);
        assertEquals(hiddenPair.field2.column, 5);
        assertEquals(hiddenPair.pairCandidates.contains(5), true);
        assertEquals(hiddenPair.pairCandidates.contains(6), true);

    }

    public void testGetHiddenPairInColumn() {
        Solver solver = new Solver();
        HiddenPairFinder hiddenPairFinder = new HiddenPairFinder();
        hiddenPairFinder.setSolver(solver);
        // http://hodoku.sourceforge.net/en/tech_hidden.php
        solver.board = new int[][]{
                {0, 4, 9, 1, 3, 2, 0, 0, 0},
                {0, 8, 1, 4, 7, 9, 0, 0, 0},
                {3, 2, 7, 6, 8, 5, 9, 1, 4},
                {0, 9, 6, 0, 5, 1, 8, 0, 0},
                {0, 7, 5, 0, 2, 8, 0, 0, 0},
                {0, 3, 8, 0, 4, 6, 0, 0, 5},
                {8, 5, 3, 2, 6, 7, 0, 0, 0},
                {7, 1, 2, 8, 9, 4, 5, 6, 3},
                {9, 6, 4, 5, 1, 3, 0, 0, 0}
        };
        solver.calculateInitialCandidates();
        HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInColumn();
        assertEquals(hiddenPair.field1.row, 4);
        assertEquals(hiddenPair.field2.row, 6);
        assertEquals(hiddenPair.field1.column, 8);
        assertEquals(hiddenPair.field2.column, 8);
        assertEquals(hiddenPair.pairCandidates.contains(1), true);
        assertEquals(hiddenPair.pairCandidates.contains(9), true);

    }

    public void testGetHiddenPairInBlock() {
        Solver solver = new Solver();
        HiddenPairFinder hiddenPairFinder = new HiddenPairFinder();
        hiddenPairFinder.setSolver(solver);
        // http://hodoku.sourceforge.net/en/tech_hidden.php
        solver.board = new int[][]{
                {0, 0, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 2, 7, 3, 6},
                {0, 0, 6, 7, 3, 0, 0, 4, 0},
                {0, 9, 4, 0, 0, 0, 0, 6, 8},
                {0, 0, 0, 0, 9, 6, 4, 0, 7},
                {6, 0, 7, 0, 5, 0, 9, 2, 3},
                {1, 0, 0, 0, 0, 0, 0, 8, 5},
                {0, 6, 0, 0, 8, 0, 2, 7, 1},
                {0, 0, 5, 0, 1, 0, 0, 9, 4}
        };
        solver.calculateInitialCandidates();
        HiddenPair hiddenPair = hiddenPairFinder.getHiddenPairInBlock();
        assertEquals(hiddenPair.field1.row, 0);
        assertEquals(hiddenPair.field2.row, 0);
        assertEquals(hiddenPair.field1.column, 0);
        assertEquals(hiddenPair.field2.column, 1);
        assertEquals(hiddenPair.pairCandidates.contains(4), true);
        assertEquals(hiddenPair.pairCandidates.contains(7), true);

    }
}
