package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.model.cfg.Application;
import fr.lip6.move.evinrude.commons.model.cfg.Cfg;
import fr.lip6.move.evinrude.commons.model.cfg.Executable;
import fr.lip6.move.evinrude.commons.model.cfg.Function;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la classe {@link Model}
 */
public class ModelTest {
	private IModel model;
	private IApplication app;
	private ICfg cfg;
	private IdGenerator idGenerator;
	private IExecutable exe;

	/**
	 * @throws java.lang.Exception En cas de problème
	 */
	@Before
	public final void setUp() throws Exception {
		app = new Application("test");
		idGenerator = new IdGenerator();
		cfg = new Cfg(app, "test.cfg", idGenerator);
		exe = new Executable(app, new Function(1, "exe", cfg));
		model = new Model(exe);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Model#enqueueNewCfg(fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg)}.
	 */
	@Test
	public final void testEnqueueNewCfg() {
		ICfg newCfg = new Cfg(app, "newCfg", idGenerator);
		model.nextNewCfg();
		model.enqueueNewCfg(newCfg);
		assertTrue(model.hasNextNewCfg());
		assertSame(newCfg, model.nextNewCfg());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Model#getExecutable()}.
	 */
	@Test
	public final void testGetExecutable() {
		assertSame(model.getExecutable(), exe);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Model#getHead()}.
	 */
	@Test
	public final void testGetHead() {
		assertSame(model.getHead(), cfg);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Model#hasNextNewCfg()}.
	 */
	@Test
	public final void testHasNextNewCfg() {
		assertTrue(model.hasNextNewCfg());
		model.nextNewCfg();
		assertFalse(model.hasNextNewCfg());
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.commons.model.petri.Model#nextNewCfg()}.
	 */
	@Test
	public final void testNextNewCfg() {
		assertSame(cfg, model.nextNewCfg());
	}

}
