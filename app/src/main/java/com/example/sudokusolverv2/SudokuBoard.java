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

public class SudokuBoard extends View {
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;

    private final int letterColor;
    private final int letterColorSolve;

    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();

    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    private int cellSize;

    private final Solver solver = new Solver();

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //Obtain attrs from attrs.xml file
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard, 0, 0);

        try {
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            letterColor = a.getInteger(R.styleable.SudokuBoard_letterColor, 0);
            letterColorSolve = a.getInteger(R.styleable.SudokuBoard_letterColorSolve, 0);
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

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);

        colorCell(canvas, solver.getSelectedRow(), solver.getSelectedColumn());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);
        drawNumbers(canvas);
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
            solver.setSelectedRow((int) Math.ceil(y / cellSize));
            solver.setSelectedColumn((int) Math.ceil(x / cellSize));
            isValid = true;
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

    public void drawNumbers(Canvas canvas) {
        letterPaint.setTextSize(cellSize-cellSize/3);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.getBoard()[r][c] != 0) {
                    updateSudokuBoard(canvas, r, c);
                }
            }
        }

        letterPaint.setColor(letterColorSolve);

        for (ArrayList<Object> letter : solver.getEmptyBoxIndex()) {
            int r = (int) letter.get(0);
            int c = (int) letter.get(1);

            updateSudokuBoard(canvas, r, c);
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
}
