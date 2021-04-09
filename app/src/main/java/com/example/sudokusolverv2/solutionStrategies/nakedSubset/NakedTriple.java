package com.example.sudokusolverv2.solutionStrategies.nakedSubset;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.HashSet;

public class NakedTriple {

    public FieldCandidates field1;
    public FieldCandidates field2;
    public FieldCandidates field3;

    public HashSet<Integer> candidateUnion;

    public NakedTriple(FieldCandidates field1, FieldCandidates field2, FieldCandidates field3,
                       HashSet<Integer> candidateUnion) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.candidateUnion = candidateUnion;
    }
}
