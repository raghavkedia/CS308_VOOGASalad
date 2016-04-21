
package engine.backend.components;

/**
 * 
 * @author raghavkedia
 *
 */

public class PathComponent extends Component {
	
	//stores which path you're on
	private int pathID;
	private double myBezierTime;
	
	
	private boolean reachedEndOfPath;
	private boolean movesWithTime;
	//stores which curve in the path you're on
	private int curveID;

	public PathComponent(){
	}
	
	public PathComponent(int id, double time){
		pathID = id;
		myBezierTime = time;
		movesWithTime = true;
	}
	
	public int getCurveID(){
		return curveID;
	}
	public void setCurveID(int newID){
		curveID = newID;
	}
	public int getPathID(){
		return pathID;
	}
	public void setPathID(int newID){
		pathID = newID;
	}
	
	public double getBezierTime() {
		return myBezierTime;
	}

	public void setBezierTime(double myBezierTime) {
		this.myBezierTime = myBezierTime;
	}

	public boolean movesWithTime(){
		return movesWithTime;
	}
	
	public boolean getReachedEndOfPath(){
		return reachedEndOfPath;
	}
	
	public void didReachEndOfPath(){
		reachedEndOfPath = true;
	}
	
	@Override
	public String getComponentInfo() {
		return "pathID: " + pathID;
	}

	@Override
	public void update(String dataName, String data) {
		if (dataName.equals("PathID")) {
			this.pathID = Integer.parseInt(data);
		}
	}
	
}
