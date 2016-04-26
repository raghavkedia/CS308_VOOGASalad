package authoring.frontend.display_elements.panels.attributes_panels.unmodifiable_panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import authoring.frontend.display_elements.panels.attributes_panels.UnmodifiableAttributesPanel;
import authoring.frontend.interfaces.display_element_interfaces.ITabDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 
 * @author Frank
 *
 */

public class UnmodifiableGameAttributesPanel extends UnmodifiableAttributesPanel {

	private BorderPane myWrapper;
	private GridPane myGridPane;
	
	public UnmodifiableGameAttributesPanel(int height, int width, ITabDisplay tabDisplay) {
		super(height, width, tabDisplay);
	}

	@Override
	protected void initializeComponents() {
		myWrapper = new BorderPane();

		List<Integer> rowConstraints = new ArrayList<Integer>();
		rowConstraints.add(BUTTON_HEIGHT_PERCENTAGE);
		rowConstraints.add(100 - BUTTON_HEIGHT_PERCENTAGE);
		List<Integer> columnConstraints = new ArrayList<Integer>();

		myGridPane = createGridWrapper(rowConstraints, columnConstraints);

		List<String> gameAttributes = (List<String>) Arrays.asList("Game Type", "Number of Players", "Number of Starting Lives",
				"Number of Lives for Defeat", "Game Timer", "Starting Resources");
		myAttributesGridPane = createAttributesGridPane(gameAttributes);
		myOpenEditorButton = createOpenEditorButton();

	}

	@Override
	protected void assembleComponents() {
		myGridPane.add(myOpenEditorButton, 0, 0);
		myGridPane.add(myAttributesGridPane, 0, 1);
		myWrapper.setCenter(myGridPane);
		myNode = myWrapper;

	}


	@Override
	protected void refreshDisplay() {
		for (int i = 0; i < myAttributes.size(); i++) {
			String currentAttribute = myAttributes.get(i);

			Text text = new Text(currentAttribute);
			text.setFont(new Font(FONT_SIZE));
			TextField tf = (TextField) myOutputMap.get(currentAttribute);
			tf.setText(myAttributesMap.get(myAttributes.get(i)));
			tf.setEditable(false);

			myOutputMap.replace(currentAttribute, tf);

		}
	}
}