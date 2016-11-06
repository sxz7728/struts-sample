package sample.action.sys;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.model.SysDict;
import sample.service.SystemService;
import sample.action.BaseAction;
import sample.utils.Utilities;

public class DictEdit extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private Integer id;

	private SysDict sysDict;

	private List<Integer> ids;

	@Action("dictEdit")
	public String execute() {
		if (Utilities.isValidId(id)) {
			sysDict = systemService.loadDict(id);
		}

		return INPUT;
	}

	@Action("dictSave")
	public void save() {
		if (!Utilities.isValidId(id)) {
			sysDict = systemService.saveDict(sysDict.getType(), sysDict.getDictKey(), sysDict.getDictValue(), sysDict.getParentKey(),
					sysDict.getSequence(), getUserInfo());
		} else {
			sysDict = systemService.updateDict(id, sysDict.getDictKey(), sysDict.getDictValue(), sysDict.getParentKey(), sysDict.getSequence(),
					getUserInfo());
		}

		writeJson(sysDict.getId());
	}

	@Action("dictDelete")
	public void delete() {
		systemService.deleteDict(ids, getUserInfo());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysDict getSysDict() {
		return sysDict;
	}

	public void setSysDict(SysDict sysDict) {
		this.sysDict = sysDict;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
