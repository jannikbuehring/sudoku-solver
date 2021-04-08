package com.example.sudokusolverv2.solutionStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

public class NakedPair implements Comparable<NakedPair> {

    public FieldCandidates field1;
    public FieldCandidates field2;


    public NakedPair(FieldCandidates field1, FieldCandidates field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    @Override
    public int compareTo(NakedPair nakedPair) {
        if (this.field1.row != nakedPair.field1.row) return 1;
        if (this.field1.column != nakedPair.field1.column) return 1;
        if (this.field2.row != nakedPair.field2.row) return 1;
        if (this.field2.column != nakedPair.field2.column) return 1;
        return 0;
    }
}
