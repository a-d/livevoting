package de.fuberlin.livevoting.server.action.interceptor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ParameterIdInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 15696213979531502L;
	private static final String EMPTY = "_empty";
	private static final Log log = LogFactory.getLog(ParameterIdInterceptor.class);

	private static final String[] allowedIds = new String[] { "id", "questionId", "courseId", "teacherId", "userId", "answerId", "voteId" };
	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		Map<String, Object> paraMap = context.getParameters();
		
		if(paraMap!=null && paraMap.size()>0) {
			boolean updateMap = false;
			for(String id : allowedIds) {
				updateMap |= takeIdParameter(id, paraMap);
			}
			if(updateMap) {
				context.setParameters(paraMap);
			}
		}
		return invocation.invoke();
	}
	
	private boolean takeIdParameter(String key, Map<String, Object> paraMap) {
		if(paraMap.containsKey(key)) {
			try {
				Object oId = paraMap.get(key);
				if(oId instanceof String) {
					paraMap.put(key, Long.parseLong((String) oId));
					return true;
				}
				else if(oId instanceof String[]) {
					String[] arrId = (String[]) oId;
					if(arrId.length==1) {
						String id = arrId[0];
						if(id.equals(EMPTY)) {
							paraMap.remove(key);
							return true;
						}
						else if(id.matches("\\d+(,\\d+)+")) {			
							arrId = id.split(",");
						}
					}
					if(arrId.length>1) {
						Long[] ids = new Long[arrId.length];
						for(int i=0; i<arrId.length; i++) {
							try { ids[i] = Long.parseLong(arrId[i]); }
							catch(Exception e) { ids[i] = null; }
						}
						paraMap.remove(key);
						paraMap.put(key+"s", ids);
						return true;
					}
				}
			}
			catch(Exception e) {
				log.warn("Could not check for "+key+" in parameter list", e);
			}
		}

		return false;
	}
}
