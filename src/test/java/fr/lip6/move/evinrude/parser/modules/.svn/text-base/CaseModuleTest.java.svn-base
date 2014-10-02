package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Condition;
import fr.lip6.move.evinrude.commons.model.cfg.Variable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.parser.IModule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la classe {@link CaseModule}
 */
public class CaseModuleTest {
	private IModule module;
	private MockParser parser;

	private String caseTest = "case 100: goto <L3>;";

	/**
	 * @throws java.lang.Exception En cas d'erreur
	 */
	@Before
	public final void setUp() throws Exception {
		parser = new MockParser();
		IFunction func = parser.getCfg().createFunction(1, "func");
		IBlock block = func.createBlock("2");
		ICondition condition = new Condition(1, 11, "==", block);
		condition.setLeftMember(new Variable("bobo", ""));
		parser.setCurrentCondition(condition);
		module = new CaseModule(parser);
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.CaseModule#match(java.lang.String)}.
	 */
	@Test
	public final void testMatch() {
		assertTrue(module.match(caseTest));
	}

	/**
	 * Test method for {@link fr.lip6.move.evinrude.parser.modules.CaseModule#process(java.lang.String)}.
	 */
	@Test
	public final void testProcess() {
		ICondition condition = parser.getCurrentCondition();
		assertTrue(module.process(caseTest));
		assertNotSame(condition, parser.getCurrentCondition());
		assertNotNull(parser.getCurrentCondition());
		assertNotNull(condition.getRightMember());
		assertNull(parser.getCurrentCondition().getRightMember());
	}

}
