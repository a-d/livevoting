package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import de.fuberlin.livevoting.server.action.kind.ActionSupportSelection;
import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;


public class SelectionAnswerAction extends ActionSupportSelection {
	private List<Answer> availableAnswers;
	private SimpleObjectDAOImpl<Answer> answerDao = new AnswerDAOImpl();
	private Long defaultAnswerId;

	private static final long serialVersionUID = 6721064966173343669L;

	public String execute() throws Exception {
		availableAnswers = answerDao.listAll();
		return SUCCESS;
	}

	public List<Answer> getAvailableAnswers() {
		return availableAnswers;
	}
	

	public void setDefaultAnswerId(Long id) {
		this.defaultAnswerId = id;
	}
	
	public Long getDefaultAnswerId() {
		return defaultAnswerId;
	}
}
