package engine.backend.components;

/**
 * 
 * @author
 *
 */
public class PurchaseComponent extends Component {

	private double myValue;

	public PurchaseComponent() {
		super();
	}

	/**
	 * Initializes a purchase component with an existing purchase component.
	 * 
	 * @param component
	 */
	public PurchaseComponent(PurchaseComponent component) {
		this.myValue = component.getValue();
	}

	/**
	 * 
	 * @return The double with the value of this component. Represents purchase
	 *         price.
	 */
	public double getValue() {
		return myValue;
	}

	/**
	 * Sets the value of this component. Represents purchase price.
	 * 
	 * @param myValue
	 */
	public void setValue(double myValue) {
		this.myValue = myValue;
	}

	public String getComponentInfo() {
		return "Value:" + myValue;
	}

	@Override
	public void update(String dataName, String data) {
		if (dataName.equals("Value")) {
			this.myValue = Double.parseDouble(data);
			return;
		}
	}

}
