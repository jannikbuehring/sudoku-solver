package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenQuad;
import com.example.sudokusolverv2.solutionStrategies.hiddenSubset.HiddenQuadFinder;

import junit.framework.TestCase;

public class HiddenQuadFinderTest extends TestCase {

    public void testGetHiddenQuadInRow() {
        Solver solver = new Solver();
        HiddenQuadFinder hiddenQuadFinder = new HiddenQuadFinder();
        hiddenQuadFinder.setSolver(solver);
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

        //Naked pair is in row 2
        assertEquals(hiddenQuad.field1.row, 2);
        assertEquals(hiddenQuad.field2.row, 2);

        //And in columns 3 and 5
        assertEquals(hiddenQuad.field1.column, 3);
        assertEquals(hiddenQuad.field2.column, 5);
    }

    public void testGetHiddenQuadInColumn() {
        Solver solver = new Solver();
        HiddenQuadFinder hiddenQuadFinder = new HiddenQuadFinder();
        hiddenQuadFinder.setSolver(solver);
        solver.board = new int[][]{
                {0, 0, 1, 4, 9, 0, 7, 0, 0},
                {0, 0, 4, 0, 0, 6, 9, 0, 0},
                {8, 0, 9, 0, 1, 7, 5, 4, 0},
                {2, 8, 0, 0, 7, 0, 0, 5, 9},
                {4, 1, 7, 9, 0, 0, 2, 6, 8},
                {5, 9, 0, 0, 2, 0, 0, 0, 0},
                {0, 4, 5, 0, 8, 0, 0, 0, 3},
                {0, 0, 8, 7, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 9, 8, 0, 0}
        };
        solver.calculateInitialCandidates();
        HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInColumn();

        //Naked pair is in col 4
        assertEquals(hiddenQuad.field1.column, 4);
        assertEquals(hiddenQuad.field2.column, 4);

        //And in rows 1 and 4
        assertEquals(hiddenQuad.field1.row, 1);
        assertEquals(hiddenQuad.field2.row, 4);
    }

    public void testGetHiddenQuadInBlock() {
        Solver solver = new Solver();
        HiddenQuadFinder hiddenQuadFinder = new HiddenQuadFinder();
        hiddenQuadFinder.setSolver(solver);
        solver.board = new int[][]{
                {0, 0, 1, 4, 9, 0, 7, 0, 0},
                {0, 0, 4, 0, 0, 6, 9, 0, 0},
                {8, 0, 9, 0, 1, 7, 5, 4, 0},
                {2, 8, 0, 0, 7, 0, 0, 5, 9},
                {4, 1, 7, 9, 0, 0, 2, 6, 8},
                {5, 9, 0, 0, 2, 0, 0, 0, 0},
                {0, 4, 5, 0, 8, 0, 0, 0, 3},
                {0, 0, 8, 7, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 9, 8, 0, 0}
        };
        solver.calculateInitialCandidates();
        HiddenQuad hiddenQuad = hiddenQuadFinder.getHiddenQuadInBlock();

        //Naked pair is in col 4
        assertEquals(hiddenQuad.field1.column, 8);
        assertEquals(hiddenQuad.field2.column, 8);

        //And in rows 1 and 4
        assertEquals(hiddenQuad.field1.row, 0);
        assertEquals(hiddenQuad.field2.row, 2);
    }
}
