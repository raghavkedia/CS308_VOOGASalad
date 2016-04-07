package backend;

/**
 * Author Raghav Kedia rk145
 */

import backend.game_object.components.Vector;

public class BezierCurve {
	
	private static final int SEGMENT_COUNT = 50;
	private Vector startPointVector;
	private Vector control1Vector;
	private Vector control2Vector;
	private Vector endPointVector;
	private double myLength;
	
	public BezierCurve(double startX, double startY, double c1X, double c1Y, 
			double c2X, double c2Y, double endX, double endY) {

		startPointVector = new Vector(startX, startY);
		control1Vector = new Vector(c1X, c1Y);
		control2Vector = new Vector(c2X, c2Y);
		endPointVector = new Vector(endX, endY);
	}

	public Vector calculateNewBezierPoint(double t,
			Vector p0, Vector p1, Vector p2, Vector p3){

		Vector p = new Vector();
		
		double u = 1 - t;
		double tt = t*t;
		double uu = u*u;
		double uuu = uu * u;
		double ttt = tt * t;

		p = p.add(p0.scale(uuu));
		p = p.add(p1.scale(3 * uu * t));
		p = p.add(p2.scale(3 * u * tt));
		p = p.add(p3.scale(ttt));

		return p;
	}
	
	public double getLength(){
		return myLength;
	}
	
	public double calculateBezierLength(){
		
		Vector q0 = calculateNewBezierPoint(0, startPointVector, control1Vector, 
				control2Vector, endPointVector);
		double t;
		double length = 0;
		for(int i = 1; i <= SEGMENT_COUNT; i++){
			t = (double) i / (double) SEGMENT_COUNT;
			Vector q1 = calculateNewBezierPoint(t, startPointVector, control1Vector, 
				control2Vector, endPointVector);
			length += q1.calculateDistance(q0);

			//System.out.println(length);
			q0 = q1;
		}
		
		return length;
		
	}
	
	public double calculateBezierTime(double velocity){
		//since V = d/t, t = d/V, this is just a rough approximation. 
		
		return myLength / velocity;
	}
	
	//given velocity, calculate distance of bezier curve, and then divide to find time

}