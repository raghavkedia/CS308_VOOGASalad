package engine.frontend;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class BoardPane {
	private EngineView myEngineView;
	private Pane myPane;
	private Map<Integer, EntityView> myImageMap = new HashMap<>();
	
	public BoardPane(EngineView ev){
		myEngineView = ev;
	}
	
	public Node buildNode(){
		myPane = new Pane();
		myPane.setStyle("-fx-background-color: #C0C0C0;");
		myPane.setMinSize(myEngineView.loadUIIntResource("BoardWidth")/2, myEngineView.loadUIIntResource("BoardHeight")/2);
		myPane.setPrefSize(myEngineView.loadUIIntResource("BoardWidth"), myEngineView.loadUIIntResource("BoardHeight"));
		return myPane;
	}
	
	public void createCharacterImage(double xCoord, double yCoord, String image, int id, double width, double height){
		ImageView myPlayer = new ImageView(new Image(image));
		myPlayer.setFitWidth(width);
		myPlayer.setFitHeight(height);
		myPlayer.setX(xCoord);
		myPlayer.setY(yCoord);
		myPane.getChildren().add(myPlayer);
		
	}
	
	/**
	 * updates entity with id to correct coordinate and size, if size is negative 
	 * @param xCoord
	 * @param yCoord
	 * @param image
	 * @param id
	 * @param width
	 * @param height
	 */
	public void updateEntity(double xCoord, double yCoord, String image, int id, double width, double height){
		if(myImageMap.containsKey(id)){
			myImageMap.get(id).update(xCoord, yCoord, image, width, height);
		} else {
			EntityView ev = new EntityView(myEngineView.getEngineController(), xCoord, yCoord, image, id, width, height);
			myImageMap.put(id, ev);
			myPane.getChildren().add(ev.getNode());
		}
	}
	
	public void deleteEntity(int id){
		if(myImageMap.containsKey(id)){
			myPane.getChildren().remove(myImageMap.get(id).getNode());
			myImageMap.remove(id);
		}
	}
	
	public void attemptTower(double xLoc, double yLoc){
		myEngineView.getEngineController().attemptTower(xLoc, yLoc);
	}
}
