package engine.backend.systems.Events;

import engine.backend.entities.IEntity;

public class EndOfPathEvent extends Event{
	
	public EndOfPathEvent(IEntity entity){
		super.setEntity(entity);
	}
	
}