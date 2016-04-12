package backend.xml_converting;

import backend.GameWorld;

public class GameWorldToXMLWriter extends ObjectToXMLWriter {

	
	public GameWorldToXMLWriter() {
		setXMLAlias();
	}

	private void setXMLAlias() {
		// Add all alias to set up
	}

	@Override
	public Object xMLToObject(String xml) {
		return (GameWorld) getXstream().fromXML(xml);
	}

}
