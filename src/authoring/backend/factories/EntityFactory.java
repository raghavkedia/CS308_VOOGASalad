//Kushal Byatnal
package authoring.backend.factories;

import java.util.List;

import engine.backend.GameStatisticsObject;
import engine.backend.components.Component;
import engine.backend.entities.Entity;

public class EntityFactory {
	private GameStatisticsObject myStats;
	
	public EntityFactory(GameStatisticsObject stats) {
		this.myStats = stats;
	}

	public Entity createEntity(int levelID, String name, String type, double price, List<Component> components){	
		Entity newEntity = new Entity(myStats.nextEntityID(), name, type, price);
		for (Component component : components) {
			newEntity.addComponent(component);
		}
		newEntity.setLevelID(levelID);
		return newEntity;
	}
}
