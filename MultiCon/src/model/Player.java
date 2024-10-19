package model;

import java.util.Objects;

/**
 * An instance of a Connect-Four player.
 */
public class Player {

    private final char token;

    /**
     * Instantiates a new player with the provided token.
     * @param token Token of the player.
     */
    public Player(char token) {
        this.token = token;
    }

    /**
     * Returns the token of the player.
     * @return the token of the player.
     */
    private char getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return String.valueOf(this.token);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Player p && p.getToken() == this.getToken();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.token);
    }

}
