package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.parser.IModule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la classe {@link EmptyBlockModule}
 */
public class EmptyBlockModuleTest {
	private IModule module;
	private MockParser parser;

	private String block = "  # BLOCK 2";

	/**
	 * Initialisation du module testé
	 */
	@Before
	public final void setUp() {
		parser = new MockParser();
		parser.setCurrentFunction(parser.getCfg().createFunction(1, "function"));
		module = new EmptyBlockModule(parser);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.EmptyBlockModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch() {
		assertTrue(module.match(block));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.EmptyBlockModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess() {
		assertTrue(module.process(block));
		assertNotNull(parser.getCurrentBlock());
		assertNotNull(parser.getCurrentFunction().getBlock("2"));
	}

}
