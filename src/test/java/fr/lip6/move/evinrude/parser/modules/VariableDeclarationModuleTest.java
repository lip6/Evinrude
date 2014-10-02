package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.parser.IModule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la classe {@link VariableDeclarationModule}
 */
public class VariableDeclarationModuleTest {
	private IModule module;
	private MockParser parser;

	private String var1 = "  intD.0 nD.2721;";
	private String var2 = "  charD.1 * * D.2727;";
	private String var3 = "  static charD.1 * classD.2084[4] = {(charD.1 *) \"comment\", (charD.1 *) \"keyword\", (charD.1 *) \"preproc\", (charD.1 *) \"stdfunc\"};";

	/**
	 * Initialisation du module testé
	 */
	@Before
	public final void setUp() {
		parser = new MockParser();
		parser.setCurrentFunction(parser.getCfg().createFunction(1, "function"));
		module = new VariableDeclarationModule(parser);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.VariableDeclarationModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch1() {
		assertTrue(module.match(var1));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.VariableDeclarationModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch2() {
		assertTrue(module.match(var2));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.VariableDeclarationModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch3() {
		assertTrue(module.match(var3));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.VariableDeclarationModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess1() {
		assertTrue(module.process(var1));
		assertEquals(parser.getCurrentFunction().getVariables().size(), 1);
		assertEquals(parser.getCurrentFunction().getVariables().get(0), "n");
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.VariableDeclarationModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess2() {
		assertTrue(module.process(var2));
		assertEquals(parser.getCurrentFunction().getVariables().size(), 1);
		assertEquals(parser.getCurrentFunction().getVariables().get(0), "D.2727");
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.VariableDeclarationModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess3() {
		assertTrue(module.process(var3));
		assertEquals(parser.getCurrentFunction().getVariables().size(), 1);
		assertEquals(parser.getCurrentFunction().getVariables().get(0), "class");
	}
}
