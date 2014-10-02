package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.parser.IModule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la classe {@link FunctionModule}
 */
public class FunctionModuleTest {
	private IModule module;
	private MockParser parser;

	private String function = "function ()";
	private String functionWithParam = "function (param)";
	private String functionWithMultiParam = "function (param1, param2,param3)";

	/**
	 * Initialisation du module testé
	 */
	@Before
	public final void setUp() {
		parser = new MockParser();
		module = new FunctionModule(parser);
	}

	/**
	 * Test de la méthode match()
	 */
	@Test
	public final void testMatch1() {
		assertTrue(module.match(function));
	}

	/**
	 * Test de la méthode match()
	 */
	@Test
	public final void testMatch2() {
		assertTrue(module.match(functionWithParam));
	}

	/**
	 * Test de la méthode match()
	 */
	@Test
	public final void testMatch3() {
		assertTrue(module.match(functionWithMultiParam));
	}

	/**
	 * Test de la méthode process()
	 */
	@Test
	public final void testProcess1() {
		IFunction func;

		parser.setCurrentFunction(null);
		assertTrue(module.process(function));
		func = parser.getCurrentFunction();
		assertNotNull(func);
		assertEquals(func.getName(), "function");
	}

	/**
	 * Test de la méthode process()
	 */
	@Test
	public final void testProcess2() {
		IFunction func;

		parser.setCurrentFunction(null);
		assertTrue(module.process(functionWithParam));
		func = parser.getCurrentFunction();
		assertNotNull(func);
		assertEquals(func.getName(), "function");
		assertEquals(func.getParameters().size(), 1);
		assertEquals(func.getParameters().get(0), "param");
	}

	/**
	 * Test de la méthode process()
	 */
	@Test
	public final void testProcess3() {
		IFunction func;

		parser.setCurrentFunction(null);
		assertTrue(module.process(functionWithMultiParam));
		func = parser.getCurrentFunction();
		assertNotNull(func);
		assertEquals(func.getName(), "function");
		assertEquals(func.getParameters().size(), 3);
		assertEquals(func.getParameters().get(0), "param1");
		assertEquals(func.getParameters().get(1), "param2");
		assertEquals(func.getParameters().get(2), "param3");
	}
}
