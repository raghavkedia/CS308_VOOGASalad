package authoring.frontend.display_elements.grid_factories;

import authoring.frontend.IAuthoringView;
import authoring.frontend.display_elements.panels.RulesEditorPanel;
import authoring.frontend.display_elements.panels.attributes_panels.ModifiableAttributesPanel;

/**
 * The EditorGridFactory superclass is responsible for creating the additional
 * modifiable attributes and Rules Panels UI elements.
 * 
 * 
 * @author Frank
 *
 */

public abstract class EditorGridFactory extends GridFactory {

	public EditorGridFactory(IAuthoringView controller) {
		super(controller);
	}

	/**
	 * @return instantiated and formatted myRulesPanel
	 */
	public abstract RulesEditorPanel createRulesPanel();

	/**
	 * @return instantiated and formatted myModifiableAttributesPanel
	 */
	public abstract ModifiableAttributesPanel createModifiableAttributesPanel();
}