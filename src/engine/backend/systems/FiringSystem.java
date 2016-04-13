package engine.backend.systems;

import java.util.List;
import java.util.ResourceBundle;

import authoring.backend.factories.EntityFactory;
import engine.backend.components.FiringComponent;
import engine.backend.components.MovementComponent;
import engine.backend.components.PositionComponent;
import engine.backend.components.Vector;
import engine.backend.entities.IEntity;
import engine.backend.entities.InGameEntityFactory;
import engine.backend.game_object.Level;

/**
 * 
 * @author raghavkedia
 *
 */

public class FiringSystem implements ISystem{

	@Override 
	public void update(Level myLevel, InGameEntityFactory myEntityFactory, ResourceBundle myComponentTagResources) {
		// TODO Auto-generated method stub
		List<IEntity> entities = myLevel.getEntities();
		for(IEntity shootingEntity : entities){
			
			if(shootingEntity.hasComponent(myComponentTagResources.getString("Firing"))){
				
				for(IEntity targetEntity : entities){
					
					if(shootingEntity.equals(targetEntity)){
						continue;
					}
					
					//needs to check if it's something it can fire at
					if(targetEntity.getName().equals("Enemy")){
						
						IEntity firedEntity = handleFiring(shootingEntity, targetEntity, myEntityFactory, myComponentTagResources);
						if(firedEntity == null){
							continue;
						}
						else{
							entities.add(firedEntity);
						}
						
					}
					
				}
				
			}
			
		}
	}
	
	private IEntity handleFiring(IEntity shootingEntity, IEntity targetEntity, InGameEntityFactory myEntityFactory, ResourceBundle myComponentTagResources){
		
		//Get firing component from shooting Entity
		FiringComponent firingComponent = (FiringComponent) shootingEntity.getComponent(myComponentTagResources.getString("Firing"));
		//Get Position component of shooting entity
		PositionComponent shootingPosComponent = (PositionComponent) shootingEntity.getComponent(myComponentTagResources.getString("Position"));
		MovementComponent shootingMovComponent = (MovementComponent) shootingEntity.getComponent(myComponentTagResources.getString("Movement"));
		//Get position component of target entity;
		PositionComponent targetPosComponent = (PositionComponent) targetEntity.getComponent(myComponentTagResources.getString("Position"));
		
		//Get position of shooting entity
		Vector shootingPosVector = shootingPosComponent.getPositionVector();
		//get position of target entity;
		Vector targetPosVector = targetPosComponent.getPositionVector();
		
		//if target is in range of shooter
		if(shootingPosVector.calculateDistance(targetPosVector) <= firingComponent.getEnemyInSightRange() 
				&& firingComponent.getAmmunitionAmount() > 0){
			
			//get an instance of the shooters ammo
			String firedEntityName = firingComponent.getAmmunition();
			IEntity firedEntity = myEntityFactory.createEntity(firedEntityName);
			
			//set the position and movement components of the ammo
			PositionComponent firedPosComponent = (PositionComponent) firedEntity.getComponent(myComponentTagResources.getString("Position"));
			MovementComponent firedMovComponent = (MovementComponent) firedEntity.getComponent(myComponentTagResources.getString("Movement"));
			
			firedPosComponent.setPositionVector(shootingPosVector);
			
			double xComp = targetPosVector.getX() - shootingPosVector.getX();
			double yComp = targetPosVector.getY() - shootingPosVector.getY();
			Vector firedVelVector = new Vector(xComp, yComp);
			firedVelVector = firedVelVector.normalize();
			firedVelVector.scale(firingComponent.getAmmunitionSpeed());
			firedMovComponent.setCurrentVelocityVector(firedVelVector);
			firedMovComponent.setDefaultVelocityVector(firedVelVector);
			
			shootingMovComponent.setTheta(targetPosVector.calculateDirection(shootingPosVector));
			
			return firedEntity;
			
		}
		
		return null;

	}

}