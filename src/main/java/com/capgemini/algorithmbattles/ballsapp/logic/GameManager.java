package com.capgemini.algorithmbattles.ballsapp.logic;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capgemini.algorithmbattles.ballsapp.connector.RestService;
import com.capgemini.algorithmbattles.ballsapp.logic.model.BoardCell;
import com.capgemini.algorithmbattles.ballsapp.logic.model.Player;
import com.capgemini.algorithmbattles.ballsapp.solution.GamePlayer;

@Component
public class GameManager {

  private static final Logger LOG = LoggerFactory.getLogger(GameManager.class);

  private GamePlayer gamePlayer;

  /**
   * Creates the {@link GamePlayer}
   *
   * @param playerString the number of the player (1s or 2nd).
   */
  public void startGame(String playerString) {
    if (gamePlayer != null) {
      LOG.warn("Current game was interrupted because a new game was started on the client.");
    }
    Player player = playerString.equals("player1") ? Player.PLAYER_1 : Player.PLAYER_2;
    gamePlayer = new GamePlayer(player);
  }

  /**
   * The application should calculate the next move after this method call.
   *
   * @return the next {@link BoardCell move} for current player.
 * @throws IOException 
   */
  public BoardCell nextMove() throws IOException {
    return gamePlayer.nextMove();
  }

  /**
   * The opponent made the move passed in param.
   *
   * @param move the {@link BoardCell} made by the opponent.
   */
  public void moveMadeByOpponent(BoardCell move) {
    gamePlayer.moveMadeByOpponent(move);
  }
}
