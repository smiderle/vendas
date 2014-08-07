package br.com.vendas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

public interface GenericDAO<DOMAINTYPE> {
	Session getSession();
	DOMAINTYPE save(Object data);
	List<DOMAINTYPE> save(List<DOMAINTYPE> data);		
	DOMAINTYPE saveOrUpdate(Object data);	
	List<DOMAINTYPE> saveOrUpdate(List<DOMAINTYPE> datas);
	DOMAINTYPE load(Object id);
	List<DOMAINTYPE> loadAll();
	void delete(DOMAINTYPE entity) ;
	void flush() ;
	void clear() ;
    List<DOMAINTYPE> findByCriteria(Criterion... criterion) ;	
}
