package com.capgemini.algorithmbattles.ballsapp.logic;

import com.capgemini.algorithmbattles.ballsapp.logic.model.Board;
import com.capgemini.algorithmbattles.ballsapp.logic.model.Player;

/**
 * This class draws the board on the console.
 */
public class BoardDrawer {

  /**
   * It draws the board on the console.
   *
   * @param board the {@link Board} to draw
   */
  public static void drawBoard(Player[][] board) {
    for (int x = 0; x < board.length; x++) {
      for (int y = 0; y < board.length; y++) {
        printPlayer(board[x][y]);
      }
      System.out.println();
    }
    printLine();
  }

  private static void printLine() {
    for (int i = 0; i < 40; i++) {
      System.out.print("-");
    }
    System.out.println();
  }

  private static void printPlayer(Player player) {
    if (player == null) {
      System.out.print("-");
    } else if (player == Player.PLAYER_1) {
      System.out.print("1");
    } else if (player == Player.PLAYER_2) {
      System.out.print("2");
    }
    System.out.print("\t");
  }
}
