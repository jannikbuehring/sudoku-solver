package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

public class XWingPair {

    public FieldCandidates field1;
    public FieldCandidates field2;

    public Integer value;

    public XWingPair(FieldCandidates field1, FieldCandidates field2, Integer value) {
        this.field1 = field1;
        this.field2 = field2;
        this.value = value;
    }
}
