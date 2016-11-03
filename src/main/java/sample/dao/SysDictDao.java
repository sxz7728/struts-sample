package sample.dao;

import java.util.List;
import java.util.Map;

import sample.model.SysDict;
import sample.utils.QueryBuilder;

public interface SysDictDao extends BaseDao<SysDict> {
	public List<Map<String, ?>> dictionary(QueryBuilder qb);
}
