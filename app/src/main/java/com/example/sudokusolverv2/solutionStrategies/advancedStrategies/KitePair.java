package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

public class KitePair {

    public FieldCandidates field1;
    public FieldCandidates field2;

    public int value;

    public KitePair(FieldCandidates field1, FieldCandidates field2, int value) {
        this.field1 = field1;
        this.field2 = field2;
        this.value = value;
    }
}
