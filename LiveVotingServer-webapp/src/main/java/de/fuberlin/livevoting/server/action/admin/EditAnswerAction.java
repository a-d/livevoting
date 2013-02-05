package de.fuberlin.livevoting.server.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportCRUD;
import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;


@Results( { @Result(name = "error", location = "messages.jsp") })
public class EditAnswerAction extends ActionSupportCRUD<Answer> implements ModelDriven<Answer> {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(EditAnswerAction.class);

	private SimpleObjectDAOImpl<Answer> answerDao = new AnswerDAOImpl();
	private Answer anwer = new Answer();

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	protected Log getLog() { return log; }
	public boolean validateLogin() { return false; } // TODO
	public Answer getModel() { return anwer; }
	public void setModel(Answer area) { this.anwer = area; }
	protected SimpleObjectDAO<Answer> getModelDao() { return answerDao; }
}
