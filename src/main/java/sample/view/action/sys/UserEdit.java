package sample.view.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.service.SystemService;
import sample.view.action.BaseAction;

public class UserEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	@Action("userEdit")
	public String execute() {
		return INPUT;
	}

	@Action("userSave")
	public void save() {
	}

	@Action("userDelete")
	public void delete() {
	}
}
