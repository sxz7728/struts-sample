package sample.action.sys;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.model.SysMenu;
import sample.service.SystemService;
import sample.action.BaseAction;
import sample.utils.Utilities;

public class MenuEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private Integer id;

	private Integer moduleId;

	private Integer parentId;

	private SysMenu sysMenu;

	private List<Integer> ids;

	@Action("menuEdit")
	public String execute() {
		if (Utilities.isValidId(id)) {
			sysMenu = systemService.loadMenu(id);
			moduleId = sysMenu.getModuleId();
			parentId = sysMenu.getParentId();
		}

		return INPUT;
	}

	@Action("menuSave")
	public void save() {
		if (!Utilities.isValidId(id)) {
			sysMenu = systemService.saveMenu(moduleId, parentId, sysMenu.getName(), sysMenu.getUrl(), sysMenu.getSequence(), sysMenu.getCssClass(),
					getUserInfo());
		} else {
			sysMenu = systemService.updateMenu(id, parentId, sysMenu.getName(), sysMenu.getUrl(), sysMenu.getSequence(), sysMenu.getCssClass(),
					getUserInfo());
		}

		writeJson(sysMenu.getId());
	}

	@Action("menuDelete")
	public void delete() {
		systemService.deleteMenu(ids, getUserInfo());
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

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
