package fr.lip6.move.evinrude.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The root of the description of a perspective.
 */
public interface IPerspective {
	List<IProperty> EMPTY_PROPERTIES = Collections.unmodifiableList(new ArrayList<IProperty>(0));

	/**
	 * @return name of the perspective, the name is used as an identifier then must be unique.
	 */
	String getName();

	/**
	 * @return list of rules
	 */
	List<IRule> getRules();

	/**
	 * @return list of properties. Depend of the level of the property, the property is applied on the local Petri net or the complete net.
	 */
	List<IProperty> getProperties();

	/**
	 * @return list of dependencies : names of perspectives and/or rules.
	 */
	List<String> getDependencies();
}
