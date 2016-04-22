package engine.backend.rules;


import engine.backend.components.IComponent;
import engine.backend.components.SizeComponent;

import java.util.ArrayList;
import java.util.List;



public class Rule {

	private List<Predicate> conditionals;
	private EntityAction action;
	//this string needs to get moved to action

    /**
     * thing moves across the screen and disappears at a certain point
     */

	public Rule() {
		conditionals = new ArrayList<Predicate>();
	}
	
	public void addPredicate(Predicate myPredicate){
		conditionals.add(myPredicate);
	}
	
	public void setMyAction(EntityAction myAction){
		action = myAction;
	}


	
	public List<Predicate> getMyConditionals(){
		return conditionals;
	}
	
	public void increaseSize(IComponent myComponent, Integer delta){
		((SizeComponent) myComponent).increaseSize(delta);
	}

}
