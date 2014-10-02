package fr.lip6.move.evinrude.commons.model.petri;


import fr.lip6.move.evinrude.builder.Builder;
import fr.lip6.move.evinrude.builder.Flattener;
import fr.lip6.move.evinrude.builder.perspectives.struct.StructuralPerspective;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.Application;
import fr.lip6.move.evinrude.commons.model.cfg.Cfg;
import fr.lip6.move.evinrude.commons.model.cfg.Executable;
import fr.lip6.move.evinrude.commons.model.cfg.Function;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;
import fr.lip6.move.evinrude.main.Browser;
import fr.lip6.move.evinrude.optimizer.IReduction;
import fr.lip6.move.evinrude.optimizer.Reducer;
import fr.lip6.move.evinrude.parser.Parser;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la classe {@link SubModel}
 */
public class SubModelTest {
	private SubModel callsModel;
	private SubModel emptyModel;

    /**
     * Initialisation du logger et des propriétés
     */
    @BeforeClass
    public static void setUpBeforeClass() {
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
	 * @throws java.lang.Exception En cas d'erreur
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
		callsModel = (SubModel) executable.getModel();
		new Flattener().flatten(executable);
		new Reducer(app).reduc(executable, IReduction.FLAT);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		assertEquals(emptyModel.hashCode(), emptyModel.hashCode());
		assertFalse(emptyModel.hashCode() == callsModel.hashCode());
		IPlace place = emptyModel.createPlace("p1", IPlace.NORMAL);
		try {
			ISubModel test1 = place.createSubModel(11);
			ISubModel test2 = place.getSubModel(11);
			assertEquals(test1.hashCode(), test2.hashCode());
		} catch (EvinrudeException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#createArc(fr.lip6.move.evinrude.commons.model.petri.interfaces.INode, fr.lip6.move.evinrude.commons.model.petri.interfaces.INode, int)}.
	 */
	@Test
	public final void testCreateArcINodeINodeInt() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		IPlace p2 = emptyModel.createPlace("p2", IPlace.NORMAL);
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);

		IArc a = emptyModel.createArc(p1, t1, IArc.NORMAL);
		assertEquals(a.getType(), IArc.NORMAL);
		assertEquals(p1.getOutgoingArcs().size(), 1);
		assertEquals(t1.getIncomingArcs().size(), 1);

		try {
			emptyModel.createArc(p1, p2, IArc.NORMAL);
		} catch (EvinrudeException e) {
			try {
				emptyModel.createArc(t1, t2, IArc.NORMAL);
			} catch (EvinrudeException e1) {
				return;
			}
		}
		fail();
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#createArc(fr.lip6.move.evinrude.commons.model.petri.interfaces.INode, fr.lip6.move.evinrude.commons.model.petri.interfaces.INode)}.
	 */
	@Test
	public final void testCreateArcINodeINode() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		IPlace p2 = emptyModel.createPlace("p2", IPlace.NORMAL);
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);

		emptyModel.createArc(p1, t1);
		assertEquals(p1.getOutgoingArcs().size(), 1);
		assertEquals(t1.getIncomingArcs().size(), 1);

		try {
			emptyModel.createArc(p1, p2);
		} catch (EvinrudeException e) {
			try {
				emptyModel.createArc(t1, t2);
			} catch (EvinrudeException e1) {
				return;
			}
		}
		fail();
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#createPlace(java.lang.String, int)}.
	 */
	@Test
	public final void testCreatePlaceStringInt() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		assertNotNull(p1);
		assertEquals(p1.getName(), "p1");
		assertTrue(p1.isTyped(IPlace.NORMAL));

		try {
			emptyModel.createPlace("p1", IPlace.INPUT);
		} catch (EvinrudeException e) {
			return;
		}
		fail();
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#createPlace(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testCreatePlaceStringString() {
		IPlace p1 = emptyModel.createPlace("p1_ref", "p1");
		assertNotNull(p1);
		assertEquals(p1.getName(), "p1_ref");
		assertEquals(p1.getTargetName(), "p1");
		assertTrue(p1.isTyped(IPlace.VIRTUAL));

		try {
			emptyModel.createPlace("p1_ref", "toto");
		} catch (EvinrudeException e) {
			return;
		}
		fail();
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#createPlace(fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace)}.
	 */
	@Test
	public final void testCreatePlaceIPlace() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		emptyModel.removeNode(p1);
		IPlace copyP1 = emptyModel.createPlace(p1);
		assertNotNull(copyP1);
		assertEquals(p1, copyP1);

		try {
			emptyModel.createPlace(p1);
		} catch (EvinrudeException e) {
			return;
		}
		fail();
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#createTransition(java.lang.String, int)}.
	 */
	@Test
	public final void testCreateTransitionStringInt() {
		ITransition t1 = emptyModel.createTransition("t1", ITransition.FUNCTIONCALL);
		assertNotNull(t1);
		assertEquals(t1.getName(), "t1");
		assertTrue(t1.isTyped(ITransition.FUNCTIONCALL));

		try {
			emptyModel.createTransition("t1", ITransition.FUNCTIONCALL);
		} catch (EvinrudeException e) {
			return;
		}
		fail();
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#createTransition(fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition)}.
	 */
	@Test
	public final void testCreateTransitionITransition() {
		ITransition t1 = emptyModel.createTransition("t1", ITransition.FUNCTIONCALL);
		emptyModel.removeNode(t1);
		ITransition copyT1 = emptyModel.createTransition(t1);
		assertNotNull(copyT1);
		assertEquals(t1, copyT1);

		try {
			emptyModel.createTransition(t1);
		} catch (EvinrudeException e) {
			return;
		}
		fail();
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getArcs()}.
	 */
	@Test
	public final void testGetArcs() {
		assertEquals(emptyModel.getArcs().size(), 0);

		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);
		ITransition t3 = emptyModel.createTransition("t3", ITransition.STRUCTURAL);

		Set<IArc> arcs = new HashSet<IArc>();
		arcs.add(emptyModel.createArc(p1, t1));
		arcs.add(emptyModel.createArc(p1, t2));
		arcs.add(emptyModel.createArc(p1, t3));

		assertNotNull(emptyModel.getArcs());
		assertEquals(emptyModel.getArcs().size(), 3);
		for (IArc arc : emptyModel.getArcs()) {
			assertNotNull(arc);
			assertTrue(arcs.contains(arc));
		}

		assertEquals(38, callsModel.getArcs().size());
	}

	/**
	 * Test method for {@link SubModel#getFirstArc(fr.lip6.move.evinrude.commons.model.petri.interfaces.INode, fr.lip6.move.evinrude.commons.model.petri.interfaces.INode)}
	 */
	@Test
	public final void testGetFirstArc() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);
		ITransition t3 = emptyModel.createTransition("t3", ITransition.STRUCTURAL);

		IArc a1 = emptyModel.createArc(p1, t1);
		IArc a3 = emptyModel.createArc(p1, t1, IArc.INHIBITOR);
		IArc a2 = emptyModel.createArc(p1, t2);

		assertNull(emptyModel.getFirstArc(p1, t3));
		assertNull(emptyModel.getFirstArc(t1, p1));
		assertTrue(new HashSet<IArc>(Arrays.asList(a1, a3)).contains(emptyModel.getFirstArc(p1, t1)));
		assertEquals(emptyModel.getFirstArc(p1, t2), a2);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getArc(fr.lip6.move.evinrude.commons.model.petri.interfaces.INode, fr.lip6.move.evinrude.commons.model.petri.interfaces.INode)}.
	 */
	@Test
	public final void testGetArcsINodeINode() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);
		ITransition t3 = emptyModel.createTransition("t3", ITransition.STRUCTURAL);

		IArc a1 = emptyModel.createArc(p1, t1);
		IArc a3 = emptyModel.createArc(p1, t1, IArc.INHIBITOR);
		IArc a2 = emptyModel.createArc(p1, t2);

		assertTrue(emptyModel.getArcs(p1, t3).isEmpty());
		assertTrue(emptyModel.getArcs(t1, p1).isEmpty());
		assertEquals(emptyModel.getArcs(p1, t1), new HashSet<IArc>(Arrays.asList(a1, a3)));
		assertEquals(emptyModel.getArcs(p1, t2), Collections.singleton(a2));

		assertFalse(callsModel.getArcs(callsModel.getNode("0_1_EXIT"), callsModel.getNode("return_0_2_2_72_funcA")).isEmpty());
		assertFalse(callsModel.getArcs(callsModel.getNode("0_0_ENTRY"), callsModel.getNode("sys_0_0_2_10_printf")).isEmpty());
		assertTrue(callsModel.getArcs(callsModel.getNode("sys_0_2_2_71___builtin_puts"), callsModel.getNode("0_2_ENTRY")).isEmpty());
		assertTrue(callsModel.getArcs(callsModel.getNode("0_2_2_72_funcA_path"), callsModel.getNode("call_0_2_2_72_funcA")).isEmpty());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getTopModel()}.
	 */
	@Test
	public final void testGetTopModel() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		SubModel sub1 = (SubModel) p1.createSubModel(1);
		IPlace p2 = sub1.createPlace("p2", IPlace.NORMAL);
		SubModel sub2 = (SubModel) p2.createSubModel(1);

		assertSame(emptyModel, emptyModel.getTopModel());
		assertNotSame(sub1, sub1.getTopModel());
		assertSame(emptyModel, sub1.getTopModel());
		assertNotSame(sub2, sub2.getTopModel());
		assertSame(emptyModel, sub2.getTopModel());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getParentPlace()}.
	 */
	@Test
	public final void testGetParentPlace() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		SubModel sub1 = (SubModel) p1.createSubModel(1);
		IPlace p2 = sub1.createPlace("p2", IPlace.NORMAL);
		SubModel sub2 = (SubModel) p2.createSubModel(1);

		assertNull(emptyModel.getParentPlace());
		assertSame(p1, sub1.getParentPlace());
		assertSame(p2, sub2.getParentPlace());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getPlace(java.lang.String)}.
	 */
	@Test
	public final void testGetPlace() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		IPlace p2 = emptyModel.createPlace("p2", IPlace.NORMAL);

		assertEquals(p1, emptyModel.getPlace("p1"));
		assertEquals(p2, emptyModel.getPlace("p2"));
		assertNull(emptyModel.getPlace("p3"));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getPlaces()}.
	 */
	@Test
	public final void testGetPlaces() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		IPlace p2 = emptyModel.createPlace("p2", IPlace.NORMAL);
		IPlace p3 = emptyModel.createPlace("p3", IPlace.NORMAL);

		assertEquals(3, emptyModel.getPlaces().size());
		assertTrue(emptyModel.getPlaces().contains(p1));
		assertTrue(emptyModel.getPlaces().contains(p2));
		assertTrue(emptyModel.getPlaces().contains(p3));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getTransition(java.lang.String)}.
	 */
	@Test
	public final void testGetTransition() {
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);

		assertEquals(t1, emptyModel.getTransition("t1"));
		assertEquals(t2, emptyModel.getTransition("t2"));
		assertNull(emptyModel.getTransition("t3"));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getTransitions()}.
	 */
	@Test
	public final void testGetTransitions() {
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);
		ITransition t3 = emptyModel.createTransition("t3", ITransition.STRUCTURAL);

		assertEquals(3, emptyModel.getTransitions().size());
		assertTrue(emptyModel.getTransitions().contains(t1));
		assertTrue(emptyModel.getTransitions().contains(t2));
		assertTrue(emptyModel.getTransitions().contains(t3));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getNode(java.lang.String)}.
	 */
	@Test
	public final void testGetNode() {
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);

		assertEquals(t1, emptyModel.getNode("t1"));
		assertEquals(t2, emptyModel.getNode("t2"));
		assertEquals(p1, emptyModel.getNode("p1"));
		assertNull(emptyModel.getNode("t3"));

		assertNotNull(callsModel.getNode(StructuralPerspective.GLOBAL_EXIT));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getNodes()}.
	 */
	@Test
	public final void testGetNodes() {
		ITransition t1 = emptyModel.createTransition("t1", ITransition.STRUCTURAL);
		ITransition t2 = emptyModel.createTransition("t2", ITransition.STRUCTURAL);
		IPlace p1 = emptyModel.createPlace("p1", IPlace.NORMAL);
		IPlace p2 = p1.createSubModel(1).createPlace("p2", IPlace.NORMAL);

		assertTrue(emptyModel.getNodes().contains(t1));
		assertTrue(emptyModel.getNodes().contains(t2));
		assertTrue(emptyModel.getNodes().contains(p1));
		assertFalse(emptyModel.getNodes().contains(p2));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getInstructionNumber()}.
	 */
	@Test
	public final void testGetInstructionNumber() {
		assertEquals(emptyModel.getInstructionNumber(), 0);
		assertEquals(emptyModel.createPlace("p", IPlace.NORMAL).createSubModel(11).getInstructionNumber(), 11);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getInputPlace()}.
	 */
	@Test
	public final void testGetInputPlace() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.INPUT);
		emptyModel.createPlace("p2", IPlace.NORMAL);

		assertEquals(p1, emptyModel.getInputPlace());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#getOutputPlaces()}.
	 */
	@Test
	public final void testGetOutputPlaces() {
		IPlace p1 = emptyModel.createPlace("p1", IPlace.OUTPUT);
		IPlace p2 = emptyModel.createPlace("p2", IPlace.OUTPUT);
		IPlace p3 = emptyModel.createPlace("p3", IPlace.OUTPUT);
		emptyModel.createPlace("p4", IPlace.NORMAL);

		assertEquals(new HashSet<IPlace>(Arrays.asList(p1, p2, p3)), new HashSet<IPlace>(emptyModel.getOutputPlaces()));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#integrateSubModel(fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel)}.
	 */
	@Test
	public final void testIntegrateSubModel() {
		int arcs = callsModel.getArcs().size();
		int places = callsModel.getPlaces().size();
		int transitions = callsModel.getTransitions().size();
		emptyModel.integrateSubModel(callsModel);

		assertEquals(arcs, emptyModel.getArcs().size());
		assertEquals(places, emptyModel.getPlaces().size());
		assertEquals(transitions, emptyModel.getTransitions().size());
		assertEquals(0, callsModel.getArcs().size());
		assertEquals(0, callsModel.getPlaces().size());
		assertEquals(0, callsModel.getTransitions().size());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#removeNode(fr.lip6.move.evinrude.commons.model.petri.interfaces.INode)}.
	 */
	@Test
	public final void testRemoveNode() {
		callsModel.removeNode(callsModel.getNode(StructuralPerspective.GLOBAL_EXIT));
		assertEquals(37, callsModel.getArcs().size());
		assertEquals(16, callsModel.getPlaces().size());
		assertEquals(14, callsModel.getTransitions().size());
		callsModel.removeNode(callsModel.getNode("0_2_ENTRY"));
		assertEquals(36, callsModel.getArcs().size());
		assertEquals(15, callsModel.getPlaces().size());
		assertEquals(14, callsModel.getTransitions().size());
		callsModel.removeNode(callsModel.getNode("return_0_2_2_72_funcA"));
		assertEquals(31, callsModel.getArcs().size());
		assertEquals(15, callsModel.getPlaces().size());
		assertEquals(13, callsModel.getTransitions().size());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#removeArc(fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc)}.
	 */
	@Test
	public final void testRemoveArc() {
		INode source = callsModel.getNode("0_1_EXIT");
		INode target = callsModel.getNode("return_0_2_2_72_funcA");
		IArc arc = callsModel.getFirstArc(source, target);
		assertNotNull(callsModel.removeArc(arc));
		assertEquals(37, callsModel.getArcs().size());
		assertFalse(source.getOutgoingArcs().contains(arc));
		assertFalse(target.getIncomingArcs().contains(arc));
		assertNull(callsModel.removeArc(arc));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		assertTrue(emptyModel.equals(emptyModel));
		assertFalse(emptyModel.equals(callsModel));
		assertFalse(callsModel.equals(emptyModel));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.SubModel#toString()}.
	 */
	@Test
	public final void testToString() {
		assertEquals("SubModel(0, linked to [null])", callsModel.toString());
		IPlace p = emptyModel.createPlace("p", IPlace.NORMAL);
		ISubModel sub = p.createSubModel(1);
		assertEquals("SubModel(1, linked to [" + p + "])", sub.toString());
	}

}
