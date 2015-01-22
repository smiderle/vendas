package br.com.vendas.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vendas.domain.customer.Customer;

public class CustomerBuilder {
	
	private final String URL_FOTO = "https://s3-sa-east-1.amazonaws.com/vendas.pictures.product/23ab5f12-fd42-4f54-a17d-775e1d63eb0a"; 
	
	public List<Customer> create(Integer organizationID, Integer branchID){
		Customer customer = new Customer(organizationID, branchID, "1", "GETULIO VARGAS", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);		
		Customer customer2 = new Customer(organizationID, branchID, "2", "GETULIO VARGAS", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer3 = new Customer(organizationID, branchID, "3", "JUCELINO KUBICHEK", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer4 = new Customer(organizationID, branchID, "4", "DEODORO DA FONSECA", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer5 = new Customer(organizationID, branchID, "5", "FLORIANO PEIXOTO", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer6 = new Customer(organizationID, branchID, "6", "PRUDENTE DE MORAIS", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer7 = new Customer(organizationID, branchID, "7", "CAMPOS SALES", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer8 = new Customer(organizationID, branchID, "8", "RODRIGUES ALVES", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer9 = new Customer(organizationID, branchID, "9", "AFONSO PENA", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		Customer customer10 = new Customer(organizationID, branchID, "10", "NILO PEÇANHA", "GETULIO", 1, "11111111111", "55555555", "11111111", "22222222", "11111111", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, URL_FOTO, null, null, null);
		
		
		
		List<Customer> customers = new ArrayList<>();
		customers.add( customer );
		customers.add( customer2);
		customers.add( customer3);
		customers.add( customer4 );
		customers.add( customer5 );
		customers.add( customer6 );
		customers.add( customer7 );
		customers.add( customer8 );
		customers.add( customer9 );
		customers.add( customer10 );		
		return customers;		
	}
}