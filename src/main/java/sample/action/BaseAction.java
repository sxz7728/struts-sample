package sample.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;

import sample.exception.NotLoggedInException;
import sample.exception.ValidateException;
import sample.service.SystemService;
import sample.utils.DictUtils;
import sample.utils.Globals;
import sample.utils.JsonResult;
import sample.utils.JsonUtils;
import sample.utils.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ServletContextAware {

	private static final long serialVersionUID = 1L;

	private static final String SESSION_USER_INFO = "user_info";

	private final boolean needAuth;

	private int start;

	private int length;

	private String queryName;

	private HttpServletRequest servletRequest;

	private HttpServletResponse servletResponse;

	private ServletContext servletContext;

	@Autowired
	private Globals globals;

	@Autowired
	private DictUtils dictUtils;

	@Autowired
	private SystemService systemService;

	private UserInfo userInfo;

	public BaseAction() {
		needAuth = true;
	}

	public BaseAction(boolean needAuth) {
		this.needAuth = needAuth;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public HttpSession getSession() {
		return servletRequest.getSession();
	}

	public void authenticate() {
		if (needAuth) {
			userInfo = (UserInfo) getSession().getAttribute(SESSION_USER_INFO);

			if (userInfo == null) {
				throw new NotLoggedInException();
			}

			userInfo.setOperateDate(new Date());
		}
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		getSession().setAttribute(SESSION_USER_INFO, userInfo);
	}

	public Globals getGlobals() {
		return globals;
	}

	public DictUtils getDictUtils() {
		return dictUtils;
	}

	public void writeJson(JsonResult result) {
		PrintWriter writer = null;

		try {
			servletResponse.setContentType("text/html;charset=utf-8");
			writer = servletResponse.getWriter();
			writer.println(JsonUtils.toJson(result));
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	public void writeJson(Object data) {
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		result.setData(data);
		writeJson(result);
	}

	public void writeJson(Exception error) {
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		result.setError(error.getMessage());
		writeJson(result);
	}

	public void writeJson(ValidateException error) {
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		result.setError(error.getMessage());
		result.setMessages(error.getMessages());
		writeJson(result);
	}
}
