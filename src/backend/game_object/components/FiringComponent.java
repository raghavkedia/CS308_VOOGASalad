package backend.game_object.components;

import java.util.List;

import backend.game_object.entities.IEntity;

/**
 * 
 * @author raghavkedia
 *
 */

public class FiringComponent extends Component implements IComponent{
	
	private IEntity myAmmunition;
	private int myAmmunitionAmount;
	private double myAmmunitionSpeed;
	private double myEnemyInSightRange;
	private double myDirectionToFire;

	public IEntity getAmmunition() {
		return myAmmunition;
	}

	public void setAmmunition(IEntity myAmmunition) {
		this.myAmmunition = myAmmunition;
	}

	public int getAmmunitionAmount() {
		return myAmmunitionAmount;
	}

	public void setAmmunitionAmount(int myAmmunitionAmount) {
		this.myAmmunitionAmount = myAmmunitionAmount;
	}

	public double getEnemyInSightRange() {
		return myEnemyInSightRange;
	}

	public void setEnemyInSightRange(double myEnemyInSightRange) {
		this.myEnemyInSightRange = myEnemyInSightRange;
	}

	public double getDirectionToFire() {
		return myDirectionToFire;
	}

	public void setDirectionToFire(double myDirectionToFire) {
		this.myDirectionToFire = myDirectionToFire;
	}

	public double getAmmunitionSpeed() {
		return myAmmunitionSpeed;
	}

	public void setAmmunitionSpeed(double myAmmunitionSpeed) {
		this.myAmmunitionSpeed = myAmmunitionSpeed;
	}
	
}
