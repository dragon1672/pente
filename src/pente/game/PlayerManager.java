package pente.game;

import pente.board.Color;
import pente.player.PlayerBrain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayerManager {
    final List<Player> players;
    private int currentPlayer = 0;

    PlayerManager(List<Player> players) {
        this.players = Collections.unmodifiableList(players);
    }

    public static PlayerManager createFromBrains(PlayerBrain ... brains) {
        Stack<Color> possibleColors = Stream.of(Color.values())
                .filter(color -> color.isPlayer)
                .collect(Collectors.toCollection(Stack::new));

        return new PlayerManager(Stream.of(brains)
                .map(brain -> new Player(brain, possibleColors.pop()))
                .collect(Collectors.toList()));
    }

    Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    Player getNextPlayer() {
        return players.get((currentPlayer + 1) % players.size());
    }

    Player getPreviousPlayer() {
        return players.get((currentPlayer - 1) % players.size());
    }

    Optional<Player> getPlayerFromColor(Color color) {
        return players.stream().filter(player -> player.color == color).findFirst();
    }

    void setupNextPlayer() {
        currentPlayer++;
        currentPlayer %= players.size();
    }
}
