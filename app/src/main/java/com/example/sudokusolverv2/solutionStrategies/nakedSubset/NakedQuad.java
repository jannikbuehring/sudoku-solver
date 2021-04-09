package com.example.sudokusolverv2.solutionStrategies.nakedSubset;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.HashSet;

public class NakedQuad {

    public FieldCandidates field1;
    public FieldCandidates field2;
    public FieldCandidates field3;
    public FieldCandidates field4;

    public HashSet<Integer> candidateUnion;

    public NakedQuad(FieldCandidates field1, FieldCandidates field2, FieldCandidates field3,
                     FieldCandidates field4, HashSet<Integer> candidateUnion) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.candidateUnion = candidateUnion;
    }
}
