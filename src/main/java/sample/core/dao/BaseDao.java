package sample.core.dao;

import java.util.List;
import java.util.Map;

import sample.core.utils.Datagrid;
import sample.core.utils.QueryBuilder;

public interface BaseDao<T> {
	public T load(Integer id);

	public T save(T obj);

	public T update(T obj);

	public T saveOrUpdate(T obj);

	public Integer update(QueryBuilder qb);

	public void delete(T obj);

	public void delete(Integer id);

	public Integer delete(QueryBuilder qb);

	public List<T> find(QueryBuilder qb);

	public Integer count(QueryBuilder qb);

	public Datagrid datagrid(QueryBuilder qb);

	public List<T> hqlList(String hql, QueryBuilder qb);

	public List<Map<String, ?>> hqlListMap(String hql, QueryBuilder qb);

	public <U> List<U> hqlListBean(String hql, QueryBuilder qb, Class<U> clazz);

	public <U> U hqlUnique(String hql, QueryBuilder qb);

	public List<T> sqlList(String sql, QueryBuilder qb);

	public List<Map<String, ?>> sqlListMap(String sql, QueryBuilder qb);

	public <U> List<U> sqlListBean(String sql, QueryBuilder qb, Class<U> clazz);

	public <U> U sqlUnique(String sql, QueryBuilder qb);
}
