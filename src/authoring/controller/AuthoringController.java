package authoring.controller;

import java.util.Map;
import java.util.Observable;

import authoring.backend.ModelManager;
import authoring.backend.data.GlobalData;

/*
 * @author: Jonathan Ma
 */

public class AuthoringController implements IAuthoringController {
	
	private final GlobalData globaldata;
	private final ModelManager model;
	
	public AuthoringController(GlobalData globaldata) {
		this.globaldata = globaldata;
		this.model = new ModelManager(globaldata);
		setListener();
	}
	
	private void setListener() {
		this.globaldata.getData().addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == globaldata.getData()) {
			parseInput(globaldata.getData().getData());
		}
	}

	@Override
	public void parseInput(Map<String, String> input) {
		Map<String, String> data = processData(input);
		String type = data.get("Type");
		String command = data.get("Command");
		data.remove("Command");
		data.remove("Type");
		if (type.equals("Entity")) {
			model.updateEntities(command, data);
			return;
		}
		if (type.equals("Level")) {
			model.updateLevels(command, data);
			return;
		}
		if (type.equals("Mode")) {
			model.updateModes(command, data);
			return;
		}
		if (type.equals("Game")) {
			model.updateGame(command, data);
			return;
		}
	}
	
	private Map<String, String> processData(Map<String, String> data) {
		for (String key : data.keySet()) {
			if (data.get(key).equals("") || data.get(key) == null) {
				System.out.println(key);
				data.put(key, "0");
			}
		}
		return data;
	}
	
}

