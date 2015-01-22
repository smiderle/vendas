package br.com.vendas.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vendas.domain.product.Product;

public class ProductBuilder {
	
	private final String URL_FOTO = "https://s3-sa-east-1.amazonaws.com/vendas.pictures.product/23ab5f12-fd42-4f54-a17d-775e1d63eb0a";

	
	public List<Product> create( Integer organizationID,  Integer branchID ) {
		Product product = new Product(organizationID, branchID, "1", null,"Batata Doce Roxa 700g", "Batata Doce Roxa 700g", "SC","9876543210123", 0.88, 1800.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product2 = new Product(organizationID, branchID, "2", null,"Batata Tipo Inglesa Inteira Descascada Quasi Pronto a Vácuo", "Pronto a Vácuo", "SC","9874561230123", 17.65, 432.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product3 = new Product(organizationID, branchID, "3", null,"Salame Italiano fatiado Sadia 100g", "Salame", "KG","9876542546986", 17.55, 9862.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product4 = new Product(organizationID, branchID, "4", null,"Mortadela de frango light Aurora 500g", "alguma referencia", "PÇ","9878976249824", 7.54, 200.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product5 = new Product(organizationID, branchID, "5", null,"Arroz Tio João Branco Tipo 1 Pacote 5000 g", "alguma referencia", "SC","9876578901358", 8.88, 1500.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product6 = new Product(organizationID, branchID, "6", null,"Feijão Carioca Camil Tipo 1 Pacote 1 Kg", "alguma referencia", "SC","9876549827634", 4.91, 885.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product7 = new Product(organizationID, branchID, "7", null,"Sabonete Phebo Odor de Rosas 90 Gramas", "alguma referencia", "SC","9877896369994", 1.32, 732.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product8 = new Product(organizationID, branchID, "8", null,"Maionese Hellmann's Light Pote 500 g", "alguma referencia", "SC","9877878787878", 4.99, 320.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product9 = new Product(organizationID, branchID, "9", null,"Suco Pronto Do bem Limonada Caixa 200 ml", "alguma referencia", "SC","9876544120132", 6.54, 488.0, true, new Date(), new Date(), false, URL_FOTO);				
		Product product10 = new Product(organizationID, branchID, "10", null,"Cerveja Skol Pilsen Lata 269 ml 1 Unidade", "alguma referencia", "LT","9876587320132", 1.54, 1988.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product11 = new Product(organizationID, branchID, "11", null,"Cerveja Itaipava Pilsen Lata 350 ml 1 Unidade", "alguma referencia", "LT","9876534870132", 1.31, 450.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product12 = new Product(organizationID, branchID, "12", null,"Cerveja Brhama", "alguma referencia", "LT","9876587320867", 2.00, 1988.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product13 = new Product(organizationID, branchID, "13", null,"Cerveja Stella Artois Premium Lager Long Neck 275 ml 1 Unidade", "alguma referencia", "GR","9876587320222", 2.14, 2188.0, true, new Date(), new Date(), false, URL_FOTO);		
		Product product14 = new Product(organizationID, branchID, "14", null,"Doritos Queijo Nacho Elma Chips 110 g", "alguma referencia", "PC","9873157320222", 4.13, 1874.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product15 = new Product(organizationID, branchID, "15", null,"Salgadinho Batata Palha Tradicional Visconti 140 g", "alguma referencia", "PC","9876535920222", 3.95, 7892.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product16 = new Product(organizationID, branchID, "16", null,"Ruffles Original Elma Chips 100 g", "alguma referencia", "GR","9876587246222", 5.91, 13874.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product17 = new Product(organizationID, branchID, "17", null,"Presunto cozido sem capa de gordura fatiado Sadia 250g", "alguma referencia", "GR","98765858502", 5.88, 154.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product18 = new Product(organizationID, branchID, "18", null,"Queijo Parmesão Ralado", "alguma referencia", "GR","9876514140222", 5.11, 3169.0, true, new Date(), new Date(), false, URL_FOTO);
		Product product19 = new Product(organizationID, branchID, "19", null,"Margarina Becel Original Com Sal 250 g", "alguma referencia", "GR","9876587320222", 8.11, 9354.0, true, new Date(), new Date(), false, URL_FOTO);
				
		List<Product> products = new ArrayList<>();
		
		products.add(product);
		products.add(product2);
		products.add(product3);
		products.add(product4);
		products.add(product5);
		products.add(product6);
		products.add(product7);
		products.add(product8);
		products.add(product9);
		products.add(product10);
		products.add(product11);
		products.add(product12);
		products.add(product13);
		products.add(product14);
		products.add(product15);
		products.add(product16);
		products.add(product17);
		products.add(product18);
		products.add(product19);
		
		
		
		return products;
		
	}
}
