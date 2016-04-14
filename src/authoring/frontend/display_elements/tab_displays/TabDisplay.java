package authoring.frontend.display_elements.tab_displays;

import authoring.frontend.IAuthoringView;
import authoring.frontend.display_elements.editor_displays.EditorDisplay;
import authoring.frontend.display_elements.grids.Grid;
import authoring.frontend.interfaces.display_element_interfaces.ITabDisplay;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The TabDisplay superclass acts as a container for it's Grid and corresponding
 * editor.
 * 
 * @author Frank
 *
 */

public abstract class TabDisplay implements ITabDisplay {

	private static final int EDITOR_SCENE_WIDTH = 1200;
	private static final int EDITOR_SCENE_HEIGHT = 800;
	protected EditorDisplay myEditorDisplay;
	protected Grid myGrid;
	protected IAuthoringView myController;

	public TabDisplay(IAuthoringView controller) {
		myController = controller;
	}

	@Override
	public Node getNode() {
		return myGrid.getNode();
	}

	@Override
	public void openEditorDisplay() {
		Stage editorStage = new Stage();
		BorderPane root = new BorderPane();
		root.setCenter(myEditorDisplay.getNode());
		Scene editorScene = new Scene(root, EDITOR_SCENE_WIDTH, EDITOR_SCENE_HEIGHT, Color.WHITE);
		editorStage.setScene(editorScene);
		editorStage.show();
	}

}
