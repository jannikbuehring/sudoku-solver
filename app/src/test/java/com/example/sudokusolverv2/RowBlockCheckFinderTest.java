package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.rowBlockCheck.RowBlockCheck;
import com.example.sudokusolverv2.solutionStrategies.rowBlockCheck.RowBlockCheckFinder;

import junit.framework.TestCase;

public class RowBlockCheckFinderTest extends TestCase {

    public void testRowBlockCheck() {
        Solver solver = new Solver();
        RowBlockCheckFinder rowBlockCheckFinder = new RowBlockCheckFinder();
        rowBlockCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {1, 2, 0, 0, 5, 6, 0, 8, 0},
                {0, 0, 5, 9, 0, 1, 0, 0, 6},
                {0, 0, 6, 0, 0, 2, 1, 0, 5},
                {0, 1, 2, 0, 0, 0, 4, 0, 7},
                {0, 3, 0, 1, 0, 0, 0, 0, 0},
                {7, 6, 9, 0, 2, 0, 0, 1, 3},
                {0, 0, 7, 0, 1, 8, 0, 9, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 3, 0, 7, 0}
        };
        solver.calculateInitialCandidates();
        RowBlockCheck rowBlockCheck = rowBlockCheckFinder.rowBlockCheck();

        assertEquals(rowBlockCheck.candidate1.row, 3);
        assertEquals(rowBlockCheck.candidate1.column, 4);

        assertEquals(rowBlockCheck.candidate2.row, 3);
        assertEquals(rowBlockCheck.candidate2.column, 5);
    }

    public void testApplyRowBlockCheckToBlock() {
        Solver solver = new Solver();
        RowBlockCheckFinder rowBlockCheckFinder = new RowBlockCheckFinder();
        rowBlockCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {1, 2, 0, 0, 5, 6, 0, 8, 0},
                {0, 0, 5, 9, 0, 1, 0, 0, 6},
                {0, 0, 6, 0, 0, 2, 1, 0, 5},
                {0, 1, 2, 0, 0, 0, 4, 0, 7},
                {0, 3, 0, 1, 0, 0, 0, 0, 0},
                {7, 6, 9, 0, 2, 0, 0, 1, 3},
                {0, 0, 7, 0, 1, 8, 0, 9, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 3, 0, 7, 0}
        };
        solver.calculateInitialCandidates();
        rowBlockCheckFinder.applyRowBlockCheckToBlock();

        assertEquals(solver.calculatedCandidates.get(4 * 9 + 4).size(), 3);
        assertEquals(solver.calculatedCandidates.get(4 * 9 + 5).size(), 3);
    }

    public void testColumnBlockCheck() {
        Solver solver = new Solver();
        RowBlockCheckFinder rowBlockCheckFinder = new RowBlockCheckFinder();
        rowBlockCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {1, 2, 0, 0, 5, 6, 0, 8, 0},
                {0, 0, 5, 9, 0, 1, 0, 0, 6},
                {0, 0, 6, 0, 0, 2, 1, 0, 5},
                {0, 1, 2, 0, 0, 0, 4, 0, 7},
                {0, 3, 0, 1, 0, 0, 0, 0, 0},
                {7, 6, 9, 0, 2, 0, 0, 1, 3},
                {0, 0, 7, 0, 1, 8, 0, 9, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 3, 0, 7, 0}
        };
        solver.calculateInitialCandidates();
        RowBlockCheck rowBlockCheck = rowBlockCheckFinder.columnBlockCheck();

        assertEquals(rowBlockCheck.candidate1.row, 6);
        assertEquals(rowBlockCheck.candidate1.column, 1);

        assertEquals(rowBlockCheck.candidate2.row, 7);
        assertEquals(rowBlockCheck.candidate2.column, 1);

        assertEquals(rowBlockCheck.candidate3.row, 8);
        assertEquals(rowBlockCheck.candidate2.column, 1);
    }

    public void testApplyColumnBlockCheckToBlock() {
        Solver solver = new Solver();
        RowBlockCheckFinder rowBlockCheckFinder = new RowBlockCheckFinder();
        rowBlockCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {1, 2, 0, 0, 5, 6, 0, 8, 0},
                {0, 0, 5, 9, 0, 1, 0, 0, 6},
                {0, 0, 6, 0, 0, 2, 1, 0, 5},
                {0, 1, 2, 0, 0, 0, 4, 0, 7},
                {0, 3, 0, 1, 0, 0, 0, 0, 0},
                {7, 6, 9, 0, 2, 0, 0, 1, 3},
                {0, 0, 7, 0, 1, 8, 0, 9, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 3, 0, 7, 0}
        };
        solver.calculateInitialCandidates();
        rowBlockCheckFinder.applyColumnBlockCheckToBlock();

        assertEquals(solver.calculatedCandidates.get(6 * 9).size(), 4);
        assertEquals(solver.calculatedCandidates.get(7 * 9).size(), 5);
        assertEquals(solver.calculatedCandidates.get(8 * 9).size(), 4);
    }
}
