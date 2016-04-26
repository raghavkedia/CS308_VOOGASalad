package engine.backend.components;

import java.util.ArrayList;
import java.util.List;

import engine.backend.entities.IEntity;

/**
 * A component that gives the ability to an entity to spawn more entities.
 * @author 
 *
 */
public class SpawnerComponent extends Component {

	private List<Spawn> mySpawns;
	private int pathID;
	
	public SpawnerComponent(int id){
		mySpawns = new ArrayList<Spawn>();
		pathID = id;
	}
	
	public SpawnerComponent(List<Spawn> spawns, int id){
		mySpawns = spawns;
		pathID = id;
	}
	
	public SpawnerComponent(SpawnerComponent component) {
		this.mySpawns = component.getSpawns();
		this.pathID = component.getPathID();
	}
	
	public void addSpawn(Spawn spawn){
		mySpawns.add(spawn);
	}
	
	public List<Spawn> getSpawns(){
		return mySpawns;
	}
	
	@Override
	public void initWithParams(String[] params) {
		// TODO Auto-generated method stub
		
	}
	
	public int getPathID(){
		return pathID;
	}

}
