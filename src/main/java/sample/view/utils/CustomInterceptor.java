package sample.view.utils;

import java.lang.reflect.Method;

import sample.view.action.BaseAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CustomInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		if (invocation.getAction() instanceof BaseAction) {
			BaseAction baseAction = (BaseAction) invocation.getAction();

			try {
				baseAction.authenticate();
				return invocation.invoke();
			} catch (Exception e) {
				String methodName = invocation.getProxy().getMethod();

				if (methodName != null) {
					Method method = baseAction.getClass().getMethod(methodName, new Class<?>[] {});

					if (method.getReturnType() == void.class) {
						e.printStackTrace();
						baseAction.writeJson(e);
						return null;
					}
				}

				throw e;
			}
		} else {
			return invocation.invoke();
		}
	}
}
