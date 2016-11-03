package sample.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import sample.info.UserInfo;
import sample.service.SystemService;

@Namespace("/")
public class Index extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private String username;

	private String password;

	public Index() {
		super(false);
	}

	@Action("index")
	public String execute() {
		return INPUT;
	}

	@Action("login")
	public void login() {
		UserInfo userInfo = systemService.login(username, password);

		if (userInfo != null) {
			setUserInfo(userInfo);
			writeJson(true);
		} else {
			writeJson(false);
		}
	}

	@Action("logout")
	public void logout() {
		setUserInfo(null);
		writeJson(true);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
