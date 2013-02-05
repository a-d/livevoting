package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import de.fuberlin.livevoting.server.action.kind.ActionSupportSelection;
import de.fuberlin.livevoting.server.dao.TeacherDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Teacher;


public class SelectionTeacherAction extends ActionSupportSelection {
	private List<Teacher> availableTeachers;
	private SimpleObjectDAOImpl<Teacher> teacherDao = new TeacherDAOImpl();
	private Long defaultTeacherId;

	private static final long serialVersionUID = 6721064966173343669L;

	public String execute() throws Exception {
		availableTeachers = teacherDao.listAll();
		return SUCCESS;
	}

	public List<Teacher> getAvailableTeachers() {
		return availableTeachers;
	}
	

	public void setDefaultTeacherId(Long id) {
		this.defaultTeacherId = id;
	}
	
	public Long getDefaultTeacherId() {
		return defaultTeacherId;
	}
}
