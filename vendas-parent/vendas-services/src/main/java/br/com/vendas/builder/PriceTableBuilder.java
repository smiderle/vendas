package br.com.vendas.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vendas.domain.order.PriceTable;

public class PriceTableBuilder {

	public List<PriceTable> create(Integer organizationID, Integer branchID){
		PriceTable priceTable = new PriceTable(organizationID, branchID, 1, "TABELA PADRAO", 0.0, false, true, new Date(), false);
		/*PriceTable priceTable2 = new PriceTable(organizationID, branchID, 2, "TABELA DE PRECO ACRÉSCIMO 30%", 30.0, true, true, new Date(), false);
		PriceTable priceTable3 = new PriceTable(organizationID, branchID, 3, "TABELA DE PRECO ACRÉSCIMO 20%", 20.0, true, true, new Date(), false);
		PriceTable priceTable4 = new PriceTable(organizationID, branchID, 4, "TABELA DE PRECO DESCONTO 30%", 30.0, false, true, new Date(), false);
		PriceTable priceTable5 = new PriceTable(organizationID, branchID, 5, "TABELA DE PRECO DESCONTO 20%", 20.0, false, true, new Date(), false);*/

		List<PriceTable> tabelas = new ArrayList<>();
		tabelas.add(priceTable);
		/*tabelas.add(priceTable2);
		tabelas.add(priceTable3);
		tabelas.add(priceTable4);
		tabelas.add(priceTable5);*/
		return tabelas;
	}
}