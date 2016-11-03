package sample.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.service.SystemService;
import sample.action.BaseAction;

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
