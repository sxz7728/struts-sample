package sample.core.utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class QueryUtils {
	public static QueryBuilder addColumn(QueryBuilder qb, String str,
			Object... params) {
		return qb.addColumn(str, params);
	}

	public static QueryBuilder addColumn(QueryBuilder qb, String name) {
		return qb.addColumn(ColumnUtils.column(name));
	}

	public static QueryBuilder addColumn(QueryBuilder qb, String name,
			String alias) {
		return qb.addColumn(ColumnUtils.column(name, alias));
	}

	public static QueryBuilder addSetColumn(QueryBuilder qb, String name,
			Object param) {
		return qb.addColumn(name + " = {0}", param);
	}

	public static QueryBuilder addWhere(QueryBuilder qb, String str,
			Object... params) {
		return qb.addWhere(str, params);
	}

	public static QueryBuilder addWhereIfNotNull(QueryBuilder qb, String str,
			Object param) {
		if (param != null) {
			qb.addWhere(str, param);
		}

		return qb;
	}

	public static QueryBuilder addWhereIfNotEmpty(QueryBuilder qb, String str,
			String param) {
		if (!StringUtils.isEmpty(param)) {
			qb.addWhere(str, param);
		}

		return qb;
	}

	public static QueryBuilder addWhereIfNotEmpty(QueryBuilder qb, String str,
			Collection<?> param) {
		if (!CollectionUtils.isEmpty(param)) {
			qb.addWhere(str, param);
		}

		return qb;
	}

	public static QueryBuilder addWhereWithDefault(QueryBuilder qb, String str,
			Collection<?> param, Object obj) {
		return qb.addWhere(str,
				CollectionUtils.isEmpty(param) ? Lists.newArrayList(obj)
						: param);
	}

	public static QueryBuilder addOrder(QueryBuilder qb, String str) {
		return qb.addOrder(str);
	}
}
