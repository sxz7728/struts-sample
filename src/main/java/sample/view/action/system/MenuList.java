package sample.view.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.model.SysModule;
import sample.core.service.SystemService;
import sample.core.utils.DictUtils;
import sample.core.utils.QueryBuilder;
import sample.core.utils.QueryUtils;
import sample.view.action.BaseAction;

public class MenuList extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private Integer moduleId;

	@Action("menuList")
	public String execute() {
		return INPUT;
	}

	@Action("menuDatagrid")
	public void datagrid() {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addColumn(qb, "t.name");
		QueryUtils.addColumn(qb, "t.parentId");
		QueryUtils.addColumn(qb, "t.url");
		QueryUtils.addColumn(qb, "t.sequence");
		QueryUtils.addColumn(qb, "t.cssClass");
		QueryUtils.addWhere(qb, "and t.deleted = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.sysModule.id = {0}", moduleId);
		QueryUtils.addOrder(qb, "t.sequence");
		QueryUtils.addOrder(qb, "t.id");
		writeJson(systemService.datagridMenu(qb));
	}

	public List<SysModule> findModule() {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addWhere(qb, "and t.deleted = {0}", DictUtils.NO);
		return systemService.findModule(qb);
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
}
