package se_ex01;

import java.util.Scanner;

public class GUIConsole {
	Player player = new Player(0);
	Scanner s = new Scanner(System.in);
	DotsNBoxesEngine gameEngine;
	Player winner;
	int numberOfMoves = 0;
	String wall;
	String[][] map;

	public GUIConsole() {
		// leave as default?!
	}

	/**
	 * Prompt to enter the name of the player who is playing
	 * 
	 * @return True if the name is entered
	 */
	public boolean enterPlayerName() {
		String name = s.next();
		player.setName(name);
		return true;
	}

	/**
	 * Prompt to enter the number of players for this gaming round
	 * 
	 * @return True if the number of players are entered
	 */
	public boolean enterNumberOfPlayers() {
		String numberOfPlayers = s.next();
		Integer input = Integer.parseInt(numberOfPlayers);
		while (!(input instanceof Integer)) {
			System.out.println("0 is invalid, please enter an integer greater than 0");
			numberOfPlayers = s.next();
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean mapDimension() {
		int width = 1;
		int height = 1;

		while (!(width >= 2 && width % 2 == 0 && height >= 2 && height % 2 == 0)) {
			System.out.print("Please enter the width, even integer greater than 1: ");
			width = s.nextInt();
			System.out.print("Please enter the height, even integer greater than 1: ");
			height = s.nextInt();
		}
		map = new String[width][height];
		return true;
	}

	/**
	 * The player gives an integer input to remove a wall
	 * 
	 * @return The wall number
	 */
	public int move(Player player) {
		System.out.print(player.getName() + ": Please enter a number of a wall: ");
		int input = s.nextInt();
		
		return input;
	}
	
	/**
	 * Initialize 
	 * @return
	 */
	public String[][] initializeMap() {
		return null;
	}
	
	/**
	 * Update after the map was modified and print on console
	 * @param newMap
	 */
	public void updateMap(String[][] newMap) {
		
	}
	
	public void endOfGame() {
		//winner etc. wird ausgegeben 
	}
	

}
