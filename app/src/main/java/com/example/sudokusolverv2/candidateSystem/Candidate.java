package com.example.sudokusolverv2.candidateSystem;

public class Candidate implements Comparable<Candidate> {

    public int value;
    public int row;
    public int column;

    public Candidate(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public int compareTo(Candidate o) {
        if (this.value != o.value && this.row == o.row && this.column == o.column) return 1;
        return 0;
    }
}
