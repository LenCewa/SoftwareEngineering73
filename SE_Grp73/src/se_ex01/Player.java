package se_ex01;

public class Player {
	public String name;
	public int score;
	public int ID;
	public boolean isAI = false;
	public AI supportingAI;

	/**
	 * Player who plays the game
	 * 
	 * @param name
	 *            the name of the player
	 * @param score
	 *            the score of the player during the game, initialize with 0
	 */
	public Player(String name, int score) {
		this.score = score;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Increases the score of a player by any integer value that is passed
	 * 
	 * @param pointsToIncreaseBy
	 *            The points that the player gained by his strategically move
	 */
	public void increaseScoreBy(int pointsToIncreaseBy) {
		this.score += pointsToIncreaseBy;
	}
	
	public String toString() {
		return "Name: " + this.name + "\tScore: " + this.score;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public String getShortName() {
		return "P" + ID;
	}

}
