package model;

import java.util.StringJoiner;

/**
 * Represents a MultiCon board, the board, that gets played on and is displayed to the user.
 * The board is a 2D grid of cells, where each cell can be empty or occupied by a player.
 */
public class Board {
    private static final int DEFAULT_WIDTH = 7;
    private static final int DEFAULT_HEIGHT = 6;

    private static final String EMPTY_FIELD = " ";
    private static final String COLUMN_DELIMITER = "|";

    private final int width;
    private final int height;
    private final int winLength;
    private final Player[][] cells;

    /**
     * Creates a new Connect-four board with the given player count.
     * Uses the default board size of 6x7.
     *
     * @param winningLineLength Amount of tiles needed in one line to win the game
     */
    public Board(int winningLineLength) {
        this.winLength = winningLineLength;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.cells = new Player[width][height];
    }

    /**
     * Creates a new Connect-four board with the given player count and board size.
     * @param winningLineLength Amount of tiles needed in one line to win the game
     * @param width Board width
     * @param height Board height
     */
    public Board(int winningLineLength, int width, int height) {
        this.winLength = winningLineLength;
        this.width = width;
        this.height = height;
        this.cells = new Player[width][height];
    }

    /**
     * Checks if the given column index is valid.
     *
     * @param index the column index to check
     * @return true if the index is valid, false otherwise
     */
    public boolean isValidColumnIndex(int index) {
        return index <= this.width - 1 && index >= 0;
    }

    /**
     * Checks if the the field defined by the given x and y coordinates is empty.
     *
     * @param x the column index
     * @param y the row index
     * @return true if the field is empty, false otherwise
     */
    private boolean isFieldEmpty(int x, int y) {
        return cells[x][y] == null;
    }

    /**
     * Checks if the given column is full.
     *
     * @param index the column index
     * @return true if the column is full, false otherwise
     */
    public boolean isColumnFull(int index) {
        return !isFieldEmpty(index, 0);
    }

    /**
     * Checks if the board is full.
     *
     * @return true if the board is full, false otherwise
     */
    public boolean isBoardFull() {
        for (int i = 0; i < width; i++) {
            if (!this.isColumnFull(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given player has won the game by placing a tile in the given column.
     * All possible winning combinations (of a line) are checked.
     *
     * @param player the player who places the tile
     * @param x the column in which the tile should be placed
     * @param y the row in which the tile should be placed
     * @return true if the player won the game, false otherwise
     */
    private boolean isWinningMove(Player player, int x , int y) {
        boolean isVertical = countVertical(player,x,y) >= winLength;
        boolean isHorizontal = countHorizontal(player,x,y) >= winLength;
        boolean firstDiagonal = countFirstDiagonal(player,x,y) >= winLength;
        boolean secondDiagonal = countSecondDiagonal(player,x,y) >= winLength;

        return isVertical || isHorizontal || firstDiagonal ||secondDiagonal;
    }


    private int countVertical(Player player, int putX, int putY) {
        int count = 1;

        // walking downwards from the given putX
        for (int y = putY + 1; y < this.height; y++) {
            if (cells[putX][y] == null || !this.cells[putX][y].equals(player)) {
                break;
            } else {
                count++;
            }
        }

        // walking upwards
        for (int y = putY - 1; y >= 0; y--) {
            if (cells[putX][y] == null || !this.cells[putX][y].equals(player)) {
                break;
            } else {
                count++;
            }
        }

        return count;
    }

    private int countHorizontal(Player player, int putX, int putY) {
        int count = 1;

        // walking right from the given putY
        for (int x = putX + 1; x < this.width; x++) {
            if (cells[x][putY] == null || !this.cells[x][putY].equals(player)) {
                break;
            } else {
                count++;
            }
        }

        // walking left
        for (int x = putX - 1; x >= 0; x--) {
            if (cells[x][putY] == null || !this.cells[x][putY].equals(player)) {
                break;
            } else {
                count++;
            }
        }

        return count;
    }

    private int countFirstDiagonal(Player player, int putX, int putY) {
        int count = 1;

        // walking top-left
        for (int i = 1; putX - i >= 0 && putY - i >= 0; i++) {
            Player cell = cells[putX - i][putY - i];
            if (cell == null || !cell.equals(player)) {
                break;
            } else {
                count++;
            }
        }

        // walking bottom-right
        for (int i = 1; putX + i < this.width && putY + i < this.height; i++) {
            Player cell = this.cells[putX + i][putY + i];
            if (cell == null || !cell.equals(player)) {
                break;
            } else {
                count++;
            }
        }

        return count;

    }


    private int countSecondDiagonal(Player player, int putX, int putY) {
        int count = 1;

        // walking top-right
        for (int i = 1; putX + i <= this.width && putY - i >= 0; i++) {
            Player cell = this.cells[putX + 1][putY - i];
            if (cell == null || !cell.equals(player)) {
                break;
            } else {
                count++;
            }
        }

        // walking bottom-left
        for (int i = 1; putX - i >= 0 && putY + i < this.height; i++) {
            Player cell = this.cells[putX - i][putY + i];
            if (cell == null || !cell.equals(player)) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }


    /**
     * Places a tile in the given column by marking the topmost
     * empty field as the player's field.
     *
     * @param player the player who places the tile
     * @param column the column in which the tile should be placed
     * @return true if the player won by placing the tile, false otherwise
     */
    public boolean placeTile(Player player, int column) {
        int yToPut = -1;

        for (int y = this.height -1; y >= 0; y--) {
            if (this.isFieldEmpty(column, y)) {
                yToPut = y;
                break;
            }
        }
        if (yToPut < 0) {
            return false;
        }

        this.cells[column][yToPut] = player;

        return this.isWinningMove(player, column, yToPut);
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());

        for (int y = 0; y < this.height; y++) {
            StringBuilder line = new StringBuilder();
            line.append(COLUMN_DELIMITER);

            for (int x = 0; x < this.width; x++) {
                Player cell = cells[x][y];

                if (cell == null) {
                    line.append(EMPTY_FIELD);
                } else {
                    line.append(cell);
                }

                line.append(COLUMN_DELIMITER);
            }
            joiner.add(line);
        }

        return joiner.toString();
    }
}
