package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;

public class HiddenQuadFinder {

    private Solver solver;

    private  boolean checkIfHiddenQuadCanRemoveCandidatesFromRow(HiddenQuad hiddenQuad) {
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenQuad.field1.column || c == hiddenQuad.field2.column
                    || c == hiddenQuad.field3.column || c == hiddenQuad.field4.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, return true
                for (Integer candidate : hiddenQuad.candidates) {
                    if (solver.calculatedCandidates.get(hiddenQuad.field1.row * 9 + c).contains(candidate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private  boolean checkIfHiddenQuadCanRemoveCandidatesFromColumn(HiddenQuad hiddenQuad) {
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden triple
            if (r == hiddenQuad.field1.row || r == hiddenQuad.field2.row
                    || r == hiddenQuad.field3.row || r == hiddenQuad.field4.row) {
                // if the field contains other candidates which are not in the
                // hidden triple, return true
                for (Integer candidate : hiddenQuad.candidates) {
                    if (solver.calculatedCandidates.get(r * 9 + hiddenQuad.field1.column).contains(candidate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private  boolean checkIfHiddenQuadCanRemoveCandidatesFromBlock(HiddenQuad hiddenQuad) {
        int boxRow = hiddenQuad.field1.row / 3;
        int boxCol = hiddenQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden pair
                if (r == hiddenQuad.field1.row && c == hiddenQuad.field1.column
                        || r == hiddenQuad.field2.row && c == hiddenQuad.field2.column
                        || r == hiddenQuad.field3.row && c == hiddenQuad.field3.column
                        || r == hiddenQuad.field4.row && c == hiddenQuad.field4.column) {
                    for (Integer candidate : hiddenQuad.candidates) {
                        if (solver.calculatedCandidates.get(r * 9 + c).contains(candidate)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void applyHiddenQuadToRow() {
        HiddenQuad hiddenQuad = getHiddenQuadInRow();
        if (hiddenQuad == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenQuad.field1.column || c == hiddenQuad.field2.column
                    || c == hiddenQuad.field3.column || c == hiddenQuad.field4.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, remove them
                solver.calculatedCandidates.get(hiddenQuad.field1.row * 9 + c)
                        .removeAll(hiddenQuad.candidates);
                solver.personalCandidates.get(hiddenQuad.field1.row * 9 + c)
                        .removeAll(hiddenQuad.candidates);
            }
        }
    }

    public void applyHiddenQuadToColumn() {
        HiddenQuad hiddenQuad = getHiddenQuadInColumn();
        if (hiddenQuad == null) {
            return;
        }
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden triple
            if (r == hiddenQuad.field1.row || r == hiddenQuad.field2.row
                    || r == hiddenQuad.field3.row || r == hiddenQuad.field4.row) {
                // if the field contains other candidates which are not in the
                // hidden triple, remove them
                solver.calculatedCandidates.get(r * 9 + hiddenQuad.field1.column)
                        .removeAll(hiddenQuad.candidates);
                solver.personalCandidates.get(r * 9 + hiddenQuad.field1.column)
                        .removeAll(hiddenQuad.candidates);
            }
        }
    }

    public void applyHiddenQuadToBlock() {
        HiddenQuad hiddenQuad = getHiddenQuadInBlock();
        if (hiddenQuad == null) {
            return;
        }
        int boxRow = hiddenQuad.field1.row / 3;
        int boxCol = hiddenQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden pair
                if (r == hiddenQuad.field1.row && c == hiddenQuad.field1.column
                        || r == hiddenQuad.field2.row && c == hiddenQuad.field2.column
                        || r == hiddenQuad.field3.row && c == hiddenQuad.field3.column
                        || r == hiddenQuad.field4.row && c == hiddenQuad.field4.column) {
                    solver.calculatedCandidates.get(r * 9 + c)
                            .removeAll(hiddenQuad.candidates);
                    solver.personalCandidates.get(r * 9 + c)
                            .removeAll(hiddenQuad.candidates);
                }
            }
        }
    }

    public HiddenQuad getHiddenQuadInRow() {
        return null;
    }

    public HiddenQuad getHiddenQuadInColumn() {
        return null;
    }

    public HiddenQuad getHiddenQuadInBlock() {
        return null;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
