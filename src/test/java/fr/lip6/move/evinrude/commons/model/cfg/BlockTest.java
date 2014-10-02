package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;

import java.security.InvalidParameterException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jean-Baptiste Voron
 *
 */
public class BlockTest {
	private IBlock bloc11, bloc12, bloc21, bloc31;
	private IFunction func1, func2, func3;
	/**
	 * @throws Exception En cas de problèmes
	 */
	@Before
	public final void setUp() throws Exception {
		IApplication app = new Application("fooApp");
		IdGenerator idGenerator = new IdGenerator();
		ICfg fooCfg = new Cfg(app, "foo.cfg", idGenerator);
		ICfg bazCfg = new Cfg(app, "bar.cfg", idGenerator);
		app.addCfg(fooCfg);
		app.addCfg(bazCfg);

		func1 = fooCfg.createFunction(1, "fooFunction");
		func2 = fooCfg.createFunction(2, "barFunction");
		func3 = bazCfg.createFunction(3, "bazFunction");

		bloc11 = func1.createBlock("foo_0", 2);
		bloc12 = func1.createBlock("foo_1", 4);
		bloc21 = func2.createBlock("foo_2");
		bloc31 = func3.createBlock("bar_0", 8);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getId()}.
	 */
	@Test
	public final void testGetId() {
		assertEquals("foo_0", bloc11.getId());
		assertEquals("foo_1", bloc12.getId());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getStartingLine()}.
	 */
	@Test
	public final void testGetStartingLine() {
		assertEquals(2, bloc11.getStartingLine());
		assertEquals(4, bloc12.getStartingLine());
		assertEquals(-1, bloc21.getStartingLine());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#setStartingLine(int)}.
	 */
	@Test
	public final void testSetStartingLine() {
		// Sinon, comportement normal
		assertEquals(-1, bloc21.getStartingLine());
		bloc21.setStartingLine(2);
		assertEquals(2, bloc21.getStartingLine());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#setStartingLine(int)}.
	 */
	@Test(expected = InvalidParameterException.class)
	public final void testSetStartingLine2() {
		// Pas de modification attendue si la starting line existe déjà
		assertEquals(2, bloc11.getStartingLine());
		bloc11.setStartingLine(2);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getFunction()}.
	 */
	@Test
	public final void testGetParent() {
		assertEquals(func1, bloc11.getFunction());
		assertEquals(func1, bloc12.getFunction());
		assertEquals(func2, bloc21.getFunction());
		assertEquals(func3, bloc31.getFunction());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#addSuccessor(java.lang.String)}.
	 */
	@Test
	public final void testAddSuccessor() {
		assertTrue(bloc11.getSuccessors().size() == 0);
		bloc11.addSuccessor(bloc12.getId());
		assertTrue(bloc11.getSuccessors().size() == 1);
		assertEquals(bloc12, bloc11.getSuccessors().iterator().next());

		// Ajout du même successeur
		bloc11.addSuccessor(bloc12.getId());
		assertTrue(bloc11.getSuccessors().size() == 1);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#addPredecessor(java.lang.String)}.
	 */
	@Test
	public final void testAddPredecessor() {
		assertTrue(bloc11.getPredecessors().size() == 0);
		bloc11.addPredecessor(bloc12.getId());
		assertTrue(bloc11.getPredecessors().size() == 1);

		// Ajout du même predecesseur
		bloc11.addSuccessor(bloc12.getId());
		assertTrue(bloc11.getPredecessors().size() == 1);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#addAssignement(fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement)}.
	 */
	@Test
	public final void testAddAssignement() {
		assertNull(bloc11.getInstructions().get(4));
		IAssignement assign = new Assignement(44, 4, bloc11);
		bloc11.addAssignement(assign);
		assertEquals(1, bloc11.getInstructions().size());
		assertEquals(assign, bloc11.getInstructions().get(44));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#addAssignement(fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement)}.
	 */
	@Test(expected = InvalidParameterException.class)
	public final void testAddAssignement2() {
		assertNull(bloc11.getInstructions().get(4));
		IAssignement assign = new Assignement(44, 4, bloc11);
		bloc11.addAssignement(assign);

		IAssignement assign2 = new Assignement(44, 4, bloc12);
		bloc11.addAssignement(assign2);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#addCall(java.lang.Integer, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testAddCall() {
		assertNull(bloc11.getInstructions().get(32));
		ICall call = new Call(44, 32, "barFunction", bloc11);
		bloc11.addCall(call);

		ICall call2 = (ICall) bloc11.getInstructions().get(44);
		assertNotNull(call2);
		assertEquals(call2.getBlock(), bloc11);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#addConditionnal(fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition)}.
	 */
	@Test
	public final void testAddConditionnal() {
		assertEquals(null, bloc11.getCondition());
		ICondition cond = new Condition(99, 90, "<", bloc11);
		bloc11.addConditionnal(cond);
		assertEquals(cond, bloc11.getCondition());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#addConditionnal(fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition)}.
	 */
	@Test(expected = InvalidParameterException.class)
	public final void testAddConditionnal2() {
		assertEquals(null, bloc11.getCondition());
		ICondition cond = new Condition(99, 90, "<", bloc11);
		bloc11.addConditionnal(cond);

		ICondition cond2 = new Condition(111, 100, ">", bloc11);
		bloc11.addConditionnal(cond2);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getPredecessors()}.
	 */
	@Test
	public final void testGetPredecessors() {
		assertTrue(bloc11.getPredecessors().size() == 0);
		bloc11.addPredecessor(bloc12.getId());
		assertTrue(bloc11.getPredecessors().size() == 1);
		assertEquals(bloc12, bloc11.getPredecessors().iterator().next());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getSuccessors()}.
	 */
	@Test
	public final void testGetSuccessors() {
		assertTrue(bloc11.getSuccessors().size() == 0);
		bloc11.addSuccessor(bloc12.getId());
		assertTrue(bloc11.getSuccessors().size() == 1);
		assertEquals(bloc12, bloc11.getSuccessors().iterator().next());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getLocalFunctionCalls()}.
	 */
	@Test
	public final void testGetFunctionCalls() {
		assertEquals(0, bloc11.getLocalFunctionCalls().size());
		bloc11.addCall(new Call(772, 72, "bazFunction", bloc11));
		ICall call = (ICall) bloc11.getInstructions().get(772);
		assertNotNull(call);
		assertEquals(1, bloc11.getLocalFunctionCalls().size());

		bloc11.addCall(new Call(773, 73, "barFunction", bloc11));
		ICall call2 = (ICall) bloc11.getInstructions().get(773);
		assertNotNull(call2);
		assertEquals(2, bloc11.getLocalFunctionCalls().size());

		assertEquals(call.getFunctionName(), bloc11.getLocalFunctionCalls().get(772).getName());
		assertEquals(call2.getFunctionName(), bloc11.getLocalFunctionCalls().get(773).getName());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getLibraryCalls()}.
	 */
	@Test
	public final void testGetLibraryCalls() {
		assertEquals(0, bloc11.getLibraryCalls().size());
		bloc11.addCall(new Call(332, 32, "printf", bloc11));

		ICall call = (ICall) bloc11.getInstructions().get(332);
		assertNotNull(call);
		assertEquals(1, bloc11.getLibraryCalls().size());
		assertEquals(call, bloc11.getLibraryCalls().get(332));

		bloc11.addCall(new Call(333, 33, "fopen", bloc11));
		ICall call2 = (ICall) bloc11.getInstructions().get(333);
		assertNotNull(call2);
		assertEquals(2, bloc11.getLibraryCalls().size());
		assertEquals(call2, bloc11.getLibraryCalls().get(333));

		bloc11.addCall(new Call(773, 73, "barFunction", bloc11));
		ICall call3 = (ICall) bloc11.getInstructions().get(773);
		assertNotNull(call3);
		assertEquals(2, bloc11.getLibraryCalls().size());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getInstructions()}.
	 */
	@Test
	public final void testGetInstructions() {
		assertEquals(0, bloc11.getInstructions().size());
		IAssignement assign = new Assignement(444, 44, bloc11);
		bloc11.addAssignement(assign);
		assertEquals(1, bloc11.getInstructions().size());
		assertEquals(assign, bloc11.getInstructions().get(444));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Block#getCondition()}.
	 */
	@Test
	public final void testGetCondition() {
		assertEquals(null, bloc11.getCondition());
		ICondition cond = new Condition(990, 90, "<", bloc11);
		bloc11.addConditionnal(cond);
		assertEquals(cond, bloc11.getCondition());
	}

}
