package authoring_environment.frontend.display_elements.grid_factories.editor_grid_factories;

import java.io.File;
import authoring_environment.controller.IController;
import authoring_environment.frontend.display_elements.grid_factories.EditorGridFactory;
import authoring_environment.frontend.display_elements.panels.EditorViewPanel;
import authoring_environment.frontend.display_elements.panels.Panel;
import authoring_environment.frontend.display_elements.panels.RulesEditorPanel;
import authoring_environment.frontend.display_elements.panels.attributes_panels.ModifiableAttributesPanel;
import authoring_environment.frontend.display_elements.panels.attributes_panels.modifiable_panels.ModifiableGameAttributesPanel;
import authoring_environment.frontend.display_elements.panels.button_dashboards.StandardButtonDashboard;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 
 * @author Frank
 *
 */

public class GameEditorGridFactory extends EditorGridFactory {

	public GameEditorGridFactory(IController controller) {
		super(controller);
	}

	@Override
	public Panel createRulesPanel() {
		RulesEditorPanel editorPanel = new RulesEditorPanel(PANEL_SIZE, PANEL_SIZE);
		editorPanel.initialize();
		return editorPanel;
	}

	@Override
	public Panel createModifiableAttributesPanel() {
		ModifiableAttributesPanel panel = new ModifiableGameAttributesPanel(PANEL_SIZE, PANEL_SIZE);
		panel.initialize();
		return panel;
	}

	@Override
	public Panel createPrimaryDisplay() {
		EditorViewPanel editorView = new EditorViewPanel(50, 50);
		editorView.initialize();
		editorView.setImage(new Image("DrumpfVader.png")); // set default
															// image as
															// question
															// mark or
															// something

		editorView.getPanelBar().addButton("Upload Image", e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
			File imageFile = fileChooser.showOpenDialog(null);
			if (imageFile != null) {
				editorView.setImage(new Image(imageFile.toURI().toString()));
			}
		});
		return editorView;
	}

	@Override
	public Panel createButtonDashboard() {
		StandardButtonDashboard buttons = new StandardButtonDashboard(PANEL_SIZE, PANEL_SIZE);
		buttons.initialize();
		return buttons;
	}

}
