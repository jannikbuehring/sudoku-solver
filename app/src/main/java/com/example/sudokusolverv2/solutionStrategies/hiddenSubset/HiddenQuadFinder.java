package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class HiddenQuadFinder {

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

    private boolean checkForFourDistinctFields(ArrayList<FieldCandidates> fields,
                                               ArrayList<FieldCandidates> fields2,
                                               ArrayList<FieldCandidates> fields3,
                                               ArrayList<FieldCandidates> fields4) {
        ArrayList<FieldCandidates> distinctFields = new ArrayList<>();
        distinctFields.addAll(fields);

        for (FieldCandidates field : fields2) {
            if (!distinctFields.contains(field)) {
                distinctFields.add(field);
            }
        }

        for (FieldCandidates field : fields3) {
            if (!distinctFields.contains(field)) {
                distinctFields.add(field);
            }
        }

        for (FieldCandidates field : fields4) {
            if (!distinctFields.contains(field)) {
                distinctFields.add(field);
            }
        }

        if (distinctFields.size() == 4) {
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<FieldCandidates> findDistinctFields(ArrayList<FieldCandidates> fields,
                                                          ArrayList<FieldCandidates> fields2,
                                                          ArrayList<FieldCandidates> fields3,
                                                          ArrayList<FieldCandidates> fields4) {
        ArrayList<FieldCandidates> distinctFields = new ArrayList<>();
        distinctFields.addAll(fields);

        for (FieldCandidates field : fields2) {
            if (!distinctFields.contains(field)) {
                distinctFields.add(field);
            }
        }

        for (FieldCandidates field : fields3) {
            if (!distinctFields.contains(field)) {
                distinctFields.add(field);
            }
        }

        for (FieldCandidates field : fields4) {
            if (!distinctFields.contains(field)) {
                distinctFields.add(field);
            }
        }

        return distinctFields;
    }

    public HiddenQuad getHiddenQuadInRow() {
        for (int r = 0; r < 9; r++) {
            HashSet<Integer> possibleQuadNumbers = new HashSet<>();
            for (int i = 1; i < 10; i++) {
                if (solver.countCandidateOccurrencesInRow(r, i) >= 2
                        && solver.countCandidateOccurrencesInRow(r, i) <= 4) {
                    possibleQuadNumbers.add(i);
                }
            }

            if (possibleQuadNumbers.size() >= 3) {
                for (int possibleQuadNumber : possibleQuadNumbers) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInRow(r, possibleQuadNumber);
                    for (int possibleQuadNumber2 : possibleQuadNumbers) {
                        ArrayList<FieldCandidates> fields2 = solver.findCandidateOccurrencesInRow(r, possibleQuadNumber2);
                        for (int possibleQuadNumber3 : possibleQuadNumbers) {
                            ArrayList<FieldCandidates> fields3 = solver.findCandidateOccurrencesInRow(r, possibleQuadNumber3);
                            for (int possibleQuadNumber4 : possibleQuadNumbers) {
                                ArrayList<FieldCandidates> fields4 = solver.findCandidateOccurrencesInRow(r, possibleQuadNumber4);
                                if (possibleQuadNumber == possibleQuadNumber2 || possibleQuadNumber == possibleQuadNumber3
                                        || possibleQuadNumber2 == possibleQuadNumber3 || possibleQuadNumber == possibleQuadNumber4
                                        || possibleQuadNumber2 == possibleQuadNumber4 || possibleQuadNumber3 == possibleQuadNumber4) {
                                    continue;
                                }

                                if (checkForFourDistinctFields(fields, fields2, fields3, fields4)) {
                                    ArrayList<FieldCandidates> distinctFields = findDistinctFields(fields, fields2, fields3, fields4);
                                    HashSet<Integer> candidates = new HashSet<>();
                                    candidates.add(possibleQuadNumber);
                                    candidates.add(possibleQuadNumber2);
                                    candidates.add(possibleQuadNumber3);
                                    candidates.add(possibleQuadNumber4);
                                    HiddenQuad hiddenQuad = new HiddenQuad(distinctFields.get(0),
                                            distinctFields.get(1), distinctFields.get(2),
                                            distinctFields.get(3), candidates);
                                    if (checkIfHiddenQuadCanRemoveCandidatesFromRow(hiddenQuad)) {
                                        return hiddenQuad;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public HiddenQuad getHiddenQuadInColumn() {
        for (int c = 0; c < 9; c++) {
            HashSet<Integer> possibleQuadNumbers = new HashSet<>();
            for (int i = 1; i < 10; i++) {
                if (solver.countCandidateOccurrencesInColumn(c, i) >= 2
                        && solver.countCandidateOccurrencesInColumn(c, i) <= 4) {
                    possibleQuadNumbers.add(i);
                }
            }

            if (possibleQuadNumbers.size() >= 3) {
                for (int possibleQuadNumber : possibleQuadNumbers) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInColumn(c, possibleQuadNumber);
                    for (int possibleQuadNumber2 : possibleQuadNumbers) {
                        ArrayList<FieldCandidates> fields2 = solver.findCandidateOccurrencesInColumn(c, possibleQuadNumber2);
                        for (int possibleQuadNumber3 : possibleQuadNumbers) {
                            ArrayList<FieldCandidates> fields3 = solver.findCandidateOccurrencesInColumn(c, possibleQuadNumber3);
                            for (int possibleQuadNumber4 : possibleQuadNumbers) {
                                ArrayList<FieldCandidates> fields4 = solver.findCandidateOccurrencesInColumn(c, possibleQuadNumber4);
                                if (possibleQuadNumber == possibleQuadNumber2 || possibleQuadNumber == possibleQuadNumber3
                                        || possibleQuadNumber2 == possibleQuadNumber3 || possibleQuadNumber == possibleQuadNumber4
                                        || possibleQuadNumber2 == possibleQuadNumber4 || possibleQuadNumber3 == possibleQuadNumber4) {
                                    continue;
                                }

                                if (checkForFourDistinctFields(fields, fields2, fields3, fields4)) {
                                    ArrayList<FieldCandidates> distinctFields = findDistinctFields(fields, fields2, fields3, fields4);
                                    HashSet<Integer> candidates = new HashSet<>();
                                    candidates.add(possibleQuadNumber);
                                    candidates.add(possibleQuadNumber2);
                                    candidates.add(possibleQuadNumber3);
                                    candidates.add(possibleQuadNumber4);
                                    HiddenQuad hiddenQuad = new HiddenQuad(distinctFields.get(0),
                                            distinctFields.get(1), distinctFields.get(2),
                                            distinctFields.get(3), candidates);
                                    if (checkIfHiddenQuadCanRemoveCandidatesFromColumn(hiddenQuad)) {
                                        return hiddenQuad;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public HiddenQuad getHiddenQuadInBlock() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                HashSet<Integer> possibleQuadNumbers = new HashSet<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        for (int i = 1; i < 10; i++) {
                            if (solver.countCandidateOccurrencesInBlock(r, c, i) >= 2
                                    && solver.countCandidateOccurrencesInBlock(r, c, i) <= 4) {
                                possibleQuadNumbers.add(i);
                            }
                        }

                        if (possibleQuadNumbers.size() >= 3) {
                            for (int possibleQuadNumber : possibleQuadNumbers) {
                                ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInBlock(r, c, possibleQuadNumber);
                                for (int possibleQuadNumber2 : possibleQuadNumbers) {
                                    ArrayList<FieldCandidates> fields2 = solver.findCandidateOccurrencesInBlock(r, c, possibleQuadNumber2);
                                    for (int possibleQuadNumber3 : possibleQuadNumbers) {
                                        ArrayList<FieldCandidates> fields3 = solver.findCandidateOccurrencesInBlock(r, c, possibleQuadNumber3);
                                        for (int possibleQuadNumber4 : possibleQuadNumbers) {
                                            ArrayList<FieldCandidates> fields4 = solver.findCandidateOccurrencesInBlock(r, c, possibleQuadNumber4);
                                            if (possibleQuadNumber == possibleQuadNumber2 || possibleQuadNumber == possibleQuadNumber3
                                                    || possibleQuadNumber2 == possibleQuadNumber3 || possibleQuadNumber == possibleQuadNumber4
                                                    || possibleQuadNumber2 == possibleQuadNumber4 || possibleQuadNumber3 == possibleQuadNumber4) {
                                                continue;
                                            }

                                            if (checkForFourDistinctFields(fields, fields2, fields3, fields4)) {
                                                ArrayList<FieldCandidates> distinctFields = findDistinctFields(fields, fields2, fields3, fields4);
                                                HashSet<Integer> candidates = new HashSet<>();
                                                candidates.add(possibleQuadNumber);
                                                candidates.add(possibleQuadNumber2);
                                                candidates.add(possibleQuadNumber3);
                                                candidates.add(possibleQuadNumber4);
                                                HiddenQuad hiddenQuad = new HiddenQuad(distinctFields.get(0),
                                                        distinctFields.get(1), distinctFields.get(2),
                                                        distinctFields.get(3), candidates);
                                                if (checkIfHiddenQuadCanRemoveCandidatesFromBlock(hiddenQuad)) {
                                                    return hiddenQuad;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
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
