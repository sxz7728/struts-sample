package sample.core.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sample.core.dao.BaseDao;
import sample.core.utils.ColumnUtils;
import sample.core.utils.Datagrid;
import sample.core.utils.QueryBuilder;
import sample.core.utils.Utilities;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	public final Class<T> modelClass;

	public final String HQL_UPDATE;

	public final String HQL_DELETE;

	public final String HQL_FIND;

	public final String HQL_COUNT;

	public final String HQL_DATAGRID;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		super();
		modelClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		HQL_UPDATE = " update " + modelClass.getSimpleName()
				+ " t set {0} where 1 = 1 {1} ";

		HQL_DELETE = " delete " + modelClass.getSimpleName()
				+ " t where 1 = 1 {0} ";

		HQL_FIND = " from " + modelClass.getSimpleName()
				+ " t where 1 = 1 {0} {1} ";

		HQL_COUNT = " select count(*) from " + modelClass.getSimpleName()
				+ " t where 1 = 1 {0} ";

		HQL_DATAGRID = " select " + ColumnUtils.column("t.id") + " {0} from "
				+ modelClass.getSimpleName() + " t where 1 = 1 {1} {2} ";
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Query setParams(Query query, List<Object> params, String prefix) {
		if (params != null) {
			for (int i = 0; i < params.size(); ++i) {
				if (params.get(i) instanceof Collection) {
					query.setParameterList(prefix + (i + 1),
							(Collection<?>) params.get(i));
				} else {
					query.setParameter(prefix + (i + 1), params.get(i));
				}
			}
		}

		return query;
	}

	protected Query setColumnParams(Query query, QueryBuilder qb) {
		return setParams(query, qb.getColumnParams(), qb.PREFIX_COLUMN_PARAMS);
	}

	protected Query setWhereParams(Query query, QueryBuilder qb) {
		return setParams(query, qb.getWhereParams(), qb.PREFIX_WHERE_PARAMS);
	}

	protected Query setFirstResult(Query query, QueryBuilder qb) {
		if (qb.getStart() > 0) {
			query.setFirstResult(qb.getStart());
		}

		return query;
	}

	protected Query setMaxResults(Query query, QueryBuilder qb) {
		if (qb.getLength() > 0) {
			query.setMaxResults(qb.getLength());
		}

		return query;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T load(Integer id) {
		return (T) getSession().load(modelClass, id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T save(T obj) {
		getSession().save(obj);
		return obj;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T update(T obj) {
		getSession().update(obj);
		return obj;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T saveOrUpdate(T obj) {
		getSession().saveOrUpdate(obj);
		return obj;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer update(QueryBuilder qb) {
		if (!qb.hasColumn() || !qb.hasWhere()) {
			throw new RuntimeException(
					"update must have column and where condition.");
		}

		Query query = getSession().createQuery(
				Utilities.format(HQL_UPDATE, qb.getColumn(), qb.getWhere()));
		setWhereParams(query, qb);
		setColumnParams(query, qb);
		return query.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T obj) {
		getSession().delete(obj);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer id) {
		delete(load(id));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer delete(QueryBuilder qb) {
		if (!qb.hasWhere()) {
			throw new RuntimeException("delete must have where condition.");
		}

		String hql = Utilities.format(HQL_DELETE, qb.getWhere());
		return setWhereParams(getSession().createQuery(hql), qb)
				.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> find(QueryBuilder qb) {
		return hqlList(HQL_FIND, qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer count(QueryBuilder qb) {
		return hqlUnique(HQL_COUNT, qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagrid(QueryBuilder qb) {
		return datagrid(HQL_DATAGRID, HQL_COUNT, qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	protected Datagrid datagrid(String hqlFind, String hqlCount, QueryBuilder qb) {
		Datagrid result = new Datagrid();
		result.setRows(hqlListMap(hqlFind, qb));

		if (qb.getLength() > 0) {
			result.setCount(this.<Integer> hqlUnique(hqlCount, qb));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> hqlList(String hql, QueryBuilder qb) {
		Query query = getSession().createQuery(
				Utilities.format(hql, qb.getWhere(), qb.getOrder()));
		setWhereParams(query, qb);
		setFirstResult(query, qb);
		setMaxResults(query, qb);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map<String, ?>> hqlListMap(String hql, QueryBuilder qb) {
		Query query = getSession().createQuery(
				Utilities.format(hql,
						(qb.hasColumn() ? "," : "") + qb.getColumn(),
						qb.getWhere(), qb.getOrder()));
		setColumnParams(query, qb);
		setWhereParams(query, qb);
		setFirstResult(query, qb);
		setMaxResults(query, qb);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public <U> List<U> hqlListBean(String hql, QueryBuilder qb, Class<U> clazz) {
		Query query = getSession().createQuery(
				Utilities.format(hql, qb.getWhere(), qb.getOrder()));
		setWhereParams(query, qb);
		setFirstResult(query, qb);
		setMaxResults(query, qb);
		return query.setResultTransformer(Transformers.aliasToBean(clazz))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public <U> U hqlUnique(String hql, QueryBuilder qb) {
		Query query = getSession().createQuery(
				Utilities.format(hql, qb.getWhere()));
		setWhereParams(query, qb);
		return (U) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> sqlList(String sql, QueryBuilder qb) {
		Query query = getSession().createSQLQuery(
				Utilities.format(sql, qb.getWhere(), qb.getOrder()));
		setWhereParams(query, qb);
		setFirstResult(query, qb);
		setMaxResults(query, qb);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map<String, ?>> sqlListMap(String sql, QueryBuilder qb) {
		Query query = getSession().createSQLQuery(
				Utilities.format(sql,
						(qb.hasColumn() ? "," : "") + qb.getColumn(),
						qb.getWhere(), qb.getOrder()));
		setColumnParams(query, qb);
		setWhereParams(query, qb);
		setFirstResult(query, qb);
		setMaxResults(query, qb);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public <U> List<U> sqlListBean(String sql, QueryBuilder qb, Class<U> clazz) {
		Query query = getSession().createSQLQuery(
				Utilities.format(sql, qb.getWhere(), qb.getOrder()));
		setWhereParams(query, qb);
		setFirstResult(query, qb);
		setMaxResults(query, qb);
		return query.setResultTransformer(Transformers.aliasToBean(clazz))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public <U> U sqlUnique(String sql, QueryBuilder qb) {
		Query query = getSession().createSQLQuery(
				Utilities.format(sql, qb.getWhere()));
		setWhereParams(query, qb);
		return (U) query.uniqueResult();
	}
}