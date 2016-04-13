package engine.backend.components;

import java.util.List;

/**
 * 
 * @author raghavkedia
 *
 */

public class MovementComponent extends Component implements IComponent{
	
	//private double myVelocity;
	//private double myAcceleration;
	private Vector myCurrentVelocityVector;
	private Vector myDefaultVelocityVector;
	private double myTheta;
	
	
	//figure out direction of velocity;
	
	//this is the angle to rotate myTheta by
	private double myCurrentOmega;
	private double myInitialOmega;
	
	private boolean canMove;
	private boolean canRotate;
	
	public MovementComponent(double velocity, double theta, double omega) {
		// TODO Auto-generated constructor stub
		myCurrentVelocityVector = new Vector(velocity, 0);
		myDefaultVelocityVector = new Vector(velocity, 0);
		myTheta = theta;
		myCurrentOmega = omega;
		//myVelocity = velocity;
		//myAcceleration = acceleration;
	}
	
	public Vector getCurrentVelocityVector(){
		return myCurrentVelocityVector;
	}
	public void setCurrentVelocityVector(Vector vel){
		myCurrentVelocityVector = vel;
	}
	public void setDefaultVelocityVector(Vector vel){
		myDefaultVelocityVector = vel;
	}
	public double getTheta(){
		return myTheta;
	}
	
	public void setTheta(double theta){
		myTheta = theta;
	}
	
	public double getCurrentOmega() {
		return myCurrentOmega;
	}

	public void setOmega(double omega) {
		this.myCurrentOmega = omega;
	}
	
	public boolean canMove(){
		return canMove;
	}
	
	public boolean canRotate(){
		return canRotate;
	}
	
	public void setCanMove(boolean bool){
		canMove = bool;
	}
	public void setCanRotate(boolean bool){
		canRotate = bool;
	}

	@Override
	public void initWithParams(List params) {
		// TODO Auto-generated method stub
		
	}
}