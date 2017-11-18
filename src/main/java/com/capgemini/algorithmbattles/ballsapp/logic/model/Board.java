package com.capgemini.algorithmbattles.ballsapp.logic.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.algorithmbattles.ballsapp.logic.BoardDrawer;
import com.capgemini.algorithmbattles.ballsapp.logic.GameManager;
import com.capgemini.algorithmbattles.ballsapp.solution.Direction;

public class Board {

	private static final int SIZE = 10;
	private Player[][] board = new Player[SIZE][SIZE];
	private static final Character PLAYERCHARACTER = Character.valueOf('+');
	private static final Character OPPONENTCHARACTER = Character.valueOf('-');
	private static Map<String, Integer> weights = new Hashtable<>();

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
		// TODO: To implement.
		return false;
	}

	public void printBoard() {
		BoardDrawer.drawBoard(this.board);
	}

	public BoardCell getEmptyCell(Player player) throws IOException {

		List<String> strategies = Files.readAllLines(Paths.get("src/main/resources/ballsStrategy.txt"), StandardCharsets.UTF_8);
		List<String> weightsProperties = Files.readAllLines(Paths.get("src/main/resources/weights.txt"), StandardCharsets.UTF_8);
		for(String line : weightsProperties) {
			String[] property = line.split(";");
			weights.put(property[0], Integer.valueOf(property[1]));
		}
		for(String strategy : strategies) {
			BoardCell boardCell = searchForPlace(strategy, player);
			if(boardCell != null) {
				return boardCell;
			}
		}

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

	private BoardCell searchForPlace(String sequence, Player player) {

		BoardCell boardCell;
		List<Player> listOfPlayers = getPlayerList(sequence, player);
		for(Direction direction : Direction.values()) {
			boardCell = searchDirection(listOfPlayers, player, direction);
			if(boardCell != null) {
				System.out.println("x = " + boardCell.getX() + ", y = " + boardCell.getY());
				return boardCell;
			}
		}
		return null;

	}

	private BoardCell searchDirection(List<Player> listOfPlayers, Player player, Direction direction) {

		int xMin = getXMinForDirection(direction, listOfPlayers);
		int xMax = getXMaxForDirection(direction, listOfPlayers);
		int yMin = getYMinForDirection(direction, listOfPlayers);
		int yMax = getYMaxForDirection(direction, listOfPlayers);
		int xIncrement = getXIncrement(direction);
		int yIncrement = getYIncrement(direction);

		for(int i = xMin; i < xMax; i++) {
			for(int j = yMin; j < yMax; j++) {
				boolean matches = true;
				for(int k = 0; k < listOfPlayers.size(); k++) {
					matches = matches && (board[i + k * xIncrement][j + k * yIncrement] == listOfPlayers.get(k));
				}
				if(matches) {
					List<Integer[]> indices = getEmptyIndices(i, j, direction, listOfPlayers);
					Integer[] place = getPlace(indices, player, direction);
					return new BoardCell(place[0] , place[1], player);
				}
			}
		}
		return null;
	}

	private Integer[] getPlace(List<Integer[]> indices, Player player, Direction direction) {

		if(indices.size() == 1)
			return indices.get(0);
		Map<Integer[], Integer> placesWeight = new HashMap<>();
		for(Integer[] index : indices) {
			Integer placeWeight = 0;
			for(Direction dir : Direction.values()) {
				if(dir.equals(direction))
					continue;
				placeWeight += getWeightInPositiveDirection(index, direction, player);
				placeWeight += getWeightInNegativeDirection(index, direction, player);
			}
			placesWeight.put(index, placeWeight);
		}
		Integer[] maxWeightedIndex = getMaxWeightedIndex(placesWeight);
		return maxWeightedIndex;

	}

	private Integer[] getMaxWeightedIndex(Map<Integer[], Integer> placesWeight) {

		Iterator<Entry<Integer[], Integer>> iterator = placesWeight.entrySet().iterator();
		Entry<Integer[], Integer> first = iterator.next();
		Integer[] highestIndex = first.getKey();
		Integer highestValue = first.getValue();
		while(iterator.hasNext()) {
			Entry<Integer[], Integer> entry = iterator.next();
			if(entry.getValue() > highestValue) {
				highestIndex = entry.getKey();
				highestValue = entry.getValue();
			}
		}
		return highestIndex;
	
	}

	private Integer getWeightInPositiveDirection(Integer[] index, Direction direction, Player player) {
		
		int xIncrement = getXIncrement(direction);
		int yIncrement = getYIncrement(direction);
		Iterator<Entry<String, Integer>> iterator = weights.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			List<Player> listOfPlayers = getPlayerList(entry.getKey(), player);
			boolean matches = true;
			if(index[0] + listOfPlayers.size() * xIncrement < 0
					|| index[0] + (listOfPlayers.size() + 1) * xIncrement > SIZE
					|| index[1] + listOfPlayers.size() * yIncrement < 0
					|| index[1] + (listOfPlayers.size() + 1) * yIncrement > SIZE)
				continue;
			for(int k = 0; k < listOfPlayers.size(); k++) {
				matches = matches && listOfPlayers.get(k) == (board[index[0] + (k + 1) * xIncrement][index[1] + (k + 1) * yIncrement]);
			}
			if(matches)
				return entry.getValue();
		}
		return 0;
		
	}

	private Integer getWeightInNegativeDirection(Integer[] index, Direction direction, Player player) {
		
		int xIncrement = - getXIncrement(direction);
		int yIncrement = - getYIncrement(direction);
		Iterator<Entry<String, Integer>> iterator = weights.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			List<Player> listOfPlayers = getPlayerList(entry.getKey(), player);
			boolean matches = true;
			if(index[0] + listOfPlayers.size() * xIncrement < 0
					|| index[0] + (listOfPlayers.size() + 1) * xIncrement > SIZE
					|| index[1] + listOfPlayers.size() * yIncrement < 0
					|| index[1] + (listOfPlayers.size() + 1) * yIncrement > SIZE)
				continue;
			for(int k = 0; k < listOfPlayers.size(); k++) {
				matches = matches && listOfPlayers.get(k) == (board[index[0] + (k + 1) * xIncrement][index[1] + (k + 1) * yIncrement]);
			}
			if(matches)
				return entry.getValue();
		}
		return 0;
		
	}

	private List<Integer[]> getEmptyIndices(int i, int j, Direction direction, List<Player> listOfPlayers) {

		int xIncrement = getXIncrement(direction);
		int yIncrement = getYIncrement(direction);
		List<Integer[]> indices = new ArrayList<>();
		for(int k = 0; k < listOfPlayers.size(); k++) {
			if(listOfPlayers.get(k) == null)
				indices.add(new Integer[]{i + k * xIncrement, j + k * yIncrement});
		}
		return indices;

	}

	private int getXIncrement(Direction direction) {

		if(Direction.VERTICALLY.equals(direction))
			return 0;
		if(Direction.NEGATIVESLANT.equals(direction))
			return -1;
		return 1;

	}

	private int getYIncrement(Direction direction) {

		if(Direction.HORIZONTALLY.equals(direction))
			return 0;
		return 1;

	}

	private int getXMinForDirection(Direction direction, List<Player> listOfPlayers) {

		if(Direction.NEGATIVESLANT.equals(direction))
			return listOfPlayers.size() - 1;
		else
			return 0;

	}

	private int getXMaxForDirection(Direction direction, List<Player> listOfPlayers) {

		if(Direction.HORIZONTALLY.equals(direction)
				|| Direction.POSITIVESLANT.equals(direction))
			return SIZE - listOfPlayers.size() + 1;
		else
			return SIZE;

	}

	private int getYMinForDirection(Direction direction, List<Player> listOfPlayers) {

		return 0;

	}

	private int getYMaxForDirection(Direction direction, List<Player> listOfPlayers) {

		if(Direction.HORIZONTALLY.equals(direction))
			return SIZE;
		else
			return SIZE - listOfPlayers.size() + 1;

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