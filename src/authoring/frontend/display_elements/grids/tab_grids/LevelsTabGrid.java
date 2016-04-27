package authoring.frontend.display_elements.grids.tab_grids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import authoring.frontend.IAuthoringView;
import authoring.frontend.display_elements.grid_factories.tab_grid_factories.LevelsTabGridFactory;
import authoring.frontend.display_elements.grids.TabGrid;
import authoring.frontend.display_elements.panels.GridViewPanel;
import authoring.frontend.display_elements.panels.button_dashboards.MainButtonDashboard;
import authoring.frontend.display_elements.tab_displays.TabDisplay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Frank
 *
 */

public class LevelsTabGrid extends TabGrid {

	private Map<String, String> currentInfo = new TreeMap<String, String>();
	private String newName;
	private Map<String, String> myLevels;

	public LevelsTabGrid(IAuthoringView controller, TabDisplay tabDisplay) {
		super(controller, tabDisplay);
	}

	@Override
	public void initialize() {
		initializeGridFactory();
		initializeGrid();
		assembleGridComponents();
		myLevels = new HashMap<String, String>();
	}

	@Override
	protected void initializeGridFactory() {
		myGridFactory = new LevelsTabGridFactory(myController, myTabDisplay);
	}

	@Override
	protected void assembleGridComponents() {
		super.assembleGridComponents();
		((MainButtonDashboard) myButtonDashboard).getDuplicateButton().setOnAction(e -> duplicate(currentInfo));
		((MainButtonDashboard) myButtonDashboard).getDeleteButton().setOnAction(e -> delete(currentInfo));

	}

	@Override
	public void setAttributesPanel(Map<String, String> info) {
		myUnmodifiableAttributesPanel.setAttributes(info);
	}

	public void update(List<Map<String, String>> data) {
		myLevels.clear();

		GridViewPanel gridView = (GridViewPanel) getPrimaryDisplay();
		gridView.clearImages();

		if (data.isEmpty()) {
			gridView.resetGrid();
		}

		for (Map<String, String> info : data) {
			if (myLevels.containsKey(info.get("Name"))) {
				continue;
			};
			
			myLevels.put(info.get("Name"), info.get("MapBackgroundImage"));
			info.remove("EntityNames");

			ImageView iv = new ImageView(info.get("MapBackgroundImage"));
			iv.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue,
						Boolean newValue) {
					if (newValue) {
						info.remove("Type");
						setAttributesPanel(info);
						currentInfo = info;
					}
				}
			});
			gridView.addImage(iv);
		}

	}

	private void duplicate(Map<String, String> info) {
		Map<String, String> duplicateEntity = new TreeMap<String, String>();
		for (String s : info.keySet()) {
			duplicateEntity.put(s, info.get(s));
		}

		String newName = promptNewName();

		if (!newName.equals("")) {
			duplicateEntity.replace("Name", newName);
			myTabDisplay.openEditorDisplay(duplicateEntity);
		}
	}

	private void delete(Map<String, String> info) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Entity Warning");
		alert.setContentText("Are you sure you want to delete this Level?");

		ButtonType buttonTypeOK = new ButtonType("OK");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == buttonTypeOK) {
			myController.deleteData(info);
			currentInfo.clear();
			setAttributesPanel(currentInfo);
			alert.close();
		} else if (result.get() == buttonTypeCancel) {
			return;
		}
	}

	private String promptNewName() {
		Stage promptStage = new Stage();
		VBox promptBox = new VBox();
		promptBox.setAlignment(Pos.CENTER);
		Label prompt = new Label("Enter new name:");
		TextField textBox = new TextField();
		textBox.setMaxWidth(200);
		promptBox.getChildren().add(prompt);
		promptBox.getChildren().add(textBox);
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		Button cancelButton = new Button("Cancel");
		Button saveButton = new Button("Save");
		cancelButton.setOnAction(e -> promptStage.close());
		textBox.setOnAction(e -> {
			newName = textBox.getText();
			promptStage.close();
		});

		saveButton.setOnAction(e -> {
			newName = textBox.getText();
			promptStage.close();
		});
		buttonBox.getChildren().addAll(cancelButton, saveButton);
		promptBox.getChildren().add(buttonBox);
		Scene promptScene = new Scene(promptBox, 300, 200);
		promptStage.setScene(promptScene);
		promptStage.showAndWait();
		return newName;
	}

	public Map<String, String> getLevels() {
		return myLevels;
	}
}
