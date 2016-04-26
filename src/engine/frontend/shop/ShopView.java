package engine.frontend.shop;

/**
 * @author HaydenBader
 */

import engine.frontend.overall.EngineView;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ShopView {
	
	private EngineView myEngineView;
	String myType;
	private HBox myHBox; 
	private ImageView myImageView;
	
	public ShopView(EngineView ev){
		myEngineView = ev;		
	}
	
	public Node buildShopView(String image, String type, double cost, double width, double height){
		myHBox = new HBox();
		
		Text name = new Text(type);		
		myType = type;
		myImageView = new ImageView(new Image(image));
		myImageView.setFitWidth(width);
		myImageView.setFitHeight(height);
		
		myHBox.getChildren().addAll(name, myImageView);
		myHBox.setOnDragDetected(e -> selectTower(e));
		
		return myHBox;
	}

	public void selectTower(MouseEvent e){
		
		//myEngineView.getStage().getScene().setCursor(value);
		myEngineView.getDummyCursor().changePic(myImageView.getImage());
		myEngineView.getStage().getScene().setCursor(Cursor.NONE);
		Dragboard db = myHBox.startDragAndDrop(TransferMode.ANY);
        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(myType);
        db.setContent(content);
        e.consume();		
	}
	
	public void changeCursor(MouseEvent e){
		myEngineView.getStage().getScene().setCursor(Cursor.NONE);
		
	}
	
	public Node getNode(){
		return myHBox;
	}
}