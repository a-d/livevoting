package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import de.fuberlin.livevoting.server.action.kind.JsonTableAction;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Course;
import de.fuberlin.livevoting.server.domain.Question;
import de.fuberlin.livevoting.server.domain.Question.Field;
import de.fuberlin.livevoting.server.domain.Teacher;


@Result(type = "json", params = {
        "includeProperties",
		"^(rows|page|total|records|sord|sidx),"+
        "^gridModel\\[\\d+\\]\\.(id|text|multipleChoice|customAnswer|dateFrom|dateTo|picture)," +
        "^gridModel\\[\\d+\\]\\.course\\.(id|name)," +
        ""
    })
public class JsonTableQuestionAction extends JsonTableAction<Question> {
	private static final long serialVersionUID = 5278944653342163482L;

	private SimpleObjectDAOImpl<Question> dao = new QuestionDAOImpl();
	
	@Override
	protected Class<Question> getEntityClass() { return Question.class; }
	
	@Override
	protected SimpleObjectDAO<Question> getEntityDao() { return dao; }
	
	@Override
	protected void initialize() {
		setParameterAttributes(ParameterType.String,
				Field.text,
				Field.picture,
				Field.course+"."+Teacher.Field.name
		);
		setParameterAttributes(ParameterType.Long,
				Field.id,
				Field.course+"."+Course.Field.id
		);
		setParameterAttributes(ParameterType.Date,
				Field.dateFrom,
				Field.dateTo
		);
		setParameterAttributes(ParameterType.Boolean,
				Field.multipleChoice,
				Field.customAnswer
		);
	}
	
	@Override
	protected String parameterToField(String searchField) {
		if(searchField.equals("questionId")) { return Field.id.toString(); }
		else if(searchField.equals("courseId")) { return Field.course+"."+Course.Field.id.toString(); }
		else if(searchField.equals("courseName")) { return Field.course+"."+Course.Field.name.toString(); }
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
	public List<Question> getGridModel() { return super.getGridModel(); }
	public String getSord() { return super.getSord(); }
	public String getSidx() { return super.getSidx(); }

}
