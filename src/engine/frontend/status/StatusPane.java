package engine.frontend.status;

import java.util.ResourceBundle;

import engine.frontend.overall.EngineView;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/*
 * Todos:
 * Add button enabling based on current game status
 */

public class StatusPane {
	private EngineView myEngineView;
	private Pane myPane;
	
	public static final String DEFAULT_RESOURCE = "engine/frontend/status/statuspane";
	private ResourceBundle myResources;
	
	private ControlManager myControlManager;
	
	public StatusPane(EngineView ev){
		myEngineView = ev;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE);
		myControlManager = new ControlManager(this);
	}
	
	public Node buildNode(DoubleExpression widthBinding, DoubleExpression heightBinding){
		myPane = new Pane();
		myPane.setStyle("-fx-background-color: #ffffff;");
		
		bindWidth(widthBinding);
		bindHeight(heightBinding);
		
		HBox hbox = new HBox();
		hbox.getChildren().add(buildRecordControls());
		hbox.getChildren().add(myControlManager.buildGameControls());
		
		myPane.getChildren().add(hbox);
		return myPane;
	}
	
	private void bindWidth(DoubleExpression db){
		myPane.minWidthProperty().bind(db);
		myPane.maxWidthProperty().bind(db);
	}
	
	private void bindHeight(DoubleExpression db){
		myPane.minHeightProperty().bind(db);
		myPane.maxHeightProperty().bind(db);
	}
	
	private VBox buildRecordControls(){
		VBox vbox = new VBox();
		
		Button record = createButton(myResources.getString("RecordLabel"));
		Button stop = createButton(myResources.getString("StopRecordLabel"));
		Button picture = createButton(myResources.getString("PictureLabel"));
		
		record.setOnAction(e -> {
			myEngineView.getGameCapture().startCapture();
			record.setDisable(true);
			stop.setDisable(false);
		});
		
		stop.setDisable(true);
		stop.setOnAction(e -> {
			myEngineView.getGameCapture().endCapture();
			record.setDisable(false);
			stop.setDisable(true);
		});
		
		picture.setOnAction(e -> myEngineView.getGameCapture().takeScreenshot(myEngineView.getBody()));
		
		vbox.minWidthProperty().bind(myPane.widthProperty().divide(4));
		vbox.minHeightProperty().bind(myPane.heightProperty());
		vbox.maxHeightProperty().bind(myPane.heightProperty());
		
		vbox.getChildren().addAll(record, stop, picture);
		return vbox;
	}
	
	public Button createButton(String s){
		Button button = new Button(s);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setMaxWidth(Double.MAX_VALUE);
		VBox.setVgrow(button, Priority.ALWAYS);
		return button;
	}
	
	
	private Node buildStatDisplay(String name){
		return null;
	}
	
	public ResourceBundle getMyResources(){
		return myResources;
	}
	
	public Pane getPane(){
		return myPane;
	}
}