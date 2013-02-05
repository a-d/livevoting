package de.fuberlin.livevoting.server.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Course;

public class CourseDAOImpl extends SimpleObjectDAOImpl<Course> implements CourseDAO {

	protected static final Logger log = Logger.getLogger(CourseDAOImpl.class);


	@Override
	public Class<Course> getEntityClass() { return Course.class; }


	/**
	 * Used to list all the courses.
	 */
	public List<Course> listActives(Date currDate) {
		return listByCriteria(
			DetachedCriteria.forClass(Course.class)
				.add(Restrictions.gt("dateTo", currDate))
				.addOrder(Order.asc("name"))
		);
	}
	
	/**
	 * Used to list all the courses.
	 */
	public List<Course> listOlds(Date currDate) {
		return listByCriteria(
			DetachedCriteria.forClass(Course.class)
				.add(Restrictions.lt("dateTo", currDate))
				.addOrder(Order.asc("name"))
		);
	}
}
