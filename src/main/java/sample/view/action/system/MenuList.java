package sample.view.action.system;

import org.apache.struts2.convention.annotation.Action;

import sample.view.action.BaseAction;

public class MenuList extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Action("menuList")
	public String execute() {
		return INPUT;
	}
}
