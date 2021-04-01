package com.example.sudokusolverv2;

import com.example.sudokusolverv2.sudokuValidation.Validator;

import junit.framework.TestCase;

import org.apache.commons.lang3.SerializationUtils;

public class ValidatorTest extends TestCase {

    //TODO: Add new testing class for validator
    public void testPreCheckLessThan17Cells() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        // 16 entries (not enough for valid sudoku)
        solver.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 0, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        assertFalse(validator.preCheckLessThan17Cells());

        Solver solver2 = new Solver();
        validator.setSolver(solver2);
        // 17 entries (theoretically enough for valid sudoku)
        solver2.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 0, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        assertTrue(validator.preCheckLessThan17Cells());
    }

    public void testPreCheckMoreThan2NumbersNotOccurring() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        solver.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 0, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 0, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertFalse(validator.preCheckMoreThan2NumbersNotOccurring());

        Solver solver2 = new Solver();
        validator.setSolver(solver2);
        solver2.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertTrue(validator.preCheckMoreThan2NumbersNotOccurring());
    }

    public void testValidateRow() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        solver.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertTrue(validator.validateRow(1));
        assertFalse(validator.validateRow(2));
    }

    public void testValidateCol() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        solver.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 4, 0, 4, 0, 0}
        };
        assertTrue(validator.validateCol(0));
        assertFalse(validator.validateCol(4));
    }

    public void testValidateSubsquares() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        solver.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertTrue(validator.validateSubsquares());

        Solver solver2 = new Solver();
        validator.setSolver(solver2);
        solver2.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 4},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertFalse(validator.validateSubsquares());
    }

    public void testValidateBoard() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        solver.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertTrue(validator.validateBoard());

        Solver solver2 = new Solver();
        validator.setSolver(solver2);
        solver2.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 2, 0, 9, 0, 2, 0, 9},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 4, 0, 0, 6, 8},
                {0, 1, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertFalse(validator.validateBoard());
    }

    public void testCheckIfSudokuHasTooManySolutions() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        Solver solverUnsolved;
        solverUnsolved = (Solver) SerializationUtils.clone(solver);
        solver.board = new int[][]{
                {2, 9, 5, 7, 4, 3, 8, 6, 1},
                {4, 3, 1, 8, 6, 5, 9, 0, 0},
                {8, 7, 6, 1, 9, 2, 5, 4, 3},
                {3, 8, 7, 4, 5, 9, 2, 1, 6},
                {6, 1, 2, 3, 8, 7, 4, 9, 5},
                {5, 4, 9, 2, 1, 6, 7, 3, 8},
                {7, 6, 3, 5, 2, 4, 1, 8, 9},
                {9, 2, 8, 6, 7, 1, 3, 5, 4},
                {1, 5, 4, 9, 3, 8, 6, 0, 0}
        };
        assertTrue(validator.checkIfSudokuHasTooManySolutions(solverUnsolved.board));
    }

    public void testCheckIfSudokuHasOneSolution() {
        Solver solver = new Solver();
        Validator validator = new Validator();
        validator.setSolver(solver);
        solver.board = new int[][]{
                {1, 2, 3, 4, 5, 6, 0, 0, 0},
                {4, 5, 6, 7, 8, 1, 0, 0, 0},
                {7, 8, 9, 2, 3, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        assertFalse(validator.checkIfSudokuHasOneSolution());

        Solver solver2 = new Solver();
        validator.setSolver(solver2);
        solver2.board = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        assertTrue(validator.checkIfSudokuHasOneSolution());
    }
}