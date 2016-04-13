/**
 * 
 * @author mario_oliver93
 *
 */
package engine.backend;

public class GameStatisticsObject {

	private double myMoney;
	private double myScore;
	private double myHealth;
	
	private int currentMode = 0;
	private int currentLevel = 0;
	private int numOfLevels = 0;
	private int numOfModes = 0;
	private int nextAvailableID = 0;
	private int nextAvailableEntityID = 0;

	public GameStatisticsObject() {
		
	}

	public void nextLevel() {
		++currentLevel;
	}

	public int getCurrentMode() {
		return currentMode;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void incrementNumLevels() {
		++numOfLevels;
	}

	public void incrementNumModes() {
		++numOfModes;
	}

	public int nextAvailableID() {
		return nextAvailableID++;
	}
	
	public int nextEntityID(){
		return nextAvailableEntityID++;
	}

}
