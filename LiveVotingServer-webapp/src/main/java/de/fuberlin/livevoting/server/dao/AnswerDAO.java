package de.fuberlin.livevoting.server.dao;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.Answer;

public interface AnswerDAO extends SimpleObjectDAO<Answer> {

	Integer getNumberOfVotes(Answer answer);
	
}
