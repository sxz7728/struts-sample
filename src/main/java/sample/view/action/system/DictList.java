package sample.view.action.system;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.core.service.SystemService;
import sample.core.utils.QueryBuilder;
import sample.core.utils.QueryUtils;
import sample.view.action.BaseAction;

public class DictList extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private String dictType;

	private Integer current;

	private Integer rowCount;

	@Action("dictList")
	public String execute() {
		return INPUT;
	}

	@Action("dictDatagrid")
	public void datagrid() {
		QueryBuilder qb = QueryUtils.addWhereNotDeleted(new QueryBuilder(
				current - 1, rowCount));
		QueryUtils.addColumn(qb, "t.dictKey");
		QueryUtils.addColumn(qb, "t.dictValue");
		// QueryUtils.addWhere(qb, "and t.dictType = {0}", dictType);
		writeJson(systemService.datagridDict(qb));
	}

	@Action("dictDictionary")
	public void dictionary() {
		QueryBuilder qb = QueryUtils.addWhereNotDeleted(new QueryBuilder());
		QueryUtils.addWhere(qb, "and t.dictType = {0}", dictType);
		writeJson(systemService.dictionaryDict(qb));
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

}
