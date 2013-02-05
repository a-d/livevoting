package de.fuberlin.livevoting.server.action.download;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.domain.Course;

public class PropertiesAction extends FileAction implements ModelDriven<Course> {

	private static final long serialVersionUID = -6659925652584240539L;
	private static Logger log = Logger.getLogger(PropertiesAction.class);


	private String mimeType = "application/plain";
	private String fileExtension = "properties";
	private String fileName = "livevoting";
	private InputStream fileStream; 

	private Course course = new Course();
	
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

			byte[] bytes = createPropertiesText().getBytes("UTF-8");
			InputStream stream = new ByteArrayInputStream(bytes);
			setFileStream(stream);
			
			return SUCCESS;
			
		} catch (Exception ex) {
			log.warn("Could not create properties: "+ex.getMessage());
			return ERROR;
		}
	}
	
	
	
	private String createPropertiesText() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		
		String hostname = request.getServerName()+":"+request.getServerPort();
		Long courseId = course.getId();
		String path = request.getContextPath()+"/";
		if(path.length()>2 && path.charAt(0)=='/') {
			path = path.substring(1);
		}
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("#automatic generated properties file from server\n");
		sb.append("#"+(new Date().toString())+"\n");
		
		sb.append("serverPath="+path +"\n");
		sb.append("courseId="+courseId+"\n");
		sb.append("serverHostname="+hostname+"\n");
		
		sb.append("titleDateFormat=dd.MM.yyy H\\:m\n");
		sb.append("getActionUrl=adm/clientrequest/getQuestion?qId\\=\n");
		sb.append("postActionUrl=adm/clientrequest/postQuestion?cId\\=\n");
		
		return sb.toString();
	}


	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
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



	public Course getModel() {
		return course;
	}
}
