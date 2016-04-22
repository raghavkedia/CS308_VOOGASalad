/**
 * author raghav kedia rk145
 */

package engine.backend.components;

public class PositionComponent extends Component implements IComponent{
	
	private Vector myPositionVector;
	private Vector myCriticalPosition;
	public static final int DEFAULTPOSITION = 50;
	
	public PositionComponent() {
		double posX = DEFAULTPOSITION;
		double posY = DEFAULTPOSITION;
		
		myPositionVector = new Vector(posX, posY);
	}
	
	public PositionComponent(PositionComponent posComp){
		double posX = posComp.getX();
		double posY = posComp.getY();
		
		myPositionVector = new Vector(posX, posY);
	}
	
	public PositionComponent(double x, double y){
		myPositionVector = new Vector(x, y);
	}

	public Vector getPositionVector() {
		return myPositionVector;
	}

	public void setPositionVector(Vector myPositionVector) {
		this.myPositionVector = myPositionVector;
	}

	public double getX() {
		return myPositionVector.getX();
	}
	
	public double getY() {
		return myPositionVector.getY();
	}

	@Override
	public void initWithParams(String[] params) {
		//x is 1, y is 2
		myPositionVector = new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1]));
	}

	public Vector getCriticalPosition() {
		return myCriticalPosition;
	}

	public void setCriticalPosition(Vector myCriticalPosition) {
		this.myCriticalPosition = myCriticalPosition;
	}

}
