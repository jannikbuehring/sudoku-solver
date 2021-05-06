package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.Solver;
import com.example.sudokusolverv2.candidateSystem.Candidate;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.ArrayList;
import java.util.HashSet;

public class HiddenTripleFinder {

    private Solver solver;

    private boolean checkIfHiddenTripleCanRemoveCandidatesFromRow(HiddenTriple hiddenTriple) {
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenTriple.field1.column || c == hiddenTriple.field2.column
                    || c == hiddenTriple.field3.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, return true
                for (Integer candidate : hiddenTriple.candidates) {
                    if (solver.calculatedCandidates.get(hiddenTriple.field1.row * 9 + c).contains(candidate)) {
                        return true;
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
        for (int c = 0; c < 9; c++) {
            // only look at the fields of the hidden triple
            if (c == hiddenTriple.field1.column || c == hiddenTriple.field2.column
                    || c == hiddenTriple.field3.column) {
                // if the field contains other candidates which are not in the
                // hidden triple, remove them
                solver.calculatedCandidates.get(hiddenTriple.field1.row * 9 + c)
                        .removeAll(hiddenTriple.candidates);
                solver.personalCandidates.get(hiddenTriple.field1.row * 9 + c)
                        .removeAll(hiddenTriple.candidates);
            }
        }
    }

    public HiddenTriple getHiddenTripleInRow() {
        /*
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
                                    HiddenTriple hiddenTriple = new HiddenPair(pair, pair2,
                                            pair.candidateSet);
                                    if (checkIfHiddenTripleCanRemoveCandidatesFromRow(hiddenTriple)) {
                                        return hiddenTriple;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
        return null;
    }

        public void setSolver (Solver solver){
            this.solver = solver;
        }
    }
