package sample.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import sample.action.BaseAction;
import sample.exception.NotLoggedInException;
import sample.exception.ValidateException;

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
				String result = invocation.invoke();

				if (StringUtils.isEmpty(baseAction.getServletResponse().getContentType())) {
					baseAction.writeJson(true);
				}

				return result;
			} catch (Exception e) {
				String methodName = invocation.getProxy().getMethod();
				Method method = baseAction.getClass().getMethod(methodName, new Class<?>[] {});

				if (method.getReturnType() == void.class) {
					if (e instanceof ValidateException) {
						baseAction.writeJson((ValidateException) e);
					} else {
						if (!(e instanceof NotLoggedInException)) {
							e.printStackTrace();
						}

						baseAction.writeJson(e);
					}

					return null;
				}

				throw e;
			}
		} else {
			return invocation.invoke();
		}
	}
}
