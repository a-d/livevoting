package de.fuberlin.livevoting.server.dao;

import org.apache.log4j.Logger;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Message;

public class MessageDAOImpl extends SimpleObjectDAOImpl<Message> implements MessageDAO {

	protected static final Logger log = Logger.getLogger(MessageDAOImpl.class);


	@Override
	public Class<Message> getEntityClass() { return Message.class; }

}
