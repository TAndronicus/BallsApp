package com.capgemini.algorithmbattles.ballsapp.logic;

import com.capgemini.algorithmbattles.ballsapp.logic.model.BoardCell;
import com.capgemini.algorithmbattles.ballsapp.logic.model.Player;
import com.capgemini.algorithmbattles.ballsapp.solution.GamePlayer;
import org.junit.Test;

/**
 * The tests for the game-application.
 */
public class GamePlayerTest {

  /**
   * This test checks if the application is able to have two instances and play against each other.
   */
  @Test
  public void shouldPlayGameWithItself() {
    GamePlayer gamePlayer1 = new GamePlayer(Player.PLAYER_1);
    GamePlayer gamePlayer2 = new GamePlayer(Player.PLAYER_2);

    while (!gamePlayer1.isGameFinished() && !gamePlayer2.isGameFinished()) {

      BoardCell move = gamePlayer1.nextMove();
      gamePlayer2.moveMadeByOpponent(move);
      gamePlayer1.printBoard();

      if (!gamePlayer2.isGameFinished()) {
        move = gamePlayer2.nextMove();
        gamePlayer1.moveMadeByOpponent(move);
        gamePlayer2.printBoard();
      }
    }
  }
}
