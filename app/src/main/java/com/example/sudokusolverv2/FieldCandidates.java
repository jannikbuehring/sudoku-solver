package com.example.sudokusolverv2;

import java.util.HashSet;

public class FieldCandidates {

    public HashSet<Integer> candidateSet;
    public int row = -1;
    public int column = -1;

    public FieldCandidates(HashSet<Integer> fieldCandidateSet) {
        candidateSet = fieldCandidateSet;
    }
}
