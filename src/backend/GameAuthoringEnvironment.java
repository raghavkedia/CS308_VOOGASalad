/**
 * 
 * @author mario_oliver93
 *
 */

package backend;

import backend.game_object.entities.Entity;
import backend.game_object.entities.EntityFactoryClass;
import backend.rules.Action;
import backend.rules.Predicate;
import backend.rules.Rule;
import backend.systems.SystemsController;
import exception.DrumpfTowerException;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GameAuthoringEnvironment {

	private EntityFactoryClass entityFactory;
	private GameWorld trumpGame;
	private FrontEndGameAuthoringEnvironment frontMockData;
	private FrontEndAccessController frontendController;

	public GameAuthoringEnvironment() {
		this.entityFactory = new EntityFactoryClass();
		this.frontMockData = new FrontEndGameAuthoringEnvironment();
	}
	
	public void setBackendController(FrontEndAccessController frontendController){
		this.frontendController = frontendController;
	}

	public GameWorld createGameObject() {
		return new GameWorld();
	}

	public GameWorld setGameObjectWithMockData(FrontEndGameAuthoringEnvironment mockData) {
		trumpGame = createGameObject();
		trumpGame.initializeGameObject(mockData.modesWanted, mockData.levelsWanted);
		// can add extra layer iterating through each level so we add entity to
		// the right level
		// assuming we only want to add to level one right now
		Level currLevel = (trumpGame.getModes().get(trumpGame.getGameStats().getCurrentMode()).getLevels()
				.get(trumpGame.getGameStats().getCurrentLevel()));
		int tempx_y = 0;
		frontendController.createCharacterImage(tempx_y, tempx_y, currLevel.getMap().getImage(), currLevel.getMap().getXSize(), currLevel.getMap().getYSize());
		for (int eachSprite = 0; eachSprite < mockData.level1SpritesComponentWanted.length; eachSprite++) {
			Entity entity;
			entity = entityFactory.makeEntity(trumpGame, mockData.level1SpritesComponentWanted[eachSprite]);
			// this is the line that assumes we are on mode one and level one
			Rule myRule = new Rule(); myRule.addPredicate(new Predicate(mockData.myRules[eachSprite][0])); 
			myRule.setMyAction(new Action(mockData.myRules[eachSprite][1]));
			entity.addRule(myRule);
			trumpGame.getModes().get(trumpGame.getGameStats().getCurrentMode()).getLevels()
					.get(trumpGame.getGameStats().getCurrentLevel()).addToEntities(entity);
		}
		
		trumpGame.printWhatIHave();
		return trumpGame;
	}

}
