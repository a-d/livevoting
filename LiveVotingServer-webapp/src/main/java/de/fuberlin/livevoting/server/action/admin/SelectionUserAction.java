package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import de.fuberlin.livevoting.server.action.kind.ActionSupportSelection;
import de.fuberlin.livevoting.server.dao.UserDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.User;


public class SelectionUserAction extends ActionSupportSelection {
	private List<User> availableUsers;
	private SimpleObjectDAOImpl<User> userDao = new UserDAOImpl();
	private Long defaultUserId;

	private static final long serialVersionUID = 6721064966173343669L;

	public String execute() throws Exception {
		availableUsers = userDao.listAll();
		return SUCCESS;
	}

	public List<User> getAvailableUsers() {
		return availableUsers;
	}
	

	public void setDefaultUserId(Long id) {
		this.defaultUserId = id;
	}
	
	public Long getDefaultUserId() {
		return defaultUserId;
	}
}
