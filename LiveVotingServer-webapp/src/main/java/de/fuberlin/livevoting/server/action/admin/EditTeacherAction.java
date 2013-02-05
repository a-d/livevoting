package de.fuberlin.livevoting.server.action.admin;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportCRUD;
import de.fuberlin.livevoting.server.dao.TeacherDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Teacher;


@Results( { @Result(name = "error", location = "teachers.jsp") })
public class EditTeacherAction extends ActionSupportCRUD<Teacher> implements ModelDriven<Teacher> {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(EditTeacherAction.class);

	private SimpleObjectDAOImpl<Teacher> teacherDao = new TeacherDAOImpl();
	private Teacher teacher = new Teacher();

	@Override
	protected void savePrepare(Teacher obj) throws Exception {
		if(obj.getPassword()!=null && !obj.getPassword().equals("")) {
			Date dateRegistered = null;
			if(obj.getId()!=null) {
				dateRegistered = teacherDao.getById(obj.getId()).getDateRegistered();
			}
			if(dateRegistered==null) {
				dateRegistered = new Date();
			}
			obj.setDateRegistered(dateRegistered);
			obj.generateHashedPassword();
		}
	}
	
	@Override
	protected void updatePrepare(Teacher obj, Teacher oldObj) throws Exception {
		obj.generateHashedPassword();
	}
	
	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	protected Log getLog() { return log; }
	public boolean validateLogin() { return false; } // TODO
	public Teacher getModel() { return teacher; }
	public void setModel(Teacher teacher) { this.teacher = teacher; }
	protected SimpleObjectDAO<Teacher> getModelDao() { return teacherDao; }
}
