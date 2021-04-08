package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

public class NakedPair {

    public FieldCandidates field1;
    public FieldCandidates field2;

    public NakedPair(FieldCandidates field1, FieldCandidates field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
