package br.com.vendas.helper;

import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.order.Installment;
import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.domain.order.PriceTable;
import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.product.Product;
import br.com.vendas.domain.product.ProductGroup;
import br.com.vendas.domain.user.User;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.domain.user.UserAction.Category;
import br.com.vendas.domain.user.UserAction.Operation;

public class UserActionHelper {

	/**
	 * Cadastro de um usuário
	 * @param userID
	 * @param title
	 * @param description
	 */
	public static UserAction createUserSave(Integer fromUserID, User user) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.USER);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Usuário");
		userAction.setDescription("Usuário cadastrou o usuário \""+ user.getName() + "\"");
		return userAction;
	}
	
	/**
	 * Edição do usuario
	 * @param userID
	 * @param title
	 * @param description
	 */
	public static UserAction createUserEdit(Integer fromUserID, User user) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.USER);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição de Usuário");
		userAction.setDescription("Usuário alterou o usuário \"" + user.getName() + "\"");
		return userAction;
	}
	
	/**
	 * Remoção de um usuário
	 * @param userID
	 * @param title
	 * @param description
	 */
	public static UserAction createUserDelete(Integer fromUserID, String title, User user) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.USER);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Usuário Removido");
		userAction.setDescription("Usuário removeu o usuário \"" + user.getName() + "\"");
		return userAction;
	}
	
	/**
	 * Cadastro de usuario
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createCustomerSave(Integer fromUserID, Customer customer) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.CUSTOMER);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Cliente");
		userAction.setDescription("Usuário cadastrou o cliente \"" + customer.getName() + "\"");
		return userAction;
	}
	
	/**
	 * Edição de Cliente
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createCustomerEdit(Integer fromUserID, Customer customer) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.CUSTOMER);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição de Cliente");
		userAction.setDescription("Usuário alterou o cliente \"" + customer.getName() + "\"");
		return userAction;
	}
	
	/**
	 * Remoção de Cliente
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createCustomerDelete(Integer fromUserID, Customer customer) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.CUSTOMER);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cliente Removido");
		userAction.setDescription("Usuário removeu o cliente \"" + customer.getName() + "\"");
		return userAction;
	}
	
	
	/**
	 * Cadastro de Produto
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createProductSave(Integer fromUserID, Product product) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRODUCT);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Produto");
		userAction.setDescription("Usuário cadastrou o produto \"" + product.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Edição de Produto
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createProductEdit(Integer fromUserID, Product product) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRODUCT);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição de Produto");
		userAction.setDescription("Usuário alterou o produto \"" + product.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Remoção de Produto
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createProductDelete(Integer fromUserID, Product product) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRODUCT);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Produto Removido");
		userAction.setDescription("Usuário removeu o produto \"" + product.getDescription() + "\"");
		return userAction;
	}
	
	
	
	
	/**
	 * Cadastro de Filial
	 * @param fromUserID
	 * @param 
	 * @return
	 */
	public static UserAction createBranchSave(Integer fromUserID, BranchOffice branch) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.BRANCH);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Filial");
		userAction.setDescription("Usuário cadastrou a filial \"" + branch.getFancyName() + "\"");
		return userAction;
	}
	
	/**
	 * Edição de Filial
	 * @param fromUserID
	 * @param 
	 * @return
	 */
	public static UserAction createBranchEdit(Integer fromUserID, BranchOffice branch) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.BRANCH);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição de Filial");
		userAction.setDescription("Usuário alterou a filial \"" + branch.getFancyName() + "\"");
		return userAction;
	}
	
	/**
	 * Remoção de Filial
	 * @param fromUserID
	 * @param 
	 * @return
	 */
	public static UserAction createBranchDelete(Integer fromUserID, BranchOffice branch) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.BRANCH);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Filial Removida");
		userAction.setDescription("Usuário removeu a filial \"" + branch.getFancyName() + "\"");
		return userAction;
	}
	
	/**
	 * Cadastro de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createPriceTableSave(Integer fromUserID, PriceTable priceTable) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRICE_TABLE);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Tabela de Preço");
		userAction.setDescription("Usuário cadastrou a  tabela de preço\"" + priceTable.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Edição de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createPriceTableEdit(Integer fromUserID, PriceTable priceTable) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRICE_TABLE);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição de Tabela de Preço");
		userAction.setDescription("Usuário editou a Tabela de Preço \"" + priceTable.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Remoção de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createPriceTableDelete(Integer fromUserID, PriceTable priceTable) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRICE_TABLE);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Tabela de Preço Removida");
		userAction.setDescription("Usuário removeu a tabela de preço \"" + priceTable.getDescription() + "\"");
		return userAction;
	}
	
	
	/**
	 * Cadastro de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createInstallmentSave(Integer fromUserID, Installment installment) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.INSTALLMENT);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Parcelamento");
		userAction.setDescription("Usuário cadastrou o parcelamento \"" + installment.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Edição de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createInstallmentEdit(Integer fromUserID, Installment installment) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.INSTALLMENT);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição do Parcelamento");
		userAction.setDescription("Usuário editou o parcelamento \"" + installment.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Remoção de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createInstallmentDelete(Integer fromUserID, Installment installment) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.INSTALLMENT);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Parcelamento Removido");
		userAction.setDescription("Usuário removeu o parcelamento \"" + installment.getDescription() + "\"");
		return userAction;
	}
	

	/**
	 * Cadastro de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createProductGroupSave(Integer fromUserID, ProductGroup productGroup) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRODUCT_GROUP);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Grupo de Produto");
		userAction.setDescription("Usuário cadastrou o grupo de produto \"" + productGroup.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Edição de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createProductGroupEdit(Integer fromUserID, ProductGroup productGroup) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRODUCT_GROUP);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição do Grupo de Produto");
		userAction.setDescription("Usuário editou o  grupo de produto \"" + productGroup.getDescription() + "\" foi editado");
		return userAction;
	}
	
	/**
	 * Remoção de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createProductGroupDelete(Integer fromUserID, ProductGroup productGroup) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PRODUCT_GROUP);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Grupo de Produto Removido");
		userAction.setDescription("Usuário removeu o grupo de produto \"" + productGroup.getDescription() + "\"");
		return userAction;
	}
	
	/**
	 * Cadastro de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createOrderSave(Integer fromUserID, Order order) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.ORDER);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Cadastro de Pedido/Orçamento");
		
		String description = "";
		
		if(order.getType() == Order.PEDIDO){
			description = "Usuário criou o Pedido Nº " + order.getID() +" para o cliente " + order.getCustomer().getName();
		} else {
			description = "Usuário criou o Orçamento Nº " + order.getID();
		}
		
		userAction.setDescription(description);
		
		return userAction;
	}
	
	/**
	 * Edição de Tabela de Preço
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createOrderEdit(Integer fromUserID, Order order) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.ORDER);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Edição do Pedido/Orçamento");

		String description = "";
		
		if(order.getType() == Order.PEDIDO){
			description = "Usuário editou o Pedido Nº " + order.getID() ;
		} else {
			description = "Usuário editou o Orçamento Nº " + order.getID() ;
		}
		
		userAction.setDescription(description);
		
		return userAction;
	}
	
	/**
	 * Remoção de Pedido
	 * @param fromUserID
	 * @param customer
	 * @return
	 */
	public static UserAction createOrderDelete(Integer fromUserID, Order order) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.ORDER);
		userAction.setOperation(Operation.DELETE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Pedido/Orçamento Removido");
		
		String description = "";
		
		if(order.getType() == Order.PEDIDO){
			description = "Usuário removeu o Pedido Nº " + order.getID() ;
		} else {
			description = "Usuário removeu o Orçamento Nº " + order.getID() ;
		}
		
		userAction.setDescription(description);
		return userAction;
	}
	
	public static UserAction createOrderPaymentEdit(Integer fromUserID, OrderPayment orderPayment) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.PAYMENT);
		userAction.setOperation(Operation.UPDATE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Baixa de parcela");		
		
		userAction.setDescription("Usuário efetuou a baixa da parcela Nº " + orderPayment.getID() + ", pedido Nº " + orderPayment.getOrder().getID());
		return userAction;
	}
	
	public static UserAction createUserAccess(Integer fromUserID) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.LOGIN);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Acesso ao Sistema");		
		
		userAction.setDescription("Usuário acessou o sistema");
		return userAction;
	}
	
	public static UserAction createTargetsSaveUpdate(Integer fromUserID) {
		UserAction userAction = new UserAction();
		userAction.setCategory(Category.TARGETS);
		userAction.setOperation(Operation.SAVE);
		userAction.setUserID(fromUserID);
		userAction.setTitle("Metas");		
		
		userAction.setDescription("Usuário cadastrou/alterou as metas de venda");
		return userAction;
	}

}
