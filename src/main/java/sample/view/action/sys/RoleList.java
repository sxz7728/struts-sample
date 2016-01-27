package sample.view.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.service.SystemService;
import sample.view.action.BaseAction;

public class RoleList extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;
	
	@Action("roleList")
	public String execute() {
		return INPUT;
	}

	@Action("roleDatagrid")
	public void datagrid() {
		
	}
}
