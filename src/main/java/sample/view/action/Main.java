package sample.view.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.model.SysModule;
import sample.core.service.SystemService;
import sample.core.utils.QueryBuilder;
import sample.core.utils.QueryUtils;

@Namespace("/")
public class Main extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private List<SysModule> sysModules;

	private Integer moduleId;

	@Action("main")
	public String execute() {
		QueryBuilder qb = QueryUtils.addWhereNotDeleted(new QueryBuilder());
		QueryUtils.addWhereWithDefault(qb, "and t.id in {0}", getUserInfo()
				.getModuleIds(), -1);
		QueryUtils.addOrder(qb, "t.sequence");
		sysModules = systemService.findModule(qb);
		return INPUT;
	}

	@Action("sidebar")
	public void sidebar() {
		QueryBuilder qb = QueryUtils.addWhereNotDeleted(new QueryBuilder());
		QueryUtils.addColumn(qb, "t.parentId");
		QueryUtils.addColumn(qb, "t.name");		
		QueryUtils.addWhere(qb, "and t.sysModule.id = {0}", moduleId);
		QueryUtils.addWhereWithDefault(qb, "and t.id in {0}", getUserInfo()
				.getMenuIds(), -1);
		QueryUtils.addOrder(qb, "t.sequence");
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
