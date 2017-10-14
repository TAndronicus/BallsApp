package com.capgemini.algorithmbattles.ballsapp.common.api;

public class BoardCellTO {

  private int x;
  private int y;
  private String player;

  public BoardCellTO() {

  }

  public BoardCellTO(int x, int y, String player) {
    this.x = x;
    this.y = y;
    this.player = player;
  }

  public String getPlayer() {
    return player;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

}
