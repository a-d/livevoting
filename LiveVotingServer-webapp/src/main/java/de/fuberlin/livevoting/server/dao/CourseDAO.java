package de.fuberlin.livevoting.server.dao;

import java.util.Date;
import java.util.List;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.Course;

public interface CourseDAO extends SimpleObjectDAO<Course> {

	public List<Course> listActives(Date currDate);
	public List<Course> listOlds(Date currDate);

}
