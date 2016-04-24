package engine.backend.systems.Events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import engine.backend.entities.IEntity;

public abstract class EntityEvent implements IEvent {

	private int entityID;
	private List<String> eventIDs;

	public void setEventID(List<String> identifiers) {
		this.eventIDs = new ArrayList<String>();
		this.eventIDs.add(identifiers.get(0) + this.getClass().getSimpleName()); 
	}
	
	public void setEventID(String indentifier) {
		this.eventIDs = new ArrayList<String>();
		this.eventIDs.add(indentifier + this.getClass().getSimpleName());
	}

	public Collection<Integer> getEntityIDs() {
		Collection<Integer> entities = new ArrayList<Integer>();
		entities.add(entityID);
		return entities;
	}

	public void setEntity(int entityID) {
		this.entityID = entityID;
	}
}
