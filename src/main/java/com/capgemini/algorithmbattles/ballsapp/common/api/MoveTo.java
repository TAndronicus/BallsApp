package com.capgemini.algorithmbattles.ballsapp.common.api;

import com.capgemini.algorithmbattles.ballsapp.logic.model.BoardCell;
import com.capgemini.algorithmbattles.ballsapp.logic.model.Player;

public class MoveTo {

  private String player;
  private BoardCellTO cell;

  public static MoveTo mapToMoveTO(BoardCell move) {
    MoveTo result = new MoveTo();
    result.player = move.getPlayer().toString();
    result.cell = new BoardCellTO(move.getX(), move.getY(), move.getPlayer().toString());
    return result;
  }

  public static BoardCell mapToBoardCell(MoveTo moveTo) {
    return new BoardCell(moveTo.getCell().getX(), moveTo.getCell().getY(),
            Player.mapToPlayer(moveTo.getCell().getPlayer()));
  }

  public String getPlayer() {
    return player;
  }

  public BoardCellTO getCell() {
    return cell;
  }

}
