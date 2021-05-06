package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenTriple;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenTripleFinder;

import junit.framework.TestCase;

public class HiddenTripleFinderTest extends TestCase {

    public void testGetHiddenTripleInRow() {
        Solver solver = new Solver();
        HiddenTripleFinder hiddenTripleFinder = new HiddenTripleFinder();
        hiddenTripleFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {0, 0, 0, 7, 4, 0, 0, 0, 8},
                {4, 9, 6, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 4, 0},
                {0, 0, 0, 0, 5, 7, 6, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 2, 1},
                {0, 0, 3, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 0, 0},
                {1, 2, 4, 0, 7, 0, 0, 5, 0},
                {0, 0, 0, 0, 0, 0, 7, 0, 0}
        };
        solver.calculateInitialCandidates();
        HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInRow();

        assertEquals(hiddenTriple.field1.row, 4);
        assertEquals(hiddenTriple.field2.row, 4);
        assertEquals(hiddenTriple.field3.row, 4);

        assertEquals(hiddenTriple.field1.column, 1);
        assertEquals(hiddenTriple.field2.column, 2);
        assertEquals(hiddenTriple.field3.column, 6);

        assertTrue(hiddenTriple.candidates.contains(4));
        assertTrue(hiddenTriple.candidates.contains(5));
        assertTrue(hiddenTriple.candidates.contains(7));
    }

    public void testGetHiddenTripleInColumn() {
        Solver solver = new Solver();
        HiddenTripleFinder hiddenTripleFinder = new HiddenTripleFinder();
        hiddenTripleFinder.setSolver(solver);
        // http://hodoku.sourceforge.net/de/tech_hidden.php
        solver.board = new int[][]{
                {5, 0, 0, 6, 2, 0, 0, 3, 7},
                {0, 0, 4, 8, 9, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 0},
                {9, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 6, 0, 5},
                {7, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 9, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 7, 0, 0},
                {6, 8, 0, 5, 7, 0, 0, 0, 2}
        };
        solver.calculateInitialCandidates();
        HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInColumn();

        assertEquals(hiddenTriple.field1.column, 5);
        assertEquals(hiddenTriple.field2.column, 5);
        assertEquals(hiddenTriple.field3.column, 5);

        assertEquals(hiddenTriple.field1.row, 3);
        assertEquals(hiddenTriple.field2.row, 5);
        assertEquals(hiddenTriple.field3.row, 7);

        assertTrue(hiddenTriple.candidates.contains(2));
        assertTrue(hiddenTriple.candidates.contains(5));
        assertTrue(hiddenTriple.candidates.contains(6));
    }

    public void testGetHiddenTripleInBlock() {
        Solver solver = new Solver();
        HiddenTripleFinder hiddenTripleFinder = new HiddenTripleFinder();
        hiddenTripleFinder.setSolver(solver);
        // http://hodoku.sourceforge.net/de/tech_hidden.php
        solver.board = new int[][]{
                {2, 8, 0, 0, 0, 0, 4, 7, 3},
                {5, 3, 4, 8, 2, 7, 1, 9, 6},
                {0, 7, 1, 0, 3, 4, 0, 8, 0},
                {3, 0, 0, 5, 0, 0, 0, 4, 0},
                {0, 0, 0, 3, 4, 0, 0, 6, 0},
                {4, 6, 0, 7, 9, 0, 3, 1, 0},
                {0, 9, 0, 2, 0, 3, 6, 5, 4},
                {0, 0, 3, 0, 0, 9, 8, 2, 1},
                {0, 0, 0, 0, 8, 0, 9, 3, 7}
        };
        solver.calculateInitialCandidates();
        HiddenTriple hiddenTriple = hiddenTripleFinder.getHiddenTripleInBlock();

        assertEquals(hiddenTriple.field1.column, 1);
        assertEquals(hiddenTriple.field2.column, 1);
        assertEquals(hiddenTriple.field3.column, 2);

        assertEquals(hiddenTriple.field1.row, 7);
        assertEquals(hiddenTriple.field2.row, 8);
        assertEquals(hiddenTriple.field3.row, 8);

        assertTrue(hiddenTriple.candidates.contains(2));
        assertTrue(hiddenTriple.candidates.contains(4));
        assertTrue(hiddenTriple.candidates.contains(5));

    }
}
