package de.fuberlin.livevoting.server.action.download;

import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public abstract class FileAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	@Override
	public abstract String execute();
	
	
	// getter and setter
	
	public abstract InputStream getFileStream();
	public abstract void setFileStream(InputStream fileStream);
	public abstract void setFileExtension(String fileExtension);
	public abstract String getFileExtension();
	public abstract void setFileName(String fileName);
	public abstract String getFileName();
	public abstract void setMimeType(String mimeType);
	public abstract String getMimeType();

}
