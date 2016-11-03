package sample.action.sys;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import sample.model.SysDict;
import sample.service.SystemService;
import sample.action.BaseAction;
import sample.utils.DictUtils;
import sample.utils.QueryBuilder;
import sample.utils.QueryUtils;

public class DictList extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemService systemService;

	private String type;

	private String parentKey;

	private String typeName;

	@Action("dictList")
	public String execute() {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.type = {0}", DictUtils.DICT_TYPE);
		QueryUtils.addWhere(qb, "and t.dictKey = {0}", type);
		SysDict sysDict = systemService.getDict(qb);
		typeName = sysDict.getDictValue();
		return INPUT;
	}

	@Action("dictDatagrid")
	public void datagrid() {
		QueryBuilder qb = new QueryBuilder(getStart(), getLength());
		QueryUtils.addColumn(qb, "t.dictKey");
		QueryUtils.addColumn(qb, "t.dictValue");
		QueryUtils.addColumn(qb, "t.sequence");
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.type = {0}", type);

		if (!StringUtils.isBlank(getQueryName())) {
			QueryUtils.addWhere(qb, "and (t.dictKey like {0} or t.dictValue like {0})", "%" + getQueryName() + "%");
		}

		QueryUtils.addOrder(qb, "t.sequence");
		QueryUtils.addOrder(qb, "t.id");
		writeJson(systemService.datagridDict(qb));
	}

	@Action("dictDictionary")
	public void dictionary() {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.type = {0}", type);
		QueryUtils.addWhereIfNotEmpty(qb, "and t.parentKey = {0}", parentKey);
		QueryUtils.addOrder(qb, "t.sequence");
		QueryUtils.addOrder(qb, "t.id");
		writeJson(systemService.dictionaryDict(qb));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
