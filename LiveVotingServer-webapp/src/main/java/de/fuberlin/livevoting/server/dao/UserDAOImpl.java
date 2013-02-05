package de.fuberlin.livevoting.server.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.User;

public class UserDAOImpl extends SimpleObjectDAOImpl<User> implements UserDAO {

	protected static final Logger log = Logger.getLogger(UserDAOImpl.class);

	@Override
	public Class<User> getEntityClass() { return User.class; }

	
	public List<User> listByDate(Date sinceDate) {
		return listByCriteria(
			DetachedCriteria.forClass(User.class)
				.add(Restrictions.gt("date", sinceDate))
				.addOrder(Order.asc("date"))
		);
	}


}
