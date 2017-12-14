package com.capgemini.algorithmbattles.ballsapp.logic.model;

/**
 * The number of the player determines the order of them in the game.
 */
public enum Player {

  /**
   * This player is the first player to make a move.
   */
  PLAYER_1,
  /**
   * This player is the second player to make a move.
   */
  PLAYER_2;

  public String toString() {
    return this.equals(PLAYER_1) ? "player1" : "player2";
  }

  public static Player mapToPlayer(String player) {
    return "player1".equals(player) ? PLAYER_1 : PLAYER_2;
  }
  
  public static Player getOtherPlayer(Player player) {
	  Player otherPlayer = PLAYER_1.equals(player) ? PLAYER_2 : PLAYER_1;
	  return otherPlayer;
  }

}
