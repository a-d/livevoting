package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import de.fuberlin.livevoting.server.action.kind.ActionSupportSelection;
import de.fuberlin.livevoting.server.dao.MessageDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Message;


public class SelectionMessageAction extends ActionSupportSelection {
	private List<Message> availableMessages;
	private SimpleObjectDAOImpl<Message> messageDao = new MessageDAOImpl();
	private Long defaultMessageId;

	private static final long serialVersionUID = 6721064966173343669L;

	public String execute() throws Exception {
		availableMessages = messageDao.listAll();
		return SUCCESS;
	}

	public List<Message> getAvailableMessages() {
		return availableMessages;
	}
	

	public void setDefaultMessageId(Long id) {
		this.defaultMessageId = id;
	}
	
	public Long getDefaultMessageId() {
		return defaultMessageId;
	}
}
