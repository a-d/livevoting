package de.fuberlin.livevoting.server.action.interceptor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import de.fuberlin.livevoting.server.action.admin.LoginAction;
import de.fuberlin.livevoting.server.action.kind.ActionSupportAdmin;
import de.fuberlin.livevoting.server.domain.Teacher;


public class AuthenticationInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 15696213979541502L;
	private static final Log log = LogFactory.getLog(AuthenticationInterceptor.class);
	public static final String LOGIN_RESULT = "login";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		
		Object teacher = session.get(LoginAction.LOGIN_USER_KEY);
		if (teacher==null || !(teacher instanceof Teacher)) {
			log.debug("Login needed for action");
			return LOGIN_RESULT;
		} else {
	        if(invocation.getAction() instanceof ActionSupportAdmin) {
	        	ActionSupportAdmin action = (ActionSupportAdmin) invocation.getAction();
	            action.checkAuthorization((Teacher) teacher);
	        }
			return invocation.invoke();
		}
	}
	
}