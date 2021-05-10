package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.HashSet;

public class ThirdEyeFinder {

    private Solver solver;

    public void applyThirdEye() {
        ThirdEye thirdEye = getThirdEye();
        if (thirdEye == null) {
            return;
        }
        HashSet<Integer> toRemove = thirdEye.thirdEye.candidateSet;
        toRemove.remove(thirdEye.value);

        solver.calculatedCandidates.get(thirdEye.thirdEye.row * 9 + thirdEye.thirdEye.column)
                .removeAll(toRemove);
        solver.personalCandidates.get(thirdEye.thirdEye.row * 9 + thirdEye.thirdEye.column)
                .removeAll(toRemove);
    }

    // https://www.thinkgym.de/r%C3%A4tselarten/sudoku/l%C3%B6sungsstrategien-2/
    public ThirdEye getThirdEye() {
        ThirdEye thirdEye = null;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                // only check fields without an assigned number
                if (solver.board[r][c] == 0) {
                    // third eye can only exist if no field has more than 3 candidates or less than 2
                    if (solver.countCandidates(r, c) > 3 || solver.countCandidates(r, c) < 2) {
                        return null;
                    }
                    // there can only be one field with 3 candidates
                    if (solver.countCandidates(r, c) == 3) {
                        if (thirdEye == null) {
                            thirdEye = new ThirdEye(new FieldCandidates(r, c,
                                    solver.calculatedCandidates.get(r * 9 + c)));
                        } else {
                            return null;
                        }

                    }
                }
            }
        }

        if (thirdEye == null) {
            return null;
        }
        else {
            for (int candidate : thirdEye.thirdEye.candidateSet) {
                if (solver.countCandidateOccurrencesInBlock(thirdEye.thirdEye.row,
                        thirdEye.thirdEye.column, candidate) == 3) {
                    thirdEye.value = candidate;
                }
            }
            return thirdEye;
        }


    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
