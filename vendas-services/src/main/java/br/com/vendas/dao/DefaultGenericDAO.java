package br.com.vendas.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract class DefaultGenericDAO<DOMAINTYPE> implements
		GenericDAO<DOMAINTYPE> {

	public abstract EntityManager getEntityManager();

	public abstract void setEntityManager(EntityManager entityManager);

	private final Class<DOMAINTYPE> entityType;

	@SuppressWarnings("unchecked")
	public DefaultGenericDAO() {
		entityType = (Class<DOMAINTYPE>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DOMAINTYPE save(Object data) {
		/*
		 * saveOrUpdate is better than persist/merge as it allows us to change
		 * the ID on an object without going back to the db to check its
		 * version.
		 * 
		 * The 'Putting it all together' table at:
		 * http://blog.xebia.com/2009/03/
		 * 23/jpa-implementation-patterns-saving-detached-entities/ is very
		 * helpful for this
		 */
		try {
			Session session = getSession();
			session.save(data);
		} catch (NonUniqueObjectException e) { // this should update and sync things back up
			data = getEntityManager().merge(data);
		}
		return (DOMAINTYPE) data;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DOMAINTYPE> save(List<DOMAINTYPE> data) {
		List<DOMAINTYPE> toReturn = new ArrayList<>();
		Session session = getSession();
		for (DOMAINTYPE domaintype : data) {
			session.save(domaintype);
			toReturn.add(domaintype);
		}
		return toReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DOMAINTYPE saveOrUpdate(Object data){
		try {
			Session session = getSession();
			session.saveOrUpdate(data);
		} catch (NonUniqueObjectException e) { // this should update and sync things back up
			data = getEntityManager().merge(data);
		}
		return (DOMAINTYPE) data;
	}
	
	
	@Override
	public List<DOMAINTYPE> saveOrUpdate(List<DOMAINTYPE> data){
		List<DOMAINTYPE> toReturn = new ArrayList<>();		
		Session session = getSession();
		for (DOMAINTYPE domaintype : data) {
			session.saveOrUpdate(domaintype);
			toReturn.add(domaintype);
		}
		return toReturn ;
	}

	@Override
	public DOMAINTYPE load(Object id) {
		return getEntityManager().find(entityType, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DOMAINTYPE> loadAll() {
		return getEntityManager().createQuery(
				"FROM " + renderClassName(entityType)).getResultList();
	}

	public void delete(DOMAINTYPE entity) {
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	@SuppressWarnings("unchecked")
    public List<DOMAINTYPE> findByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(entityType);  
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();
    }
	
	@SuppressWarnings("unchecked")
    public List<DOMAINTYPE> findByCriteria(Order order,Criterion... criterion) {
		Criteria crit = getSession().createCriteria(entityType);  
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if(order != null){
        	crit.addOrder(order);
        }
        return crit.list();
    }

	private static String renderClassName(Class<?> clazz) {
		String className = clazz.getCanonicalName();
		int lastDot = className.lastIndexOf(".");
		return className.substring(lastDot + 1);
	}
}
