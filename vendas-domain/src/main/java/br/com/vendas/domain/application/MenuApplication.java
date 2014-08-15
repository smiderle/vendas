package br.com.vendas.domain.application;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.com.vendas.domain.Domain;

@Entity
@Table(name="MENU_APLICACAO")
public class MenuApplication extends Domain{
	
	private static final long serialVersionUID = 8930646934576454374L;

	public MenuApplication() {	
	}
	
	public MenuApplication(Integer menuID) {	
		this.menuID = menuID;
	}

	@Id
	@Column(name="IDMENU")
	private Integer menuID;
	
	@Column
	private String icon;
	
	@Column(nullable=false)
	private String label;
		
	@Column(name="ordem", nullable=false)
	private Integer order;
	
	@Column
	private String url;
	
	
	@Column(name="submenu")
	private Boolean isSubmenu;
	
	@ManyToOne(fetch=FetchType.EAGER)	
	@JoinColumn(name="MENUPAI", nullable=true)
	@JsonIgnore
	private MenuApplication parentMenu;
	
	@OrderBy("order")
	@OneToMany(fetch=FetchType.EAGER,cascade =CascadeType.ALL, mappedBy="parentMenu")
	private Set<MenuApplication> childrenMenu = new LinkedHashSet<MenuApplication>();

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

	public MenuApplication getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(MenuApplication parentMenu) {
		this.parentMenu = parentMenu;
	}

	

	public Boolean getIsSubmenu() {
		return isSubmenu;
	}

	public void setIsSubmenu(Boolean isSubmenu) {
		this.isSubmenu = isSubmenu;
	}

	public Set<MenuApplication> getChildrenMenu() {
		return childrenMenu;
	}

	public void setChildrenMenu(Set<MenuApplication> childrenMenu) {
		this.childrenMenu = childrenMenu;
	}

	public Boolean isSubmenu() {
		return isSubmenu;
	}

	public void setSubmenu(Boolean isSubmenu) {
		this.isSubmenu = isSubmenu;
	}
	
	
}
