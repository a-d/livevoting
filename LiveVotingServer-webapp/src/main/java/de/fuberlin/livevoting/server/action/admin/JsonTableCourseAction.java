package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import de.fuberlin.livevoting.server.action.kind.JsonTableAction;
import de.fuberlin.livevoting.server.dao.CourseDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Course;
import de.fuberlin.livevoting.server.domain.Course.Field;
import de.fuberlin.livevoting.server.domain.Teacher;


@Result(type = "json", params = {
        "includeProperties",
		"^(rows|page|total|records|sord|sidx),"+
        "^gridModel\\[\\d+\\]\\.(id|name|token|url|dateFrom|dateTo)," +
        "^gridModel\\[\\d+\\]\\.teacher\\.(id|name)," +
        ""
    })
public class JsonTableCourseAction extends JsonTableAction<Course> {
	private static final long serialVersionUID = 5278944653342163482L;

	private SimpleObjectDAOImpl<Course> dao = new CourseDAOImpl();
	
	@Override
	protected Class<Course> getEntityClass() { return Course.class; }
	
	@Override
	protected SimpleObjectDAO<Course> getEntityDao() { return dao; }
	
	@Override
	protected void initialize() {
		setParameterAttributes(ParameterType.String,
				Field.url,
				Field.name,
				Field.token,
				Field.teacher+"."+Teacher.Field.name
		);
		setParameterAttributes(ParameterType.Long,
				Field.id,
				Field.teacher+"."+Teacher.Field.id
		);
		setParameterAttributes(ParameterType.Date,
				Field.dateFrom,
				Field.dateTo
		);
	}
	
	@Override
	protected String parameterToField(String searchField) {
		if(searchField.equals("courseId")) { return Field.id.toString(); }
		else if(searchField.equals("teacherId")) { return Field.teacher+"."+Teacher.Field.id.toString(); }
		else if(searchField.equals("teacherName")) { return Field.teacher+"."+Teacher.Field.name.toString(); }
		else return super.parameterToField(searchField);
	}

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	
	public String getJSON() { return super.getJSON(); }
	public Integer getRows() { return super.getRows(); }
	public Integer getPage() { return super.getPage(); }
	public Integer getTotal() { return super.getTotal(); }
	public Integer getRecords() { return super.getRecords(); }
	public List<Course> getGridModel() { return super.getGridModel(); }
	public String getSord() { return super.getSord(); }
	public String getSidx() { return super.getSidx(); }

}
