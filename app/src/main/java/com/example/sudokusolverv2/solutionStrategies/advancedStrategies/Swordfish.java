package com.example.sudokusolverv2.solutionStrategies.advancedStrategies;

public class Swordfish {

    public SwordfishTriplet triplet1;
    public SwordfishTriplet triplet2;
    public SwordfishTriplet triplet3;

    public int value;

    public Swordfish(SwordfishTriplet triplet1, SwordfishTriplet triplet2, SwordfishTriplet triplet3,
                     int value) {
        this.triplet1 = triplet1;
        this.triplet2 = triplet2;
        this.triplet3 = triplet3;
        this.value = value;
    }
}
