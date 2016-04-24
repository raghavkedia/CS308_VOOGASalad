package authoring.frontend.display_elements.tab_displays;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import authoring.backend.data.ObservableList;
import authoring.frontend.IAuthoringView;
import authoring.frontend.display_elements.editor_displays.EntityEditorDisplay;
import authoring.frontend.display_elements.grids.tab_grids.EntitiesTabGrid;
import engine.backend.entities.Entity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import authoring.frontend.display_elements.grids.TabGrid;

/**
 * 
 * @author benchesnut, Frank
 *
 */

public class EntitiesTabDisplay extends TabDisplay {

	private TabPane myEntitiesTabPane;
	private ObservableList<Entity> myEntityList;
	private String genreName;

	public EntitiesTabDisplay(int tabIndex, IAuthoringView controller) {
		super(tabIndex, controller);
		myController = controller;
		myEntityList = myController.getEntityList();
		myEntityList.addObserver(this);
	}

	public void initialize() {
		myEntitiesTabPane = new TabPane(); // tab of entity types
		myEditorDisplay = new EntityEditorDisplay(myController);
		myEditorDisplay.initialize();

		createNewTab("Ammo");
		createNewTab("Enemy");
		createNewTab("Tower");
		setTabPaneActions();
	}

	@Override
	public Node getNode() {
		return myEntitiesTabPane;
	}

	private void setTabPaneActions() {
		Tab addNewTypeTab = new Tab("Add New...", null);

		myEntitiesTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab selectedTab) {
				if (selectedTab == addNewTypeTab) {
					String newGenre = promptGenreName();
					if (newGenre != "") {
						myEntitiesTabPane.getTabs().remove(addNewTypeTab);
						createNewTab(newGenre);
						myEntitiesTabPane.getTabs().add(addNewTypeTab);
					} else {
						myEntitiesTabPane.getSelectionModel().select(oldTab);
					}
				}
			}
		});

		ContextMenu tabContextMenu = new ContextMenu();
		MenuItem tabMenu = new MenuItem("Change Genre name");
		tabMenu.setOnAction(e -> myEntitiesTabPane.getSelectionModel().getSelectedItem().setText(promptGenreName()));
		tabContextMenu.getItems().add(tabMenu);
		myEntitiesTabPane.setContextMenu(tabContextMenu);
		myEntitiesTabPane.getTabs().add(addNewTypeTab);
	}

	private void createNewTab(String name) {
		EntitiesTabGrid grid = new EntitiesTabGrid(myController, this);
		grid.initialize();
		Tab newTab = new Tab(name, grid.getNode());
		newTab.setOnSelectionChanged(e -> {
			if (newTab.isSelected()) {
				myGrid = grid;
			}
		});
		myEntitiesTabPane.getTabs().add(newTab);
		myEntitiesTabPane.getSelectionModel().select(newTab);
	}

	private String promptGenreName() {
		Stage promptStage = new Stage();
		genreName = "";
		VBox promptBox = new VBox();
		promptBox.setAlignment(Pos.CENTER);
		Label prompt = new Label("Enter new genre name:");
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
			genreName = textBox.getText();
			promptStage.close();
		});

		saveButton.setOnAction(e -> {
			genreName = textBox.getText();
			promptStage.close();
		});
		buttonBox.getChildren().addAll(cancelButton, saveButton);
		promptBox.getChildren().add(buttonBox);
		Scene promptScene = new Scene(promptBox, 300, 200);
		promptStage.setScene(promptScene);
		promptStage.showAndWait();
		return genreName;
	}

	@Override
	public void update(Observable o, Object arg) {
		Tab tempTab = myEntitiesTabPane.getSelectionModel().getSelectedItem();

		@SuppressWarnings("unchecked")
		List<Map<String, String>> data = (List<Map<String, String>>) arg;

		for (Tab t : myEntitiesTabPane.getTabs()) {
			if (!t.getText().equals("Add New...")) {
				myEntitiesTabPane.getSelectionModel().select(t);
				((EntitiesTabGrid) myGrid).update(data, t.getText());
			}
		}
		// myGrid.setAttributesPanel(data.get(0));
		myEntitiesTabPane.getSelectionModel().select(tempTab);
	}

	@Override
	public Map<String, String> getDefaultAttributesMap() {
		Map<String, String> map = new TreeMap<String, String>();

		map.put("DisplayComponent_Image", null);
		map.put("DisplayComponent_CanBeShown", null);
		map.put("Name", null);
		map.put("Genre", null);

		System.out.println("*****1. EntitiesTabDisplay: got default entities attributes");
		System.out.println(map);
		return map;
	}

	public void initializeHotKeys() {
		((TabGrid) myGrid).initializeHotKeys();

	}

	@Override
	public String getName() {
		return "Entities";
	}

	public List<String> getGenres() {
		List<String> genres = new ArrayList<String>();
		myEntitiesTabPane.getTabs().forEach(t -> genres.add(t.getText()));
		genres.remove("Add New...");
		return genres;
	}

	public List<String> getEntities() {
		Tab tempTab = myEntitiesTabPane.getSelectionModel().getSelectedItem();

		List<String> entities = new ArrayList<String>();
		for (Tab t : myEntitiesTabPane.getTabs()) {
			if (!t.getText().equals("Add New...")) {
				myEntitiesTabPane.getSelectionModel().select(t);
				entities.addAll(((EntitiesTabGrid) myGrid).getEntities());
			}
		}
		myEntitiesTabPane.getSelectionModel().select(tempTab);
		return entities;
	}

}
