package br.com.vendas.domain.application;

import java.util.LinkedHashSet;
import java.util.Set;

import br.com.vendas.domain.converter.MenuApplicationConverter;

public class MenuApplicationPojo {
	
	public MenuApplicationPojo(MenuApplication menuApplication) {
		setMenuID(menuApplication.getMenuID());
		//setChildrenMenu(new MenuApplicationConverter().convert(menuApplication.getChildrenMenu()));
		setIcon(menuApplication.getIcon());
		setIsSubmenu(menuApplication.isSubmenu());
		setLabel(menuApplication.getLabel());
		setOrder(menuApplication.getOrder());
		setUrl(menuApplication.getUrl());
	}

	private Integer menuID;
	
	private String icon;
	
	private String label;
		
	private Integer order;
	
	private String url;
	
	private Boolean isSubmenu;
	
	private Set<MenuApplicationPojo> childrenMenu = new LinkedHashSet<MenuApplicationPojo>();

	public Integer getMenuID() {
		return menuID;
	}

	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsSubmenu() {
		return isSubmenu;
	}

	public void setIsSubmenu(Boolean isSubmenu) {
		this.isSubmenu = isSubmenu;
	}

	public Set<MenuApplicationPojo> getChildrenMenu() {
		return childrenMenu;
	}

	public void setChildrenMenu(Set<MenuApplicationPojo> childrenMenu) {
		this.childrenMenu = childrenMenu;
	}

	


}
