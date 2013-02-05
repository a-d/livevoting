package de.fuberlin.livevoting.server.dao;

import org.apache.log4j.Logger;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Vote;

public class VoteDAOImpl extends SimpleObjectDAOImpl<Vote> implements VoteDAO {

	protected static final Logger log = Logger.getLogger(VoteDAOImpl.class);


	@Override
	public Class<Vote> getEntityClass() { return Vote.class; }

}
