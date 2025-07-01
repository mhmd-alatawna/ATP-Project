package org.example.atpprojectpartc.Model;

import org.example.algorithms.mazeGenerators.Position;

public class MazeDTO {
    int[][] matrix ;
    int rows , columns ;
    Position startPosition , goalPosition ;

    public MazeDTO(int[][] matrix, int rows, int columns, Position startPosition, Position goalPosition) {
        this.matrix = matrix;
        this.rows = rows;
        this.columns = columns;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }
}
