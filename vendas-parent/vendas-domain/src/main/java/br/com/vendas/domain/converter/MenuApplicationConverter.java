package br.com.vendas.domain.converter;

import java.util.LinkedHashSet;
import java.util.Set;

import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.application.MenuApplicationPojo;

public class MenuApplicationConverter {
	
	/**
	 * Converte uma lista de MenuApplication em uma lista de MenuApplicationPojo
	 * @param menus
	 * @return
	 */
	public Set<MenuApplicationPojo> convert(Set<MenuApplication> menus){
		Set<MenuApplicationPojo> menusPojo = new LinkedHashSet<MenuApplicationPojo>();
		for (MenuApplication menuApplication : menus) {
			menusPojo.add(new MenuApplicationPojo(menuApplication));
		}
		return menusPojo;
	}
}