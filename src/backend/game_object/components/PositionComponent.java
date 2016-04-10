/**
 * author raghav kedia rk145
 */

package backend.game_object.components;

public class PositionComponent extends Component implements IComponent{
	
	private Vector myPositionVector;
	
	
	public PositionComponent(){
		myPositionVector = new Vector();
	}
	
	public PositionComponent(double x, double y) {
		// TODO Auto-generated constructor stub
//		setPositionVector(new Vector(x, y));
		myPositionVector = new Vector(x, y);
	}
	
	
	@Override
	public void update(){
		
	}
	
	@Override
	public String getTag(){
		return "Position";
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

}
