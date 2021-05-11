package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

public class Kite {

    public KitePair pair1;
    public KitePair pair2;

    public FieldCandidates field;

    public int value;

    public Kite(FieldCandidates field, int value) {
        this.field = field;
        this.value = value;
    }

    public Kite(KitePair kitePair1, KitePair kitePair2, FieldCandidates field, int value) {
        this.pair1 = kitePair1;
        this.pair2 = kitePair2;
        this.field = field;
        this.value = value;
    }
}
