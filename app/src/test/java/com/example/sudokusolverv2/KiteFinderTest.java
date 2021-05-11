package com.example.sudokusolverv2;



import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.Kite;
import com.example.sudokusolverv2.solutionStrategies.advancedStrategies.KiteFinder;

import junit.framework.TestCase;

public class KiteFinderTest extends TestCase {

    public void testGetKite() {
        Solver solver = new Solver();
        KiteFinder kiteFinder = new KiteFinder();
        kiteFinder.setSolver(solver);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-2/
        solver.board = new int[][]{
                {6, 4, 0, 8, 5, 3, 0, 9, 0},
                {9, 0, 0, 6, 4, 1, 8, 5, 0},
                {1, 5, 8, 7, 2, 9, 6, 4, 3},
                {3, 2, 5, 1, 6, 7, 9, 8, 4},
                {4, 0, 1, 9, 8, 2, 3, 0, 5},
                {8, 9, 0, 4, 3, 5, 0, 2, 0},
                {2, 0, 9, 5, 1, 8, 4, 0, 0},
                {7, 1, 0, 2, 9, 4, 5, 0, 8},
                {5, 8, 4, 3, 7, 6, 2, 1, 9}
        };
        solver.calculateInitialCandidates();
        Kite kite = kiteFinder.getKite();

        assertEquals(kite.value, 6);

        assertEquals(kite.field.row, 7);
        assertEquals(kite.field.column, 7);

        Solver solver2 = new Solver();
        KiteFinder kiteFinder2 = new KiteFinder();
        kiteFinder2.setSolver(solver2);
        // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-2/
        solver2.board = new int[][]{
                {0, 0, 7, 3, 2, 0, 0, 5, 0},
                {8, 0, 9, 0, 6, 7, 0, 2, 4},
                {0, 2, 0, 9, 0, 0, 0, 6, 0},
                {5, 0, 0, 2, 0, 6, 0, 8, 3},
                {0, 8, 2, 0, 0, 0, 6, 1, 0},
                {9, 0, 0, 8, 0, 0, 2, 4, 5},
                {0, 9, 0, 0, 0, 2, 5, 7, 6},
                {2, 0, 0, 6, 0, 0, 8, 9, 1},
                {0, 1, 0, 0, 0, 9, 4, 3, 2}
        };
        solver2.calculateInitialCandidates();
        Kite kite2 = kiteFinder2.getKite();

        assertEquals(kite2.value, 7);

        assertEquals(kite2.field.row, 8);
        assertEquals(kite2.field.column, 4);
    }
}
