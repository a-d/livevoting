package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import de.fuberlin.livevoting.server.action.kind.ActionSupportSelection;
import de.fuberlin.livevoting.server.dao.VoteDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Vote;


public class SelectionVoteAction extends ActionSupportSelection {
	private List<Vote> availableVotes;
	private SimpleObjectDAOImpl<Vote> voteDao = new VoteDAOImpl();
	private Long defaultVoteId;

	private static final long serialVersionUID = 6721064966173343669L;

	public String execute() throws Exception {
		availableVotes = voteDao.listAll();
		return SUCCESS;
	}

	public List<Vote> getAvailableVotes() {
		return availableVotes;
	}
	

	public void setDefaultVoteId(Long id) {
		this.defaultVoteId = id;
	}
	
	public Long getDefaultVoteId() {
		return defaultVoteId;
	}
}
