package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import de.fuberlin.livevoting.server.action.kind.ActionSupportSelection;
import de.fuberlin.livevoting.server.dao.CourseDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Course;


public class SelectionCourseAction extends ActionSupportSelection {
	private List<Course> availableCourses;
	private SimpleObjectDAOImpl<Course> courseDao = new CourseDAOImpl();
	private Long defaultCourseId;

	private static final long serialVersionUID = 6721064966173343669L;

	public String execute() throws Exception {
		availableCourses = courseDao.listAll();
		return SUCCESS;
	}

	public List<Course> getAvailableCourses() {
		return availableCourses;
	}
	

	public void setDefaultCourseId(Long id) {
		this.defaultCourseId = id;
	}
	
	public Long getDefaultCourseId() {
		return defaultCourseId;
	}
}
