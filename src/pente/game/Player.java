package pente.game;

import pente.board.Color;
import pente.player.PlayerBrain;

class Player {
    final PlayerBrain playerBrain;
    final Color color;
    int captures = 0;

    Player(PlayerBrain playerBrain, Color color) {
        this.playerBrain = playerBrain;
        this.color = color;
    }
}
