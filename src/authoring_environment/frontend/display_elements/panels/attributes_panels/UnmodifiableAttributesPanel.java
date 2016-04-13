package authoring_environment.frontend.display_elements.panels.attributes_panels;

import java.util.List;

import authoring_environment.frontend.display_elements.panels.Panel;
import authoring_environment.frontend.display_elements.tab_displays.TabDisplay;
import authoring_environment.frontend.interfaces.display_element_interfaces.ITabDisplay;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * The UnmodifiableAttributesPanel displays aspect-specific attributes,
 * modifiable or not. This panel will typically be part of the right subgrid.
 * 
 * @author Frank
 *
 */

public abstract class UnmodifiableAttributesPanel extends Panel {

	protected TabDisplay myTabDisplay;
	protected final int BUTTON_HEIGHT_PERCENTAGE = 8;
	protected final int TITLED_PANE_HEIGHT = 350;
	protected final double ATTRIBUTES_PANEL_WIDTH = 800 * 0.4275;
	//scene width * 0.4275, hardcoded I know. Based on 30% column constraint.
	protected Button myOpenEditorButton;

	public UnmodifiableAttributesPanel(int height, int width, ITabDisplay tabDisplay) {
		super(height, width);
		myTabDisplay = (TabDisplay) tabDisplay;
	}

	/**
	 * Creates Button that opens myTabDisplay.
	 * 
	 * @return
	 */
	protected Button createOpenEditorButton() {
		Button button = new Button("Open Editor");
		button.setPrefSize(600, 600);
		button.setOnAction(e -> myTabDisplay.openEditorDisplay());
		return button;
	}

	/**
	 * Creates GridPane with set row and column constraints.
	 * 
	 * @return
	 */
	protected GridPane createGridWrapper(List<Integer> rowConstraints, List<Integer> columnConstraints) {
		GridPane grid = new GridPane();

		for (Integer i : rowConstraints) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(i);
			grid.getRowConstraints().add(row);
		}
		
		for (Integer i : columnConstraints) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(i);
			grid.getColumnConstraints().add(column);
		}
		
		return grid;
	}
	
	/**
	 * Creates a TitledPane given name and Node
	 * 
	 * @return
	 */
	protected TitledPane createTitledPane(String name, Node node) {
		TitledPane tp = new TitledPane(name, node);
		tp.setPrefSize(600, 600);
		return tp;
		
	}
}
