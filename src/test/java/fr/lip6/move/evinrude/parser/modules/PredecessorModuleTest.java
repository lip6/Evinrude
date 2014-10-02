package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.parser.IModule;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test de la classe {@link PredecessorModule}
 */
public class PredecessorModuleTest {
	private IModule module;
	private MockParser parser;

	private String pred1 = "  # PRED: ENTRY (fallthru)";
	private String pred2 = "  # PRED: 2 (fallthru) 3 (fallthru)";

	/**
	 * Initialisation du module testé
	 */
	@Before
	public final void setUp() {
		parser = new MockParser();
		parser.setCurrentBlock(parser.getCfg().createFunction(1, "function").createBlock("1"));
		module = new PredecessorModule(parser);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.PredecessorModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch1() {
		assertTrue(module.match(pred1));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.PredecessorModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch2() {
		assertTrue(module.match(pred2));
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
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.PredecessorModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess1() {
		assertTrue(module.process(pred1));
		assertEquals(parser.getCurrentBlock().getPredecessors().size(), 1);
		assertTrue(containsBlockId(parser.getCurrentBlock().getPredecessors(), "ENTRY"));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.PredecessorModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess2() {
		assertTrue(module.process(pred2));
		assertEquals(parser.getCurrentBlock().getPredecessors().size(), 2);
		assertTrue(containsBlockId(parser.getCurrentBlock().getPredecessors(), "2"));
		assertTrue(containsBlockId(parser.getCurrentBlock().getPredecessors(), "3"));
	}
}
