package ui;

import model.Game;

import java.util.Scanner;

/**
 * Represents the user interface for the MultiCon game.
 */
public class MultiConUI {
    private static final String ERROR_MESSAGE_COLUMN_FULL = "ERROR: Selected column is full.";
    private static final String ERROR_MESSAGE_INVALID_INDEX = "ERROR: Invalid index.";
    private static final String OUTPUT_MOVE = "%d. Zug, Spieler %s:";
    private static final String OUTPUT_WINNER = "Sieger: Spieler %s";
    private static final String OUTPUT_DRAW = "Unentschieden!";
    private static final String QUIT_COMMAND = "quit";

    private final Game game;
    private boolean isRunning = false;
    private Scanner scanner;

    /**
     * Creates a new MultiConUI instance.
     *
     * @param game The game to be played.
     */
    public MultiConUI(Game game) {
        this.game = game;
    }

    public void start() {
        this.isRunning = true;
        this.scanner = new Scanner(System.in);

        System.out.println(this.game.getBoard().toString());

        while (this.isRunning) {
            handleMove();
        }

        this.scanner.close();

    }


    private void handleMove() {
        System.out.println(String.format(OUTPUT_MOVE, this.game.getNextMoveNumber(), this.game.getCurrentPlayerIndex() + 1));

        int index;
        String input = this.scanner.nextLine();

        if (input.equals(QUIT_COMMAND)) {
            this.isRunning = false;
            return;
        }

        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(ERROR_MESSAGE_INVALID_INDEX);
            return;
        }

        // Shifting the index, since the board is 0-indexed, but the UI is 1-indexed
        index--;

        if (!this.game.getBoard().isValidColumnIndex(index)) {
            System.out.println(ERROR_MESSAGE_INVALID_INDEX);
            return;
        }
        if (this.game.getBoard().isColumnFull(index)) {
            System.out.println(ERROR_MESSAGE_COLUMN_FULL);
            return;
        }

        this.game.place(index);

        System.out.println(this.game.getBoard().toString());

        if (this.game.hasWinner()) {
            System.out.println(String.format(OUTPUT_WINNER, this.game.getWinner() + 1));
            this.isRunning = false;
        } else if (this.game.isDraw()) {
            System.out.println(OUTPUT_DRAW);
            this.isRunning = false;
        }
    }

}
