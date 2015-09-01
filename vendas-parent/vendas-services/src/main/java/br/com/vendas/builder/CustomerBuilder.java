package br.com.vendas.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vendas.core.util.PictureUtil;
import br.com.vendas.domain.customer.Customer;

public class CustomerBuilder {


	public List<Customer> create(Integer organizationID, Integer branchID){
		Customer customer = new Customer(organizationID, branchID, "1", "Ronaldinho Gaucho", "RONALDINHO", 1, "80127475532", "85856521", "(44) 3265-7478", "(44) 3265-7478", "(44) 3265-7478", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/555555-e511-473c-8ffe-5e8dcc9f666b-ronaldinho.jpg" , null, null, null);
		Customer customer2 = new Customer(organizationID, branchID, "2", "Michael Jordan", "Michael", 1, "18715804127", "84565412", "(41) 4454-4569", "(41) 4454-4569", "(41) 4454-4569", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/8c753f1c-0ade-11e5-a6c0-1697f925ec7b-maicon.jpg", null, null, null);
		Customer customer3 = new Customer(organizationID, branchID, "3", "Gustavo Kuerten", "Guga", 1, "88829645230", "23457712", "(46) 3225-4566", "(46) 3225-4566", "(46) 3225-4566", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/0eaee528-0adf-11e5-a6c0-1697f925ec7b-guga.jpg", null, null, null);
		Customer customer4 = new Customer(organizationID, branchID, "4", "Dercy Gonçalves", "Dercy", 1, "53429593905", "549321565", "(45) 3525-6478", "(45) 3525-6478", "(45) 3525-6478", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false,"https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/450af904-0adf-11e5-a6c0-1697f925ec7b-dercy.jpg", null, null, null);
		Customer customer5 = new Customer(organizationID, branchID, "5", "Hebe Camargo", "Hebe", 1, "82331521530", "32641211", "(46) 3562-1236", "(46) 3562-1236", "(46) 3562-1236", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/5ebbdeea-0adf-11e5-a6c0-1697f925ec7b-hebe.jpg", null, null, null);
		Customer customer6 = new Customer(organizationID, branchID, "6", "Felipe Massa", "Felipe Massa", 1, "45084427145", "38792512", "(46) 3224-4545", "(46) 3224-4545", "(46) 3224-4545", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/caf88cac-0adf-11e5-a6c0-1697f925ec7b-massa.jpg", null, null, null);
		Customer customer7 = new Customer(organizationID, branchID, "7", "Neymar Junior", "GETULIO", 1, "72742535900", "1113657462", "(41) 2311-1232", "(41) 2311-1232", "(41) 2311-1232", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/362619f4-0ae0-11e5-a6c0-1697f925ec7b-neymar.jpg", null, null, null);
		Customer customer8 = new Customer(organizationID, branchID, "8", "Oscar Schmidt", "Oscar", 1, "62132577480", "336523511", "(65) 3245-3254", "(65) 3245-3254", "(65) 3245-3254", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/91bd1ea2-0ae0-11e5-a6c0-1697f925ec7b-oscar.jpg", null, null, null);
		Customer customer9 = new Customer(organizationID, branchID, "9", "Fatima Bernardes", "Fatima", 1, "76688113546", "22585521", "(63) 3425-6545", "(63) 3425-6545", "(63) 3425-6545", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/d2f04098-0ae0-11e5-a6c0-1697f925ec7b-fatima.jpg", null, null, null);
		Customer customer10 = new Customer(organizationID, branchID, "10", "Angélica Ksyvickis", "Angélica", 1, "42719761648", "336989556", "(25) 3125-2525", "(25) 3125-2525", "(25) 3125-2525", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, "https://s3-sa-east-1.amazonaws.com/vendasup.pictures.default/337c4380-0ae1-11e5-a6c0-1697f925ec7b-angelica.jpg", null, null, null);
		Customer customer11 = new Customer(organizationID, branchID, "10", "Luciano Huck", "Luciano Huck", 1, "42719761648", "336989556", "(25) 3125-2525", "(25) 3125-2525", "(25) 3125-2525", "88888888", "COMPLEMENTO", "ALGUMA OBSERVAÇÃO", new Date(), new Date(), null, "11111111", "NOME DA RUA", "BAIRRO", "80", "email@gmail.com", 0.0, 100000.0, null, true, new Date(), false, PictureUtil.URL_USUARIO_SEM_FOTO, null, null, null);



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
		customers.add( customer11 );
		return customers;
	}
}