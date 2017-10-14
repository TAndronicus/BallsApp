package com.capgemini.algorithmbattles.ballsapp.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.capgemini.algorithmbattles.ballsapp.connector.RestService;
import com.capgemini.algorithmbattles.ballsapp.logic.model.BoardCell;
import com.capgemini.algorithmbattles.ballsapp.logic.model.Player;
import com.capgemini.algorithmbattles.ballsapp.solution.GamePlayer;

/**
 * This class is responsible for creating an instance of a game while connecting and passing calls to {@link #nextMove()}
 * and {@link #moveMadeByOpponent(BoardCell)} from the {@link RestService}.
 * <p>
 * ATTENTION: The application must be rebooted to play a new game each time (also another instance of the application
 * can be started on another port). Unfortunately this is the best way to implement this without a database and
 * by adding a database the task would be much more difficult to implement. Now YOU - the programmers - can hold
 * all data inside the GamePlayer object without worrying about losing them after REST-Service call is finished
 * because of the lack of the database.
 */
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
   */
  public BoardCell nextMove() {
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
