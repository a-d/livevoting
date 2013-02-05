package de.fuberlin.livevoting.server.action.download;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.Question;


public class SvgAction extends FileAction {

	private static final long serialVersionUID = -6659925652584240539L;
	private static Logger log = Logger.getLogger(SvgAction.class);

	private SimpleObjectDAO<Question> questionDAO = new QuestionDAOImpl();
	private SimpleObjectDAO<Answer> answerDAO = new AnswerDAOImpl();

	private String mimeType = "image/svg+xml";
	private String fileExtension = "svg";
	private String fileName, questionId, answerId;
	private InputStream fileStream; 

	@Override
	@Action(results={@Result(
		name=SUCCESS,
		type="stream",
		params={
				"contentType", "${mimeType}",
				"inputName", "fileStream",
				"contentDisposition", "inline;filename=\"${fileName}.${fileExtension}\"",
				"bufferSize", "1024"
				}
	)})
	public String execute() {
		try {
			String picture = null;
			if(questionId!=null) {
				Question q = questionDAO.getById(Long.valueOf(questionId));
				picture = q.getPicture();
				fileName = q.getText().hashCode()+""+picture.hashCode();
			}
			else if(answerId!=null) {
				Answer a = answerDAO.getById(Long.valueOf(answerId));
				picture = a.getPicture();
				fileName = a.getTitle().hashCode()+""+a.getTitle().hashCode()+""+picture.hashCode();
			}
			
			if(picture!=null) {
				fileStream = new ByteArrayInputStream(picture.getBytes("UTF-8"));
				return SUCCESS;
			}
			else {
				log.warn("Could not load image: No questionId or answerId entered.");
				return ERROR;
			}
			
			
		} catch (Exception ex) {
			log.warn("Could not load image: "+ex.getMessage());
			return ERROR;
		}
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionId() {
		return this.questionId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}
