package engine.frontend;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class StatusPane {
	private EngineView myEngineView;
	private Pane myPane;
	
	public StatusPane(EngineView ev){
		myEngineView = ev;
	}
	
	public Node buildNode(){
		myPane = new Pane();
		myPane.setStyle("-fx-background-color: #ffffff;");
		myPane.setMinSize(myEngineView.loadUIIntResource("StatusWidth")/2, myEngineView.loadUIIntResource("StatusHeight")/2);
		myPane.setPrefSize(myEngineView.loadUIIntResource("StatusWidth"), myEngineView.loadUIIntResource("StatusHeight"));
		return myPane;
	}
}
