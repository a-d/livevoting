package de.fuberlin.livevoting.server.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportCRUD;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Question;


@Results( { @Result(name = "error", location = "questions.jsp") })
public class EditQuestionAction extends ActionSupportCRUD<Question> implements ModelDriven<Question> {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(EditQuestionAction.class);

	private SimpleObjectDAOImpl<Question> questionDao = new QuestionDAOImpl();
	private Question question = new Question();

	@Override
	protected void updatePrepare(Question obj, Question oldObj) throws Exception {
		super.updatePrepare(obj, oldObj);
		
		obj.setPicture(oldObj.getPicture());
	}
	
	
	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	protected Log getLog() { return log; }
	public boolean validateLogin() { return false; } // TODO
	public Question getModel() { return question; }
	public void setModel(Question question) { this.question = question; }
	protected SimpleObjectDAO<Question> getModelDao() { return questionDao; }
}
