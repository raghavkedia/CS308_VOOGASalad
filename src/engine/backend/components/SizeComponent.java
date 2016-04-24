/**
 * 
 * @author mario_oliver93
 * 
 */
package engine.backend.components;

<<<<<<< HEAD
public class SizeComponent extends Component {
=======
public class SizeComponent extends Component implements IComponent {
>>>>>>> origin

	private double width;
	private double height;

	private static final int DEFAULT_HEIGHT = 50;
	private static final int DEFAULT_WIDTH = 50;

	// default component
	public SizeComponent() {
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
	}

	@Override
	public String toString() {
		return this.getTag() + " with width: " + this.width + " with height: " + this.height;
	}

	public double getHeight() {
		return height;
	}

	public void increaseSize(int delta) {
		this.width += delta;
		this.height += delta;
	}

	public void increaseSize(String delta) {
		int newVal = Integer.parseInt(delta);
		this.width += newVal;
		this.height += newVal;
	}

	public double getWidth() {
		return width;
	}

	@Override
<<<<<<< HEAD
	public String getComponentInfo() {
		return "Width:" + width + "," + "Height:" + height;
	}

	@Override
	public void update(String dataName, String data) {
		if (dataName.equals("Width")) {
			this.width = Double.parseDouble(data);
			return;
		}
		if (dataName.equals("Height")) {
			this.height = Double.parseDouble(data);
			return;
=======
	public void initWithParams(String[] params) {
		if (params.length > 0) {
			this.width = Double.parseDouble(params[0]);
			this.height = Double.parseDouble(params[1]);
>>>>>>> origin
		}
	}

}
