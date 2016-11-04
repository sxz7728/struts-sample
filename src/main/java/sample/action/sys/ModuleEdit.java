package sample.action.sys;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.model.SysModule;
import sample.service.SystemService;
import sample.action.BaseAction;
import sample.utils.Utilities;

public class ModuleEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private Integer id;

	private SysModule sysModule;

	private List<Integer> ids;

	@Action("moduleEdit")
	public String execute() {
		if (Utilities.isValidId(id)) {
			sysModule = systemService.loadModule(id);
		}

		return INPUT;
	}

	@Action("moduleSave")
	public void save() {
		if (!Utilities.isValidId(id)) {
			sysModule = systemService.saveModule(sysModule.getName(), sysModule.getSequence(), getUserInfo());
		} else {
			sysModule = systemService.updateModule(id, sysModule.getName(), sysModule.getSequence(), getUserInfo());
		}

		writeJson(sysModule);
	}

	@Action("moduleDelete")
	public void delete() {
		systemService.deleteModule(ids, getUserInfo());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(SysModule sysModule) {
		this.sysModule = sysModule;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
