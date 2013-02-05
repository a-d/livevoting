package de.fuberlin.livevoting.server.action;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.Configuration;
import de.fuberlin.livevoting.server.dao.CourseDAO;
import de.fuberlin.livevoting.server.dao.CourseDAOImpl;
import de.fuberlin.livevoting.server.dao.QuestionDAO;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.domain.Course;
import de.fuberlin.livevoting.server.domain.Question;


public class IndexAction extends ActionSupport implements ModelDriven<Course> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(IndexAction.class);

	private Map<String, Object> sessionMap = ActionContext.getContext().getSession();
	
	private CourseDAO courseDao = new CourseDAOImpl();
	private QuestionDAO questionDao = new QuestionDAOImpl();
	
	private List<Course> courseList = new LinkedList<Course>();
	private List<Question> questionList;
	
	private Course activeCourse = new Course();
	private Long onlyCourseId = null;
	
	@Override
	@Action(value="/", results={
			@Result(name="login", type="redirect", location="/admin"),
			@Result(name="success", type="redirect", location="/course_${model.Id}")
	})
	public String execute() {
		
		try {
			List<Course> courses = courseDao.listActives(new Date());
			if(courses.size()<1) {
				log.warn("no course found!");
				return LOGIN;
			}
			else if(courses.size()==1) {
				log.debug("get only course.");
				activeCourse = courses.get(0);
				return SUCCESS;
			}
			courseList = courses;
			return INPUT;
		}
		catch(Exception e) {
			log.warn("Could not load courses.");
		}
		return ERROR;
	}
	

	@Action(value="course_{id:[0-9]+}.*", results={
			@Result(name="success", location="index.jsp")
	})
	public String selectCourse() {
		if(activeCourse.getId()!=null) {
			initQuestionList();
			return SUCCESS;
		}
		return ERROR;
	}
	
	


	public boolean hasAlreadyVoted(Long a) {
		try {
			return sessionMap.get("question.answered." + a)!=null;
		}
		catch(Exception e) {
			log.warn("Could not load Request Information!");
			return false;
		}
	}
	
	
	private void initQuestionList() {
		if(getModel()!=null) {
			try {
				List<Question> qListUnchecked = new LinkedList<Question>(
					questionDao.getActiveQuestionsFromCourse(
						getModel(),
						Configuration.getInstance().getDisplayQuestionTimeout()
					)
				);
				LinkedList<Question> qListOld = new LinkedList<Question>();
				LinkedList<Question> qListNew = new LinkedList<Question>();

				java.util.Collections.sort(qListUnchecked);
				
				for(Question q : qListUnchecked) {
					if(hasAlreadyVoted(q.getId()) || q.isOld()) {
						qListOld.add(q);
					}
					else {
						qListNew.add(q);
					}
				}
				qListNew.addAll(qListOld);
				questionList = qListNew;
			}
			catch(Exception e) {
				log.warn("Could not load questions from course.", e);
			}
		}
	}

	


	public List<Course> getCourseList() {
		return courseList;
	}



	public List<Question> getQuestionList() {
		return questionList;
	}
	
	public Long getOnlyCourseId() {
		return onlyCourseId;
	}
	
	

	public Course getModel() {
		if(activeCourse!=null && activeCourse.getId()!=null && (activeCourse.getName()==null || activeCourse.getName().equals(""))) {
			Course course = courseDao.getById(activeCourse.getId());
			if(course!=null) {
				activeCourse = course;
			}
		}
		return activeCourse;
	}
}
