package sample.action.sys;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;

import sample.service.SystemService;
import sample.action.BaseAction;
import sample.utils.DictUtils;
import sample.utils.QueryBuilder;
import sample.utils.QueryUtils;

public class ModuleList extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	@Action("moduleList")
	public String execute() {
		return INPUT;
	}

	@Action("moduleDatagrid")
	public void datagrid() {
		Map<String, Object> data = Maps.newHashMap();

		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addColumn(qb, "t.id");
		QueryUtils.addColumn(qb, "t.name");
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addOrder(qb, "t.sequence");
		QueryUtils.addOrder(qb, "t.id");
		data.put("modules", systemService.datagridModule(qb));

		qb = new QueryBuilder();
		QueryUtils.addColumn(qb, "t.id");
		QueryUtils.addColumn(qb, "t.moduleId");
		QueryUtils.addColumn(qb, "t.name");
		QueryUtils.addColumn(qb, "t.parentId");
		QueryUtils.addColumn(qb, "t.url");
		QueryUtils.addColumn(qb, "t.sequence");
		QueryUtils.addColumn(qb, "t.cssClass");
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addOrder(qb, "t.sequence");
		QueryUtils.addOrder(qb, "t.id");
		data.put("menus", systemService.datagridMenu(qb));

		writeJson(data);
	}
}
