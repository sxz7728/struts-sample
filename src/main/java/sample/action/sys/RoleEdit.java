package sample.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.service.SystemService;
import sample.action.BaseAction;

public class RoleEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	@Action("roleEdit")
	public String execute() {
		return INPUT;
	}

	@Action("roleSave")
	public void save() {
	}

	@Action("roleDelete")
	public void delete() {
	}
}
