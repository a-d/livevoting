package de.fuberlin.livevoting.server.intern.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import de.fuberlin.livevoting.server.dao.QuestionDAO;
import de.fuberlin.livevoting.server.dao.UserDAO;
import de.fuberlin.livevoting.server.domain.Question;
import de.fuberlin.livevoting.server.domain.User;


public class UserUtil {
	private static Logger log = Logger.getLogger(UserUtil.class);
	
	public static User generateUser(UserDAO userDAO, QuestionDAO questionDAO) {
		User u;
		try {
			HttpServletRequest r = ServletActionContext.getRequest();
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			
			int browserHash = getRequestBrowserHash(r);
			String browserAgent = getRequestBrowserAgent(r);
			String ip = getRequestIp(r);
			
			u = userDAO.getByKey(User.Field.ip, ip);
			log.debug("Trying ip: "+ip);
			if(u != null) {
				log.debug("Found: "+u);
				regenVotes(questionDAO, u, sessionMap);
				return u;
			}
			
			if(r.getHeader("cookie")==null) {
				log.debug("Found no cookie trying browserHash: "+browserHash);
				u = userDAO.getByKey(User.Field.browserHash, browserHash);
				if(u != null) {
					log.debug("Found: "+u);
					regenVotes(questionDAO, u, sessionMap);
					return u;
				}
			}
	
			u = new User();
			u.setIp(ip);
			u.setBrowserHash(browserHash);
			u.setBrowserAgent(browserAgent);
			u.setDate(new Date());
			userDAO.save(u);
	
		}
		catch(Exception e) {
			u = new User();
			log.warn("Could not get Request Information!");
		}
		return u;
	}

	public static void regenVotes(QuestionDAO questionDAO, User u, Map<String, Object> sessionMap) {
		log.debug("Trying to receive Votes");
		Collection<Question> qList = questionDAO.getQuestionsAnsweredByUser(u);
		log.debug("vote list: "+qList);
		Date currDate = new Date();
		if(qList!=null && qList.size()>0) {
			for(Question q : qList) {
				Date dateVal = q.getDateTo()!=null ? q.getDateTo() : currDate;
				sessionMap.put("question.answered." + q.getId(), dateVal);
			}
		}
	}

	public static String getRequestBrowserAgent(HttpServletRequest r) {
		return r.getHeader("user-agent");
	}

	private static String getRequestIp(HttpServletRequest r) {
		return r.getRemoteAddr();
	}

	private static int getRequestBrowserHash(HttpServletRequest r) {
		return (r.getHeader("host") +
			"\n" + r.getHeader("user-agent") +
			"\n" + r.getHeader("accept") +
			"\n" + r.getHeader("accept-language") +
			"\n" + r.getHeader("accept-encoding") +
			"\n" + r.getHeader("accept-charset")).hashCode();
	}
	
}
