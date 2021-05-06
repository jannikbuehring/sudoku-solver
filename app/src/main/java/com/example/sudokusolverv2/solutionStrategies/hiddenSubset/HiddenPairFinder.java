package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;
import java.util.HashSet;

public class HiddenPairFinder {

    private Solver solver;

    private boolean checkIfHiddenPairCanRemoveCandidatesFromRow(HiddenPair hiddenPair) {
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden pair
            if (c == hiddenPair.field1.column || c == hiddenPair.field2.column) {
                // if other candidates of the row contain a number of the hidden pair, return true
                for (Integer candidate : hiddenPair.pairCandidates) {
                    if (solver.calculatedCandidates.get(hiddenPair.field1.row * 9 + c).contains(candidate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkIfHiddenPairCanRemoveCandidatesFromColumn(HiddenPair hiddenPair) {
        for (int r = 0; r < 9; r++) {
            // only look at the fields of the hidden pair
            if (r == hiddenPair.field1.row || r == hiddenPair.field2.row) {
                // if other candidates of the row contain a number of the hidden pair, return true
                for (Integer candidate : hiddenPair.pairCandidates) {
                    if (solver.calculatedCandidates.get(r * 9 + hiddenPair.field1.column).contains(candidate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkIfHiddenPairCanRemoveCandidatesFromBlock(HiddenPair hiddenPair) {
        int boxRow = hiddenPair.field1.row / 3;
        int boxCol = hiddenPair.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden pair
                if (r == hiddenPair.field1.row && c == hiddenPair.field1.column
                        || r == hiddenPair.field2.row && c == hiddenPair.field2.column) {
                    for (Integer candidate : hiddenPair.pairCandidates) {
                        if (solver.calculatedCandidates.get(r * 9 + c).contains(candidate)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void applyHiddenPairToRow() {
        HiddenPair hiddenPair = getHiddenPairInRow();
        if (hiddenPair == null) {
            return;
        }
        for (int c = 0; c < 9; c++) {
            // remove other candidates of the hidden pair
            if (c == hiddenPair.field1.column || c == hiddenPair.field2.column) {
                solver.calculatedCandidates.get(hiddenPair.field1.row * 9 + c)
                        .removeAll(hiddenPair.pairCandidates);
                solver.personalCandidates.get(hiddenPair.field1.row * 9 + c)
                        .removeAll(hiddenPair.pairCandidates);
            }
        }
    }

    public void applyHiddenPairToColumn() {
        HiddenPair hiddenPair = getHiddenPairInRow();
        if (hiddenPair == null) {
            return;
        }
        for (int r = 0; r < 9; r++) {
            // remove other candidates of the hidden pair
            if (r == hiddenPair.field1.row || r == hiddenPair.field2.row) {
                solver.calculatedCandidates.get(r * 9 + hiddenPair.field1.column)
                        .removeAll(hiddenPair.pairCandidates);
                solver.personalCandidates.get(r * 9 + hiddenPair.field1.column)
                        .removeAll(hiddenPair.pairCandidates);
            }
        }
    }

    public void applyHiddenPairToBlock() {
        HiddenPair hiddenPair = getHiddenPairInBlock();
        if (hiddenPair == null) {
            return;
        }
        int boxRow = hiddenPair.field1.row / 3;
        int boxCol = hiddenPair.field1.column / 3;
        for (int r = boxRow * 3; r < boxRow * 3 + 3; r++) {
            for (int c = boxCol * 3; c < boxCol * 3 + 3; c++) {
                // only look at the fields of the hidden pair
                if (r == hiddenPair.field1.row && c == hiddenPair.field1.column
                        || r == hiddenPair.field2.row && c == hiddenPair.field2.column) {
                    solver.calculatedCandidates.get(r * 9 + c)
                            .removeAll(hiddenPair.pairCandidates);
                    solver.personalCandidates.get(r * 9 + c)
                            .removeAll(hiddenPair.pairCandidates);
                }
            }
        }
    }

    public HiddenPair getHiddenPairInRow() {
        for (int r = 0; r < 9; r++) {
            // create a list with 9 sublists for every possible number
            ArrayList<ArrayList<Candidate>> list = new ArrayList<>(9);
            for (int cap = 0; cap < 9; cap++) {
                list.add(new ArrayList<>());
            }
            for (int c = 0; c < 9; c++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidates = new
                            FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                    if (candidates.candidateSet != null) {
                        for (int value : candidates.candidateSet) {
                            Candidate candidate = new Candidate(r, c, value);
                            list.get(value - 1).add(candidate);
                        }
                    }
                }
            }
            ArrayList<Candidate> potentialPair = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (list.get(i).size() == 2) {
                    potentialPair.addAll(list.get(i));
                }
            }
            FieldCandidates pair = null;
            if (potentialPair.size() >= 4) {
                for (Candidate candidate : potentialPair) {
                    for (Candidate candidate2 : potentialPair) {
                        if (candidate.compareTo(candidate2) == 1) {
                            HashSet<Integer> values = new HashSet<>();
                            values.add(candidate.value);
                            values.add(candidate2.value);
                            //this can probably be solved in a way better way
                            if (pair == null) {
                                pair = new FieldCandidates(candidate.row,
                                        candidate.column, values);
                            } else {
                                FieldCandidates pair2 = new FieldCandidates(candidate.row,
                                        candidate.column, values);
                                if ((pair.row != pair2.row || pair.column != pair2.column)
                                        && pair.candidateSet.equals(pair2.candidateSet)) {
                                    HiddenPair hiddenPair = new HiddenPair(pair, pair2,
                                            pair.candidateSet);
                                    if (checkIfHiddenPairCanRemoveCandidatesFromRow(hiddenPair)) {
                                        return hiddenPair;
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

    public HiddenPair getHiddenPairInColumn() {
        for (int c = 0; c < 9; c++) {
            // create a list with 9 sublists for every possible number
            ArrayList<ArrayList<Candidate>> list = new ArrayList<>(9);
            for (int cap = 0; cap < 9; cap++) {
                list.add(new ArrayList<>());
            }
            for (int r = 0; r < 9; r++) {
                if (solver.board[r][c] == 0) {
                    FieldCandidates candidates = new
                            FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                    if (candidates.candidateSet != null) {
                        for (int value : candidates.candidateSet) {
                            Candidate candidate = new Candidate(r, c, value);
                            list.get(value - 1).add(candidate);
                        }
                    }
                }
            }
            ArrayList<Candidate> potentialPair = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (list.get(i).size() == 2) {
                    potentialPair.addAll(list.get(i));
                }
            }
            FieldCandidates pair = null;
            if (potentialPair.size() >= 4) {
                for (Candidate candidate : potentialPair) {
                    for (Candidate candidate2 : potentialPair) {
                        if (candidate.compareTo(candidate2) == 1) {
                            HashSet<Integer> values = new HashSet<>();
                            values.add(candidate.value);
                            values.add(candidate2.value);
                            //this can probably be solved in a way better way
                            if (pair == null) {
                                pair = new FieldCandidates(candidate.row,
                                        candidate.column, values);
                            } else {
                                FieldCandidates pair2 = new FieldCandidates(candidate.row,
                                        candidate.column, values);
                                if ((pair.row != pair2.row || pair.column != pair2.column)
                                        && pair.candidateSet.equals(pair2.candidateSet)) {
                                    HiddenPair hiddenPair = new HiddenPair(pair, pair2,
                                            pair.candidateSet);
                                    if (checkIfHiddenPairCanRemoveCandidatesFromColumn(hiddenPair)) {
                                        return hiddenPair;
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


    public HiddenPair getHiddenPairInBlock() {
        for (int row = 0; row < 9; row = row + 3) {
            for (int col = 0; col < 9; col = col + 3) {
                // create a list with 9 sublists for every possible number
                ArrayList<ArrayList<Candidate>> list = new ArrayList<>(9);
                for (int cap = 0; cap < 9; cap++) {
                    list.add(new ArrayList<>());
                }
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        if (solver.board[r][c] == 0) {
                            FieldCandidates candidates = new
                                    FieldCandidates(r, c, solver.calculatedCandidates.get(r * 9 + c));
                            if (candidates.candidateSet != null) {
                                for (int value : candidates.candidateSet) {
                                    Candidate candidate = new Candidate(r, c, value);
                                    list.get(value - 1).add(candidate);
                                }
                            }
                        }
                    }
                }
                ArrayList<Candidate> potentialPair = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    if (list.get(i).size() == 2) {
                        potentialPair.addAll(list.get(i));
                    }
                }
                FieldCandidates pair = null;
                if (potentialPair.size() >= 4) {
                    for (Candidate candidate : potentialPair) {
                        for (Candidate candidate2 : potentialPair) {
                            if (candidate.compareTo(candidate2) == 1) {
                                HashSet<Integer> values = new HashSet<>();
                                values.add(candidate.value);
                                values.add(candidate2.value);
                                //this can probably be solved in a way better way
                                if (pair == null) {
                                    pair = new FieldCandidates(candidate.row,
                                            candidate.column, values);
                                } else {
                                    FieldCandidates pair2 = new FieldCandidates(candidate.row,
                                            candidate.column, values);
                                    if ((pair.row != pair2.row || pair.column != pair2.column)
                                            && pair.candidateSet.equals(pair2.candidateSet)) {
                                        HiddenPair hiddenPair = new HiddenPair(pair, pair2,
                                                pair.candidateSet);
                                        if (checkIfHiddenPairCanRemoveCandidatesFromBlock(hiddenPair)) {
                                            return hiddenPair;
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
