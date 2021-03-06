package authoring.frontend.configuration;

import java.util.Map;

import authoring.frontend.IAuthoringView;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Frank
 *
 */

public class LabelCell extends ListCell<Label> {

	private Map<String, String> myImageMap;
	private IAuthoringView myController;

	public LabelCell(Map<String, String> imageMap, IAuthoringView controller) {
		myImageMap = imageMap;
		myController = controller;
	}

	@Override
	protected void updateItem(Label item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setItem(null);
			setGraphic(null);
		} else {
			Label newLabel = new Label(item.getText());

			ImageView newImage = new ImageView(
					new Image(myController.getImageMap().get(myImageMap.get(item.getText()))));
			newImage.setFitHeight(Constants.getInt("LABEL_CELL_IMAGE_SIZE"));
			newImage.setFitWidth(Constants.getInt("LABEL_CELL_IMAGE_SIZE"));

			newImage.setPreserveRatio(true);
			newLabel.setGraphic(newImage);
			newLabel.setStyle("-fx-text-fill: black;");
			setGraphic(newLabel);
		}
	}

}
