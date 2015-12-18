package sample.view.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.model.SysModule;
import sample.core.service.SystemService;
import sample.core.utils.Utilities;
import sample.view.action.BaseAction;

public class ModuleEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private Integer id;

	private SysModule sysModule;

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
}
