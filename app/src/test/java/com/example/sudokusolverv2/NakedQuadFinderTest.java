package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedQuad;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedQuadFinder;

import junit.framework.TestCase;

public class NakedQuadFinderTest extends TestCase {

    public void testGetNakedQuadInRow() {
        Solver solver = new Solver();
        NakedQuadFinder nakedQuadFinder = new NakedQuadFinder();
        nakedQuadFinder.setSolver(solver);
        // https://sudokusolver.app/nakedquad.html
        solver.board = new int[][]{
                {0, 9, 5, 6, 0, 0, 0, 0, 7},
                {7, 1, 2, 0, 4, 0, 0, 0, 6},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 4, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 9, 0, 7, 2, 0, 4},
                {2, 0, 0, 4, 6, 8, 0, 1, 9},
                {0, 2, 0, 0, 0, 6, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 9, 0, 2},
                {0, 8, 0, 5, 0, 0, 0, 7, 1}
        };
        solver.calculateInitialCandidates();
        NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInRow();

        assertEquals(nakedQuad.candidateUnion.size(), 4);
        assertEquals(nakedQuad.field1.row, 2);

        assertEquals(nakedQuad.field1.column, 0);
        assertEquals(nakedQuad.field2.column, 1);
        assertEquals(nakedQuad.field3.column, 2);
        assertEquals(nakedQuad.field4.column, 8);

    }

    public void testGetNakedQuadInColumn() {
        Solver solver = new Solver();
        NakedQuadFinder nakedQuadFinder = new NakedQuadFinder();
        nakedQuadFinder.setSolver(solver);
        // http://www.manifestmaster.com/jetsudoku/nakedQuad.html
        // the example was not right and had to be adjusted (added 7s to make it a real naked quad)
        solver.board = new int[][]{
                {0, 0, 0, 0, 9, 0, 0, 0, 0},
                {0, 0, 0, 0, 3, 1, 6, 0, 0},
                {0, 0, 0, 0, 4, 8, 0, 9, 0},
                {7, 1, 9, 8, 6, 3, 4, 5, 2},
                {6, 0, 0, 0, 7, 0, 0, 3, 0},
                {2, 0, 0, 0, 1, 0, 7, 6, 0},
                {1, 0, 0, 0, 2, 7, 0, 8, 6},
                {8, 6, 0, 0, 5, 9, 2, 0, 7},
                {0, 0, 0, 1, 8, 6, 0, 4, 5}
        };
        solver.calculateInitialCandidates();
        NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInColumn();

        assertEquals(nakedQuad.candidateUnion.size(), 4);
        assertEquals(nakedQuad.field1.column, 2);

        assertEquals(nakedQuad.field1.row, 4);
        assertEquals(nakedQuad.field2.row, 5);
        assertEquals(nakedQuad.field3.row, 6);
        assertEquals(nakedQuad.field4.row, 7);

    }

    public void testGetNakedQuadInBlock() {
        Solver solver = new Solver();
        NakedQuadFinder nakedQuadFinder = new NakedQuadFinder();
        nakedQuadFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {0, 0, 0, 7, 1, 0, 2, 5, 0},
                {0, 3, 1, 6, 0, 0, 0, 0, 8},
                {0, 5, 7, 9, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 7, 0, 0, 6, 2, 1, 0, 5},
                {0, 0, 6, 0, 9, 7, 8, 0, 2},
                {0, 0, 9, 2, 0, 1, 0, 6, 0},
                {0, 0, 0, 0, 7, 9, 3, 2, 1},
                {0, 0, 0, 0, 0, 6, 0, 8, 9}
        };
        solver.calculateInitialCandidates();
        NakedQuad nakedQuad = nakedQuadFinder.getNakedQuadInBlock();

        assertEquals(nakedQuad.candidateUnion.size(), 4);

        assertEquals(nakedQuad.field1.row, 6);
        assertEquals(nakedQuad.field1.column, 1);

        assertEquals(nakedQuad.field2.row, 7);
        assertEquals(nakedQuad.field2.column, 0);

        assertEquals(nakedQuad.field3.row, 7);
        assertEquals(nakedQuad.field3.column, 1);

        assertEquals(nakedQuad.field4.row, 7);
        assertEquals(nakedQuad.field4.column, 2);

    }

    public void testApplyNakedQuadToRow() {
        Solver solver = new Solver();
        NakedQuadFinder nakedQuadFinder = new NakedQuadFinder();
        nakedQuadFinder.setSolver(solver);
        // https://sudokusolver.app/nakedquad.html
        solver.board = new int[][]{
                {0, 9, 5, 6, 0, 0, 0, 0, 7},
                {7, 1, 2, 0, 4, 0, 0, 0, 6},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 4, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 9, 0, 7, 2, 0, 4},
                {2, 0, 0, 4, 6, 8, 0, 1, 9},
                {0, 2, 0, 0, 0, 6, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 9, 0, 2},
                {0, 8, 0, 5, 0, 0, 0, 7, 1}
        };
        solver.calculateInitialCandidates();
        nakedQuadFinder.applyNakedQuadToRow();

        assertEquals(solver.calculatedCandidates.get(2 * 9 + 3).size(), 3);

    }

    public void testApplyNakedQuadToColumn() {
        Solver solver = new Solver();
        NakedQuadFinder nakedQuadFinder = new NakedQuadFinder();
        nakedQuadFinder.setSolver(solver);
        // http://www.manifestmaster.com/jetsudoku/nakedQuad.html
        // the example was not right and had to be adjusted (added 7s to make it a real naked quad)
        solver.board = new int[][]{
                {0, 0, 0, 0, 9, 0, 0, 0, 0},
                {0, 0, 0, 0, 3, 1, 6, 0, 0},
                {0, 0, 0, 0, 4, 8, 0, 9, 0},
                {7, 1, 9, 8, 6, 3, 4, 5, 2},
                {6, 0, 0, 0, 7, 0, 0, 3, 0},
                {2, 0, 0, 0, 1, 0, 7, 6, 0},
                {1, 0, 0, 0, 2, 7, 0, 8, 6},
                {8, 6, 0, 0, 5, 9, 2, 0, 7},
                {0, 0, 0, 1, 8, 6, 0, 4, 5}
        };
        solver.calculateInitialCandidates();
        nakedQuadFinder.applyNakedQuadToColumn();

        assertEquals(solver.calculatedCandidates.get(2).size(), 4);
        assertEquals(solver.calculatedCandidates.get(9 + 2).size(), 2);
        assertEquals(solver.calculatedCandidates.get(2 * 9 + 2).size(), 4);
    }

    public void testApplyNakedQuadToBlock() {
        Solver solver = new Solver();
        NakedQuadFinder nakedQuadFinder = new NakedQuadFinder();
        nakedQuadFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {0, 0, 0, 7, 1, 0, 2, 5, 0},
                {0, 3, 1, 6, 0, 0, 0, 0, 8},
                {0, 5, 7, 9, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 7, 0, 0, 6, 2, 1, 0, 5},
                {0, 0, 6, 0, 9, 7, 8, 0, 2},
                {0, 0, 9, 2, 0, 1, 0, 6, 0},
                {0, 0, 0, 0, 7, 9, 3, 2, 1},
                {0, 0, 0, 0, 0, 6, 0, 8, 9}
        };
        solver.calculateInitialCandidates();
        nakedQuadFinder.applyNakedQuadToBlock();

        assertEquals(solver.calculatedCandidates.get(6 * 9).size(), 2);
        assertEquals(solver.calculatedCandidates.get(8 * 9).size(), 4);
        assertEquals(solver.calculatedCandidates.get(8 * 9 + 1).size(), 2);
        assertEquals(solver.calculatedCandidates.get(8 * 9 + 2).size(), 2);
    }
}
