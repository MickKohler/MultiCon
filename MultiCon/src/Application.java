import ui.MultiConUI;
import model.Game;
import model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class that acts as the entrance of the MultiCon application.
 */
public final class Application {
    private static final String ERROR_UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";
    private static final String ERROR_TOO_MANY_PLAYERS = "ERROR: Too many players.";
    private static final String ERROR_INVALID_PLAYER_NAME = "ERROR: Invalid player name.";
    private static final String ERROR_DUPLICATE_PLAYER_NAME = "ERROR: Duplicate player name.";
    private static final int MAX_PLAYER_COUNT = 6;
    private static final List<Player> DEFAULT_PLAYERS = List.of(new Player('x'), new Player('o'));
    private static final int EMPTY_ARGUMENTS = 0;

    private Application() {
        throw new AssertionError(ERROR_UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * The entry point of the program.
     *
     * @param args the player symbols or empty
     */
    public static void main(String[] args) {
        List<Player> players = parsePlayerArguments(args);
        if (players == null) {
            return;
        }

        Game game = new Game(players);
        MultiConUI ui = new MultiConUI(game);

        ui.start();
    }

    /**
     * Parses the player arguments and transformes them into a list of players.
     *
     * @param args the player symbols
     * @return a list of players
     */
    private static List<Player> parsePlayerArguments(String[] args) {
        List<Player> players = new ArrayList<>(args.length);

        if (args.length == EMPTY_ARGUMENTS) {
            return DEFAULT_PLAYERS;
        } else if (args.length > MAX_PLAYER_COUNT) {
            System.out.println(ERROR_TOO_MANY_PLAYERS);
            return null;
        }

        for (String singleArgument : args) {
            if (singleArgument.length() != 1) {
                System.out.println(ERROR_INVALID_PLAYER_NAME);
                return null;
            }

            Player player = new Player(singleArgument.charAt(EMPTY_ARGUMENTS));

            if (players.contains(player)) {
                System.out.println(ERROR_DUPLICATE_PLAYER_NAME);
            }
            players.add(player);

        }
        return players;
    }

}
