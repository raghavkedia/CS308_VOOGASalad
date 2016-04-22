package engine.backend.entities;

import engine.backend.components.IComponent;
import engine.backend.rules.EntityAction;
import engine.backend.rules.Rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Entity implements IEntity {

	private String myName;
	private String myType;
	private Map<String, String> entityInfo;
	private List<Rule> myRules;
	private int myID;
	private Map<String, IComponent> myComponents;
	private boolean hasBeenModified;

	public Entity(int myID, String myName, String myType) {
		this.myName = myName;
		this.myType = myType;
		this.myID = myID;

		// this.myValue = myValue;
	}

	public Entity(String myName, String myType, double myValue) {
		this.myName = myName;
		this.myType = myType;
		// this.myValue = myValue;
	}
	
	private void initializeInfo() {
		entityInfo.put("Type", "Entity");
		entityInfo.put("Genre", myType);
		entityInfo.put("Name", myName);
	}

	public void addRule(Rule myRule) {
		myRules.add(myRule);
	}

	public void addComponent(IComponent component) {
		myComponents.put(component.getTag(), component);
		String[] componentInfo = component.getComponentInfo().split(",");
		for (String s : componentInfo) {
			String[] info = s.split(":");
			String tag = component.getTag() + "_" + info[0];
			String value = info[1];
			entityInfo.put(tag, value);
		}
	}

	public IComponent getComponent(String tag) {
		return myComponents.get(tag);
	}

	public Set<String> getComponentTags() {
		return myComponents.keySet();
	}

	public Collection<IComponent> getComponents() {
		return myComponents.values();
	}

	public int getID() {
		return myID;
	}

	@Override
	public void setLevelID(int levelID) {
		myID = levelID;
	}

	public String getName() {
		return myName;
	}

	public String getType() {
		return myType;
	}
	
	public Map<String, String> getInfo() {
		return entityInfo;
	}
	
	public void setID(int myID) {
		this.myID = myID;
	}

	public void setMane(String name) {
		this.myName = name;
	}

	public boolean hasComponent(String tag) {
		return myComponents.get(tag) != null;
	}

	public boolean hasBeenModified() {
		return hasBeenModified;
	}

	public void setHasBeenModified(boolean bool) {
		hasBeenModified = bool;
	}

	public void setMyType(String myType) {
		this.myType = myType;
	}

	@Override
	public String toString() {
		return "Entity [myID=" + myID + ", components=" + myComponents + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Entity) {
			Entity temp = (Entity) o;
			if (this.myName.equals(temp.myName)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}



	@Override
	public void applyAction(EntityAction action, ResourceBundle myComponentTagResources) {
		String component = action.getComponentToModifiy();
		String instanceVar = action.getValueInComponent();
		String newVal = action.getNewValue();
		Method setMethod;

		String fullName = myComponentTagResources.getString(component);
		Class<? extends IComponent> componentClass = myComponents.get(fullName).getClass();

		try {
			Object componentClassInstance = componentClass.newInstance();
			componentClassInstance = componentClass.cast(myComponents.get(fullName));
			// put in resource file!!!
			String methodName = "set" + instanceVar;

			setMethod = componentClassInstance.getClass().getMethod(methodName, String.class);

			setMethod.invoke(componentClassInstance, newVal);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}