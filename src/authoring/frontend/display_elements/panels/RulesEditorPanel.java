package authoring.frontend.display_elements.panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import authoring.frontend.IAuthoringView;
import authoring.frontend.display_elements.panels.attributes_panels.ModifiableAttributesPanel;
import authoring.frontend.display_elements.panels.attributes_panels.modifiable_panels.ModifiableLevelAttributesPanel;
import authoring.frontend.display_elements.panels.button_dashboards.EditorButtonDashboard;
import authoring.frontend.editor_features.EntityComponents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The RulesEditorPanel contains two scrollable views of "if" and "then"
 * conditions, which together create a rule.
 * 
 * @author Frank
 *
 */

public class RulesEditorPanel extends Panel {

	public static final int COLUMN_1_WIDTH_PERCENTAGE = 10;
	public static final int COLUMN_2_WIDTH_PERCENTAGE = 15;
	public static final int COLUMN_3_WIDTH_PERCENTAGE = 30;
	public static final int COLUMN_4_WIDTH_PERCENTAGE = 15;
	public static final int COLUMN_5_WIDTH_PERCENTAGE = 30;
	public static final List<String> EDITABLE_LEVEL_ATTRIBUTES = (List<String>) Arrays.asList("NumLives", "CurrentResources");
	
	private EditorButtonDashboard mySimpleButtonDashboard;
	private Button myAddNewIfButton;
	private Button myAddNewThenButton;
	private ListView<String> myIfStatements;
	private ListView<String> myThenStatements;
	private Stage myRulesStage;
	private IAuthoringView myController;
	private ModifiableAttributesPanel myAttributes;
	private ObservableList<String> myRules;

	public RulesEditorPanel(double height, double width, IAuthoringView controller, ModifiableAttributesPanel attributes) {
		super(height, width);
		myController = controller;
		myAttributes = attributes;
	}

	@Override
	protected void initializeComponents() {
		mySimpleButtonDashboard = new EditorButtonDashboard(MAX_SIZE, MAX_SIZE);
		mySimpleButtonDashboard.initialize();
		myRules = FXCollections.observableArrayList();
		myRulesStage = new Stage();
		((ModifiableLevelAttributesPanel) myAttributes).updateRulesView(myRules);
		myAddNewIfButton = new Button("Add New If");
		myAddNewIfButton.setOnAction(e -> openStatementCreator(openIfScene()));
		myAddNewThenButton = new Button("Add New Then");
		myAddNewThenButton.setOnAction(e -> openStatementCreator(openThenScene()));
		myIfStatements = new ListView<String>();
		myThenStatements = new ListView<String>();
		
	}


	@Override
	protected void assembleComponents() {
		List<Integer> rowConstraints = new ArrayList<Integer>();
		List<Integer> columnConstraints = new ArrayList<Integer>();
		columnConstraints.add(COLUMN_1_WIDTH_PERCENTAGE);
		columnConstraints.add(COLUMN_2_WIDTH_PERCENTAGE);
		columnConstraints.add(COLUMN_3_WIDTH_PERCENTAGE);
		columnConstraints.add(COLUMN_4_WIDTH_PERCENTAGE);
		columnConstraints.add(COLUMN_5_WIDTH_PERCENTAGE);

		GridPane grid = createGridWrapper(rowConstraints, columnConstraints);

		VBox ifbox = createVBoxWrapper("If Statements", myAddNewIfButton);
		VBox thenbox = createVBoxWrapper("Then Statements", myAddNewThenButton);
		mySimpleButtonDashboard.getSaveButton().setOnAction(e -> {
			StringBuilder sb = new StringBuilder();
			myIfStatements.getItems().forEach(event -> sb.append(event + "+"));
			sb.replace(sb.length()-1, sb.length(), ":");
			myThenStatements.getItems().forEach(action -> sb.append(action + "+"));
			sb.deleteCharAt(sb.length()-1);
			myRules.add(sb.toString());
						
			myIfStatements.getItems().clear();
			myThenStatements.getItems().clear();
		});

		grid.add(mySimpleButtonDashboard.getNode(), 0, 0);
		grid.add(ifbox, 1, 0);
		grid.add(myIfStatements, 2, 0);
		grid.add(thenbox, 3, 0);
		grid.add(myThenStatements, 4, 0);
		myNode = grid;

	}

	/**
	 * Creates a VBox wrapper containing a Label and Button.
	 * 
	 * @param labelText
	 * @param button
	 * @return
	 */
	private VBox createVBoxWrapper(String labelText, Button button) {
		VBox vb = new VBox();
		Label label = new Label(labelText);
		label.setPrefSize(MAX_SIZE, MAX_SIZE);
		button.setPrefSize(MAX_SIZE, MAX_SIZE);
		vb.getChildren().addAll(label, button);
		return vb;

	}
	
	private String openStatementCreator(Scene s) {
		myRulesStage.setScene(s);
		myRulesStage.showAndWait();
		return null;
	}
	

	private Scene openIfScene() {
		VBox ifStatementBuilder = new VBox();
		Scene ifScene = new Scene(ifStatementBuilder, 400, 400, Color.WHITE);
		
		HBox selectEventBox = new HBox();
		Text eventText = new Text("Choose the type of event:");
		HBox selectEntitiesBox = new HBox();
		Text entityText = new Text();

		ComboBox<String> eventChooser = new ComboBox<String>();
		ComboBox<Label> entityChooser = new ComboBox<Label>();
		ComboBox<Label> entityChooser2 = new ComboBox<Label>();
		
		Button saveButton = new Button("Create Event");
		saveButton.setOnAction(e -> {
			StringBuilder sb = new StringBuilder();
			sb.append(entityChooser.getSelectionModel().getSelectedItem().getText() + "_");
			if (entityChooser2.isVisible()) {
				sb.append(entityChooser2.getSelectionModel().getSelectedItem().getText() + "_");
			}
			sb.append(eventChooser.getSelectionModel().getSelectedItem());
			myIfStatements.getItems().add(sb.toString());
			myRulesStage.close();
		});
		
		eventChooser.getItems().addAll("CollisionEvent", "CriticalHealthEvent", "CriticalPositionEvent", "EntityClickedEvent");
		eventChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				ifStatementBuilder.getChildren().clear();
				ifStatementBuilder.getChildren().add(selectEventBox);
				
				if (newValue == null) return;
				
				entityChooser2.setVisible(false);
				if (newValue.equals("CollisionEvent")) {
					entityText.setText("Choose the entities for this event:");
					entityChooser2.setVisible(true);
				}
				else {
					entityText.setText("Choose the entity for this event:");
				}
				ifStatementBuilder.getChildren().addAll(selectEntitiesBox, saveButton);
			}
			
		});
		
		// link the list of level entities to these combo boxes
		entityChooser.getItems().addAll(((ModifiableLevelAttributesPanel) myAttributes).getLevelEntities());
		entityChooser2.getItems().addAll(((ModifiableLevelAttributesPanel) myAttributes).getLevelEntities());
		
		selectEventBox.getChildren().addAll(eventText, eventChooser);
		selectEntitiesBox.getChildren().addAll(entityText, entityChooser, entityChooser2);
		ifStatementBuilder.getChildren().add(selectEventBox);
		return ifScene;
	}

	private Scene openThenScene() {
		VBox thenStatementBuilder = new VBox();
		Scene thenScene = new Scene(thenStatementBuilder, 400, 400, Color.WHITE);
		HBox selectTypeBox = new HBox();
		Text typeText = new Text("Select the type of the affected object:");
		HBox selectEntityBox = new HBox();
		Text entityText = new Text("Select the affected entity:");
		HBox selectAttributeBox = new HBox();
		Text attributeText = new Text("Select the attribute to update:");
		HBox selectNewValueBox = new HBox();
		Text newValueText = new Text("Enter the new value:");
		HBox selectLevelValueToModifyBox = new HBox();
		Text levelValueText = new Text("Select the level value to change:");
		HBox enterDeltaValueBox = new HBox();
		Text deltaValueText = new Text("Enter the delta value:");
		
		ComboBox<String> typeChooser = new ComboBox<String>();
		typeChooser.getItems().addAll("Level", "Entity");
		ComboBox<Label> entityChooser = new ComboBox<Label>();
		entityChooser.getItems().addAll(((ModifiableLevelAttributesPanel) myAttributes).getLevelEntities());
		ComboBox<String> attributeChooser = new ComboBox<String>();
		ComboBox<Label> newValueChooser = new ComboBox<Label>();
		ComboBox<String> levelValueChooser = new ComboBox<String>();
		levelValueChooser.getItems().addAll(EDITABLE_LEVEL_ATTRIBUTES);
		TextField deltaValueField = new TextField();
		
		Button saveButton = new Button("Create Action");
		saveButton.setOnAction(e -> {
			StringBuilder sb = new StringBuilder();
			sb.append(typeChooser.getSelectionModel().getSelectedItem() + "_");
			if (typeChooser.getSelectionModel().getSelectedItem().equals("Entity")) {
				sb.append(entityChooser.getSelectionModel().getSelectedItem().getText() + "_");
				sb.append(attributeChooser.getSelectionModel().getSelectedItem() + "_");
				
			}
			else {
				sb.append(levelValueChooser.getSelectionModel().getSelectedItem() + "_");
			}
			if (thenStatementBuilder.getChildren().contains(selectNewValueBox)) {
				sb.append(newValueChooser.getSelectionModel().getSelectedItem());
			}
			else {
				sb.append(deltaValueField.getText());
			}
			myThenStatements.getItems().add(sb.toString());
			myRulesStage.close();
		});
		
		typeChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				thenStatementBuilder.getChildren().clear();
				thenStatementBuilder.getChildren().add(selectTypeBox);
				if (newValue.equals("Level")) {
					levelValueChooser.getSelectionModel().clearSelection();
					thenStatementBuilder.getChildren().add(selectLevelValueToModifyBox);
				}
				else {
					entityChooser.getSelectionModel().clearSelection();
					thenStatementBuilder.getChildren().add(selectEntityBox);
				}
			}
			
		});
		
		entityChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {

			@Override
			public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
				thenStatementBuilder.getChildren().clear();
				thenStatementBuilder.getChildren().addAll(selectTypeBox, selectEntityBox);
				
				if (newValue == null) return;
				
				attributeChooser.getSelectionModel().clearSelection();
				attributeChooser.getItems().clear();
				attributeChooser.getItems().addAll(myController.getEntities().get(newValue.getText()).keySet());
				thenStatementBuilder.getChildren().add(selectAttributeBox);
			}
			
		});
		
		attributeChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				thenStatementBuilder.getChildren().clear();
				thenStatementBuilder.getChildren().addAll(selectTypeBox, selectEntityBox,
						selectAttributeBox);
				
				if (newValue == null) return;
				
				newValueChooser.getSelectionModel().clearSelection();
				newValueChooser.getItems().clear();
				deltaValueField.clear();
				
				if (EntityComponents.getVariableType(newValue).equals("Numerical")) {
					thenStatementBuilder.getChildren().add(enterDeltaValueBox);
				}
				else {
					thenStatementBuilder.getChildren().add(selectNewValueBox);
				}
				
				if (EntityComponents.getVariableType(newValue).equals("Boolean")) {
					newValueChooser.getItems().addAll(new Label("true"), new Label("false"));
				}
				else if (EntityComponents.getVariableType(newValue).equals("Image")) {
					newValueChooser.getItems().addAll(myController.getAuthoringViewManager().getImageChooser().getList());
				}
				else if (EntityComponents.getVariableType(newValue).equals("Entity")) {
					// add all of the level entities
					newValueChooser.getItems().addAll(((ModifiableLevelAttributesPanel) myAttributes).getLevelEntities());
				}
				
				thenStatementBuilder.getChildren().add(saveButton);
			}
			
		});	

		
		levelValueChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				thenStatementBuilder.getChildren().clear();
				thenStatementBuilder.getChildren().addAll(selectTypeBox, selectLevelValueToModifyBox);
				
				if (newValue == null) return;
				
				deltaValueField.clear();
				thenStatementBuilder.getChildren().addAll(enterDeltaValueBox, saveButton);
			}
			
		});
		

		selectTypeBox.getChildren().addAll(typeText, typeChooser);
		selectEntityBox.getChildren().addAll(entityText, entityChooser);
		selectAttributeBox.getChildren().addAll(attributeText, attributeChooser);
		selectLevelValueToModifyBox.getChildren().addAll(levelValueText, levelValueChooser);
		selectNewValueBox.getChildren().addAll(newValueText, newValueChooser);
		enterDeltaValueBox.getChildren().addAll(deltaValueText, deltaValueField);
		
		thenStatementBuilder.getChildren().add(selectTypeBox);
		return thenScene;
	}

	public String getRules() {
		StringBuilder sb = new StringBuilder();
		myRules.forEach(rule -> sb.append(rule + " "));
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public void setRules(String rulesString) {
		myRules.clear();
		if (rulesString != null) {
			List<String> rules = Arrays.asList(rulesString.split(" "));
			myRules.addAll(rules);
		}
	}

}
