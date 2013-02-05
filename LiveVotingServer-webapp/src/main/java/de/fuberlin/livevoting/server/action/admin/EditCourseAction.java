package de.fuberlin.livevoting.server.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportCRUD;
import de.fuberlin.livevoting.server.dao.CourseDAO;
import de.fuberlin.livevoting.server.dao.CourseDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.Course;


@Results( { @Result(name = "error", location = "messages.jsp") })
public class EditCourseAction extends ActionSupportCRUD<Course> implements ModelDriven<Course> {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(EditCourseAction.class);

	private CourseDAO courseDao = new CourseDAOImpl();
	private Course course = new Course();

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	protected Log getLog() { return log; }
	public boolean validateLogin() { return false; } // TODO
	public Course getModel() { return course; }
	public void setModel(Course course) { this.course = course; }
	protected SimpleObjectDAO<Course> getModelDao() { return courseDao; }
}
