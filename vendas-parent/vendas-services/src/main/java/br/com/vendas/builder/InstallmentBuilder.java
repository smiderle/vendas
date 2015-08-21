package br.com.vendas.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vendas.domain.order.Installment;

public class InstallmentBuilder {
	
	public List<Installment> create(Integer organizationID, Integer branchID){
		List<Installment> installments = new ArrayList<>();
		Installment installment = new Installment(organizationID, branchID, 1, "30x60x90", "30 60 90", 10.0, true, new Date(), false);
		Installment installment2 = new Installment(organizationID, branchID, 2, "30x60", "30 60", 5.0, true, new Date(), false);
		Installment installment3 = new Installment(organizationID, branchID, 3, "Entrada 30 60", "0 30 60", 2.0, true, new Date(), false);
		Installment installment4 = new Installment(organizationID, branchID, 4, "A VISTA", "0", 0.0, true, new Date(), false);
		Installment installment5 = new Installment(organizationID, branchID, 5, "30x60x90x120x150", "30 60 90 120 150", 20.0, true, new Date(), false);
		
		
		installments.add(installment);
		installments.add(installment2);
		installments.add(installment3);
		installments.add(installment4);
		installments.add(installment5);
		return installments;
	}

}
