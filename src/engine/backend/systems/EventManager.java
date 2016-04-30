package engine.backend.systems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import backend.xml_converting.GameWorldToXMLWriter;
import backend.xml_converting.ObjectToXMLWriter;
import engine.backend.components.PositionComponent;
import engine.backend.components.Vector;
import engine.backend.entities.IEntity;
import engine.backend.entities.InGameEntityFactory;
import engine.backend.game_features.GameShop;
import engine.backend.game_object.GameWorld;
import engine.backend.game_object.IModifiable;
import engine.backend.game_object.Level;
import engine.backend.game_object.Mode;
import engine.backend.game_object.GameStatistics;
import engine.backend.rules.EntityAction;
import engine.backend.rules.IAction;
import engine.backend.rules.LevelAction;
import engine.backend.rules.Rule;
import engine.backend.systems.Events.AddEntityEvent;
import engine.backend.systems.Events.EntityDroppedEvent;
import engine.backend.systems.Events.GameEvent;
import engine.backend.systems.Events.IEvent;
import engine.backend.systems.Events.NextWaveEvent;
import engine.backend.systems.Events.UpdateEntityEvent;
import engine.backend.systems.Events.WaveOverEvent;
import engine.backend.utilities.ComponentTagResources;
import engine.controller.IEngineController;

public class EventManager implements Observer {
	
	IEngineController myEngineController;
	GameWorld myGameWorld;
	GameStatistics currentModeStatistics;
	private List<Rule> myRuleAgenda;
	InGameEntityFactory myEntityFactory;
	private GameShop myGameShop;

	public EventManager(IEngineController engineController, GameWorld game) {
		myEngineController = engineController;
		myGameWorld = game;
		// pass in right values
		currentModeStatistics = game.getGameStatistics();
		myGameShop = new GameShop();
	}

	public void setEntityFactory(InGameEntityFactory factory) {
		myEntityFactory = factory;
	}

	public void setLevelRules(Level level) {
		setCustomRules(level.getRuleAgenda());
	}

	public Level getCurrentLevel() {
		String modeName = currentModeStatistics.getCurrentMode();
		int levelIndex = currentModeStatistics.getCurrentLevelIndex();
		if (myGameWorld.getLevelWithId(modeName, levelIndex).shouldRevert()) {
			GameWorldToXMLWriter serializer = new GameWorldToXMLWriter();
			Level myLevel = (Level) serializer
					.xMLToObject(myGameWorld.getLevelWithId(modeName, levelIndex).getLastSerializedVersion());
			myLevel.setShouldRevert(false);
			myGameWorld.putLevelInMap(modeName, levelIndex, myLevel);
		}
		return myGameWorld.getLevelWithId(currentModeStatistics.getCurrentMode(),
				currentModeStatistics.getCurrentLevelIndex());
	}

	public Mode getCurrentMode() {
		return myGameWorld.getModes().get(currentModeStatistics.getCurrentMode());
	}

	public void updateGameShop() {
		myGameShop.setShopItems(getCurrentLevel().getShopItems());
		myGameShop.updateShop(currentModeStatistics.getCurrentResources());
		myEngineController.updateShop(myGameShop.getShopItems());
	}

	@Override
	public void update(Observable o, Object arg) {
		handleSystemEvent((IEvent) arg);
	}

	public void sendUpdatedEntity(UpdateEntityEvent myEvent) {
		myEngineController.updateEntity(myEvent.getX(), myEvent.getY(), myEvent.getImage(), myEvent.getID(),
				myEvent.getSizeX(), myEvent.getsizeY(), myEvent.getShow());
	}

	public void setCustomRules(List<Rule> rules) {
		this.myRuleAgenda = rules;
	}

	private void handleSystemEvent(IEvent myEvent) {

		if (myEvent instanceof UpdateEntityEvent) {
			sendUpdatedEntity((UpdateEntityEvent) myEvent);
			return;
		}

		if (myEvent instanceof AddEntityEvent) {
			handleAddEntityEvent(myEvent);
		}

		if (myEvent instanceof WaveOverEvent) {
			handleWaveOverEvent((WaveOverEvent) myEvent);
		}

	}

	/**
	 * Handles setting the mode and level when clicked.
	 * 
	 * @param modeName
	 * @param level
	 * @throws IOException
	 */
	public void handleGameStartEvent(GameEvent event) throws IOException {
		handleModeClickedEvent(event.getModeName());
		handleLevelClickedEvent(event.getLevel());
	}

	/**
	 * Handles when a mode has been selected.
	 * 
	 * @param modeName
	 */
	private void handleModeClickedEvent(String modeName) {
		currentModeStatistics.setCurrentMode(modeName);
	}

	/**
	 * Handles when a level has been selected.
	 * 
	 * @param Level
	 * @throws IOException
	 */
	private void handleLevelClickedEvent(int level) throws IOException {
		currentModeStatistics.setCurrentLevelIndex(level);
		serializeLevel();
	}

	/**
	 * Handles when user goes to the next level.
	 * 
	 */
	public void handleGoToNextLevelEvent() {
		currentModeStatistics.setCurrentLevelIndex(currentModeStatistics.getCurrentLevelIndex() + 1);
		serializeLevel();
		updateEntityFactory();
	}

	private void serializeLevel() {
		String modeName = currentModeStatistics.getCurrentMode();
		int levelIndex = currentModeStatistics.getCurrentLevelIndex();
		myGameWorld.getLevelWithId(modeName, levelIndex)
				.setLastSerializedVersion(serializeLevel(myGameWorld.getLevelWithId(modeName, levelIndex)));
	}

	private String serializeLevel(Object o) {
		ObjectToXMLWriter serializer = new GameWorldToXMLWriter();
		return serializer.getXMLfromObject(o);
	}

	private void handleWaveOverEvent(WaveOverEvent event) {
		myEngineController.waveIsOver();
	}
	
	public void handleLevelOver() {
		
		boolean noLives = currentModeStatistics.noMoreLives();
		if(noLives){
			myEngineController.levelIsLost();
			resetLevel();
		}
		else{
			if(getCurrentLevel().lastWaveOver()){
				myEngineController.levelIsWon();
				resetLevel();
			}
			else{
				return;
			}
		}
		
	}
	
	private void resetLevel(){
		String modeName = currentModeStatistics.getCurrentMode();
		int levelIndex = currentModeStatistics.getCurrentLevelIndex();
		myGameWorld.getLevelWithId(modeName, levelIndex).setShouldRevert(true);
	}

	private void handleNextWaveEvent(NextWaveEvent event) {
		getCurrentLevel().setSendNextWave(true);
	}

	/**
	 * Creates a new entity from the entity factory, sets the position
	 * component, and adds entity to screen map.
	 * 
	 * @param event
	 */
	private void handleEntityDropEvent(EntityDroppedEvent event) {

		double value = event.getEntityValue();
		currentModeStatistics.setCurrentResources(Double.toString(value));

		IEntity newEntity = myEntityFactory.createEntity(event.getEntityName());
		PositionComponent posComp = (PositionComponent) newEntity
				.getComponent(ComponentTagResources.positionComponentTag);
		posComp.setPositionVector(new Vector(event.getXCoordinate(), event.getYCoordinate()));
		getCurrentLevel().addEntityToMap(newEntity);
	}

	public void updateEntityFactory() {
		if (myEntityFactory.isCurrent(getCurrentLevel().getIndex())) {
			return;
		}
		myEntityFactory.setEntities(getCurrentLevel().getAuthoredEntities());
		myEntityFactory.setID(getCurrentLevel().getIndex());
		return;
	}

	/**
	 * Applies actions to an entity if applicable, else sees if it is a level
	 * action then changes things accordingly.
	 * 
	 * @param entity
	 * @param actions
	 */
	private void applyActions(IEntity entity, Collection<IAction> actions) {
		for (IAction a : actions) {
			if (a instanceof EntityAction) {
				System.out.println(((EntityAction) a).getEntityName());
				if (((EntityAction) a).getEntityName().equals(entity.getName())) {
					((IModifiable) entity).applyAction((EntityAction) a);
				}
			} else if (a instanceof LevelAction) {
				currentModeStatistics.applyAction((LevelAction) a);
			}
		}
	}

	private void applyActions(Collection<Integer> entityIDs, Collection<IAction> actions) {
		Collection<IEntity> myEntities = new ArrayList<IEntity>();
		entityIDs.forEach(i -> myEntities.add(getCurrentLevel().getEntityWithID(i)));
		myEntities.forEach(entity -> applyActions(entity, actions));
	}

	/**
	 * Takes in collection of non map user events generated, and handles them
	 * accordingly
	 * 
	 * @param events
	 */
	public void handleNonMapEvents(Collection<IEvent> events) {

		for (IEvent event : events) {
			if (event instanceof EntityDroppedEvent) {
				System.out.println(event.getEventID());
				handleEntityDropEvent((EntityDroppedEvent) event);
			} else if (event instanceof NextWaveEvent) {
				handleNextWaveEvent((NextWaveEvent) event);
			}
		}

	}

	// supposed to handle list of events generated in each loop iteration
	public void handleGeneratedEvents(Map<String, Set<Integer>> generatedEventMap) {
		for (Rule rule : myRuleAgenda) {
			List<Set<Integer>> myPossibleEntities = new ArrayList<Set<Integer>>();
			Collection<String> ruleEvents = rule.getEvents();
			Set<Integer> myFinalEntities;
			for (String event : ruleEvents) {
				if (!generatedEventMap.containsKey(event)) {
					myPossibleEntities.clear();
					break;
				}
				myPossibleEntities.add(generatedEventMap.get(event));
			}
			if (myPossibleEntities.size() > 0) {
				myFinalEntities = new HashSet<Integer>(myPossibleEntities.get(0));
				myPossibleEntities.forEach(e -> myFinalEntities.retainAll(e));

				// apply actions
				if (myFinalEntities.size() > 0) {
					applyActions(myFinalEntities, rule.getActions());

					// remove IDs
					generatedEventMap.values().forEach(s -> s.removeAll(myFinalEntities));

				}

			}

		}
	}

	private void handleAddEntityEvent(IEvent myEvent) {
		AddEntityEvent event = (AddEntityEvent) myEvent;
		getCurrentLevel().addEntityToMap(event.getNewEntities());
	}

	public GameWorld getGameWorld() {
		return myGameWorld;

	}

	public GameStatistics getModeStatistics() {
		return currentModeStatistics;
	}

	public InGameEntityFactory getEntityFactory() {
		return myEntityFactory;
	}

	public void initializeRules() {
		myRuleAgenda = getCurrentLevel().getRuleAgenda();
	}

}
