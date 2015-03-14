package sample.core.dao;

import java.util.List;
import java.util.Map;

import sample.core.model.SysDict;
import sample.core.utils.QueryBuilder;

public interface SysDictDao extends BaseDao<SysDict> {
	public List<Map<String, ?>> dictionary(QueryBuilder qb);
}
