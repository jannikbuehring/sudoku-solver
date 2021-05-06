package com.example.sudokusolverv2.solutionStrategies.hiddenSubset;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

import java.util.HashSet;

public class HiddenQuad {

    public FieldCandidates field1;
    public FieldCandidates field2;
    public FieldCandidates field3;
    public FieldCandidates field4;

    public HashSet<Integer> candidates;

    public HiddenQuad(FieldCandidates field1, FieldCandidates field2, FieldCandidates field3,
                      FieldCandidates field4, HashSet<Integer> candidates) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.candidates = candidates;
    }
}
