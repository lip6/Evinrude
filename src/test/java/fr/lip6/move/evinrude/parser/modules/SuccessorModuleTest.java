package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.parser.IModule;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test de la classe {@link SuccessorModule}
 */
public class SuccessorModuleTest {
	private IModule module;
	private IBlock block;
	private MockParser parser;

	private String succ1 = "  # SUCC: EXIT";
	private String succ2 = "  # SUCC: 3 (true) 5 (false)";

	/**
	 * Initialisation du module testé
	 */
	@Before
	public final void setUp() {
		parser = new MockParser();
		block = parser.getCfg().createFunction(1, "function").createBlock("1");
		parser.setCurrentBlock(block);
		module = new SuccessorModule(parser);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.SuccessorModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch1() {
		assertTrue(module.match(succ1));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.SuccessorModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch2() {
		assertTrue(module.match(succ2));
	}

	/**
	 * @param blocks liste de blocks
	 * @param blockId id à rechercher
	 * @return <code>true</code> si la liste contient un bloc avec cette id <code>false</code> sinon.
	 */
	private boolean containsBlockId(Collection<IBlock> blocks, String blockId) {
		for (IBlock block : blocks) {
			if (block.getId().equals(blockId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.SuccessorModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess1() {
		assertTrue(module.process(succ1));
		assertEquals(block.getSuccessors().size(), 1);
		assertTrue(containsBlockId(block.getSuccessors(), "EXIT"));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.SuccessorModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess2() {
		parser.setCurrentBlock(block);
		assertTrue(module.process(succ2));
		assertEquals(block.getSuccessors().size(), 2);
		assertTrue(containsBlockId(block.getSuccessors(), "3"));
		assertTrue(containsBlockId(block.getSuccessors(), "5"));
	}
}
