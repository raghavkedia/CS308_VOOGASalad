package engine.backend.systems;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import engine.backend.entities.IEntity;
import engine.backend.entities.InGameEntityFactory;
import engine.backend.game_object.Level;

public class SystemSetUp {

	private Level myLevel;
	private Map<String, Set<Integer>> myEventMap;
	private InGameEntityFactory myEntityFactory;
	private GameSystem gameSystem;

	public SystemSetUp(boolean playing, Map<String, Set<Integer>> myEventMap, InGameEntityFactory myEntityFactory, double currentSecond, Level level) {
		if (!playing)
			return;
		this.myEventMap = myEventMap;
		this.myEntityFactory = myEntityFactory;
		gameSystem = new GameSystem() {
			
			@Override
			public void update(Level myLevel, double currentSecond, SystemSetUp setUp) { }
		};
	}
	
	/**
	 * Filters a list of entities given with the give string of tags. Returns
	 * the filtered list of entities.
	 * 
	 * @param entities
	 * @param tag
	 * @return
	 */
	public List<IEntity> getEntitiesWithTag(Collection<IEntity> entities, String tag) {
		return entities.stream().filter(e -> e.hasComponent(tag)).collect(Collectors.toList());
	}

	public Map<String, Set<Integer>> getMyEventMap() {
		return myEventMap;
	}

	public InGameEntityFactory getMyEntityFactory() {
		return myEntityFactory;
	}
	
	

}
