package de.fuberlin.livevoting.server.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportCRUD;
import de.fuberlin.livevoting.server.dao.VoteDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Vote;


@Results( { @Result(name = "error", location = "votes.jsp") })
public class EditVoteAction extends ActionSupportCRUD<Vote> implements ModelDriven<Vote> {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(EditVoteAction.class);

	private SimpleObjectDAOImpl<Vote> voteDao = new VoteDAOImpl();
	private Vote vote = new Vote();

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	protected Log getLog() { return log; }
	public boolean validateLogin() { return false; } // TODO
	public Vote getModel() { return vote; }
	public void setModel(Vote vote) { this.vote = vote; }
	protected SimpleObjectDAO<Vote> getModelDao() { return voteDao; }
}
