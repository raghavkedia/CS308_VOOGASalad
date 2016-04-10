package backend.game_object.components;

public class DisplayComponent extends Component implements IComponent{
	
	private boolean canBeShown;
	private String imageToShow = "/DrumpfVader.png";
	
	public DisplayComponent(){
		this.canBeShown = true;
	}
	
	public DisplayComponent(boolean shown) {
		this.canBeShown = shown;
	}
	
	public boolean shouldBeShown(){
		return canBeShown;
	}
	
	public String getImage(){
		return imageToShow;
	}
	
	public void doNotShow(){
		canBeShown = false;
	}
	
	public static void main(String[] args){
		Object c = new DisplayComponent(true);
		System.out.println(c.getClass());
	}
	
	//need to use resource file
	@Override
	public String getTag(){
		return "Display";
	}

}
