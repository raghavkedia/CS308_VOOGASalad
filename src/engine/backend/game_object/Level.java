/**
 * 
 * @author mario_oliver93
 *
 */

package engine.backend.game_object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.backend.entities.Entity;
import engine.backend.entities.IEntity;
import engine.backend.map.GameMap;
import engine.backend.rules.EntityAction;

public class Level {
	private List<IEntity> authoredEntities;
	private Map<Integer, IEntity> entities;
	private int myID;
	private String myParentModeName;
	private GameMap map;
	private double timer;

	public Level(int myID, GameMap map) {
		this.myID = myID;
		this.map = map;
	}

	public Level(int myID) {
		this.authoredEntities = new ArrayList<IEntity>();
		this.entities = new HashMap<Integer, IEntity>();
		this.myID = myID;
	}

	public int getId() {
		return myID;
	}

	public Map<Integer, IEntity> getEntities() {
		return entities;
	}
	
	public void addEntityToMap(IEntity entity) {
		entities.put(entity.getID(), entity);
	}
	
	public void addEntityToMap(Collection<IEntity> entities) {
		entities.stream()
				.forEach(e -> addEntityToMap(e));
	}
	
	public IEntity getEntityWithID(int entityID) {
		return entities.get(entityID);
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "Level [entities=" + entities + "] ";
	}

	public void addToEntities(IEntity entity) {
		entity.setLevelID(myID);
		authoredEntities.add(entity);
	}

	public void setModeName(String modeID) {
		this.myParentModeName = modeID;
	}

	public String getModeID() {
		return myParentModeName;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Level) {
			Level temp = (Level) o;
			if (this.myID == temp.myID) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Map<String, List<EntityAction>> getCustomEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
