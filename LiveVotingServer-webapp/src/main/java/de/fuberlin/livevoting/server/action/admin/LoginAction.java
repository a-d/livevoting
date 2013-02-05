package de.fuberlin.livevoting.server.action.admin;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.dao.TeacherDAO;
import de.fuberlin.livevoting.server.dao.TeacherDAOImpl;
import de.fuberlin.livevoting.server.domain.Teacher;


public class LoginAction extends ActionSupport implements ModelDriven<Teacher> {
	private static final long serialVersionUID = 1054489770482813351L;
	private static final Log log = LogFactory.getLog(LoginAction.class);

	private TeacherDAO teacherDao = new TeacherDAOImpl();
	private Teacher teacher = new Teacher();
	private String url;

	private List<String> availableLogins = new LinkedList<String>();
	
	public static final String LOGIN_USER_KEY = "teacher";
	private static final String LOGIN_FAIL_MSG_DEFAULT = "Teacher name or password incorrect.";
	private static final String LOGIN_FAIL_MSG_KEY = "global.fail.login";
	private static final boolean DEBUG_MODE = false;

	
	
	@Override
	@Action(results={@Result(name=SUCCESS, location="%{url}", type="redirect")})
	public String execute() throws Exception {
		if (teacher.getName()!=null && teacher.getName().length() != 0) {
			if (processLoginAttempt()) {
				log.debug("login successfull, redirecting to "+getUrl());
				return SUCCESS;
			} else {
				String failMessage = LOGIN_FAIL_MSG_KEY!=null ? getText(LOGIN_FAIL_MSG_KEY) : LOGIN_FAIL_MSG_DEFAULT;
				addActionError(failMessage);
			}
		}

		return INPUT;
	}
	
	
	@Action(value="logout", results={@Result(name=SUCCESS, location="/", type="redirect")})
	public String logout() {
		Map<String, Object> session = ActionContext.getContext().getSession();		
		session.remove(LOGIN_USER_KEY);
		return SUCCESS;
	}
	
	
	private boolean processLoginAttempt() {
		Teacher userObject = null;
		if(getAvailableLogins().size()<1 || DEBUG_MODE) {
			userObject = new Teacher();
		}
		else {
			String pw = teacher.getPassword();
		    if(pw==null || pw.length()==0) {
		    	log.info("No Password: "+pw);
		    	return false;
		    }
		    
		    String username = teacher.getName();
		    userObject = teacherDao.getByKey(Teacher.Field.name, username);
		    if(userObject==null) {
		    	log.info("No User found: \""+username+"\"");
		    	return false;
		    }
		    
		    if(!userObject.isCorrectPassword(pw)) {
		    	log.info("Password incorrect: \""+pw+"\"");
		    	return false;
		    }
		}
		
		userObject.populateByRequest(ServletActionContext.getRequest());
    	ActionContext.getContext().getSession().put(LOGIN_USER_KEY, userObject);
    	log.info("User logged in: "+userObject);
        return true;
	}

	public Collection<String> getAvailableLogins() {
		if(availableLogins.size()<1){
			availableLogins.clear();
			for(Teacher teacher : teacherDao.listAll()) {
				availableLogins.add(teacher.getName());
			}
			Collections.sort(availableLogins);
		}
		return availableLogins;
	}

	public String getUrl() {
		if(url==null || url.equals("") || url.toLowerCase().endsWith("login")) {
			setUrl("index");
		}
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Teacher getModel() {
		return teacher;
	}
}
