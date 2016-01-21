package sample.view.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.model.SysDict;
import sample.core.service.SystemService;
import sample.core.utils.Utilities;
import sample.view.action.BaseAction;

public class DictEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private Integer id;

	private String type;

	private SysDict sysDict;

	@Action("dictEdit")
	public String execute() {
		if (Utilities.isValidId(id)) {
			sysDict = systemService.loadDict(id);
			type = sysDict.getType();
		}

		return INPUT;
	}

	@Action("dictSave")
	public void save() {
		if (!Utilities.isValidId(id)) {
			sysDict = systemService.saveDict(type, sysDict.getDictKey(), sysDict.getDictValue(),
					sysDict.getParentKey(), sysDict.getSequence(), getUserInfo());
		} else {
			sysDict = systemService.updateDict(id, sysDict.getDictKey(), sysDict.getDictValue(),
					sysDict.getParentKey(), sysDict.getSequence(), getUserInfo());
		}

		writeJson(sysDict);
	}

	@Action("dictDelete")
	public void delete() {
		writeJson(systemService.deleteDict(id));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SysDict getSysDict() {
		return sysDict;
	}

	public void setSysDict(SysDict sysDict) {
		this.sysDict = sysDict;
	}
}