package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenQuad;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenQuadFinder;

import junit.framework.TestCase;

public class HiddenQuadFinderTest extends TestCase {

    /*
    public void testGetHiddenQuadInRow() {
        Solver solver = new Solver();
        HiddenQuadFinder hiddenQuadFinder = new HiddenQuadFinder();
        hiddenQuadFinder.setSolver(solver);
        // could not find an example for this
        solver.board = new int[][]{
                {0, 0, 0, 1, 3, 7, 0, 0, 0},
                {7, 0, 0, 5, 9, 6, 1, 3, 0},
                {0, 0, 9, 0, 8, 0, 0, 6, 0},
                {0, 0, 3, 0, 2, 0, 0, 0, 0},
                {5, 0, 0, 8, 0, 0, 9, 2, 0},
                {0, 2, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 8},
                {8, 7, 4, 0, 0, 0, 0, 0, 0},
                {0, 6, 5, 3, 4, 8, 0, 0, 0}
        };
        solver.calculateInitialCandidates();
        HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInRow();

        assertEquals(hiddenQuad.field1.row, 2);
        assertEquals(hiddenQuad.field2.row, 2);

        assertEquals(hiddenQuad.field1.column, 3);
        assertEquals(hiddenQuad.field2.column, 5);

        assertTrue(hiddenQuad.candidates.contains(2));
        assertTrue(hiddenQuad.candidates.contains(4));
        assertTrue(hiddenQuad.candidates.contains(5));
        assertTrue(hiddenQuad.candidates.contains(8));
    }*/

    public void testGetHiddenQuadInColumn() {
        Solver solver = new Solver();
        HiddenQuadFinder hiddenQuadFinder = new HiddenQuadFinder();
        hiddenQuadFinder.setSolver(solver);
        // http://hodoku.sourceforge.net/de/tech_hidden.php
        solver.board = new int[][]{
                {0, 3, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 8, 0, 9, 0, 0, 0, 0},
                {4, 0, 0, 6, 0, 8, 0, 0, 0},
                {0, 0, 0, 5, 7, 6, 9, 4, 0},
                {0, 0, 0, 9, 8, 3, 5, 2, 0},
                {0, 0, 0, 1, 2, 4, 0, 0, 0},
                {2, 7, 6, 0, 0, 5, 1, 9, 0},
                {0, 0, 0, 7, 0, 9, 0, 0, 0},
                {0, 9, 5, 0, 0, 0, 4, 7, 0}
        };
        solver.calculateInitialCandidates();
        HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInColumn();

        assertEquals(hiddenQuad.field1.column, 8);
        assertEquals(hiddenQuad.field2.column, 8);
        assertEquals(hiddenQuad.field3.column, 8);
        assertEquals(hiddenQuad.field4.column, 8);

        assertEquals(hiddenQuad.field1.row, 0);
        assertEquals(hiddenQuad.field2.row, 1);
        assertEquals(hiddenQuad.field2.row, 2);
        assertEquals(hiddenQuad.field2.row, 7);

        assertTrue(hiddenQuad.candidates.contains(2));
        assertTrue(hiddenQuad.candidates.contains(4));
        assertTrue(hiddenQuad.candidates.contains(5));
        assertTrue(hiddenQuad.candidates.contains(9));
    }

    public void testGetHiddenQuadInBlock() {
        Solver solver = new Solver();
        HiddenQuadFinder hiddenQuadFinder = new HiddenQuadFinder();
        hiddenQuadFinder.setSolver(solver);
        // http://hodoku.sourceforge.net/de/tech_hidden.php
        solver.board = new int[][]{
                {8, 1, 6, 5, 7, 3, 2, 9, 4},
                {3, 9, 2, 0, 0, 0, 0, 0, 0},
                {4, 5, 7, 2, 0, 9, 0, 0, 6},
                {9, 4, 1, 0, 0, 0, 5, 6, 8},
                {7, 8, 5, 4, 9, 6, 1, 2, 3},
                {6, 2, 3, 8, 0, 0, 0, 4, 0},
                {2, 7, 9, 0, 0, 0, 0, 0, 1},
                {1, 3, 8, 0, 0, 0, 0, 7, 0},
                {5, 6, 4, 0, 0, 0, 0, 8, 2}
        };
        solver.calculateInitialCandidates();
        HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInBlock();

        assertEquals(hiddenQuad.field1.column, 4);
        assertEquals(hiddenQuad.field2.column, 5);
        assertEquals(hiddenQuad.field3.column, 4);
        assertEquals(hiddenQuad.field4.column, 5);

        assertEquals(hiddenQuad.field1.row, 6);
        assertEquals(hiddenQuad.field2.row, 6);
        assertEquals(hiddenQuad.field3.row, 7);
        assertEquals(hiddenQuad.field4.row, 7);

        assertTrue(hiddenQuad.candidates.contains(2));
        assertTrue(hiddenQuad.candidates.contains(4));
        assertTrue(hiddenQuad.candidates.contains(5));
        assertTrue(hiddenQuad.candidates.contains(8));
    }
}
