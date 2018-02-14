package gol;

import java.util.HashSet;
import java.util.Set;

public class World {

    Set<Cell> livingCells = new HashSet<>();
    private int maxRow;
    private int minRow = 500;
    private int maxCol;
    private int minCol = 500;

    public boolean isEmpty() {
        return livingCells.isEmpty();
    }

    public void reviveAt(int row, int col) {
        livingCells.add(new Cell(row, col));
        adjustWorldSize(row, col);
    }

    private void adjustWorldSize(int row, int col) {
        if (maxRow < row) {
            maxRow = row;
        }
        if (minRow > row) {
            minRow = row;
        }
        if (maxCol < col) {
            maxCol = col;
        }
        if (minCol > col) {
            minCol = col;
        }
    }

    public boolean isAliveAt(int row, int col) {
        return livingCells.contains(new Cell(row, col));
    }

    public void killAt(int row, int col) {
        livingCells.remove(new Cell(row, col));
    }

    public int countNeighboursFor(int row, int col) {
        int neighbours = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isAliveAt(i, j) && !(i == row && j == col)) {
                    neighbours ++;
                }
            }
        }
        return neighbours;
    }

    public void computeNextGeneration() {
        Set<Cell> nextGeneration = new HashSet<>();

        for (int i = minRow -1; i <= maxRow + 1 ; i++) {
            for (int j = minCol -1 ; j <= maxCol + 1; j++) {
                handleLivingCells(nextGeneration, i, j);
                handleDeadCells(nextGeneration, i, j);
            }
        }
        livingCells = nextGeneration;
    }

    private void handleDeadCells(Set<Cell> nextGeneration, int i, int j) {
        if (!isAliveAt(i, j) && countNeighboursFor(i, j) == 3) {
            nextGeneration.add(new Cell(i, j));
        }
    }

    private void handleLivingCells(Set<Cell> nextGeneration, int i, int j) {
        if (isAliveAt(i, j) && (countNeighboursFor(i, j) == 2 || countNeighboursFor(i, j) == 3)) {
            nextGeneration.add(new Cell(i, j));
        }
    }

    public void prettyPrint() {
        for (int i = minRow - 1; i <= maxRow + 1; i++) {
            for (int j = minCol - 1; j <= maxCol + 2 ; j++) {
                System.out.print("|");
                if (isAliveAt(i, j)) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }

    }

    private static class Cell {
        public final int row;
        public final int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Cell cell = (Cell) o;

            return row == cell.row && column == cell.column;

        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + column;
            return result;
        }
    }

}
