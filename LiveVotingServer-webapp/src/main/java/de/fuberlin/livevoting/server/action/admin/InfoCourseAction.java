package de.fuberlin.livevoting.server.action.admin;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportAdmin;
import de.fuberlin.livevoting.server.dao.CourseDAO;
import de.fuberlin.livevoting.server.dao.CourseDAOImpl;
import de.fuberlin.livevoting.server.domain.Course;


public class InfoCourseAction extends ActionSupportAdmin implements ModelDriven<Course> {

	private CourseDAO courseDao = new CourseDAOImpl();
	private Course course = new Course();

	private static final long serialVersionUID = 6721064966173343669L;

	
	public String execute() throws Exception {
		super.execute();
		course = courseDao.getById(course.getId());
		
		return SUCCESS;
	}

	
	public Course getModel() {
		return course;
	}
}
