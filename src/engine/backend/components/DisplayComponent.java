/**
 * 
 * @author mario_oliver93
 * 
 */


package engine.backend.components;

import java.util.List;

public class DisplayComponent extends Component implements IComponent{
	
	private boolean canBeShown;
	private String image;
	
	public DisplayComponent(String image){
		this.image = image;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public DisplayComponent(boolean shown) {
		this.canBeShown = shown;
	}
	
	public boolean shouldBeShown(){
		return canBeShown;
	}
	
	public String getImage(){
		return image;
	}
	
	public void doNotShow(){
		canBeShown = false;
	}
	
	@Override
	public String toString() {
		return this.getTag() + this.image;
	}

	public static void main(String[] args){
		Object c = new DisplayComponent(true);
		System.out.println(c.getClass());
	}

	@Override
	public void initWithParams(List<?> params) {
		this.canBeShown = true;
		
	}

}
