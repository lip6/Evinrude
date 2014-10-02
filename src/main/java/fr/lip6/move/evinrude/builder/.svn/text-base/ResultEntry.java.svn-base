package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Result to a rule process.
 */
public class ResultEntry {
	private final IRule rule;
	private final Object cfgObj;
	private final List<ISubModel> subModels;

	/**
	 * @param rule rule generated this entry
	 * @param cfgObj reference to the element of the CFG proceed to the rule
	 * @param subModels sub-models generate by the rule
	 */
	public ResultEntry(IRule rule, Object cfgObj, List<ISubModel> subModels) {
		this.rule = rule;
		this.cfgObj = cfgObj;
		this.subModels = new ArrayList<ISubModel>();
		if (subModels != null) {
			this.subModels.addAll(subModels);
		}
	}

	/**
	 * @return the rule
	 */
	public final IRule getRule() {
		return rule;
	}

	/**
	 * @return the cfgObj
	 */
	public final Object getCfgObj() {
		return cfgObj;
	}

	/**
	 * @return the subModels
	 */
	public final List<ISubModel> getSubModels() {
		return subModels;
	}

}
