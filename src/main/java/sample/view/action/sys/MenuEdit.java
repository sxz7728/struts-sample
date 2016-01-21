package sample.view.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.model.SysMenu;
import sample.core.service.SystemService;
import sample.core.utils.Utilities;
import sample.view.action.BaseAction;

public class MenuEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private Integer id;

	private Integer moduleId;

	private Integer parentId;

	private SysMenu sysMenu;

	@Action("menuEdit")
	public String execute() {
		if (Utilities.isValidId(id)) {
			sysMenu = systemService.loadMenu(id);
			moduleId = sysMenu.getSysModule().getId();
			parentId = sysMenu.getParentId();
		}

		return INPUT;
	}

	@Action("menuSave")
	public void save() {
		if (!Utilities.isValidId(id)) {
			sysMenu = systemService.saveMenu(moduleId, parentId, sysMenu.getName(), sysMenu.getUrl(),
					sysMenu.getSequence(), sysMenu.getCssClass(), getUserInfo());
		} else {
			sysMenu = systemService.updateMenu(id, parentId, sysMenu.getName(), sysMenu.getUrl(),
					sysMenu.getSequence(), sysMenu.getCssClass(), getUserInfo());
		}

		writeJson(sysMenu);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public SysMenu getSysMenu() {
		return sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}

}