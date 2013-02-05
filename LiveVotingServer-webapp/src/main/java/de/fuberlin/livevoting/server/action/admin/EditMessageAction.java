package de.fuberlin.livevoting.server.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportCRUD;
import de.fuberlin.livevoting.server.dao.MessageDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Message;


@Results( { @Result(name = "error", location = "messages.jsp") })
public class EditMessageAction extends ActionSupportCRUD<Message> implements ModelDriven<Message> {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(EditMessageAction.class);

	private SimpleObjectDAOImpl<Message> messageDao = new MessageDAOImpl();
	private Message message = new Message();

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	protected Log getLog() { return log; }
	public boolean validateLogin() { return false; } // TODO
	public Message getModel() { return message; }
	public void setModel(Message message) { this.message = message; }
	protected SimpleObjectDAO<Message> getModelDao() { return messageDao; }
}
