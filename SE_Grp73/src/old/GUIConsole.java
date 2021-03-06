package old;

import java.util.ArrayList;
import java.util.Scanner;

public class GUIConsole {

	Menu menu;
	ControlInputs police = new ControlInputs();
	DotsNBoxesEngine engine;

	String wall;
	int width;
	int height;
	Scanner sc;

	/**
	 * constructor
	 */
	public GUIConsole() {
		sc = new Scanner(System.in);
		engine = new DotsNBoxesEngine();
		menu = new Menu(engine);
	}

	/**
	 * prints the round number and the score of the players to the console
	 */
	private void displayGameStatistics() {
		System.out.println("This is round no. : " + engine.turnsPlayed);
		for (Player p : engine.playerlist.asArraylist()) {
			System.out.println(p);
		}
	}

	/**
	 * launches the menu
	 */
	public void launch() {
		menu.promptForTheMenueSettings();

		if (engine.mode == PlayingMode.AgainstHumans) {
			enterNumberOfPlayers();
		} else if (engine.mode == PlayingMode.AgainstAIMinMax) {
			enterPlayerName(1);
		} else if (engine.mode == PlayingMode.AgainstAIRandom) {
			enterPlayerName(1);
		} else if (engine.mode == PlayingMode.AIMinMaxSupport) {
			enterPlayerName(2);
		} else if (engine.mode == PlayingMode.AIRandomSupport) {
			enterPlayerName(2);
		} else {
			System.err.println("GUIConsole line 33 - State of mode in engiene: " + engine.mode);
			enterNumberOfPlayers();
		}
	}

	/**
	 * Prompt for entering a player name, after all names are entered the map
	 * dimension needs to be determined
	 */
	public void enterPlayerName(int numberOfPlayers) {
		Integer counter = 0;

		if (engine.mode == PlayingMode.AgainstAIRandom) {
			String name = police.getString(" Please enter your name ");
			Player playerOne = new Player(name, 0);
			AIRandom artificialIntelligence = new AIRandom("AIRandom", 0, engine);

			engine.playerlist.addPlayer(playerOne);
			engine.playerlist.addPlayer(artificialIntelligence);

		}

		else if (engine.mode == PlayingMode.AgainstAIMinMax) {
			String name = police.getString(" Please enter your Name ");
			Player playerOne = new Player(name, 0);
			AIMinMaxAlgo artificialIntelligence = new AIMinMaxAlgo("AIMinMax", 0, engine);

			engine.playerlist.addPlayer(playerOne);
			// AIMinMax is the second "player"
			engine.playerlist.addPlayer(artificialIntelligence);

		} else if (engine.mode == PlayingMode.AIMinMaxSupport) {
			String nameOfFirstPlayer = police.getString("Player 1: Please enter your name");
			Player playerOne = new Player(nameOfFirstPlayer, 0);
			engine.playerlist.addPlayer(playerOne);

			String nameOfSecondPlayer = police.getString("Player 2: Please enter your name");
			Player playerTwo = new Player(nameOfSecondPlayer, 0);
			engine.playerlist.addPlayer(playerTwo);

		} else if (engine.mode == PlayingMode.AIRandomSupport) {

			String nameOfFirstPlayer = police.getString("Player 1: Please enter your name");
			Player playerOne = new Player(nameOfFirstPlayer, 0);
			engine.playerlist.addPlayer(playerOne);

			String nameOfSecondPlayer = police.getString("Player 2: Please enter your name");
			Player playerTwo = new Player(nameOfSecondPlayer, 0);
			engine.playerlist.addPlayer(playerTwo);

		} else {

			// we are in first mode : player vs at least one player
			while (counter < numberOfPlayers) {

				String name = police.getString("Player " + counter + " please enter your name");
				Player currentP = new Player(name, 0);
				engine.playerlist.addPlayer(currentP);
				counter++;
			}
		}

		enterMapDimension();

	}

	/**
	 * Prompt for entering a wall number to make a move
	 */
	public void move() {
		Player currentPlayer = engine.playerlist.getCurrentPlayer();
		int input = -1;
		String needHelp = "Empty";

		displayGameStatistics();

		if (currentPlayer.isAI) {
			AI currentAI = (AI) currentPlayer;
			currentAI.clearRemainingNumbers();
			// if the current player is an AI calculate its move and add it as
			// an input
			currentAI.calculateRemainingNumbers(engine.getMap(), width, height);
			input = currentAI.getNextMove();
		} else {

			if (engine.playerlist.size() == 2 && engine.mode == PlayingMode.AgainstHumans) {

				while (!needHelp.toUpperCase().equals("YES") && !needHelp.toUpperCase().equals("NO")) {
					needHelp = police.getString("Would you like to receive help from the AI? (Type 'YES' or 'NO')");

					if (!needHelp.toUpperCase().equals("YES") && !needHelp.toUpperCase().equals("NO")) {
						System.out.println(
								"Please enter a valid command : 'YES' if you would like to receive help and 'NO' if you don't.");

					}
				}
				if (needHelp.toUpperCase().equals("YES")) {

					currentPlayer.supportingAI = new AIMinMaxAlgo(currentPlayer.name, currentPlayer.getScore(), engine);

					engine.playerlist.getCurrentPlayer().supportingAI = new AIMinMaxAlgo(
							engine.playerlist.getCurrentPlayer().getName(),
							engine.playerlist.getCurrentPlayer().getScore(), engine);

					// if the player types "yes" print the advice (move) of the
					// AI to the console
					System.out.println(engine.playerlist.getCurrentPlayer().getName()
							+ ", the MinMaxAI advises you to play the number "
							+ engine.playerlist.getCurrentPlayer().supportingAI.getNextMove() + "\n");
				}

			}
			if (engine.mode == PlayingMode.AIRandomSupport) {
				// just print the move of the AIRandom to the console
				currentPlayer.supportingAI = new AIRandom(currentPlayer.name, currentPlayer.getScore(), engine);

				currentPlayer.supportingAI.calculateRemainingNumbers(engine.getMap(), width, height);
				System.out.println(currentPlayer.getName() + ", the RandomAI advises you to play the number "
						+ currentPlayer.supportingAI.getNextMove() + "\n");
			} else if (engine.mode == PlayingMode.AIMinMaxSupport) {
				// just print the move of the AIMinMax to the console
				currentPlayer.supportingAI = new AIMinMaxAlgo(currentPlayer.name, currentPlayer.getScore(), engine);

				System.out.println(currentPlayer.getName() + ", the MinMaxAI advises you to play the number "
						+ currentPlayer.supportingAI.getNextMove() + "\n");
			}

			// if !currentPlayer.isAI --> input = currentPlayer.getNextMove;
			input = police.getNumber("Player: " + currentPlayer.getName() + " please enter a wall number",
					"\tPlease enter a positive whole number.");
		}
		// The DotsNBoxesEngine calculates the Coords of the Arrayfield with the
		// input
		int[] coords = engine.getCoordinatesOfNumberInMap(input, width, height);
		int xCoord = coords[1];
		int yCoord = coords[0];

		// If the input is correct replace the field with the correct sign and
		// check if a box is complete
		if (engine.replaceNumber(currentPlayer, input, yCoord, xCoord)) {
			if (engine.completedBox()) {
				int[] coordsComplete = engine.getCoordinatesOfCompletedBox();
				int xComplete = coordsComplete[1];
				int yComplete = coordsComplete[0];
				engine.updateBoxWithName(currentPlayer, yComplete, xComplete);

				// check for another completed Box..since one move can complete
				// 2 boxes at the same time and update the map again

				if (engine.completedBox()) {
					int[] coordsComplete2 = engine.getCoordinatesOfCompletedBox();
					int xComplete2 = coordsComplete2[1];
					int yComplete2 = coordsComplete2[0];
					engine.updateBoxWithName(currentPlayer, yComplete2, xComplete2);
				}
				displayMap();
			} else {

				engine.playerlist.nextPlayer();
				displayMap();
			}
		}
	}
	

	/**
	 * Prompt to enter the number of players for this gaming round
	 */
	public void enterNumberOfPlayers() {
		int input = -1;

		while (input < 2) {
			input = police.getNumber("Please enter a number (>= 2) of player",
					"\tPlease enter a positive whole number.");
		}
		enterPlayerName(input);
	}

	/**
	 * Prompt for entering a map dimension
	 */
	public void enterMapDimension() {
		width = -1;
		height = -1;
		int ArrayWidth, ArrayHeight;

		while (!engine.checkFieldDimension(width, height)) {
			System.out.println("\twidth * height <= 450");
			ArrayWidth = police.getNumber("Please enter the width ", "\tPlease enter a positive whole number.");
			ArrayHeight = police.getNumber("Please enter the height ", "\tPlease enter a positive whole number.");
			width = engine.calculateArrayWidth(ArrayWidth);
			height = engine.calculateArrayHeight(ArrayHeight);
		}
		engine.setHeight(height);
		engine.setWidth(width);
		engine.initializeMap();
		displayMap();
	}

	/**
	 * Prints the map (2D - Array) on the console
	 * 
	 * @param newMap
	 *            the new map that should be updated
	 */
	public void displayMap() {
		String map[][] = engine.getMap();
		for (int height = 0; height < this.height; height++) {
			System.out.println();
			for (int width = 0; width < this.width; width++) {
				if (amountOfDigits(map[height][width]) == 1) { // format
																// _for_one-digit_number
					if (width < this.width - 1) { // one line
						System.out.print("  " + map[height][width] + "  ");
					} else // begin the next line
						System.out.println("  " + map[height][width] + "  ");

				} else if (amountOfDigits(map[height][width]) == 2) { // format_for_two-digit_number
					if (width < this.width - 1) {
						System.out.print(" " + map[height][width] + "  ");
					} else
						System.out.println(" " + map[height][width] + "  ");

				} else if (amountOfDigits(map[height][width]) == 3) { // format_for_three-digit_number
					if (width < this.width - 1) {
						System.out.print(" " + map[height][width] + " ");
					} else
						System.out.println(" " + map[height][width] + " ");
				}
			}
		}
		// width, height
		if (engine.gameEnded()) {
			endOfGame();
		} else
			move();
	}

	/**
	 * Checks the amount of digits in any string
	 * 
	 * @param string
	 *            The string that should be checked
	 * @return the amount of digits in a string
	 */
	private int amountOfDigits(String string) {
		int digits = 0;
		for (int i = 0; i < string.length(); i++) {
			digits++;
		}
		return digits;
	}

	/**
	 * Prints the game statistics onto the console and the winner or winners
	 * with their score
	 */
	public void endOfGame() {
		System.out.println("GAME ENDED ------------------------------- GAME ENDED");
		Player winner = engine.playerlist.getBestPlayer();
		displayGameStatistics();

		if (engine.draw()) {
			engine.printDrawMessage();
		}

		else {

			System.out.println("\n" + "The Winner is " + winner.getName() + " - Your Score: " + winner.getScore() + "\n"
					+ "CONGRATUUUUUU....WAIT FOR IT.....UULATIONS!!! :)");

		}
	}

}
