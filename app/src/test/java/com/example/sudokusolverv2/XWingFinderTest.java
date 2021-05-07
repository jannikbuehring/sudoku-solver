package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.XWing;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.XWingFinder;

import junit.framework.TestCase;

public class XWingFinderTest extends TestCase {

    public void testGetXWingInRow() {
        Solver solver = new Solver();
        XWingFinder xWingFinder = new XWingFinder();
        xWingFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-2/
        solver.board = new int[][]{
                {1, 0, 0, 0, 0, 0, 0, 8, 0},
                {8, 0, 0, 1, 0, 0, 0, 2, 4},
                {7, 0, 0, 0, 0, 3, 1, 5, 0},
                {0, 0, 0, 0, 4, 1, 6, 9, 2},
                {0, 9, 0, 6, 7, 0, 4, 1, 3},
                {4, 1, 6, 2, 3, 9, 8, 7, 5},
                {9, 0, 1, 0, 6, 2, 5, 0, 8},
                {0, 0, 0, 3, 0, 0, 9, 0, 1},
                {0, 5, 0, 9, 1, 0, 2, 0, 7}
        };
        solver.calculateInitialCandidates();
        XWing xWing = xWingFinder.getXWingInRow();

        assertEquals(xWing.pair1.field1.row, 4);
        assertEquals(xWing.pair1.field2.row, 4);

        assertEquals(xWing.pair2.field1.row, 8);
        assertEquals(xWing.pair2.field2.row, 8);

        assertEquals(xWing.pair1.field1.column, 2);
        assertEquals(xWing.pair1.field2.column, 5);

        assertEquals(xWing.pair2.field1.column, 2);
        assertEquals(xWing.pair2.field2.column, 5);

        assertEquals(xWing.value, 8);

    }

    public void testGetXWingInColumn() {
        Solver solver = new Solver();
        XWingFinder xWingFinder = new XWingFinder();
        xWingFinder.setSolver(solver);
        // https://www.livesudoku.com/helpart/advanced/sudoku-x-wing-tutorial-pic12.png
        solver.board = new int[][]{
                {0, 0, 1, 0, 9, 0, 6, 0, 0},
                {0, 0, 0, 0, 6, 0, 5, 0, 1},
                {5, 6, 0, 1, 0, 8, 0, 0, 4},
                {6, 4, 2, 3, 7, 9, 8, 1, 5},
                {7, 3, 8, 0, 1, 0, 2, 6, 9},
                {1, 5, 9, 0, 0, 6, 4, 0, 0},
                {2, 0, 0, 6, 0, 1, 0, 5, 8},
                {3, 0, 5, 0, 0, 0, 1, 0, 6},
                {0, 1, 6, 0, 5, 0, 7, 0, 0}
        };
        solver.calculateInitialCandidates();
        XWing xWing = xWingFinder.getXWingInColumn();

        assertEquals(xWing.pair1.field1.row, 2);
        assertEquals(xWing.pair1.field2.row, 6);

        assertEquals(xWing.pair2.field1.row, 2);
        assertEquals(xWing.pair2.field2.row, 6);

        assertEquals(xWing.pair1.field1.column, 4);
        assertEquals(xWing.pair1.field2.column, 4);

        assertEquals(xWing.pair2.field1.column, 6);
        assertEquals(xWing.pair2.field2.column, 6);

        assertEquals(xWing.value, 3);
    }
}
