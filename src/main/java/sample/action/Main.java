package sample.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import sample.model.SysModule;
import sample.service.SystemService;
import sample.utils.DictUtils;
import sample.utils.QueryBuilder;
import sample.utils.QueryUtils;

@Namespace("/")
public class Main extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private List<SysModule> sysModules;

	private Integer moduleId;

	@Action("main")
	public String execute() {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.id in {0}", getUserInfo().getModuleIds());
		QueryUtils.addOrder(qb, "t.sequence");
		QueryUtils.addOrder(qb, "t.id");
		sysModules = systemService.findModule(qb);
		return INPUT;
	}

	@Action("sidebar")
	public void sidebar() {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addColumn(qb, "t.id");
		QueryUtils.addColumn(qb, "t.parentId");
		QueryUtils.addColumn(qb, "t.name");
		QueryUtils.addColumn(qb, "t.url");
		QueryUtils.addColumn(qb, "t.cssClass");
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.sysModule.id = {0}", moduleId);
		QueryUtils.addWhere(qb, "and t.id in {0}", getUserInfo().getMenuIds());
		QueryUtils.addOrder(qb, "t.sequence");
		QueryUtils.addOrder(qb, "t.id");
		writeJson(systemService.datagridMenu(qb));
	}

	public List<SysModule> getSysModules() {
		return sysModules;
	}

	public void setSysModules(List<SysModule> sysModules) {
		this.sysModules = sysModules;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
}
