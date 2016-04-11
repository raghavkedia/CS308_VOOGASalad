package authoring_environment.frontend.display_elements.grids.tab_grids;

import authoring_environment.frontend.display_elements.grid_factories.tab_grid_factories.GameTabGridFactory;
import authoring_environment.frontend.display_elements.grids.TabGrid;

/**
 * 
 * @author Frank
 *
 */

public class GameTabGrid extends TabGrid {

	@Override
	protected void initializeGridFactory() {
		myGridFactory = new GameTabGridFactory();
	}

	@Override
	protected void assembleGridComponents() {
		// TODO Auto-generated method stub

	}

}