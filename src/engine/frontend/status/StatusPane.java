package engine.frontend.status;

import engine.frontend.overall.AbstractPane;
import engine.frontend.overall.EngineView;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class StatusPane extends AbstractPane{
	public static final String DEFAULT_RESOURCE = "status";
	
	private ControlManager myControlManager;

	public StatusPane(EngineView ev){
		super(ev, DEFAULT_RESOURCE);
		myControlManager = new ControlManager(this);
	}
	
	public Node buildNode(DoubleExpression widthBinding, DoubleExpression heightBinding){
		super.buildNode(widthBinding, heightBinding);
		
		HBox hbox = new HBox();
		hbox.getChildren().add(buildRecordControls());
		hbox.getChildren().add(myControlManager.buildGameControls());
		
		getPane().getChildren().add(hbox);
		return getPane();
	}
	
	private VBox buildRecordControls(){
		VBox vbox = new VBox();
		
		Button record = createButton(loadStringResource("RecordLabel"));
		Button stop = createButton(loadStringResource("StopRecordLabel"));
		Button picture = createButton(loadStringResource("PictureLabel"));
		
		record.setOnAction(e -> {
			myEngineView.getGameCapture().startCapture();
			record.setDisable(true);
			stop.setDisable(false);
			myEngineView.getStage().setResizable(false);
		});
		
		stop.setDisable(true);
		stop.setOnAction(e -> {
			myEngineView.getGameCapture().endCapture();
			record.setDisable(false);
			stop.setDisable(true);
			myEngineView.getStage().setResizable(true);
		});
		
		picture.setOnAction(e -> myEngineView.getGameCapture().takeScreenshot());
		
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
	
	public Pane getPane(){
		return myPane;
	}
	
	public ControlManager getControlManager(){
		return myControlManager;
	}
}
