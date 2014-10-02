package fr.lip6.move.evinrude.parser.modules;


import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.parser.IModule;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la classe {@link AssignementModule}
 */
public class AssignementModuleTest {
	private IModule module;
	private MockParser parser;

	private String assignement = "arg.0D.3632 = (struct identity_tD.3621 *) argD.3622";

	/**
	 * @throws java.lang.Exception En cas d'erreur
	 */
	@Before
	public final void setUp() throws Exception {
		parser = new MockParser();
		IFunction func = parser.getCfg().createFunction(1, "func");
		parser.setCurrentBlock(func.createBlock("2"));
		module = new AssignementModule(parser);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.AssignementModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch() {
		assertTrue(module.match(assignement));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.AssignementModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess() {
		module.process(assignement);
		assertNull(parser.getCurrentAssignement());
		assertNotNull(parser.getCurrentBlock().getInstructions().get(0));
	}

}
