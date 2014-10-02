package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IOperand;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IVariable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Jean-Baptiste Voron
 *
 */
public class AssignementTest {
	private IAssignement assign;

	/**
	 * @throws Exception En cas de problème...
	 */
	@Before
	public final void setUp() throws Exception {
		assign = new Assignement(1, 1, null);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Assignement#getLeft()}.
	 */
	@Test
	public final void testGetLeft() {
		assertNull(assign.getLeft());

		IVariable var = new Variable("foo", "reg");
		assign.setLeft(var);
		assertEquals(var, assign.getLeft());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Assignement#getRight()}.
	 */
	@Test
	public final void testGetRight() {
		assertNull(assign.getRight());

		IOperand var = new Constant("foo");
		assign.setRight(var);
		assertEquals(var, assign.getRight());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Assignement#setLeft(fr.lip6.move.evinrude.commons.model.cfg.interfaces.IVariable)}.
	 */
	@Test
	public final void testSetLeft() {
		assertNull(assign.getLeft());

		IVariable var = new Variable("foo", "reg");
		assign.setLeft(var);
		assertEquals(var, assign.getLeft());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.cfg.Assignement#setRight(fr.lip6.move.evinrude.commons.model.cfg.interfaces.IOperand)}.
	 */
	@Test
	public final void testSetRight() {
		assertNull(assign.getRight());

		IOperand var = new Constant("foo");
		assign.setRight(var);
		assertEquals(var, assign.getRight());
	}
}
