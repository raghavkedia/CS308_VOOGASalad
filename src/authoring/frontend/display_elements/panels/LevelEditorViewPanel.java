package authoring.frontend.display_elements.panels;

import javafx.scene.Group;
import javafx.scene.layout.VBox;

public class LevelEditorViewPanel extends EditorViewPanel {

	private CurveBuilder myPathBuilder;
	
	public LevelEditorViewPanel(double height, double width) {
		super(height, width);
	}
	
	public void initializeComponents() {
		super.initializeComponents();
		myPathBuilder = new CurveBuilder();
		myPathBuilder.initialize();
	}
	
	public void assembleComponents() {
		VBox vbox = new VBox();
		myGroup.getChildren().addAll(myImageView, myPathBuilder.getNode());
		myScrollPane.setContent(myGroup);
		vbox.getChildren().addAll(myPanelBar.getNode(), myScrollPane);
		myPanelBar.addButton("Add Path", e -> myPathBuilder.createNewCurve());
		myPathBuilder.setSize(myImageView.getFitHeight(), myImageView.getFitWidth());
		myNode = vbox;
	}

}