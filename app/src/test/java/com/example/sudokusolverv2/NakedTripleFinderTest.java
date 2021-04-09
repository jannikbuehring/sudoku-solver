package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedTriple;
import com.example.sudokusolverv2.solutionStrategies.nakedSubset.NakedTripleFinder;

import junit.framework.TestCase;

public class NakedTripleFinderTest extends TestCase {

    public void testGetNakedTripleInRow() {
        Solver solver = new Solver();
        NakedTripleFinder nakedTripleFinder = new NakedTripleFinder();
        nakedTripleFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {0, 7, 0, 0, 0, 0, 8, 0, 0},
                {0, 2, 0, 8, 0, 0, 9, 5, 0},
                {0, 0, 0, 0, 0, 9, 6, 0, 2},
                {0, 0, 0, 3, 0, 4, 2, 8, 9},
                {0, 0, 3, 9, 0, 0, 1, 6, 4},
                {4, 0, 0, 6, 1, 0, 5, 0, 0},
                {0, 4, 0, 2, 9, 6, 0, 1, 5},
                {0, 0, 0, 0, 0, 0, 0, 9, 6},
                {0, 0, 0, 5, 3, 0, 4, 2, 8}
        };
        solver.calculateInitialCandidates();
        NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInRow();

        assertEquals(nakedTriple.candidateUnion.size(), 3);
        assertEquals(nakedTriple.field1.row, 0);

        assertEquals(nakedTriple.field1.column, 3);
        assertEquals(nakedTriple.field2.column, 7);
        assertEquals(nakedTriple.field3.column, 8);
    }

    public void testGetNakedTripleInColumn() {
        Solver solver = new Solver();
        NakedTripleFinder nakedTripleFinder = new NakedTripleFinder();
        nakedTripleFinder.setSolver(solver);
        // https://www.sudoku9981.com/sudoku-solving/naked-triple.php
        solver.board = new int[][]{
                {2, 4, 0, 0, 3, 0, 0, 0, 1},
                {5, 9, 0, 0, 1, 0, 3, 2, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 4},
                {3, 5, 2, 1, 4, 6, 8, 9, 7},
                {4, 0, 0, 3, 8, 9, 5, 1, 2},
                {1, 8, 9, 5, 7, 2, 6, 4, 3},
                {0, 2, 0, 0, 9, 3, 1, 0, 0},
                {6, 0, 0, 0, 5, 1, 0, 0, 9},
                {9, 0, 0, 0, 6, 0, 0, 3, 0}
        };
        solver.calculateInitialCandidates();
        NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInColumn();

        assertEquals(nakedTriple.candidateUnion.size(), 3);
        assertEquals(nakedTriple.field1.column, 2);

        assertEquals(nakedTriple.field1.row, 0);
        assertEquals(nakedTriple.field2.row, 1);
        assertEquals(nakedTriple.field3.row, 4);
    }

    public void testGetNakedTripleInBlock() {
        Solver solver = new Solver();
        NakedTripleFinder nakedTripleFinder = new NakedTripleFinder();
        nakedTripleFinder.setSolver(solver);
        // https://www.livesudoku.com/helpart/intermediate/sudoku-naked-triple-pic1.png
        solver.board = new int[][]{
                {0, 0, 0, 0, 0, 6, 0, 0, 4},
                {0, 0, 0, 0, 1, 0, 0, 2, 0},
                {2, 0, 5, 8, 3, 4, 1, 9, 0},
                {0, 0, 0, 0, 8, 0, 0, 5, 9},
                {0, 0, 6, 7, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 8, 0, 4, 0, 3, 1, 2},
                {9, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 6, 0, 0, 0, 0}
        };
        solver.calculateInitialCandidates();
        NakedTriple nakedTriple = nakedTripleFinder.getNakedTripleInBlock();

        assertEquals(nakedTriple.candidateUnion.size(), 3);
        assertEquals(nakedTriple.field1.row, 0);
        assertEquals(nakedTriple.field1.column, 4);

        assertEquals(nakedTriple.field2.row, 1);
        assertEquals(nakedTriple.field2.column, 3);

        assertEquals(nakedTriple.field3.row, 1);
        assertEquals(nakedTriple.field3.column, 5);
    }

    public void testApplyNakedTripleToRow() {
        Solver solver = new Solver();
        NakedTripleFinder nakedTripleFinder = new NakedTripleFinder();
        nakedTripleFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {0, 7, 0, 0, 0, 0, 8, 0, 0},
                {0, 2, 0, 8, 0, 0, 9, 5, 0},
                {0, 0, 0, 0, 0, 9, 6, 0, 2},
                {0, 0, 0, 3, 0, 4, 2, 8, 9},
                {0, 0, 3, 9, 0, 0, 1, 6, 4},
                {4, 0, 0, 6, 1, 0, 5, 0, 0},
                {0, 4, 0, 2, 9, 6, 0, 1, 5},
                {0, 0, 0, 0, 0, 0, 0, 9, 6},
                {0, 0, 0, 5, 3, 0, 4, 2, 8}
        };
        solver.calculateInitialCandidates();
        nakedTripleFinder.applyNakedTripleToRow();
        assertEquals(solver.calculatedCandidates.get(0).size(), 3);
    }

    public void testApplyNakedTripleToColumn() {
        Solver solver = new Solver();
        NakedTripleFinder nakedTripleFinder = new NakedTripleFinder();
        nakedTripleFinder.setSolver(solver);
        // https://www.sudoku9981.com/sudoku-solving/naked-triple.php
        solver.board = new int[][]{
                {2, 4, 0, 0, 3, 0, 0, 0, 1},
                {5, 9, 0, 0, 1, 0, 3, 2, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 4},
                {3, 5, 2, 1, 4, 6, 8, 9, 7},
                {4, 0, 0, 3, 8, 9, 5, 1, 2},
                {1, 8, 9, 5, 7, 2, 6, 4, 3},
                {0, 2, 0, 0, 9, 3, 1, 0, 0},
                {6, 0, 0, 0, 5, 1, 0, 0, 9},
                {9, 0, 0, 0, 6, 0, 0, 3, 0}
        };
        solver.calculateInitialCandidates();
        nakedTripleFinder.applyNakedTripleToColumn();

        assertEquals(solver.calculatedCandidates.get(2 * 9 + 2).size(), 2);
    }

    public void testApplyNakedTripleToBlock() {
        Solver solver = new Solver();
        NakedTripleFinder nakedTripleFinder = new NakedTripleFinder();
        nakedTripleFinder.setSolver(solver);
        // https://www.livesudoku.com/helpart/intermediate/sudoku-naked-triple-pic1.png
        solver.board = new int[][]{
                {0, 0, 0, 0, 0, 6, 0, 0, 4},
                {0, 0, 0, 0, 1, 0, 0, 2, 0},
                {2, 0, 5, 8, 3, 4, 1, 9, 0},
                {0, 0, 0, 0, 8, 0, 0, 5, 9},
                {0, 0, 6, 7, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 8, 0, 4, 0, 3, 1, 2},
                {9, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 6, 0, 0, 0, 0}
        };
        solver.calculateInitialCandidates();
        nakedTripleFinder.applyNakedTripleToBlock();

        assertEquals(solver.calculatedCandidates.get(0 * 9 + 3).size(), 1);
    }
}
