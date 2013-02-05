package de.fuberlin.livevoting.server.dao;

import java.util.Date;
import java.util.List;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.User;

public interface UserDAO extends SimpleObjectDAO<User> {
	
	public List<User> listByDate(Date sinceDate);

}
