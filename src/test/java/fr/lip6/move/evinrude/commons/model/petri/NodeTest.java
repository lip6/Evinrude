package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.builder.Builder;
import fr.lip6.move.evinrude.builder.Flattener;
import fr.lip6.move.evinrude.commons.model.cfg.Application;
import fr.lip6.move.evinrude.commons.model.cfg.Cfg;
import fr.lip6.move.evinrude.commons.model.cfg.Executable;
import fr.lip6.move.evinrude.commons.model.cfg.Function;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;
import fr.lip6.move.evinrude.main.Browser;
import fr.lip6.move.evinrude.optimizer.IReduction;
import fr.lip6.move.evinrude.optimizer.Reducer;
import fr.lip6.move.evinrude.parser.Parser;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la classe {@link Node}
 */
public class NodeTest {

	private IModel emptyModel;
	private IModel callsModel;

	/**
	 * @throws java.lang.Exception en cas d'erreur
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Logger log = Logger.getLogger("fr.lip6.move.evinrude");
		log.setLevel(Level.OFF);

		System.setProperty("evinrude.benchs.host", "izanami.rsr.lip6.fr");
		System.setProperty("evinrude.benchs.port", "7021");
		System.setProperty("evinrude.benchs.user", "anonymous");
		System.setProperty("evinrude.benchs.password", "");
		System.setProperty("evinrude.benchs.path", "/pub/evinrude-benchs");
		System.setProperty("evinrude.outputdir", "testOutputs");
	}

	/**
	 * @throws java.lang.Exception en cas d'erreur
	 */
	@Before
	public final void setUp() throws Exception {
		// Model vide pour les tests de création
		IApplication app = new Application("test");
		ICfg cfg = new Cfg(app, "test.cfg", new IdGenerator());
		IExecutable exe = new Executable(app, new Function(1, "exe", cfg));
		emptyModel = new Model(exe);

		// Model du programme <code>calls</code>
		new File(System.getProperty("evinrude.outputdir") + "/toys_calls").mkdirs();
		app = new Application("toys_calls");
		Parser parser = new Parser();
		app.addCfg(parser.extractCFG(app, Browser.findBenchCfg("toys_calls").next()));
		IExecutable executable = app.getExecutables().get(0);
		new Builder(app).process(executable);
		new Reducer(app).reduc(executable, IReduction.HIERARCHICAL);
		callsModel = executable.getModel();
		new Flattener().flatten(executable);
		new Reducer(app).reduc(executable, IReduction.FLAT);
	}

//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#Node(fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel, java.lang.String, int)}.
//	 */
//	@Test
//	public final void testNodeISubModelStringInt() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#getSubModelContainer()}.
//	 */
//	@Test
//	public final void testGetSubModelContainer() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#getName()}.
//	 */
//	@Test
//	public final void testGetName() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#getIncomingArcs()}.
//	 */
//	@Test
//	public final void testGetIncomingArcs() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#getOutgoingArcs()}.
//	 */
//	@Test
//	public final void testGetOutgoingArcs() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#getNextNodes()}.
//	 */
//	@Test
//	public final void testGetNextNodes() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#getPreviousNodes()}.
//	 */
//	@Test
//	public final void testGetPreviousNodes() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#setType(int)}.
//	 */
//	@Test
//	public final void testSetType() {
//		fail("Not yet implemented"); // TODO
//	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#isTyped(int)}.
	 */
	@Test
	public final void testIsTyped() {
		Node node = (Node) callsModel.getNode("0_1_EXIT");
		assertTrue(node.isTyped(IPlace.FUNCTIONEXIT));
		assertFalse(node.isTyped(IPlace.FUNCTIONENTRY));
		assertFalse(node.isTyped(IPlace.NORMAL));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#isNotTyped(int)}.
	 */
	@Test
	public final void testIsNotTyped() {
		Node node = (Node) callsModel.getNode("0_1_EXIT");
		assertFalse(node.isNotTyped(IPlace.FUNCTIONEXIT));
		assertTrue(node.isNotTyped(IPlace.FUNCTIONENTRY));
		assertTrue(node.isNotTyped(IPlace.NORMAL));

		node = (Node) emptyModel.createPlace("p", IPlace.FUNCTIONENTRY | IPlace.VIRTUAL | IPlace.INPUT);
		assertFalse(node.isNotTyped(IPlace.FUNCTIONENTRY));
		assertFalse(node.isNotTyped(IPlace.VIRTUAL));
		assertFalse(node.isNotTyped(IPlace.VIRTUAL | IPlace.INPUT));
		assertFalse(node.isNotTyped(IPlace.FUNCTIONENTRY | IPlace.VIRTUAL | IPlace.INPUT));
		assertFalse(node.isNotTyped(IPlace.FUNCTIONENTRY | IPlace.VIRTUAL | IPlace.INPUT | IPlace.FUNCTIONEXIT));
		assertTrue(node.isNotTyped(IPlace.FUNCTIONEXIT));
		assertTrue(node.isNotTyped(IPlace.OUTPUT | IPlace.RESOURCE));
	}

//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#getType()}.
//	 */
//	@Test
//	public final void testGetType() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#isPlace()}.
//	 */
//	@Test
//	public final void testIsPlace() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#isTransition()}.
//	 */
//	@Test
//	public final void testIsTransition() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#equals(java.lang.Object)}.
//	 */
//	@Test
//	public final void testEqualsObject() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Node#toString()}.
//	 */
//	@Test
//	public final void testToString() {
//		fail("Not yet implemented"); // TODO
//	}

}
