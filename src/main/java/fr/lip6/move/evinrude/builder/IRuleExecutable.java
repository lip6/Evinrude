package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.List;

/**
 * Définition d'une règle traitant un CFG
 */
public interface IRuleExecutable extends IRule {

	/**
	 * @param depenpencies Modèles venant d'une règle parente.
	 * @param executable executable à traiter
	 * @return Modèles à ajouter au modèle global.
	 * @throws EvinrudeException Erreur du modèle
	 */
	List<ISubModel> process(ResultDep depenpencies, IExecutable executable) throws EvinrudeException;

}
