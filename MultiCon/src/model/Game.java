package model;

import java.util.List;


/**
 * Represents a MultiCon game, which is played on a board by multiple players.
 * The game class is responsible for managing the players and the board.
 */
public class Game {
    private static final int MINIMUM_LENGTH_TO_WIN = 4;
    private static final double WIN_CONDITION_DIVISOR = 9.0;
    private final Board board;
    private final List<Player> players;
    private int currentPlayerIndex;
    private int numberOfWinners;
    private int moveCount;


    public Game(List<Player> players) {
        this.board = new Board(this.getWinningLineLength(players.size()));
        this.players = players;
        this.currentPlayerIndex = 0;
        this.numberOfWinners = -1;
        this.moveCount = 0;

    }


    /**
     * Getter for curent player.
     *
     * @return the index of the current player
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Calculates the winning line length based on the number of players.
     *
     * @param numberOfPlayer Number of players in the game.
     * @return The winning line length.
     */
    private int getWinningLineLength(int numberOfPlayer) {
        return (int) Math.min(MINIMUM_LENGTH_TO_WIN, Math.ceil(WIN_CONDITION_DIVISOR / numberOfPlayer));
    }

    /**
     * Returns the number of the next move.
     *
     * @return The number of the next move.
     */
    public int getNextMoveNumber() {
        return this.moveCount + 1;
    }

    /**
     * Returns whether the game state is a draw.
     *
     * @return True if the game state is a draw, false otherwise.
     */
    public boolean isDraw() {
        return this.board.isBoardFull() && !this.hasWinner();
    }

    /**
     * Checks if the game has a winner.
     *
     * @return True if the game has a winner, false otherwise.
     */
    public boolean hasWinner() {
        return this.numberOfWinners >= 0;
    }
    
    /**
     * Method to place a tile in the given column and advance to the next player.
     *
     * @param columnIndex The column in which the tile should be placed.
     */
    public void place(int columnIndex) {
        boolean hasWon = this.board.placeTile(this.players.get(this.currentPlayerIndex), columnIndex);
        if (hasWon) {
            this.numberOfWinners = this.currentPlayerIndex;
        }

        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        this.moveCount++;
    }

    /**
     * Getter for the winner.
     *
     * @return The index of the winner.
     */
    public int getWinner() {
        if (!this.hasWinner()) {
            throw new IllegalStateException("Winner was called although there is no winner.");
        }

        return this.numberOfWinners;
    }

    /**
     * Getter for the current board.
     *
     * @return The current board.
     */
    public Board getBoard() {
        return board;
    }

}
