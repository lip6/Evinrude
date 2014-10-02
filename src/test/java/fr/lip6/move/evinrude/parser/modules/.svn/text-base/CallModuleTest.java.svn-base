package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Assignement;
import fr.lip6.move.evinrude.commons.model.cfg.Variable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.parser.IModule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la classe {@link CallModule}
 */
public class CallModuleTest {
	private IModule module;
	private MockParser parser;

	private String call = "pthread_create (D.3683, 0B, workD.3623, idD.3666);";

	/**
	 * @throws java.lang.Exception En cas d'erreur
	 */
	@Before
	public final void setUp() throws Exception {
		parser = new MockParser();
		IFunction func = parser.getCfg().createFunction(1, "func");
		parser.setCurrentBlock(func.createBlock("2"));
		module = new CallModule(parser);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.CallModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch() {
		assertTrue(module.match(call));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.CallModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess() {
		module.process(call);
		assertNull(parser.getCurrentCall());
		assertNotNull(parser.getCurrentBlock().getInstructions().get(0));

		IAssignement assignement = new Assignement(1, 1, parser.getCurrentBlock());
		assignement.setLeft(new Variable("name", ""));
		parser.setCurrentAssignement(assignement);
		module.process(call);
		assertNull(parser.getCurrentCall());
		assertNotNull(assignement.getRight());

		ICall callObj = (ICall) assignement.getRight();
		assertEquals(callObj.getParameters().size(), 4);
	}

}
