package authoring.frontend.display_elements.grids;

import java.util.Map;
import authoring.frontend.IAuthoringView;
import authoring.frontend.display_elements.grid_factories.EditorGridFactory;
import authoring.frontend.display_elements.panels.Panel;
import authoring.frontend.display_elements.panels.attributes_panels.ModifiableAttributesPanel;
import authoring.frontend.display_elements.panels.button_dashboards.ButtonDashboard;
import authoring.frontend.display_elements.panels.button_dashboards.SimpleButtonDashboard;

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

	protected Panel myRulesPanel;
	protected ModifiableAttributesPanel myModifiableAttributesPanel;

	public EditorGrid(IAuthoringView controller) {
		super(controller);
	}

	@Override
	protected void initializeGrid() {
		super.initializeGrid();
		myRulesPanel = ((EditorGridFactory) myGridFactory).createRulesPanel();
		myModifiableAttributesPanel = ((EditorGridFactory) myGridFactory).createModifiableAttributesPanel();

	}

	@Override
	protected void assembleGridComponents() {
		super.assembleGridComponents();

		myGrid.add(myPrimaryDisplay.getNode(), 0, 0);
		myGrid.add(myRulesPanel.getNode(), 0, 1);
		myGrid.add(myModifiableAttributesPanel.getNode(), 1, 0);
		myGrid.add(myButtonDashboard.getNode(), 1, 1);

		((ButtonDashboard) myButtonDashboard).getSaveButton()
				.setOnAction(e -> sendData(((ModifiableAttributesPanel) myModifiableAttributesPanel).saveAttributes()));

		((SimpleButtonDashboard) myButtonDashboard).getResetButton().setOnAction(e -> resetAttributes());

	}

	protected void sendData(Map<String, String> map) {
		System.out.println("EditorGrid: myAttributesMap written to backend: ");
		System.out.println(map);
		myController.writeData(map);
		myController.showPrimaryScene();
	}

	protected void resetAttributes() {
		boolean close = ((ModifiableAttributesPanel) myModifiableAttributesPanel).createResetAlert();
		if (close) {
			myController.showPrimaryScene();
		}
	}

	public Panel getAttributesPanel() {
		return myModifiableAttributesPanel;
	}

	public void setAttributesPanel(Map<String, String> info) {
		myModifiableAttributesPanel.setAttributes(info);
	}

	public void populateComponents(Map<String, String> info) {
		// ImageView iv = new ImageView(info.get("image"));
		// ((EditorViewPanel) myPrimaryDisplay).setImage(iv.getImage());
		setAttributesPanel(info);
	}
}