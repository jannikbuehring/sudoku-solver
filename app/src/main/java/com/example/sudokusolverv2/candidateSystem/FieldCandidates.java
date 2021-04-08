package com.example.sudokusolverv2.candidateSystem;

import java.util.HashSet;

public class FieldCandidates {

    public HashSet<Integer> candidateSet;
    public int row;
    public int column;

    public FieldCandidates(int row, int column, HashSet<Integer> fieldCandidateSet) {
        this.row = row;
        this.column = column;
        this.candidateSet = fieldCandidateSet;
    }
}
