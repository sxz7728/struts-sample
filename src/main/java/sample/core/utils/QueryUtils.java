package sample.core.utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import sample.core.info.UserInfo;

import com.google.common.collect.Lists;

public class QueryUtils {
	public static QueryBuilder addColumn(QueryBuilder qb, String str, Object... params) {
		return qb.addColumn(str, params);
	}

	public static QueryBuilder addColumn(QueryBuilder qb, String name) {
		return qb.addColumn(ColumnUtils.column(name));
	}

	public static QueryBuilder addColumn(QueryBuilder qb, String name, String alias) {
		return qb.addColumn(ColumnUtils.column(name, alias));
	}

	public static QueryBuilder addColumnDict(QueryBuilder qb, String name, String type) {
		return qb.addColumn(ColumnUtils.dictValue(name, type));
	}

	public static QueryBuilder addColumnDict(QueryBuilder qb, String name, String type, String alias) {
		return qb.addColumn(ColumnUtils.dictValue(name, type, alias));
	}

	public static QueryBuilder addSetColumn(QueryBuilder qb, String name, Object param) {
		return qb.addColumn(name + " = {0}", param);
	}

	public static QueryBuilder addSetColumn(QueryBuilder qb, UserInfo userInfo) {
		qb.addColumn("t.operatorId = {0}", userInfo.getUserId());
		qb.addColumn("t.operateDate = {0}", userInfo.getOperateDate());
		return qb;
	}

	public static QueryBuilder addWhere(QueryBuilder qb, String str, Object... params) {
		return qb.addWhere(str, params);
	}

	public static QueryBuilder addWhereIfNotNull(QueryBuilder qb, String str, Object param) {
		if (param != null) {
			qb.addWhere(str, param);
		}

		return qb;
	}

	public static QueryBuilder addWhereIfNotEmpty(QueryBuilder qb, String str, String param) {
		if (!StringUtils.isEmpty(param)) {
			qb.addWhere(str, param);
		}

		return qb;
	}

	public static QueryBuilder addWhereIfNotEmpty(QueryBuilder qb, String str, Collection<?> param) {
		if (!CollectionUtils.isEmpty(param)) {
			qb.addWhere(str, param);
		}

		return qb;
	}

	public static QueryBuilder addWhereWithDefault(QueryBuilder qb, String str, Collection<?> param, Object obj) {
		return qb.addWhere(str, CollectionUtils.isEmpty(param) ? Lists.newArrayList(obj) : param);
	}

	public static QueryBuilder addOrder(QueryBuilder qb, String str) {
		return qb.addOrder(str);
	}

	public static QueryBuilder addGroup(QueryBuilder qb, String str) {
		return qb.addGroup(str);
	}

	public static QueryBuilder addHaving(QueryBuilder qb, String str) {
		return qb.addHaving(str);
	}

	public static QueryBuilder addJoin(QueryBuilder qb, String str) {
		return qb.addJoin(str);
	}
}
