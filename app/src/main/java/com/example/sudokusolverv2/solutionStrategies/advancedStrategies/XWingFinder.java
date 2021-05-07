package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;

public class XWingFinder {

    private Solver solver;

    private boolean checkIfXWingCanRemoveCandidatesFromRow(XWing xWing) {
        for (int c = 0; c < 9; c++) {
            if (c == xWing.pair1.field1.column || c == xWing.pair2.field1.column) {
                continue;
            }

            if (solver.calculatedCandidates.get(xWing.pair1.field1.row * 9 + c)
                    .contains(xWing.value) || solver.calculatedCandidates
                    .get(xWing.pair1.field2.row * 9 + c).contains(xWing.value)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkIfXWingCanRemoveCandidatesFromColumn(XWing xWing) {
        for (int r = 0; r < 9; r++) {
            if (r == xWing.pair1.field1.row || r == xWing.pair2.field1.row) {
                continue;
            }

            if (solver.calculatedCandidates.get(r * 9 + xWing.pair1.field1.column)
                    .contains(xWing.value) || solver.calculatedCandidates
                    .get(r * 9 + xWing.pair1.field2.column).contains(xWing.value)) {
                return true;
            }
        }

        return false;
    }

    public void applyXWingToRow() {
        XWing xWing = getXWingInColumn();
        if (xWing == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {
            if (c == xWing.pair1.field1.column || c == xWing.pair2.field1.column) {
                continue;
            }

            solver.calculatedCandidates.get(xWing.pair1.field1.row * 9 + c)
                    .remove(xWing.value);
            solver.personalCandidates.get(xWing.pair1.field1.row * 9 + c)
                    .remove(xWing.value);

            solver.calculatedCandidates.get(xWing.pair1.field2.row * 9 + c)
                    .remove(xWing.value);
            solver.personalCandidates.get(xWing.pair1.field2.row * 9 + c)
                    .remove(xWing.value);
        }
    }

    public void applyXWingToColumn() {
        XWing xWing = getXWingInRow();
        if (xWing == null) {
            return;
        }
        for (int r = 0; r < 9; r++) {
            if (r == xWing.pair1.field1.row || r == xWing.pair2.field1.row) {
                continue;
            }

            solver.calculatedCandidates.get(r * 9 + xWing.pair1.field1.column)
                    .remove(xWing.value);
            solver.personalCandidates.get(r * 9 + xWing.pair1.field1.column)
                    .remove(xWing.value);


            solver.calculatedCandidates.get(r * 9 + xWing.pair1.field2.column)
                    .remove(xWing.value);
            solver.personalCandidates.get(r * 9 + xWing.pair1.field2.column)
                    .remove(xWing.value);

        }
    }


    public XWing getXWingInRow() {
        for (int i = 1; i < 10; i++) {
            for (int r = 0; r < 9; r++) {
                if (solver.countCandidateOccurrencesInRow(r, i) == 2) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInRow(r, i);
                    FieldCandidates field1 = fields.get(0);
                    FieldCandidates field2 = fields.get(1);

                    XWingPair firstPair = new XWingPair(field1, field2, i);
                    if (findMatchingXWingPair(firstPair) != null) {
                        XWingPair secondPair = findMatchingXWingPair(firstPair);
                        XWing xWing = new XWing(firstPair, secondPair, i);
                        if (checkIfXWingCanRemoveCandidatesFromColumn(xWing)) {
                            return xWing;
                        }
                    }
                }
            }
        }
        return null;
    }

    public XWing getXWingInColumn() {
        for (int i = 1; i < 10; i++) {
            for (int c = 0; c < 9; c++) {
                if (solver.countCandidateOccurrencesInColumn(c, i) == 2) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInColumn(c, i);
                    FieldCandidates field1 = fields.get(0);
                    FieldCandidates field2 = fields.get(1);

                    XWingPair firstPair = new XWingPair(field1, field2, i);
                    if (findMatchingXWingPair(firstPair) != null) {
                        XWingPair secondPair = findMatchingXWingPair(firstPair);
                        XWing xWing = new XWing(firstPair, secondPair, i);
                        if (checkIfXWingCanRemoveCandidatesFromRow(xWing)) {
                            return xWing;
                        }
                    }
                }
            }
        }
        return null;
    }

    private XWingPair findMatchingXWingPair(XWingPair xWingPair) {
        // search vertically
        if (xWingPair.field1.row == xWingPair.field2.row) {
            for (int r = xWingPair.field1.row + 1; r < 9; r++) {
                if (solver.calculatedCandidates.get(r * 9 + xWingPair.field1.column)
                        .contains(xWingPair.value) && solver.calculatedCandidates
                        .get(r * 9 + xWingPair.field2.column).contains(xWingPair.value)) {
                    if (solver.countCandidateOccurrencesInRow(r, xWingPair.value) == 2) {
                        FieldCandidates field1 = new FieldCandidates(r, xWingPair.field1.column,
                                solver.calculatedCandidates.get(r * 9 + xWingPair.field1.column));
                        FieldCandidates field2 = new FieldCandidates(r, xWingPair.field2.column,
                                solver.calculatedCandidates.get(r * 9 + xWingPair.field2.column));
                        return new XWingPair(field1, field2, xWingPair.value);
                    }
                }
            }
        }
        // search horizontally
        else if (xWingPair.field1.column == xWingPair.field2.column) {
            for (int c = xWingPair.field1.column + 1; c < 9; c++) {
                if (solver.calculatedCandidates.get(xWingPair.field1.row * 9 + c)
                        .contains(xWingPair.value) && solver.calculatedCandidates
                        .get(xWingPair.field2.row * 9 + c).contains(xWingPair.value)) {
                    if (solver.countCandidateOccurrencesInColumn(c, xWingPair.value) == 2) {
                        FieldCandidates field1 = new FieldCandidates(xWingPair.field1.row, c,
                                solver.calculatedCandidates.get(xWingPair.field1.row * 9 + c));
                        FieldCandidates field2 = new FieldCandidates(xWingPair.field2.row, c,
                                solver.calculatedCandidates.get(xWingPair.field2.row * 9 + c));
                        return new XWingPair(field1, field2, xWingPair.value);
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
