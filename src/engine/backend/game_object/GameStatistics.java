package engine.backend.game_object;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import engine.backend.rules.IAction;
import engine.backend.rules.LevelAction;

/**
 * 
 * @author Raghav Kedia
 * 
 *
 */
public class GameStatistics implements IModifiable{
	
	private static final String PREFIX = "set";
	private int initialNumLives;
	private int currentNumLives;
	private double initialResources;
	private double currentResources;
	private List<Integer> endOfLevelLives;
	private List<Double> endOfLevelResources;
	private int currentLevelIndex;
	private String currentMode;

	public GameStatistics(int numLives, double resources) {
		setInitialNumLives(numLives);
		setCurrentNumLives(numLives);
		setInitialResources(resources);
		setCurrentResources(resources);
//		endOfLevelLives = new ArrayList<Integer>();
//		endOfLevelResources = new ArrayList<Double>();
	}
	
	public GameStatistics() {
		
	}
 
	public void addEndOfLevelLives(int numLives) {
		endOfLevelLives.add(numLives);
	}

	public void addEndOfLevelResources(double resources) {
		endOfLevelResources.add(resources);
	}

	public int getInitialNumLives() {
		return initialNumLives;
	}

	public void setInitialNumLives(int initialNumLives) {
		this.initialNumLives = initialNumLives;
	}

	public int getCurrentNumLives() {
		return currentNumLives;
	}

	public void setCurrentNumLives(int currentNumLives) {
		this.currentNumLives = currentNumLives;
	}
	
	public void setCurrentNumLives(String deltaNumLives){
		this.currentNumLives += Integer.parseInt(deltaNumLives);
	}

	public double getInitialResources() {
		return initialResources;
	}

	public void setInitialResources(double initialResources) {
		this.initialResources = initialResources;
	}

	public double getCurrentResources() {
		return currentResources;
	}

	public void setCurrentResources(double currentResources) {
		this.currentResources = currentResources;
	}
	

	public void setCurrentResources(String delta){
		this.currentNumLives += Double.parseDouble(delta);
	}

	public int getCurrentLevelIndex() {
		checkEndOfGame();
		return currentLevelIndex;
	}

	public void setCurrentModeIndex(String c){
		currentMode = c;
	}

	public String getCurrentMode() {
		checkEndOfGame();
		return currentMode;
	}
	
	public void setCurrentLevelIndex(int currentLevelIndex) {
		this.currentLevelIndex = currentLevelIndex;
	}
	
	public void setCurrentLevelIndex(String delta){
		this.currentLevelIndex = Integer.parseInt(delta);
	}
	
	private void checkEndOfGame(){
		if(currentNumLives == 0){
			setCurrentLevelIndex(-1);
		}
	}

	@Override
	public void applyAction(IAction action) {
		String instanceVar = ((LevelAction) action).getVariableToModify();
		String deltaVal = ((LevelAction) action).getDeltaValue();
		Method setMethod;

		try {
			String methodName = PREFIX + instanceVar;
			setMethod = this.getClass().getMethod(methodName, String.class);
			setMethod.invoke(this, deltaVal);

			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}



