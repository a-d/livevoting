package de.fuberlin.livevoting.server.action.kind;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import de.fuberlin.livevoting.server.action.interceptor.AuthenticationInterceptor;
import de.fuberlin.livevoting.server.domain.Teacher;



@InterceptorRefs({
    @InterceptorRef("authenticationSupported")
})
public abstract class ActionSupportAdmin extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Override
	@Action(results={@Result(name=AuthenticationInterceptor.LOGIN_RESULT, location="login", type="redirectAction")})
	public String execute() throws Exception {
		return super.execute();
	}

	public void checkAuthorization(Teacher user) {
		// in case something is wrong
		// throw new AccessViolation("You do not have permission to access this page.");
	}
}
