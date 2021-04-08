package com.example.sudokusolverv2.candidateSystem;

import com.example.sudokusolverv2.Solver;

import java.util.HashSet;

public class FieldCandidates extends Solver {

    public HashSet<Integer> candidateSet;
    public int row = -1;
    public int column = -1;

    public FieldCandidates(HashSet<Integer> fieldCandidateSet) {
        candidateSet = fieldCandidateSet;
    }
}
