package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

public class SwordfishTriplet {

    public FieldCandidates field1;
    public FieldCandidates field2;
    public FieldCandidates field3;

    public int value;

    public SwordfishTriplet(FieldCandidates field1, FieldCandidates field2, FieldCandidates field3,
                            int value) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.value = value;
    }
}
