package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.candidateSystem.Candidate;
import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.HashSet;

public class HiddenPair {

    public FieldCandidates field1;
    public FieldCandidates field2;

    public HashSet<Integer> pairCandidates;

    public HiddenPair(FieldCandidates field1, FieldCandidates field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public HiddenPair(FieldCandidates field1, FieldCandidates field2,
                      HashSet<Integer> pairCandidates) {
        this.field1 = field1;
        this.field2 = field2;
        this.pairCandidates = pairCandidates;
    }
}
