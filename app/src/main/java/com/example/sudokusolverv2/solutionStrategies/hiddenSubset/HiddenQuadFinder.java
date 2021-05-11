package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class HiddenQuadFinder {

    //TODO: fix calculation time

    private Solver solver;

    private boolean checkIfHiddenQuadCanRemoveCandidatesFromRow(HiddenQuad hiddenQuad) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the quad triple
            if (c == hiddenQuad.field1.column || c == hiddenQuad.field2.column
                    || c == hiddenQuad.field3.column || c == hiddenQuad.field4.column) {
                // if the field contains other candidates which are not in the
                // hidden quad, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(hiddenQuad.field1.row * 9 + c).contains(candidate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkIfHiddenQuadCanRemoveCandidatesFromColumn(HiddenQuad hiddenQuad) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden quad
            if (r == hiddenQuad.field1.row || r == hiddenQuad.field2.row
                    || r == hiddenQuad.field3.row || r == hiddenQuad.field4.row) {
                // if the field contains other candidates which are not in the
                // hidden quad, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(r * 9 + hiddenQuad.field1.column).contains(candidate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkIfHiddenQuadCanRemoveCandidatesFromBlock(HiddenQuad hiddenQuad) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        int boxRow = hiddenQuad.field1.row / 3;
        int boxCol = hiddenQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden quad
                if (r == hiddenQuad.field1.row && c == hiddenQuad.field1.column
                        || r == hiddenQuad.field2.row && c == hiddenQuad.field2.column
                        || r == hiddenQuad.field3.row && c == hiddenQuad.field3.column
                        || r == hiddenQuad.field4.row && c == hiddenQuad.field4.column) {
                    for (Integer candidate : otherCandidates) {
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
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden quad
            if (c == hiddenQuad.field1.column || c == hiddenQuad.field2.column
                    || c == hiddenQuad.field3.column || c == hiddenQuad.field4.column) {
                // if the field contains other candidates which are not in the
                // hidden quad, remove them
                solver.calculatedCandidates.get(hiddenQuad.field1.row * 9 + c)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(hiddenQuad.field1.row * 9 + c)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenQuadToColumn() {
        HiddenQuad hiddenQuad = getHiddenQuadInColumn();
        if (hiddenQuad == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden quad
            if (r == hiddenQuad.field1.row || r == hiddenQuad.field2.row
                    || r == hiddenQuad.field3.row || r == hiddenQuad.field4.row) {
                // if the field contains other candidates which are not in the
                // hidden quad, remove them
                solver.calculatedCandidates.get(r * 9 + hiddenQuad.field1.column)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(r * 9 + hiddenQuad.field1.column)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenQuadToBlock() {
        HiddenQuad hiddenQuad = getHiddenQuadInBlock();
        if (hiddenQuad == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenQuad.candidates);
        int boxRow = hiddenQuad.field1.row / 3;
        int boxCol = hiddenQuad.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden quad
                if (r == hiddenQuad.field1.row && c == hiddenQuad.field1.column
                        || r == hiddenQuad.field2.row && c == hiddenQuad.field2.column
                        || r == hiddenQuad.field3.row && c == hiddenQuad.field3.column
                        || r == hiddenQuad.field4.row && c == hiddenQuad.field4.column) {
                    solver.calculatedCandidates.get(r * 9 + c)
                            .removeAll(otherCandidates);
                    solver.personalCandidates.get(r * 9 + c)
                            .removeAll(otherCandidates);
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
