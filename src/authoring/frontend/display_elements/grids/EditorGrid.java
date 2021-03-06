package authoring.frontend.display_elements.grids;

import java.util.Map;

import authoring.frontend.IAuthoringView;
import authoring.frontend.display_elements.grid_factories.EditorGridFactory;
import authoring.frontend.display_elements.panels.Panel;
import authoring.frontend.display_elements.panels.attributes_panels.ModifiableAttributesPanel;
import authoring.frontend.display_elements.panels.button_dashboards.EditorButtonDashboard;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * The EditorGrid superclass is a subset of Grid, which is in all the
 * EditorDisplays. The extra functionality that EditorGrids have is the creation
 * of an attributes panel (for which all the attributes displayed can be
 * modified).
 * 
 * @author Frank, benchesnut
 *
 */

public abstract class EditorGrid extends Grid {

	protected ModifiableAttributesPanel myModifiableAttributesPanel;
	protected Stage myEditorStage;

	public EditorGrid(IAuthoringView controller, Stage editorStage) {
		super(controller);
		myEditorStage = editorStage;
	}

	@Override
	protected void initializeGrid() {
		super.initializeGrid();
		myModifiableAttributesPanel = ((EditorGridFactory) myGridFactory).createModifiableAttributesPanel();

	}

	@Override
	protected void assembleGridComponents() {
		super.assembleGridComponents();

		((EditorButtonDashboard) myButtonDashboard).getSaveButton().setOnAction(e -> sendData(saveAttributes()));

		((EditorButtonDashboard) myButtonDashboard).getResetButton().setOnAction(e -> resetAttributes());

	}

	/**
	 * Takes given data and sends it to the backend through the AuthoringView
	 * 
	 * @param map
	 */
	protected void sendData(Map<String, String> map) {
		System.out.println("*****5. EditorGrid: saved myAttributesMap written to backend:");
		System.out.println(map);
		myController.writeData(map);
		myEditorStage.close();

	}

	protected Map<String, String> saveAttributes() {
		return myModifiableAttributesPanel.saveAttributes();
	}

	/**
	 * Resets user input or not based on an alert created within the
	 * ModifiableAttributesPanel. If user wishes to close out, then close the
	 * editor stage.
	 */
	protected void resetAttributes() {
		boolean close = ((ModifiableAttributesPanel) myModifiableAttributesPanel).createResetAlert();
		if (close) {
			myEditorStage.close();
		}
	}

	public Panel getAttributesPanel() {
		return myModifiableAttributesPanel;
	}

	public void setAttributesPanel(Map<String, String> info) {
		((ModifiableAttributesPanel) myModifiableAttributesPanel).updateAttributes(info);
	}

	@Override
	public void initializeHotKeys() {
		Button saveButton = ((EditorButtonDashboard) myButtonDashboard).getSaveButton();
		Button resetButton = ((EditorButtonDashboard) myButtonDashboard).getResetButton();

		saveButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
				new Runnable() {
					@Override
					public void run() {
						saveButton.fire();
					}
				});

		resetButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN),
				new Runnable() {
					@Override
					public void run() {
						resetButton.fire();
					}
				});

	}

}
