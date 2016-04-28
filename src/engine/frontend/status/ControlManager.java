package engine.frontend.status;

import engine.frontend.overall.ResourceUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ControlManager extends ResourceUser{
	private StatusPane myStatusPane;
	private static final String RESOURCE_NAME = "status";
	
	private Button play;
	private Button nextWave;
	private Button nextLevel;
	private ComboBox<String> modeComboBox;
	private Button modeButton;
	
	public ControlManager(StatusPane sp){
		super(RESOURCE_NAME);
		myStatusPane = sp;
	}
	

	public VBox buildGameControls(){
		VBox vbox = new VBox();
		
		play = myStatusPane.createButton(loadStringResource("PlayLabel"));
		nextWave = myStatusPane.createButton(loadStringResource("NextWaveLabel"));
		nextLevel = myStatusPane.createButton(loadStringResource("NextLevelLabel"));
		modeButton = myStatusPane.createButton(loadStringResource("ModeTitleLabel"));
		modeComboBox = new ComboBox<String>();
		
		play.setOnAction(e ->{
			if(play.getText().equals(loadStringResource("PlayLabel"))){
				myStatusPane.getEngineView().getEngineController().setPlaying(true);
				play.setText(loadStringResource("PauseLabel"));
			} else {
				myStatusPane.getEngineView().getEngineController().setPlaying(false);
				play.setText(loadStringResource("PlayLabel"));
			}
		});
	
		nextWave.setDisable(true);
		nextWave.setOnAction(e ->{
			myStatusPane.getEngineView().getEngineController().nextWaveClicked();
			nextWave.setDisable(true);
		});
		
		nextLevel.setDisable(true);
		nextLevel.setOnAction(e ->{
			myStatusPane.getEngineView().getEngineController().nextLevelClicked();
			nextLevel.setDisable(true);
		});
		
		modeComboBox.setDisable(true);
		modeComboBox.setPromptText("Select Mode");
		modeComboBox.getItems().addAll(myStatusPane.getEngineView().getEngineController().getGameWorld().getModes().keySet());
		modeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                myStatusPane.getEngineView().getEngineController().getEventManager().getModeStatistics().setCurrentModeIndex(t1);
                modeComboBox.setDisable(true);
            }    
        });
		
		modeComboBox.setMaxHeight(Double.MAX_VALUE);
		modeComboBox.setMaxWidth(Double.MAX_VALUE);
		VBox.setVgrow(modeComboBox, Priority.ALWAYS);
		
		vbox.getChildren().addAll(play, nextWave, nextLevel, modeComboBox);
		vbox.minWidthProperty().bind(myStatusPane.getPane().widthProperty().divide(4));
		return vbox;
	}
	
	public void nextWaveEnable(){
		nextWave.setDisable(false);
	}

	public void nextLevelEnable(){
		nextWave.setDisable(false);
	}
	
	public void switchModeEnable(){
		modeComboBox.setDisable(false);
	}
	
	
}
