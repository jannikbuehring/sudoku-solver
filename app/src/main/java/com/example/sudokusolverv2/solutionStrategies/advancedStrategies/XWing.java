package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

import com.example.sudokusolverv2.candidateSystem.FieldCandidates;

public class XWing {

    public XWingPair pair1;
    public XWingPair pair2;

    public int value;

    public XWing(XWingPair pair1, XWingPair pair2, Integer value) {
        this.pair1 = pair1;
        this.pair2 = pair2;
        this.value = value;
    }

}