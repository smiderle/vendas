package br.com.vendas.dao.tabelapreco;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.pedido.PriceTable;

@Repository
public class PriceTableDAOImpl  extends ResourceDAO<PriceTable> implements PriceTableDAO{

	@Override
	public List<PriceTable> findAllByBranche(Long organizationID, Long branchID) {
		Criterion criterion =
				Restrictions.and(
						Restrictions.eq("excluded", false),
						Restrictions.and(
									Restrictions.eq("organizationID", organizationID), 
									Restrictions.eq("branchID", branchID)));				
		return findByCriteria(Order.asc("priceTableID"), criterion);
		
	}

}