package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;

public class SwordfishFinder {

    private Solver solver;

    private boolean checkIfSwordfishCanRemoveCandidatesFromRow(Swordfish swordfish) {
        for (int c = 0; c < 9; c++) {
            if (c == swordfish.triplet1.field1.column || c == swordfish.triplet2.field1.column
                    || c == swordfish.triplet3.field1.column) {
                continue;
            }

            if (solver.calculatedCandidates.get(swordfish.triplet1.field1.row * 9 + c)
                    .contains(swordfish.value) || solver.calculatedCandidates
                    .get(swordfish.triplet1.field2.row * 9 + c).contains(swordfish.value)
                    || solver.calculatedCandidates.get(swordfish.triplet1.field3.row * 9 + c)
                    .contains(swordfish.value)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfSwordfishCanRemoveCandidatesFromColumn(Swordfish swordfish) {
        for (int r = 0; r < 9; r++) {
            if (r == swordfish.triplet1.field1.row || r == swordfish.triplet2.field1.row
                    || r == swordfish.triplet3.field1.row) {
                continue;
            }

            if (solver.calculatedCandidates.get(r * 9 + swordfish.triplet1.field1.column)
                    .contains(swordfish.value) || solver.calculatedCandidates.get(r * 9
                    + swordfish.triplet1.field2.column).contains(swordfish.value)
                    || solver.calculatedCandidates.get(r * 9 + swordfish.triplet1.field3.column)
                    .contains(swordfish.value)) {
                return true;
            }
        }
        return false;
    }

    public void applySwordfishToRow() {

    }

    public void applySwordfishToColumn() {

    }

    public Swordfish getSwordfishInRow() {
        for (int i = 1; i < 10; i++) {
            for (int r = 0; r < 9; r++) {
                if (solver.countCandidateOccurrencesInRow(r, i) == 3) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInRow(r, i);
                    FieldCandidates field1 = fields.get(0);
                    FieldCandidates field2 = fields.get(1);
                    FieldCandidates field3 = fields.get(2);

                    SwordfishTriplet firstTriplet = new SwordfishTriplet(field1, field2, field3, i);
                    if (findMatchingSwordfishTriplets(firstTriplet) != null) {
                        ArrayList<SwordfishTriplet> triplets = findMatchingSwordfishTriplets(firstTriplet);
                        Swordfish swordfish = new Swordfish(firstTriplet, triplets.get(0), triplets.get(1), i);
                        if (checkIfSwordfishCanRemoveCandidatesFromColumn(swordfish)) {
                            return swordfish;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Swordfish getSwordfishInColumn() {
        for (int i = 1; i < 10; i++) {
            for (int c = 0; c < 9; c++) {
                if (solver.countCandidateOccurrencesInColumn(c, i) == 3) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInColumn(c, i);
                    FieldCandidates field1 = fields.get(0);
                    FieldCandidates field2 = fields.get(1);
                    FieldCandidates field3 = fields.get(2);

                    SwordfishTriplet firstTriplet = new SwordfishTriplet(field1, field2, field3, i);
                    if (findMatchingSwordfishTriplets(firstTriplet) != null) {
                        ArrayList<SwordfishTriplet> triplets = findMatchingSwordfishTriplets(firstTriplet);
                        Swordfish swordfish = new Swordfish(firstTriplet, triplets.get(0), triplets.get(1), i);
                        if (checkIfSwordfishCanRemoveCandidatesFromRow(swordfish)) {
                            return swordfish;
                        }
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<SwordfishTriplet> findMatchingSwordfishTriplets(SwordfishTriplet swordfishTriplet) {
        // search vertically
        SwordfishTriplet secondTriplet = null;
        if (swordfishTriplet.field1.row == swordfishTriplet.field2.row) {
            for (int r = swordfishTriplet.field1.row + 1; r < 9; r++) {
                if (solver.calculatedCandidates.get(r * 9 + swordfishTriplet.field1.column)
                        .contains(swordfishTriplet.value) && solver.calculatedCandidates
                        .get(r * 9 + swordfishTriplet.field2.column).contains(swordfishTriplet.value)
                        && solver.calculatedCandidates.get(r * 9 + swordfishTriplet.field3.column)
                        .contains(swordfishTriplet.value)) {
                    if (solver.countCandidateOccurrencesInRow(r, swordfishTriplet.value) == 3) {
                        if (secondTriplet == null) {
                            FieldCandidates field1 = new FieldCandidates(r,
                                    swordfishTriplet.field1.column, solver.calculatedCandidates
                                    .get(r * 9 + swordfishTriplet.field1.column));
                            FieldCandidates field2 = new FieldCandidates(r,
                                    swordfishTriplet.field2.column, solver.calculatedCandidates
                                    .get(r * 9 + swordfishTriplet.field2.column));
                            FieldCandidates field3 = new FieldCandidates(r,
                                    swordfishTriplet.field3.column, solver.calculatedCandidates
                                    .get(r * 9 + swordfishTriplet.field3.column));
                            secondTriplet = new SwordfishTriplet(field1, field2, field3,
                                    swordfishTriplet.value);
                        } else {
                            FieldCandidates field1 = new FieldCandidates(r,
                                    swordfishTriplet.field1.column, solver.calculatedCandidates
                                    .get(r * 9 + swordfishTriplet.field1.column));
                            FieldCandidates field2 = new FieldCandidates(r,
                                    swordfishTriplet.field2.column, solver.calculatedCandidates
                                    .get(r * 9 + swordfishTriplet.field2.column));
                            FieldCandidates field3 = new FieldCandidates(r,
                                    swordfishTriplet.field3.column, solver.calculatedCandidates
                                    .get(r * 9 + swordfishTriplet.field3.column));
                            SwordfishTriplet thirdTriplet = new SwordfishTriplet(field1, field2,
                                    field3, swordfishTriplet.value);
                            ArrayList<SwordfishTriplet> triplets = new ArrayList<>();
                            triplets.add(secondTriplet);
                            triplets.add(thirdTriplet);
                            return triplets;
                        }
                    }
                }
            }
        }
        // search horizontally
        else if (swordfishTriplet.field1.column == swordfishTriplet.field2.column) {
            for (int c = swordfishTriplet.field1.column + 1; c < 9; c++) {
                if (solver.calculatedCandidates.get(swordfishTriplet.field1.row * 9 + c)
                        .contains(swordfishTriplet.value) && solver.calculatedCandidates
                        .get(swordfishTriplet.field2.row * 9 + c).contains(swordfishTriplet.value)
                        && solver.calculatedCandidates.get(swordfishTriplet.field3.row * 9 + c)
                        .contains(swordfishTriplet.value)) {
                    if (solver.countCandidateOccurrencesInColumn(c, swordfishTriplet.value) == 3) {
                        if (secondTriplet == null) {
                            FieldCandidates field1 = new FieldCandidates(swordfishTriplet.field1.row,
                                    c, solver.calculatedCandidates
                                    .get(swordfishTriplet.field1.row * 9 + c));
                            FieldCandidates field2 = new FieldCandidates(swordfishTriplet.field2.row,
                                    c, solver.calculatedCandidates
                                    .get(swordfishTriplet.field2.row * 9 + c));
                            FieldCandidates field3 = new FieldCandidates(swordfishTriplet.field3.row,
                                    c, solver.calculatedCandidates
                                    .get(swordfishTriplet.field3.row * 9 + c));
                            secondTriplet = new SwordfishTriplet(field1, field2, field3,
                                    swordfishTriplet.value);
                        } else {
                            FieldCandidates field1 = new FieldCandidates(swordfishTriplet.field1.row,
                                    c, solver.calculatedCandidates
                                    .get(swordfishTriplet.field1.row * 9 + c));
                            FieldCandidates field2 = new FieldCandidates(swordfishTriplet.field2.row,
                                    c, solver.calculatedCandidates
                                    .get(swordfishTriplet.field2.row * 9 + c));
                            FieldCandidates field3 = new FieldCandidates(swordfishTriplet.field3.row,
                                    c, solver.calculatedCandidates
                                    .get(swordfishTriplet.field3.row * 9 + c));
                            SwordfishTriplet thirdTriplet = new SwordfishTriplet(field1, field2,
                                    field3, swordfishTriplet.value);
                            ArrayList<SwordfishTriplet> triplets = new ArrayList<>();
                            triplets.add(secondTriplet);
                            triplets.add(thirdTriplet);
                            return triplets;
                        }
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
