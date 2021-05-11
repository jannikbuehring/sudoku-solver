package com.example.sudokusolverv2.candidateSystem;

import java.util.HashSet;

public class FieldCandidates {

    public HashSet<Integer> candidateSet;
    public int row;
    public int column;

    public FieldCandidates(int row, int column, HashSet<Integer> fieldCandidateSet) {
        this.row = row;
        this.column = column;
        this.candidateSet = fieldCandidateSet;
    }

    public int compareTo(FieldCandidates field) {
        if (this.row == field.row && this.column == field.column) return 1;
        return 0;
    }

    public boolean equals(Object o) {
        if (o instanceof FieldCandidates) {
            FieldCandidates toCompare = (FieldCandidates) o;
            return this.row == toCompare.row && this.column == toCompare.column;
        }

        return false;
    }

    public boolean isInSameBlock(FieldCandidates field) {
        if (this.row / 3 == field.row / 3 && this.column / 3 == field.column / 3) {
            return true;
        }
        return false;
    }
}
