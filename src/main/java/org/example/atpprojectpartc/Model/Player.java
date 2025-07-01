package org.example.atpprojectpartc.Model;

import org.example.algorithms.mazeGenerators.Position;

public class Player {
    Position position;

    public Player(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isAt(int x, int y) {
        return this.position.getRowIndex() == x && this.position.getColumnIndex() == y;
    }

    public boolean isAt(Position position) {
        return this.position.getRowIndex() == position.getRowIndex() && this.position.getColumnIndex() == position.getColumnIndex();
    }
}
