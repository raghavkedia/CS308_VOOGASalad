package authoring.backend.factories;

import java.util.HashMap;
import java.util.Map;

import engine.backend.GameWorld;
import engine.backend.Level;
import engine.backend.Mode;
import engine.backend.components.Component;
import engine.backend.entities.Entity;
import engine.backend.entities.IEntity;

public class GameFactory {
	private EntityFactory myEntityFactory;
	private ComponentFactory myComponentFactory;
	private LevelFactory myLevelFactory;
	private ModeFactory myModeFactory;
	private GameWorld myGame;
	private GameProperties myProperties;
	
	public GameFactory() {
		myGame = new GameWorld();
		myProperties = new GameProperties();
		myModeFactory = new ModeFactory();
		myComponentFactory = new ComponentFactory();
		myEntityFactory = new EntityFactory(myGame.getGameStats());
	}
	
	public GameWorld createGame(){
		setUpModes();
		setUpLevels();
		setUpEntities();
		setUpComponents();
		return myGame;
	}
	
	private void setUpModes(){
		for (Object info : myProperties.getMyModes()){
			Mode newMode = myModeFactory.createMode(info);
			myGame.addMode(newMode);
		}
	}
	
	private void setUpLevels(){
		for (Object info: myProperties.getMyLevels()){
			Level newLevel = myLevelFactory.createLevel(info);
			Mode currentMode = myGame.getModeWithId(newLevel.getParentId());
			currentMode.addLevel(newLevel);
		}
	}
	
	private void setUpEntities(){
		for (Object info : myProperties.getMyEntities()){
			Entity newEntity = myEntityFactory.createEntity(info);
			Level currentLevel = myGame.getLevelWithId(newEntity.getLevelId());
			currentLevel.addToEntities(newEntity);
		}
	}
	
	private void setUpComponents(){
		for (Object info : myProperties.getMyComponents()){
			Component newComponent = myComponentFactory.createComponent(info);
			IEntity currentEntity = myGame.getEntityWithId(newComponent.getEntityId());
			currentEntity.addComponent(newComponent);
		}
	}
	
	private void setUpEntityMap(){
		Map<String, Map<String, Entity>> map = new HashMap<String, Map<String, Entity>>();
		for (Entity entity : myProperties.getMyEntities()){
			Map<String, Entity> existingMap = null;
			if (map.containsKey(entity.getMyType())){
				existingMap = map.get(entity.getMyType());
			}
			else{
				existingMap = new HashMap<String, Entity>();
				map.put(entity.getMyType(), existingMap);
			}
			existingMap.put(entity.getName(), entity);
		}
	}
	
	
	
	
}
