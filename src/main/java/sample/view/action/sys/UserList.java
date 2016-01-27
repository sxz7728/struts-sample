package sample.view.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.service.SystemService;
import sample.view.action.BaseAction;

public class UserList extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	@Action("userList")
	public String execute() {
		return INPUT;
	}

	@Action("userDatagrid")
	public void datagrid() {

	}
}
