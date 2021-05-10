package com.example.sudokusolverv2;

import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.ThirdEye;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.ThirdEyeFinder;

import junit.framework.TestCase;

public class ThirdEyeFinderTest extends TestCase {

    public void testGetThirdEye() {
        Solver solver = new Solver();
        ThirdEyeFinder thirdEyeFinder = new ThirdEyeFinder();
        thirdEyeFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-2/drittes-auge/
        solver.board = new int[][]{
                {2, 0, 0, 7, 4, 3, 1, 6, 5},
                {3, 4, 6, 1, 8, 5, 9, 2, 7},
                {5, 7, 1, 6, 2, 9, 8, 3, 4},
                {6, 3, 0, 0, 1, 0, 0, 4, 9},
                {7, 0, 0, 4, 3, 6, 0, 1, 8},
                {1, 0, 4, 0, 9, 0, 3, 0, 6},
                {8, 6, 7, 3, 5, 1, 4, 9, 2},
                {9, 1, 2, 8, 6, 4, 0, 0, 3},
                {4, 5, 3, 9, 7, 2, 6, 8, 1}
        };
        /*
        // extra candidates
        solver.board = new int[][]{
                {0, 0, 8, 6, 0, 0, 2, 1, 9},
                {2, 0, 6, 9, 0, 0, 5, 3, 8},
                {9, 5, 1, 2, 3, 8, 4, 6, 7},
                {4, 8, 2, 0, 9, 0, 3, 7, 6},
                {0, 9, 0, 4, 6, 2, 1, 8, 5},
                {1, 6, 5, 3, 8, 7, 9, 2, 4},
                {8, 0, 0, 0, 2, 9, 6, 0, 3},
                {5, 0, 0, 8, 0, 6, 7, 9, 2},
                {6, 2, 9, 7, 0, 3, 8, 0, 1}
        };*/
        solver.calculateInitialCandidates();
        ThirdEye thirdEye = thirdEyeFinder.getThirdEye();

        assertEquals(thirdEye.value, 5);

        assertEquals(thirdEye.thirdEye.row, 3);
        assertEquals(thirdEye.thirdEye.column, 6);
    }
}
