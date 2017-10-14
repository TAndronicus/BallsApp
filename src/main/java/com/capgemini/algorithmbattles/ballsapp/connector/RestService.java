package com.capgemini.algorithmbattles.ballsapp.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.algorithmbattles.ballsapp.common.api.MoveTo;
import com.capgemini.algorithmbattles.ballsapp.logic.GameManager;
import com.capgemini.algorithmbattles.ballsapp.logic.model.BoardCell;

/**
 * The REST-Service for firing events from the game.
 */
@RestController
public class RestService {

  @Autowired
  private GameManager gameManager;

  /**
   * The application is asked to calculate and make the next move.
   *
   * @return {@link MoveTo}
   */
  @RequestMapping(value = "/next-move", method = RequestMethod.POST)
  public MoveTo nextMove() {
    BoardCell move = gameManager.nextMove();
    return MoveTo.mapToMoveTO(move);
  }

  /**
   * The opponent made a move.
   *
   * @param moveTo {@link MoveTo}
   */
  @RequestMapping(value = "/move-made-by-opponent", method = RequestMethod.POST)
  public void moveMadeByOpponent(@RequestBody MoveTo moveTo) {
    gameManager.moveMadeByOpponent(MoveTo.mapToBoardCell(moveTo));
  }

  /**
   * The method is called when the game-application tries to connect to the player-application and start the new game.
   *
   * @param player The param says which player the current application is assigned to
   */
  @RequestMapping(value = "/start-game", method = RequestMethod.POST)
  public void connect(@RequestBody String player) {
    gameManager.startGame(player);
  }
}
