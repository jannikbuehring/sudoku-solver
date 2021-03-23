package com.example.sudokusolverv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;

public class SudokuBoard extends View {
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;
    private final int tipHighlightColor;

    private final int letterColor;
    private final int letterColorSolve;
    private final int candidateColor;

    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();
    private final Paint tipHighlightColorPaint = new Paint();

    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    private int cellSize;

    private final Solver solver = new Solver();
    boolean showAllCandidates;
    boolean showPersonalCandidates;
    String tipLocation;

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //Obtain attrs from attrs.xml file
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard, 0, 0);

        try {
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            tipHighlightColor = a.getInteger(R.styleable.SudokuBoard_tipHighlightColor, 0);
            letterColor = a.getInteger(R.styleable.SudokuBoard_letterColor, 0);
            letterColorSolve = a.getInteger(R.styleable.SudokuBoard_letterColorSolve, 0);
            candidateColor = a.getInteger(R.styleable.SudokuBoard_candidateColor, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        // Minimum of width and height
        int dimension = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cellSize = dimension / 9;

        // Make Sudoku Board a square
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        cellFillColorPaint.setColor(cellFillColor);
        cellFillColorPaint.setAntiAlias(true);

        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);
        cellsHighlightColorPaint.setAntiAlias(true);

        tipHighlightColorPaint.setStyle(Paint.Style.FILL);
        tipHighlightColorPaint.setColor(tipHighlightColor);
        tipHighlightColorPaint.setAntiAlias(true);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);

        colorCell(canvas, solver.getSelectedRow(), solver.getSelectedColumn());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);
        drawNumbers(canvas);
        drawCandidateNumbers(canvas);
        drawPersonalCandidateNumbers(canvas);
        drawTip(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;

        // Get coordinates from tap event
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);
            if (solver.fixed[row-1][col-1]) {
                isValid = false;
            }
            else {
                solver.setSelectedRow(row);
                solver.setSelectedColumn(col);
                isValid = true;
            }

        } else {
            isValid = false;
        }

        return isValid;
    }

    private void updateSudokuBoard(Canvas canvas, int r, int c) {
        String text = Integer.toString(solver.getBoard()[r][c]);
        float width, height;

        letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
        width = letterPaint.measureText(text);
        height = letterPaintBounds.height();

        canvas.drawText(text,
                (c * cellSize) + ((cellSize - width) / 2),
                (r * cellSize + cellSize) - ((cellSize - height) / 2),
                letterPaint);
    }

    private void updateSudokuBoardCandidates(Canvas canvas, int r, int c) {
        HashSet<Integer> candidates = solver.getCandidates(r,c);
        for (int candidate : candidates) {
            if (candidate == 1) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / 6),
                        (r * cellSize + cellSize) - ((cellSize - height) / (1 + (1/6f))),
                        letterPaint);

            }
            else if (candidate == 2) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / 2),
                        (r * cellSize + cellSize) - ((cellSize - height) / (1 + (1/6f))),
                        letterPaint);
            }
            else if (candidate == 3) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / (1 + (1/6f))),
                        (r * cellSize + cellSize) - ((cellSize - height) / (1 + (1/6f))),
                        letterPaint);
            }
            else if (candidate == 4) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / 6),
                        (r * cellSize + cellSize) - ((cellSize - height) / 2),
                        letterPaint);
            }
            else if (candidate == 5) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / 2),
                        (r * cellSize + cellSize) - ((cellSize - height) / 2),
                        letterPaint);
            }
            else if (candidate == 6) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / (1 + (1/6f))),
                        (r * cellSize + cellSize) - ((cellSize - height) / 2),
                        letterPaint);
            }
            else if (candidate == 7) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / 6),
                        (r * cellSize + cellSize) - ((cellSize - height) / 6),
                        letterPaint);
            }
            else if (candidate == 8) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / 2),
                        (r * cellSize + cellSize) - ((cellSize - height) / 6),
                        letterPaint);
            }
            else if (candidate == 9) {
                String text = Integer.toString(candidate);
                float width, height;

                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width = letterPaint.measureText(text);
                height = letterPaintBounds.height();

                canvas.drawText(text,
                        (c * cellSize) + ((cellSize - width) / (1 + (1/6f))),
                        (r * cellSize + cellSize) - ((cellSize - height) / 6),
                        letterPaint);
            }
        }
    }

    public void updatePersonalCandidates(Canvas canvas, int r, int c) {
        ArrayList<HashSet<Integer>> personalCandidates = solver.getPersonalCandidates();
        HashSet<Integer> candidateSet = personalCandidates.get(r * 9 + c);
        if(candidateSet.size() > 0) {
            for(int candidate : candidateSet) {
                if (candidate == 1) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / 6),
                            (r * cellSize + cellSize) - ((cellSize - height) / (1 + (1/6f))),
                            letterPaint);

                }
                else if (candidate == 2) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / 2),
                            (r * cellSize + cellSize) - ((cellSize - height) / (1 + (1/6f))),
                            letterPaint);
                }
                else if (candidate == 3) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / (1 + (1/6f))),
                            (r * cellSize + cellSize) - ((cellSize - height) / (1 + (1/6f))),
                            letterPaint);
                }
                else if (candidate == 4) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / 6),
                            (r * cellSize + cellSize) - ((cellSize - height) / 2),
                            letterPaint);
                }
                else if (candidate == 5) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / 2),
                            (r * cellSize + cellSize) - ((cellSize - height) / 2),
                            letterPaint);
                }
                else if (candidate == 6) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / (1 + (1/6f))),
                            (r * cellSize + cellSize) - ((cellSize - height) / 2),
                            letterPaint);
                }
                else if (candidate == 7) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / 6),
                            (r * cellSize + cellSize) - ((cellSize - height) / 6),
                            letterPaint);
                }
                else if (candidate == 8) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / 2),
                            (r * cellSize + cellSize) - ((cellSize - height) / 6),
                            letterPaint);
                }
                else if (candidate == 9) {
                    String text = Integer.toString(candidate);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,
                            (c * cellSize) + ((cellSize - width) / (1 + (1/6f))),
                            (r * cellSize + cellSize) - ((cellSize - height) / 6),
                            letterPaint);
                }
            }
        }
    }

    public void drawNumbers(Canvas canvas) {
        letterPaint.setTextSize(cellSize-cellSize/3);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.getBoard()[r][c] != 0) {
                    if (solver.fixed[r][c]) {
                        letterPaint.setColor(letterColor);
                    }
                    else {
                        letterPaint.setColor(letterColorSolve);
                    }
                    updateSudokuBoard(canvas, r, c);
                }
            }
        }
    }

    public void drawCandidateNumbers(Canvas canvas) {
        if (showAllCandidates) {
            letterPaint.setTextSize(cellSize - cellSize / 1.3f);
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (solver.getBoard()[r][c] == 0) {
                        letterPaint.setColor(candidateColor);
                        updateSudokuBoardCandidates(canvas, r, c);
                    }
                }
            }
        }
    }

    public void drawPersonalCandidateNumbers(Canvas canvas) {
        letterPaint.setTextSize(cellSize - cellSize / 1.3f);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.getBoard()[r][c] == 0) {
                    letterPaint.setColor(candidateColor);
                    updatePersonalCandidates(canvas, r, c);
                }
            }
        }
    }

    public void drawTip(Canvas canvas) {
        if(tipLocation == "Top horizontal") {
            canvas.drawRect(0, 0, 9 * cellSize, 3 * cellSize, tipHighlightColorPaint);
        }
        else if(tipLocation == "Middle horizontal") {
            canvas.drawRect(0, 3 * cellSize, 9 * cellSize, 6 * cellSize, tipHighlightColorPaint);
        }
        else if(tipLocation == "Bottom horizontal") {
            canvas.drawRect(0, 6 * cellSize, 9 * cellSize, 9 * cellSize, tipHighlightColorPaint);
        }
        else if(tipLocation == "Left vertical") {
            canvas.drawRect(0, 0, 3 * cellSize, 9 * cellSize, tipHighlightColorPaint);
        }
        else if(tipLocation == "Middle vertical") {
            canvas.drawRect(3 * cellSize, 0, 6 * cellSize, 9 * cellSize, tipHighlightColorPaint);
        }
        else if(tipLocation == "Right vertical") {
            canvas.drawRect(6 * cellSize, 0, 9 * cellSize, 9 * cellSize, tipHighlightColorPaint);
        }
        else {
            canvas.drawRect(0,0,0,0,tipHighlightColorPaint);
        }

    }

    public void fixNumbers() {
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                if(solver.getBoard()[r][c] != 0) {
                    solver.fixed[r][c] = true;
                }
            }
        }
    }

    private void colorCell(Canvas canvas, int row, int column) {
        if (solver.getSelectedColumn() != -1 && solver.getSelectedRow() != -1) {
            //define rectangle for column
            canvas.drawRect((column - 1) * cellSize,
                    0,
                    column * cellSize,
                    cellSize * 9,
                    cellsHighlightColorPaint);

            //define rectangle for row
            canvas.drawRect(0,
                    (row - 1) * cellSize,
                    cellSize * 9,
                    row * cellSize,
                    cellsHighlightColorPaint);

            //might be usable for overlaying later
            //cellsHighlightColorPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

            //define rectangle for subgrid
            canvas.drawRect(((column - 1) / 3) * cellSize * 3,
                    ((row - 1) / 3) * cellSize * 3,
                    (((column - 1) / 3) + 1) * cellSize * 3,
                    (((row - 1) / 3) + 1) * cellSize * 3,
                    cellsHighlightColorPaint);

            //define rectangle for clicked cell
            canvas.drawRect((column - 1) * cellSize,
                    (row - 1) * cellSize,
                    cellSize * column,
                    row * cellSize,
                    cellFillColorPaint);
        }

        invalidate();
    }

    private void drawThickLine() {
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }

    private void drawThinLine() {
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(boardColor);
    }

    private void drawBoard(Canvas canvas) {
        for (int column = 0; column < 10; column++) {
            if (column % 3 == 0) {
                drawThickLine();
            } else {
                drawThinLine();
            }
            canvas.drawLine(cellSize * column, 0, cellSize * column, getWidth(), boardColorPaint);
        }
        for (int row = 0; row < 10; row++) {
            if (row % 3 == 0) {
                drawThickLine();
            } else {
                drawThinLine();
            }
            canvas.drawLine(0, cellSize * row, getWidth(), cellSize * row, boardColorPaint);
        }
    }

    public Solver getSolver() {
        return this.solver;
    }

    public void setShowAllCandidates(boolean bool) {
        showAllCandidates = bool;
    }

    public void setShowPersonalCandidates(boolean bool) {
        showPersonalCandidates = bool;
    }

    public void setTipLocation(String location) {
        tipLocation = location;
    }
}
