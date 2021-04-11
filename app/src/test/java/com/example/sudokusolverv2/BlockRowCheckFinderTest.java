package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.blockRowCheck.BlockRowCheck;
import com.example.sudokusolverv2.solutionStrategies.blockRowCheck.BlockRowCheckFinder;

import junit.framework.TestCase;

public class BlockRowCheckFinderTest extends TestCase {

    public void testGetBlockRowCheckInRow() {
        Solver solver = new Solver();
        BlockRowCheckFinder blockRowCheckFinder = new BlockRowCheckFinder();
        blockRowCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        // the example is missing candidates in (0,7) and (0,8)
        // there is a valid block row check in row 0 as well
        solver.board = new int[][]{
                {0, 0, 0, 9, 2, 3, 1, 0, 0},
                {5, 0, 7, 0, 0, 0, 0, 8, 0},
                {2, 0, 1, 0, 8, 0, 0, 0, 0},
                {0, 4, 0, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 0, 6, 0, 0, 4, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 0, 9}
        };
        solver.calculateInitialCandidates();
        BlockRowCheck blockRowCheck = blockRowCheckFinder.getBlockRowCheckInRow();

        assertEquals(blockRowCheck.candidate1.row, 0);
        assertEquals(blockRowCheck.candidate1.column, 0);

        assertEquals(blockRowCheck.candidate2.row, 0);
        assertEquals(blockRowCheck.candidate2.column, 2);
    }

    public void testApplyBlockRowCheckToRow() {
        Solver solver = new Solver();
        BlockRowCheckFinder blockRowCheckFinder = new BlockRowCheckFinder();
        blockRowCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        // the example is missing candidates in (0,7) and (0,8)
        // there is a valid block row check in row 0 as well
        solver.board = new int[][]{
                {0, 0, 0, 9, 2, 3, 1, 0, 0},
                {5, 0, 7, 0, 0, 0, 0, 8, 0},
                {2, 0, 1, 0, 8, 0, 0, 0, 0},
                {0, 4, 0, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 0, 6, 0, 0, 4, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 0, 9}
        };
        solver.calculateInitialCandidates();
        blockRowCheckFinder.applyBlockRowCheckToRow();

        assertEquals(solver.calculatedCandidates.get(7).size(), 3);
        assertEquals(solver.calculatedCandidates.get(8).size(), 3);
    }

    public void testGetBlockRowCheckInColumn() {
        Solver solver = new Solver();
        BlockRowCheckFinder blockRowCheckFinder = new BlockRowCheckFinder();
        blockRowCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {0, 0, 0, 9, 2, 3, 1, 0, 0},
                {5, 0, 7, 0, 0, 0, 0, 8, 0},
                {2, 0, 1, 0, 8, 0, 0, 0, 0},
                {0, 4, 0, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 0, 6, 0, 0, 4, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 0, 9}
        };
        solver.calculateInitialCandidates();
        BlockRowCheck blockRowCheck = blockRowCheckFinder.getBlockRowCheckInColumn();

        assertEquals(blockRowCheck.candidate1.row, 1);
        assertEquals(blockRowCheck.candidate1.column, 1);

        assertEquals(blockRowCheck.candidate2.row, 2);
        assertEquals(blockRowCheck.candidate2.column, 1);
    }

    public void testApplyBlockRowCheckToColumn() {
        Solver solver = new Solver();
        BlockRowCheckFinder blockRowCheckFinder = new BlockRowCheckFinder();
        blockRowCheckFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-1/
        solver.board = new int[][]{
                {0, 0, 0, 9, 2, 3, 1, 0, 0},
                {5, 0, 7, 0, 0, 0, 0, 8, 0},
                {2, 0, 1, 0, 8, 0, 0, 0, 0},
                {0, 4, 0, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 0, 6, 0, 0, 4, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 0, 9}
        };
        solver.calculateInitialCandidates();
        blockRowCheckFinder.applyBlockRowCheckToColumn();

        assertEquals(solver.calculatedCandidates.get(4 * 9 + 1).size(), 4);
    }
}
