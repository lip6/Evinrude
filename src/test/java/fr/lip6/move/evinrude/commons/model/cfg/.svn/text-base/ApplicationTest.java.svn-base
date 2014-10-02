package fr.lip6.move.evinrude.commons.model.cfg;


import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Test de la classe {@link Application}
 * @author Jean-Baptiste Voron
 */
public class ApplicationTest {
	private static final String NAME = "AppTest";
	private IApplication appTest;
	private IdGenerator idGenerator;

	/**
	 * Initialisation
	 */
	@Before
	public final void setUp() {
		idGenerator = new IdGenerator();
		appTest = new Application(NAME);
	}

	/**
	 * Méthode de test pour {@link Application#getName()}
	 */
	@Test
	public final void testGetName() {
		assertTrue(appTest.getName().equals(NAME));
	}

	/**
	 * Méthode de test pour {@link Application#getCfgs()}
	 */
	@Test
	public final void testGetCfgs() {
		assertTrue(appTest.getCfgs().size() == 0);

		// L'ajout d'un CFG null ne doit rien changer !
		appTest.addCfg(null);
		assertTrue(appTest.getCfgs().size() == 0);

		appTest.addCfg(new Cfg(appTest, "foo.cfg", idGenerator));
		assertTrue(appTest.getCfgs().size() == 1);
	}

	/**
	 * Méthode de test pour {@link Application#addCfg(fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg)}
	 */
	@Test
	public final void testAddCfg() {
		appTest.addCfg(new Cfg(appTest, "foo.cfg", idGenerator));
		assertTrue(appTest.getCfgs().size() == 1);
		appTest.addCfg(new Cfg(appTest, "bar.cfg", idGenerator));
		appTest.addCfg(new Cfg(appTest, "baz.cfg", idGenerator));
		appTest.addCfg(new Cfg(appTest, "doe.cfg", idGenerator));
		appTest.addCfg(null);
		assertTrue(appTest.getCfgs().size() == 4);
	}

	/**
	 * Méthode de test pour {@link Application#getFunction(String)}
	 */
	@Test
	public final void testGetFunction() {
		ICfg cfgTest = new Cfg(appTest, "foo.cfg", idGenerator);
		cfgTest.createFunction(1, "funcFoo");
		cfgTest.createFunction(2, "funcBar");
		appTest.addCfg(cfgTest);

		ICfg cfgTest2 = new Cfg(appTest, "bar.cfg", idGenerator);
		cfgTest2.createFunction(3, "funcFoo");
		cfgTest2.createFunction(4, "funcBaz");
		appTest.addCfg(cfgTest2);

		List<IFunction> ret;
		ret = appTest.getFunction("bar");
		assertTrue(ret.size() == 0);
		ret = appTest.getFunction("funcBar");
		assertTrue(ret.size() == 1);
		ret = appTest.getFunction("funcBaz");
		assertTrue(ret.size() == 1);
		ret = appTest.getFunction("funcFoo");
		assertTrue(ret.size() == 2);
	}

	/**
	 * Méthode de test pour {@link Application#removeGnuFunctions()}
	 */
	@Test
	public final void testRemoveGnuFunctions() {

		// Test de toutes les fonctions GNU
		List<String> gnuFunc = new ArrayList<String>();
		gnuFunc.add("gnu_dev_makedev");
		gnuFunc.add("gnu_dev_major");
		gnuFunc.add("gnu_dev_minor");
		gnuFunc.add("lstat");
		gnuFunc.add("stat");
		gnuFunc.add("mknod");
		gnuFunc.add("fstat");

		ICfg cfgTest = new Cfg(appTest, "foo.cfg", idGenerator);
		int i = 1;
		for (String f : gnuFunc) {
			cfgTest.createFunction(i++, f);
		}

		// Ajout de 2 fonctions test
		cfgTest.createFunction(i++, "foo");
		cfgTest.createFunction(i++, "bar");

		appTest.addCfg(cfgTest);
		appTest.removeGnuFunctions();

		assertTrue(appTest.getFunction("foo").size() == 1);
		assertTrue(appTest.getCfgs().get(0).getFunctions().size() == 2);
	}

}
