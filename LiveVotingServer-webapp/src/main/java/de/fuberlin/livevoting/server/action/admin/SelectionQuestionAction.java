package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import de.fuberlin.livevoting.server.action.kind.ActionSupportSelection;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Question;


public class SelectionQuestionAction extends ActionSupportSelection {
	private List<Question> availableQuestions;
	private SimpleObjectDAOImpl<Question> questionDao = new QuestionDAOImpl();
	private Long defaultQuestionId;

	private static final long serialVersionUID = 6721064966173343669L;

	public String execute() throws Exception {
		availableQuestions = questionDao.listAll();
		return SUCCESS;
	}

	public List<Question> getAvailableQuestions() {
		return availableQuestions;
	}
	

	public void setDefaultQuestionId(Long id) {
		this.defaultQuestionId = id;
	}
	
	public Long getDefaultQuestionId() {
		return defaultQuestionId;
	}
}
