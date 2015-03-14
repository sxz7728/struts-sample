package sample.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import sample.core.dao.SysDictDao;
import sample.core.model.SysDict;
import sample.core.utils.QueryBuilder;

@Repository
public class SysDictDaoImpl extends BaseDaoImpl<SysDict> implements SysDictDao {
	public final String HQL_DICTIONARY;

	public SysDictDaoImpl() {
		HQL_DICTIONARY = " select " + " t.dictType as type, "
				+ " t.dictKey as key, " + " t.dictValue as value "
				+ " {0} from " + modelClass.getSimpleName()
				+ " t where 1 = 1 {1} {2} ";
	}

	public List<Map<String, ?>> dictionary(QueryBuilder qb) {
		return hqlListMap(HQL_DICTIONARY, qb);
	}
}
