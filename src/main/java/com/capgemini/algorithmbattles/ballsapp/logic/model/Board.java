package com.capgemini.algorithmbattles.ballsapp.logic.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.algorithmbattles.ballsapp.logic.BoardDrawer;
import com.capgemini.algorithmbattles.ballsapp.solution.Direction;

public class Board {

	private static final int SIZE = 10;
	private Player[][] board = new Player[SIZE][SIZE];
	private static final int[] spr = {2, 1, 3, 0, 4};
	private static final Character PLAYERCHARACTER = Character.valueOf('+');
	private static final Character OPPONENTCHARACTER = Character.valueOf('-');

	public void placeMove(BoardCell move) {
		board[move.getX()][move.getY()] = move.getPlayer();
	}

	public BoardCell getFirstEmptyCell() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] == null) {
					return new BoardCell(i, j, null);
				}
			}
		}
		return null;
	}

	public boolean isGameFinished() {
		// TODO: Please implement it.
		return false;
	}

	public void printBoard() {
		BoardDrawer.drawBoard(this.board);
	}

	public BoardCell getEmptyCell(Player player) {

		BoardCell boardCell;
		//1
		boardCell = searchForPlace("++++0", 4, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("0++++", 0, player);
		if(boardCell != null)
			return boardCell;
		//2
		boardCell = searchForPlace("+++0+", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("+0+++", 1, player);
		if(boardCell != null)
			return boardCell;
		//3
		boardCell = searchForPlace("++0++", 2, player);
		if(boardCell != null)
			return boardCell;
		//4
		boardCell = searchForPlace("----0", 4, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("0----", 0, player);
		if(boardCell != null)
			return boardCell;
		//5
		boardCell = searchForPlace("---0-", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("-0---", 1, player);
		if(boardCell != null)
			return boardCell;
		//6
		boardCell = searchForPlace("--0--", 2, player);
		if(boardCell != null)
			return boardCell;
		//7 TODO: M
		boardCell = searchForPlace("0+++0", 0, player);
		if(boardCell != null)
			return boardCell;
		//8
		boardCell = searchForPlace("0++0+0", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("0+0++0", 2, player);
		if(boardCell != null)
			return boardCell;
		//9
		boardCell = searchForPlace("0++0+", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("+0++0", 1, player);
		if(boardCell != null)
			return boardCell;
		//10
		boardCell = searchForPlace("++0+0", 2, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("0+0++", 2, player);
		if(boardCell != null)
			return boardCell;
		//11 TODO: M
		boardCell = searchForPlace("++00+", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("+00++", 1, player);
		if(boardCell != null)
			return boardCell;
		//12 TODO: M
		boardCell = searchForPlace("+0+0+", 1, player);
		if(boardCell != null)
			return boardCell;
		//13 TODO: M
		boardCell = searchForPlace("0---0", 0, player);
		if(boardCell != null)
			return boardCell;
		//14
		boardCell = searchForPlace("0--0-", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("-0--0", 1, player);
		if(boardCell != null)
			return boardCell;
		//15
		boardCell = searchForPlace("--0-0", 2, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("0-0--", 2, player);
		if(boardCell != null)
			return boardCell;
		//TODO: M
		//16
		boardCell = searchForPlace("++000", 4, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("000++", 0, player);
		if(boardCell != null)
			return boardCell;
		//17
		boardCell = searchForPlace("0++00", 4, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("00++0", 0, player);
		if(boardCell != null)
			return boardCell;
		//18
		boardCell = searchForPlace("+0+00", 4, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("00+0+", 0, player);
		if(boardCell != null)
			return boardCell;
		//19
		boardCell = searchForPlace("0+0+0", 2, player);
		if(boardCell != null)
			return boardCell;
		//20
		boardCell = searchForPlace("+00+0", 2, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("0+00+", 2, player);
		if(boardCell != null)
			return boardCell;
		//21
		boardCell = searchForPlace("+000+", 2, player);
		if(boardCell != null)
			return boardCell;
		//22
		boardCell = searchForPlace("00+00", 3, player);
		if(boardCell != null)
			return boardCell;
		//23
		boardCell = searchForPlace("0+000", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("000+0", 1, player);
		if(boardCell != null)
			return boardCell;
		//24
		boardCell = searchForPlace("+0000", 3, player);
		if(boardCell != null)
			return boardCell;
		boardCell = searchForPlace("0000+", 1, player);
		if(boardCell != null)
			return boardCell;

		//Pierwszy pusty
		for (int i = 0; i < SIZE; i++) {
			for (int j = SIZE - 1; j >= 0; j--) {
				if (board[(i + 3) % 10][(i + j + 7) % 10] == null) {
					return new BoardCell((i + 3) % 10, (i + j + 7) % 10, null);
				}
			}
		}
		return null;

	}
	
	private BoardCell searchForPlace(String sequence, int place, Player player) {
		
		BoardCell boardCell;
		List<Player> listOfPlayers = getPlayerList(sequence, player);
		boardCell = searchHorizontally(listOfPlayers, place, player);
		if(boardCell != null)  {
			System.out.println("x = " + boardCell.getX() + ", y = " + boardCell.getY());
			return boardCell;
		}
		boardCell = searchVertically(listOfPlayers, place, player);
		if(boardCell != null) {
			System.out.println("x = " + boardCell.getX() + ", y = " + boardCell.getY());
			return boardCell;
		}
		boardCell = searchPositiveSlant(listOfPlayers, place, player);
		if(boardCell != null) {
			System.out.println("x = " + boardCell.getX() + ", y = " + boardCell.getY()); 
			return boardCell;
		}
		boardCell = searchNegativeSlant(listOfPlayers, place, player);
		if(boardCell != null)
			System.out.println("x = " + boardCell.getX() + ", y = " + boardCell.getY());
		return boardCell;
		
	}
	
	private BoardCell searchHorizontally(List<Player> listOfPlayers, int place, Player player) {

		for(int i = 0; i < SIZE - listOfPlayers.size() + 1; i++) {
			for(int j = 0; j < SIZE; j++) {
				boolean matches = true;
				for(int k = 0; k < listOfPlayers.size(); k++) {
					matches = matches && (board[i + k][j] == listOfPlayers.get(k));
				}
				if(matches)
					return new BoardCell(i + place, j, player);
			}
		}
		
		return null;
		
	}

	private BoardCell searchVertically(List<Player> listOfPlayers, int place, Player player) {

		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE - listOfPlayers.size() + 1; j++) {
				boolean matches = true;
				for(int k = 0; k < listOfPlayers.size(); k++) {
					matches = matches && (board[i][j + k] == listOfPlayers.get(k));
				}
				if(matches)
					return new BoardCell(i, j + place, player);
			}
		}
		
		return null;
		
	}

	private BoardCell searchPositiveSlant(List<Player> listOfPlayers, int place, Player player) {

		for(int i = 0; i < SIZE - listOfPlayers.size() + 1; i++) {
			for(int j = 0; j < SIZE - listOfPlayers.size() + 1; j++) {
				boolean matches = true;
				for(int k = 0; k < listOfPlayers.size(); k++) {
					matches = matches && (board[i + k][j + k] == listOfPlayers.get(k));
				}
				if(matches)
					return new BoardCell(i + place, j + place, player);
			}
		}
		
		return null;
		
	}

	private BoardCell searchNegativeSlant(List<Player> listOfPlayers, int place, Player player) {
		
		for(int i = listOfPlayers.size() - 1; i < SIZE; i++) {
			for(int j = 0; j < SIZE - listOfPlayers.size() + 1; j++) {
				boolean matches = true;
				for(int k = 0; k < listOfPlayers.size(); k++) {
					matches = matches && (board[i - k][j + k] == listOfPlayers.get(k));
				}
				if(matches) 
					return new BoardCell(i - place, j + place, player);
			}
		}
		
		return null;
		
	}

	private List<Player> getPlayerList(String sequence, Player player) {
		
		List<Player> listOfPlayers = new ArrayList<>();
		for(int i = 0; i < sequence.length(); i++) {
			if(PLAYERCHARACTER.equals(sequence.charAt(i)))
				listOfPlayers.add(player);
			else if(OPPONENTCHARACTER.equals(sequence.charAt(i)))
				listOfPlayers.add(Player.getOtherPlayer(player));
			else
				listOfPlayers.add(null);
		}
		return listOfPlayers;
		
	}
	
}