package com.capgemini.algorithmbattles.ballsapp.logic.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.algorithmbattles.ballsapp.logic.BoardDrawer;
import com.capgemini.algorithmbattles.ballsapp.solution.Direction;

public class Board {

	private static final int SIZE = 10;
	private Player[][] board = new Player[SIZE][SIZE];
	private static final int[] spr = {2, 1, 3, 0, 4};

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

		BoardCell boardCell = null;

		//Sprawdzenie 4 wlasnych	
		for(Direction direction : Direction.values()) {
			boardCell = checkTacticWithoutCondition(player, true, direction, 3);
			if(boardCell != null)
				break;
		}
		if(boardCell != null)
			return boardCell;
		
		//Sprawdzenie niebezpieczenstw
		//3 lub wiecej niezbalokowanych
		for(Direction direction : Direction.values()) {
			boardCell = checkTacticWithoutCondition(Player.getOtherPlayer(player), true, direction, 2);
			if(boardCell != null)
				break;
		}
		if(boardCell != null)
			return boardCell;

		//Sprawdzenie zlozonej taktyki przeciwnika
		int sum;
		int[][] binBoard = convertBoardToNumber(Player.getOtherPlayer(player), true);
		for(int i = 2; i < 8; i++) {
			for(int j = 2; j < 8; j++){
				if(board[i][j] == null) {
					int rzedy = 0;
					sum = 0;
					for(int k = -2; k < 3; k++)
						sum += binBoard[i][j + k];
					if(sum == 2)
						rzedy++;
					sum = 0;
					for(int k = -2; k < 3; k++)
						sum += binBoard[i + k][j];
					if(sum == 2)
						rzedy++;
					sum = 0;
					for(int k = -2; k < 3; k++)
						sum += binBoard[i + k][j + k];
					if(sum == 2)
						rzedy++;
					sum = 0;
					for(int k = -2; k < 3; k++)
						sum += binBoard[i - k][j + k];
					if(sum == 2)
						rzedy++;
					if(rzedy > 1)
						return new BoardCell(i, j, null);
				}
			}
		}

		//Sprawdzenie 3 wlasnych
		for(Direction direction : Direction.values()) {
			boardCell = checkTacticWithoutCondition(player, false, direction, 2);
			if(boardCell != null)
				break;
		}
		if(boardCell != null)
			return boardCell;
		
		//Sprawdzenie 2 wlasnych
		for(Direction direction : Direction.values()) {
			boardCell = checkTacticWithoutCondition(player, true, direction, 1);
			if(boardCell != null)
				break;
		}
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
	
	public BoardCell checkTacticWithoutCondition(Player player, boolean differential, Direction direction, int determinant) {
		
		int[][] binBoard = convertBoardToNumber(player, differential);
		int[][] sumBoard = getSumBoard(binBoard, direction);
		BoardCell boardCell = getCellWithoutCondition(sumBoard, determinant, direction);
		return boardCell;
		
	}
	
	//TODO: Unused
	public BoardCell checkTacticWithDanger3(Player player, boolean differential, Direction direction, int determinant) {
		
		int[][] binBoard = convertBoardToNumber(player, differential);
		int[][] sumBoard = getSumBoard(binBoard, direction);
		BoardCell boardCell = getCellWithDanger3(sumBoard, determinant, direction, Player.getOtherPlayer(player));
		return boardCell;
		
	}
	
	//TODO: Unused
	public BoardCell getCellWithDanger3(int[][] sumBoard, int determinant, Direction direction, Player player) {
		
		List<int[]> list = getIndices(direction);
		int[] xLimit = list.get(0);
		int[] yLimit = list.get(1);
		int[] increment = list.get(2);
		int[] startingPoints = list.get(3);
		int summationStartingPoint = startingPoints[0];
		int i = 0;
		int j = 0;
		
		while(i < xLimit[1] - xLimit[0]) {
			j = 0;
			while(j < yLimit[1] - yLimit[0]) {
				if(sumBoard[i][j] > determinant && (board[i + summationStartingPoint + increment[0]][j + increment[1]] != player) 
						&& (board[i + summationStartingPoint + 2 * increment[0]][j + 2 * increment[1]] != player) && (board[i + summationStartingPoint + 3 * increment[0]][j + 3 * increment[1]] != player)) {
					if((board[i + summationStartingPoint + 3 * increment[0]][j + 3 * increment[1]] == null) && (board[i + summationStartingPoint + 4 * increment[0]][j + 4 * increment[1]] == null)) {
						j++;
						continue;
					}
					for(int k = 0; k < 5; k++)
						if(board[i + summationStartingPoint + spr[k] * increment[0]][j + spr[k] * increment[1]] == null) 
							return new BoardCell(i + summationStartingPoint + spr[k] * increment[0], j + spr[k] * increment[1], null);
				}
				j++;
			}
			i++;
		}
		
		return null;
		
	}
	
	public BoardCell getCellWithoutCondition(int[][] sumBoard, int determinant, Direction direction) {
		
		List<int[]> list = getIndices(direction);
		int[] xLimit = list.get(0);
		int[] yLimit = list.get(1);
		int[] increment = list.get(2);
		int[] startingPoints = list.get(3);
		int summationStartingPoint = startingPoints[0];
		int i = 0;
		int j;
		
		while(i < xLimit[1] - xLimit[0]) {
			j = 0;
			while(j < yLimit[1] - yLimit[0]) {
				if(sumBoard[i][j] > determinant) {
					for(int k = 0; k < 5; k++)
						if(board[i + summationStartingPoint + spr[k] * increment[0]][j + spr[k] * increment[1]] == null)
							return new BoardCell(i + summationStartingPoint + spr[k] * increment[0], j + spr[k] * increment[1], null);
				}
				j++;
			}
			i++;
		}
		
		return null;
		
	}

	public int[][] convertBoardToNumber(Player player, boolean differential) {

		int[][] bin = new int[SIZE][SIZE];
		int opponentValue = differential ? -4 : -1;

		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(board[i][j] == null)
					bin[i][j] = 0;
				else if(board[i][j] == player)
					bin[i][j] = 1;
				else
					bin[i][j] = opponentValue;
			}
		}

		return bin;

	}

	public int[][] getSumBoard(int[][] binBoard, Direction direction) {

		List<int[]> list = getIndices(direction);
		int[] xLimit = list.get(0);
		int[] yLimit = list.get(1);
		int[] increment = list.get(2);
		int[] startingPoints = list.get(3);
		int summationStartingPoint = startingPoints[0];
		int[][] sumBoard = new int[xLimit[1] - xLimit[0]][yLimit[1] - yLimit[0]];
		int sum, xInc, yInc;
		int i = 0;
		int j;
		while(i < xLimit[1] - xLimit[0]) {
			j = 0;
			while(j < yLimit[1] - yLimit[0]) {
				sum = 0; 
				xInc = 0;
				yInc = 0;
				for(int k = 0; k < 5; k++) {
					sum += binBoard[i + summationStartingPoint + xInc][j + yInc];
					xInc += increment[0];
					yInc += increment[1];
				}
				sumBoard[i][j] = sum;
				j++;
			}
			i++;
		}

		return sumBoard;

	}
	
	public List<int[]> getIndices(Direction direction) {
		
		int[] xLimit = {0, 10};
		int[] yLimit = {0, 10};
		int[] increment = {0, 0};
		int[] summationStartingPoint = {0};
		List<int[]> list = new ArrayList<int[]>();
		
		if(direction == Direction.HORIZONTALLY) {
			yLimit[1] = SIZE - 4;
			increment[1] = 1;			
		} else if(direction == Direction.VERTICALLY) {
			xLimit[1] = SIZE - 4;
			increment[0] = 1;
		} else if(direction == Direction.DIAGONAL_ASCENDING) {
			xLimit[1] = SIZE - 4;
			yLimit[1] = SIZE - 4;
			increment[0] = 1;
			increment[1] = 1;
		} else {
			xLimit[1] = SIZE - 4;
			yLimit[0] = 4;
			increment[0] = -1;
			increment[1] = 1;
		}
		summationStartingPoint[0] = direction == Direction.DIAGONAL_DESCENDING ? 4 : 0;
		
		list.add(xLimit);
		list.add(yLimit);
		list.add(increment);
		list.add(summationStartingPoint);
		
		return list;
		
	}

}