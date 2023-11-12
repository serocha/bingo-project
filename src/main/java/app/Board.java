package app;

import java.util.ArrayList;

public class Board {  // holds cells, updates cells, and determines if there's a winning combo
    private Cell[][] playingBoard;
    private final String name;

    // constructors
    public Board(String name) {
        this.playingBoard = new Cell[5][5];
        this.name = name;
    }

    // getter/setter
    public Cell[][] getPlayingBoard() {
        return playingBoard;
    }

    public void setPlayingBoard(Cell[][] playingBoard) {
        this.playingBoard = playingBoard;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder entries = new StringBuilder();
        for (Cell cell : flattenBoard()) {
            entries.append(cell.toString());
        }
        return entries.toString();
    }

    // methods
    public void resetBoard() {
        int size = getPlayingBoard()[0].length;  // get 1 dimension length
        setPlayingBoard(new Cell[size][size]);
    }

    public void addCell(int i, int j, Cell cell) {
        getPlayingBoard()[i][j] = cell;  // overwrites current index [i][j]
    }

    public Cell[] flattenBoard() {
        Cell[] flatBoard = new Cell[25];
        int rows = 5;
        int cols = 5;
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flatBoard[index++] = getPlayingBoard()[i][j];
            }
        }
        return flatBoard;
    }

    public void populateBoard(ArrayList<String>images) {
        Cell[] row = new Cell[5];
        int index = 0;
        int rowCounter = 0;
        for (int i=0; i < images.size(); i++) {  // populate from the image list
            if (i == 12)
                row[index] = new Cell("star", true);  // free space
            else
                row[index] = new Cell(images.get(i));
            index++;

            if (index == 5) {  // insert row and start a new one
                index = 0;
                getPlayingBoard()[rowCounter] = row;
                row = new Cell[5];
                rowCounter++;
            }
        }
    }

    public void markCell(String desiredImage) {
        /*
            Returns true if the desiredImage was found and isPicked was changed to true.
            Otherwise, returns false.
         */
        Cell[] cells = flattenBoard();
        for (Cell cell : cells) {  // iterate once
            if (cell.getImageName().equals(desiredImage)) {
                cell.setPicked(true);  // pick the cell
                return;
            }
        }
    }

    private Cell[] getColumn(int index) {
        return new Cell[]{
                getPlayingBoard()[0][index],
                getPlayingBoard()[1][index],
                getPlayingBoard()[2][index],
                getPlayingBoard()[3][index],
                getPlayingBoard()[4][index]
        };
    }

    private Cell[] getDiagonal(int type) {  // 0 returns [0][0] to [4][4], 1 returns [0][4] to [4][0]
        return type == 0
                ? new Cell[] {
                    getPlayingBoard()[0][0],
                    getPlayingBoard()[1][2],
                    getPlayingBoard()[2][2],
                    getPlayingBoard()[3][3],
                    getPlayingBoard()[4][4]
                }
                : new Cell[] {
                    getPlayingBoard()[4][0],
                    getPlayingBoard()[3][1],
                    getPlayingBoard()[2][2],
                    getPlayingBoard()[1][3],
                    getPlayingBoard()[0][4]
                };
    }

    private boolean allEntriesPicked(Cell[] cells) {  // evaluates an array of Cells to see if isPicked = true for all
        for (Cell cell : cells) {
            if (!cell.isPicked())
                return false;
        }
        return true;
    }

    public boolean evaluateWinner() {
        for (Cell[] row : getPlayingBoard()) {  //rows
            if (allEntriesPicked(row))
                return true;
        }

        for (int i=0; i<5; i++) {  // columns
            if (allEntriesPicked(getColumn(i)))
                return true;
        }

        if (allEntriesPicked(getDiagonal(0))) {  // diagonals
            return true;
        } else return allEntriesPicked(getDiagonal(1));  // or false
    }
}
