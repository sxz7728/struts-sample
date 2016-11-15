package sample.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import sample.service.SystemService;
import sample.utils.UserInfo;

@Namespace("/")
public class Login extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private String username;

	private String password;

	public Login() {
		super(false);
	}

	@Action("login")
	public String execute() {
		return INPUT;
	}

	@Action("doLogin")
	public void doLogin() {
		UserInfo userInfo = systemService.login(username, password);

		if (userInfo != null) {
			setUserInfo(userInfo);
		} else {
			writeJson(false);
		}
	}

	@Action("doLogout")
	public void doLogout() {
		setUserInfo(null);
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
