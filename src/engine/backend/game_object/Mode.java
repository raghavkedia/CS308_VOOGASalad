/**
 * 
 * @author mario_oliver93
 *
 */
package engine.backend.game_object;

import java.util.HashMap;
import java.util.Map;

public class Mode {

	private Map<Integer, Level> myLevels;
	private String myName;
	private GameStatistics myModeStatistics;
	private int index;

	/**
	 * Authoring Environment Constructor.
	 */
	public Mode(String myName, GameStatistics modeStatistics, Map<Integer, Level> levels) {
		this.myName = myName;
		this.myModeStatistics = modeStatistics;
		this.myLevels = levels;
	}

	/**
	 * Engine Testing Constructor.
	 */
	public Mode(String name) {
		this.myName = name;
		this.myLevels = new HashMap<Integer, Level>();
	}

	public Map<Integer, Level> getLevels() {
		return myLevels;
	}

	public void setLevelInMap(int levelIndex, Level level) {
		myLevels.put(levelIndex, level);
	}

	public GameStatistics getModeStatistics() {
		return myModeStatistics;
	}

	public String getName() {
		return this.myName;
	}

	/**
	 * Engine Testing Method
	 */
	public void addLevel(Level level) {
		this.myLevels.put(level.getIndex(), level);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Mode [levels=" + myLevels + "]";
	}

}
