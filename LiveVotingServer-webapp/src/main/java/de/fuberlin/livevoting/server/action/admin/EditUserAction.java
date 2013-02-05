package de.fuberlin.livevoting.server.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportCRUD;
import de.fuberlin.livevoting.server.dao.UserDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.User;


@Results( { @Result(name = "error", location = "users.jsp") })
public class EditUserAction extends ActionSupportCRUD<User> implements ModelDriven<User> {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(EditUserAction.class);

	private SimpleObjectDAOImpl<User> userDao = new UserDAOImpl();
	private User user = new User();

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	protected Log getLog() { return log; }
	public boolean validateLogin() { return false; } // TODO
	public User getModel() { return user; }
	public void setModel(User user) { this.user = user; }
	protected SimpleObjectDAO<User> getModelDao() { return userDao; }
}
