package engine.backend.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.backend.components.DisplayComponent;
import engine.backend.components.IComponent;
import engine.backend.components.PathComponent;
import engine.backend.components.PositionComponent;
import engine.backend.components.SizeComponent;
import engine.backend.components.Spawn;
import engine.backend.components.SpawnerComponent;
import engine.backend.components.Vector;
import engine.backend.entities.IEntity;
import engine.backend.entities.InGameEntityFactory;
import engine.backend.game_object.Level;
import engine.backend.systems.Events.AddEntityEvent;
import engine.backend.systems.Events.IEvent;
import engine.backend.systems.Events.WaveOverEvent;
import engine.backend.utilities.ComponentTagResources;

public class SpawningSystem extends GameSystem {

	private double delayTimer;

	@Override
	public void update(boolean playing, Level myLevel, Map<String, Set<Integer>> myEventMap, InGameEntityFactory myEntityFactory, double currentSecond) {		
		
		if(!playing){
			return;
		}
		
		if(myLevel.sendNextWave()){
			myLevel.setSendNextWave(false);
			delayTimer = 0;
		}

		if(delayTimer > 0){
			delayTimer = delayTimer - GameClock.getTimePerLoop();
			return;
		}

		int currentWaveIndex = myLevel.getCurrentWaveIndex();
		boolean waveIsOver = true;
		Collection<IEntity> applicableEntities = getEntitiesWithTag(myLevel.getEntities().values(), ComponentTagResources.spawnerComponentTag);
		Collection<IEntity> newEntities = new ArrayList<IEntity>();

		for(IEntity entity : applicableEntities){

			SpawnerComponent spawnerComponent = (SpawnerComponent) entity.getComponent(ComponentTagResources.spawnerComponentTag);
			PositionComponent posComponent = (PositionComponent) entity.getComponent(ComponentTagResources.positionComponentTag);

			for(Spawn spawn : spawnerComponent.getSpawns()){
				if(spawn.getWaveIndex() == currentWaveIndex && spawn.getNumEntities() > 0){
					//System.out.println(spawn.getInfo());
					waveIsOver = false;
					updateSpawn(spawn, posComponent.getPositionVector(), newEntities, myEntityFactory, currentSecond, spawnerComponent.getPathID());
				}
			}

			if(waveIsOver){
				myLevel.setCurrentWaveIndex(currentWaveIndex + 1);
				sendEvent(getWaveOverEvent(myLevel.getWaveDelayTimer()));
				delayTimer = 100 * myLevel.getWaveDelayTimer();
			}

		}

		sendEvent(getAddEntityEvent(newEntities));

	}

	private void updateSpawn(Spawn spawn, Vector newPos, Collection<IEntity> newEntities, InGameEntityFactory myEntityFactory, double currentSecond, int pathID){
		if(spawn.getTimer() <= 0 && spawn.getNumEntities() > 0){
			IEntity newEntity = myEntityFactory.createEntity(spawn.getSpawningEntityName());
			PositionComponent newPositionComponent = new PositionComponent(newPos.getX(), newPos.getY());
			newEntity.addComponent(newPositionComponent);
			if(newEntity.hasComponent(ComponentTagResources.pathComponentTag)){
				PathComponent pathComp = (PathComponent) newEntity.getComponent(ComponentTagResources.pathComponentTag);
				pathComp.setPathID(pathID);
			}
			spawn.setNumEntities(spawn.getNumEntities() - 1);
			newEntities.add(newEntity);
			spawn.resetTimer();
		}
		else{
			spawn.decrementTimer();
		}

	}

	private IEvent getWaveOverEvent(double timer){
		return new WaveOverEvent(timer);
	}

	private IEvent getAddEntityEvent(Collection<IEntity> newEntities){
		return new AddEntityEvent(newEntities);
	}

	private void sendEvent(IEvent event){
		setChanged();
		notifyObservers(event);
	}

}
