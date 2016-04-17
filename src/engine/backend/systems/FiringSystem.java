package engine.backend.systems;

import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import authoring.backend.factories.EntityFactory;
import engine.backend.components.FiringComponent;
import engine.backend.components.MovementComponent;
import engine.backend.components.PositionComponent;
import engine.backend.components.Vector;
import engine.backend.entities.IEntity;
import engine.backend.entities.InGameEntityFactory;
import engine.backend.game_object.Level;
import engine.backend.systems.Events.DeathEvent;
import engine.backend.systems.Events.FireEvent;

/**
 * 
 * @author raghavkedia
 *
 */

public class FiringSystem extends Observable implements ISystem{

	@Override 
	public void update(Level myLevel, InGameEntityFactory myEntityFactory, ResourceBundle myComponentTagResources) {
		// TODO Auto-generated method stub
		List<IEntity> entities = myLevel.getEntities();
		for(IEntity shootingEntity : entities){
			
			if(shootingEntity.hasComponent(myComponentTagResources.getString("Firing"))){
				FiringComponent firingComponent = (FiringComponent) shootingEntity.getComponent(myComponentTagResources.getString("Firing"));
				
				for(IEntity targetEntity : entities){
					
					//needs to check if it's something it can fire at
					if(firingComponent.getTargets().contains(targetEntity.getName())){
						
						PositionComponent shootingPosComponent = (PositionComponent) shootingEntity.getComponent(myComponentTagResources.getString("Position"));
						PositionComponent targetPosComponent = (PositionComponent) targetEntity.getComponent(myComponentTagResources.getString("Position"));
						
						Vector shootingPosVector = shootingPosComponent.getPositionVector();
						Vector targetPosVector = targetPosComponent.getPositionVector();
						
						if(targetIsInRange(firingComponent.getEnemyInSightRange(), shootingPosVector, targetPosVector)
								&& firingComponent.getFiringRate() == firingComponent.getCurrentTimeStep()){
							
							double xComp = targetPosVector.getX() - shootingPosVector.getX();
							double yComp = targetPosVector.getY() - shootingPosVector.getY();
							Vector firedVelVector = new Vector(xComp, yComp);
							firingComponent.setDirectionToFire(firedVelVector);
							
							sendFireEvent(shootingEntity);
							firingComponent.resetCurrentTimeStep();
							//create firing event
						}
						
					}
					
				}
				firingComponent.incrementCurrentTimeStep();
			}
			
		}
	}
	
	private boolean targetIsInRange(double range, Vector shootingPosVector, Vector targetPosVector){
		
		return shootingPosVector.calculateDistance(targetPosVector) <= range;
		
	}
	
	private void sendFireEvent(IEntity firingEntity){
		FireEvent fireEvent = new FireEvent(firingEntity);
		notifyObservers(fireEvent);
	}

}
