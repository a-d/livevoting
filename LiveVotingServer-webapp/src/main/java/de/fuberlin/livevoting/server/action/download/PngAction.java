package de.fuberlin.livevoting.server.action.download;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;


import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import de.fuberlin.livevoting.server.Configuration;
import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.Question;
import de.fuberlin.livevoting.server.domain.kind.EntityContainsPicture;

public class PngAction extends FileAction {

	private static final long serialVersionUID = -6659925652584240539L;
	private static Logger log = Logger.getLogger(PngAction.class);

	private static final int TIMEOUT_DAYS = 7;

	
	private SimpleObjectDAO<Question> questionDAO = new QuestionDAOImpl();
	private SimpleObjectDAO<Answer> answerDAO = new AnswerDAOImpl();

	private String mimeType = "image/png";
	private String fileExtension = "png";
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
			EntityContainsPicture item = null;
			
			if(questionId!=null) {
				Question q = questionDAO.getById(Long.valueOf(questionId));
				if(q!=null) {
					fileName = q.getText().hashCode()+"_"+q.getPicture().hashCode();
					item = q;
				}
			}
			else if(answerId!=null) {
				Answer a = answerDAO.getById(Long.valueOf(answerId));
				if(a!=null) {
					fileName = a.getTitle().hashCode()+"_"+a.getTitle().hashCode()+"_"+a.getPicture().hashCode();
					item = a;
				}
			}
			
			
			
			if(item!=null) {

				String imagesRoot = Configuration.getInstance().getImagesDir();
				String dir = imagesRoot + item.getClass().getSimpleName(); 
				String file = dir + File.separator + item.getId() + ".png";
				
				File f = new File(file);
				try {
					long timeout = new Date().getTime()-1000*60*60*24*TIMEOUT_DAYS; 
					if(!f.exists() || f.lastModified()<timeout) {
						new File(dir).mkdirs();
						if(f.createNewFile()) {
							f.delete();
						}
						createImage(f, item);
					}
				}
				catch(Exception e) {
					URL px = this.getClass().getClassLoader().getResource("pixel.png");
					File fPixel = new File(px.toURI());
					FileUtils.copyFile(fPixel, f);
					f = fPixel;
					log.warn("Could not create image, saved Pixel");
				}
				setFileName(file);
				setFileStream(new FileInputStream(f));

				return SUCCESS;
			}
			else {
				log.warn("Could not load image: No valid questionId or answerId entered.");
				return NONE;
			}
			
			
		} catch (Exception ex) {
			log.warn("Could not load image: "+ex.getMessage());
			return NONE;
		}
	}
	
	

	private void createImage(File f, EntityContainsPicture item) throws Exception {		
		String svgString = item.getPicture();
		
		if(!svgString.contains("svg")) {
			// svgString = "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"><text x=\"0\" y=\"0\" fill=\"black\">"+svgString+"</text></svg>";
			throw new Exception("Picture string is no simple vectore grafic");
		}
		
		FileOutputStream fout = new FileOutputStream(f);
		ByteArrayInputStream fin = new ByteArrayInputStream(svgString.getBytes("UTF-8"));
		
		PNGTranscoder t = new PNGTranscoder();
		t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(item.getPictureWidth()));
		t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(item.getPictureHeight()));
		
		TranscoderInput input = new TranscoderInput(fin);
		TranscoderOutput output = new TranscoderOutput(fout);
		t.transcode(input,output);
		
		log.info("Created new Image: "+f.getAbsolutePath());
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
