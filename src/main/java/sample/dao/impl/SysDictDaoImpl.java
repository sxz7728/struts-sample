package sample.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import sample.dao.SysDictDao;
import sample.model.SysDict;
import sample.utils.ColumnUtils;
import sample.utils.QueryBuilder;

@Repository
public class SysDictDaoImpl extends BaseDaoImpl<SysDict> implements SysDictDao {
	public final String HQL_DICTIONARY;

	public SysDictDaoImpl() {
		HQL_DICTIONARY = " select " + ColumnUtils.column("t.type") + "," + ColumnUtils.column("t.dictKey", "key") + ","
				+ ColumnUtils.column("t.dictValue", "value") + " {0} from " + modelClass.getSimpleName()
				+ " {5} where 1 = 1 {1} {3} {4} {2} ";
	}

	public List<Map<String, ?>> dictionary(QueryBuilder qb) {
		return hqlListMap(HQL_DICTIONARY, qb);
	}
}
