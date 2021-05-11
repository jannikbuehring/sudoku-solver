package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class HiddenTripleFinder {

    private Solver solver;

    private boolean checkIfHiddenTripleCanRemoveCandidatesFromRow(HiddenTriple hiddenTriple) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenTriple.field1.column || c == hiddenTriple.field2.column
                    || c == hiddenTriple.field3.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(hiddenTriple.field1.row * 9 + c).contains(candidate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkIfHiddenTripleCanRemoveCandidatesFromColumn(HiddenTriple hiddenTriple) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden triple
            if (r == hiddenTriple.field1.row || r == hiddenTriple.field2.row
                    || r == hiddenTriple.field3.row) {
                // if the field contains other candidates which are not in the
                // hidden triple, return true
                for (Integer candidate : otherCandidates) {
                    if (solver.calculatedCandidates.get(r * 9 + hiddenTriple.field1.column).contains(candidate)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkIfHiddenTripleCanRemoveCandidatesFromBlock(HiddenTriple hiddenTriple) {
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        int boxRow = hiddenTriple.field1.row / 3;
        int boxCol = hiddenTriple.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden triple
                if (r == hiddenTriple.field1.row && c == hiddenTriple.field1.column
                        || r == hiddenTriple.field2.row && c == hiddenTriple.field2.column
                        || r == hiddenTriple.field3.row && c == hiddenTriple.field3.column) {
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

    public void applyHiddenTripleToRow() {
        HiddenTriple hiddenTriple = getHiddenTripleInRow();
        if (hiddenTriple == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenTriple.field1.column || c == hiddenTriple.field2.column
                    || c == hiddenTriple.field3.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, remove them
                solver.calculatedCandidates.get(hiddenTriple.field1.row * 9 + c)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(hiddenTriple.field1.row * 9 + c)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenTripleToColumn() {
        HiddenTriple hiddenTriple = getHiddenTripleInColumn();
        if (hiddenTriple == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden triple
            if (r == hiddenTriple.field1.row || r == hiddenTriple.field2.row
                    || r == hiddenTriple.field3.row) {
                // if the field contains other candidates which are not in the
                // hidden triple, remove them
                solver.calculatedCandidates.get(r * 9 + hiddenTriple.field1.column)
                        .removeAll(otherCandidates);
                solver.personalCandidates.get(r * 9 + hiddenTriple.field1.column)
                        .removeAll(otherCandidates);
            }
        }
    }

    public void applyHiddenTripleToBlock() {
        HiddenTriple hiddenTriple = getHiddenTripleInBlock();
        if (hiddenTriple == null) {
            return;
        }
        HashSet<Integer> otherCandidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            otherCandidates.add(i);
        }
        otherCandidates.removeAll(hiddenTriple.candidates);
        int boxRow = hiddenTriple.field1.row / 3;
        int boxCol = hiddenTriple.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden triple
                if (r == hiddenTriple.field1.row && c == hiddenTriple.field1.column
                        || r == hiddenTriple.field2.row && c == hiddenTriple.field2.column
                        || r == hiddenTriple.field3.row && c == hiddenTriple.field3.column) {
                    solver.calculatedCandidates.get(r * 9 + c)
                            .removeAll(otherCandidates);
                    solver.personalCandidates.get(r * 9 + c)
                            .removeAll(otherCandidates);
                }
            }
        }
    }

    private boolean checkForThreeDistinctFields(ArrayList<FieldCandidates> fields,
                                                ArrayList<FieldCandidates> fields2,
                                                ArrayList<FieldCandidates> fields3) {
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

        if (distinctFields.size() == 3) {
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<FieldCandidates> findDistinctFields(ArrayList<FieldCandidates> fields,
                                                          ArrayList<FieldCandidates> fields2,
                                                          ArrayList<FieldCandidates> fields3) {
        ArrayList<FieldCandidates> distinctFields = new ArrayList<>();
        for (FieldCandidates field : fields) {
            if (!distinctFields.contains(field)) {
                distinctFields.add(field);
            }
        }

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

        return distinctFields;
    }

    public HiddenTriple getHiddenTripleInRow() {
        for (int r = 0; r < 9; r++) {
            HashSet<Integer> possibleTripleNumbers = new HashSet<>();
            for (int i = 1; i < 10; i++) {
                if (solver.countCandidateOccurrencesInRow(r, i) == 2
                        || solver.countCandidateOccurrencesInRow(r, i) == 3) {
                    possibleTripleNumbers.add(i);
                }
            }

            if (possibleTripleNumbers.size() >= 3) {
                for (int possibleTripleNumber : possibleTripleNumbers) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInRow(r, possibleTripleNumber);
                    for (int possibleTripleNumber2 : possibleTripleNumbers) {
                        ArrayList<FieldCandidates> fields2 = solver.findCandidateOccurrencesInRow(r, possibleTripleNumber2);
                        for (int possibleTripleNumber3 : possibleTripleNumbers) {
                            ArrayList<FieldCandidates> fields3 = solver.findCandidateOccurrencesInRow(r, possibleTripleNumber3);
                            if (possibleTripleNumber == possibleTripleNumber2 || possibleTripleNumber == possibleTripleNumber3
                                    || possibleTripleNumber2 == possibleTripleNumber3) {
                                continue;
                            }

                            if (checkForThreeDistinctFields(fields, fields2, fields3)) {
                                ArrayList<FieldCandidates> distinctFields = findDistinctFields(fields, fields2, fields3);
                                HashSet<Integer> candidates = new HashSet<>();
                                candidates.add(possibleTripleNumber);
                                candidates.add(possibleTripleNumber2);
                                candidates.add(possibleTripleNumber3);
                                HiddenTriple hiddenTriple = new HiddenTriple(distinctFields.get(0),
                                        distinctFields.get(1), distinctFields.get(2), candidates);
                                if (checkIfHiddenTripleCanRemoveCandidatesFromRow(hiddenTriple)) {
                                    return hiddenTriple;
                                }
                            }
                        }
                    }
                }
            }
        }


        return null;
    }

    public HiddenTriple getHiddenTripleInColumn() {
        for (int c = 0; c < 9; c++) {
            HashSet<Integer> possibleTripleNumbers = new HashSet<>();
            for (int i = 1; i < 10; i++) {
                if (solver.countCandidateOccurrencesInColumn(c, i) == 2
                        || solver.countCandidateOccurrencesInColumn(c, i) == 3) {
                    possibleTripleNumbers.add(i);
                }
            }

            if (possibleTripleNumbers.size() >= 3) {
                for (int possibleTripleNumber : possibleTripleNumbers) {
                    ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInColumn(c, possibleTripleNumber);
                    for (int possibleTripleNumber2 : possibleTripleNumbers) {
                        ArrayList<FieldCandidates> fields2 = solver.findCandidateOccurrencesInColumn(c, possibleTripleNumber2);
                        for (int possibleTripleNumber3 : possibleTripleNumbers) {
                            ArrayList<FieldCandidates> fields3 = solver.findCandidateOccurrencesInColumn(c, possibleTripleNumber3);
                            if (possibleTripleNumber == possibleTripleNumber2 || possibleTripleNumber == possibleTripleNumber3
                                    || possibleTripleNumber2 == possibleTripleNumber3) {
                                continue;
                            }

                            if (checkForThreeDistinctFields(fields, fields2, fields3)) {
                                ArrayList<FieldCandidates> distinctFields = findDistinctFields(fields, fields2, fields3);
                                HashSet<Integer> candidates = new HashSet<>();
                                candidates.add(possibleTripleNumber);
                                candidates.add(possibleTripleNumber2);
                                candidates.add(possibleTripleNumber3);
                                HiddenTriple hiddenTriple = new HiddenTriple(distinctFields.get(0),
                                        distinctFields.get(1), distinctFields.get(2), candidates);
                                if (checkIfHiddenTripleCanRemoveCandidatesFromColumn(hiddenTriple)) {
                                    return hiddenTriple;
                                }
                            }
                        }
                    }
                }
            }
        }


        return null;
    }

    public HiddenTriple getHiddenTripleInBlock() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                HashSet<Integer> possibleTripleNumbers = new HashSet<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        for (int i = 1; i < 10; i++) {
                            if (solver.countCandidateOccurrencesInBlock(r, c, i) == 2
                                    || solver.countCandidateOccurrencesInBlock(r, c, i) == 3) {
                                possibleTripleNumbers.add(i);
                            }
                        }

                        if (possibleTripleNumbers.size() >= 3) {
                            for (int possibleTripleNumber : possibleTripleNumbers) {
                                ArrayList<FieldCandidates> fields = solver.findCandidateOccurrencesInBlock(r, c, possibleTripleNumber);
                                for (int possibleTripleNumber2 : possibleTripleNumbers) {
                                    ArrayList<FieldCandidates> fields2 = solver.findCandidateOccurrencesInBlock(r, c, possibleTripleNumber2);
                                    for (int possibleTripleNumber3 : possibleTripleNumbers) {
                                        ArrayList<FieldCandidates> fields3 = solver.findCandidateOccurrencesInBlock(r, c, possibleTripleNumber3);
                                        if (possibleTripleNumber == possibleTripleNumber2 || possibleTripleNumber == possibleTripleNumber3
                                                || possibleTripleNumber2 == possibleTripleNumber3) {
                                            continue;
                                        }

                                        if (checkForThreeDistinctFields(fields, fields2, fields3)) {
                                            ArrayList<FieldCandidates> distinctFields = findDistinctFields(fields, fields2, fields3);
                                            HashSet<Integer> candidates = new HashSet<>();
                                            candidates.add(possibleTripleNumber);
                                            candidates.add(possibleTripleNumber2);
                                            candidates.add(possibleTripleNumber3);
                                            HiddenTriple hiddenTriple = new HiddenTriple(distinctFields.get(0),
                                                    distinctFields.get(1), distinctFields.get(2), candidates);
                                            if (checkIfHiddenTripleCanRemoveCandidatesFromColumn(hiddenTriple)) {
                                                return hiddenTriple;
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