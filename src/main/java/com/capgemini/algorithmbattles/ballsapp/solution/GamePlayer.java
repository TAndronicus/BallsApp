package com.capgemini.algorithmbattles.ballsapp.solution;

import com.capgemini.algorithmbattles.ballsapp.logic.model.Board;
import com.capgemini.algorithmbattles.ballsapp.logic.model.BoardCell;
import com.capgemini.algorithmbattles.ballsapp.logic.model.Player;

public class GamePlayer {

  private Player player;
  private Board board = new Board();

  public GamePlayer(Player player) {
    this.player = player;
  }

  /**
   * The application should calculate the next move after this method call.
   *
   * @return the next {@link BoardCell move} for current player.
   */
  public BoardCell nextMove() {
    BoardCell cell = getCellForNextMove();
    cell.setPlayer(player);
    board.placeMove(cell);
    return cell;
  }

  private BoardCell getCellForNextMove() {
    // TODO: Please implement it.
    //BoardCell firstEmptyCell = board.getFirstEmptyCell();
	BoardCell firstEmptyCell = board.getEmptyCell(player);
    firstEmptyCell.setPlayer(player);
    return firstEmptyCell;
  }

  /**
   * The opponent made the move passed in param.
   *
   * @param move the {@link BoardCell} made by the opponent.
   */
  public void moveMadeByOpponent(BoardCell move) {
    this.board.placeMove(move);
  }

  /**
   * @return true if the game is finished
   */
  public boolean isGameFinished() {
    return this.board.isGameFinished();
  }

  /**
   * Draw the board on the console.
   */
  public void printBoard() {
    this.board.printBoard();
  }
}
