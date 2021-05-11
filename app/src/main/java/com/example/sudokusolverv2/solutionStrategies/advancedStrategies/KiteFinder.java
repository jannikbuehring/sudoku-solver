package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;

public class KiteFinder {

    private Solver solver;

    private boolean checkIfKiteCanRemoveCandidates(Kite kite) {
        if (kite.field.candidateSet.contains(kite.value)) {
            return true;
        }
        return false;
    }

    public void applyKite(Kite kite) {
        if (kite.field.candidateSet.contains(kite.value)) {
            solver.calculatedCandidates.get(kite.field.row * 9 + kite.field.column)
                    .remove(kite.value);
            solver.personalCandidates.get(kite.field.row * 9 + kite.field.column)
                    .remove(kite.value);
        }
    }

    private Kite findMatchingKite(KitePair kitePair) {
        for (int c = 0; c < 9; c++) {
            if (solver.countCandidateOccurrencesInColumn(c, kitePair.value) == 2) {
                ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInColumn(c, kitePair.value);
                FieldCandidates field1 = fields.get(0);
                FieldCandidates field2 = fields.get(1);
                if (!kitePair.field1.equals(field1) && !kitePair.field1.equals(field2)
                        && !kitePair.field2.equals(field1) && !kitePair.field2.equals(field2)) {
                    KitePair kitePair2 = new KitePair(field1, field2, kitePair.value);
                    Kite kite = null;
                    if (kitePair.field1.isInSameBlock(field1)) {
                        FieldCandidates fieldToRemoveFrom = new FieldCandidates(field2.row, kitePair.field2.column, solver.calculatedCandidates.get(field2.row * 9 + kitePair.field2.column));
                        kite = new Kite(kitePair, kitePair2, fieldToRemoveFrom, kitePair.value);
                    } else if (kitePair.field1.isInSameBlock(field2)) {
                        FieldCandidates fieldToRemoveFrom = new FieldCandidates(field1.row, kitePair.field2.column, solver.calculatedCandidates.get(field1.row * 9 + kitePair.field2.column));
                        kite = new Kite(kitePair, kitePair2, fieldToRemoveFrom, kitePair.value);
                    } else if (kitePair.field2.isInSameBlock(field1)) {
                        FieldCandidates fieldToRemoveFrom = new FieldCandidates(field2.row, kitePair.field1.column, solver.calculatedCandidates.get(field2.row * 9 + kitePair.field1.column));
                        kite = new Kite(kitePair, kitePair2, fieldToRemoveFrom, kitePair.value);
                    } else if (kitePair.field2.isInSameBlock(field2)) {
                        FieldCandidates fieldToRemoveFrom = new FieldCandidates(field1.row, kitePair.field1.column, solver.calculatedCandidates.get(field1.row * 9 + kitePair.field1.column));
                        kite = new Kite(kitePair, kitePair2, fieldToRemoveFrom, kitePair.value);
                    }

                    if (kite != null) {
                        if (checkIfKiteCanRemoveCandidates(kite)) {
                            return kite;
                        }
                    }
                }
            }
        }

        return null;
    }

    public Kite getKite() {
        for (int i = 1; i < 10; i++) {
            for (int r = 0; r < 9; r++) {
                if (solver.countCandidateOccurrencesInRow(r, i) == 2) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInRow(r, i);
                    FieldCandidates field1 = fields.get(0);
                    FieldCandidates field2 = fields.get(1);

                    KitePair kitePair = new KitePair(field1, field2, i);
                    if (findMatchingKite(kitePair) != null) {
                        Kite kite = findMatchingKite(kitePair);
                        return kite;
                    }
                }
            }
        }

        return null;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
