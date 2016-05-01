package authoring.frontend.display_elements.panels.panel_bars;

import authoring.frontend.display_elements.panels.GridViewPanel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

/**
 * The GridPanelBar contains a description as well as controls to change the
 * number of columns displaying the various sprites (or modes/levels).
 * 
 * @author Frank
 *
 */

public class GridPanelBar extends PanelBar {

	private Label numColumnsLabel;
	private Button myIncreaseColumnsButton;
	private Button myDecreaseColumnsButton;
	private GridViewPanel myGridView;

	public GridPanelBar(double height, double width, GridViewPanel grid) {
		super(height, width);
		myGridView = grid;
	}

	@Override
	protected void initializeComponents() {
		super.initializeComponents();
		numColumnsLabel = new Label("# of columns");
		numColumnsLabel.setFont(new Font(25));
		myIncreaseColumnsButton = new Button("+");
		myDecreaseColumnsButton = new Button("-");
	}

	@Override
	protected void assembleComponents() {
		myDecreaseColumnsButton.setOnAction(e -> myGridView.decreaseGridSize());
		myIncreaseColumnsButton.setOnAction(e -> myGridView.increaseGridSize());

		HBox selector = assembleColumnSelector();
		selector.setAlignment(Pos.CENTER);
		myGridBar.getChildren().addAll(myDescription, selector);
		myNode = myGridBar;
	}
	
	private HBox assembleColumnSelector() {
		HBox.setHgrow(myDecreaseColumnsButton, Priority.ALWAYS);
		HBox.setHgrow(numColumnsLabel, Priority.ALWAYS);
		HBox.setHgrow(myIncreaseColumnsButton, Priority.ALWAYS);
		HBox columnSelector = new HBox(myDecreaseColumnsButton, numColumnsLabel, myIncreaseColumnsButton);
		return columnSelector;	
	}
	
	@Override
	public void setFontSize(int font) {
		super.setFontSize(font);
		numColumnsLabel.setFont(new Font(font));
	}
	
	@Override
	public void setDescription(String description) {
		myDescription.setText("You are currently viewing your " + description);

	}

	
}
