package br.com.vendas.pojo;

import java.util.List;

import br.com.vendas.domain.target.Goal;

/**
 * Classe que contém o usuário e as metas de venda
 * @author Ladair C. Smiderle Junior 04/12/2014 - 20:06:06
 *
 */
public class UserTargetsDTO {
	
	public UserTargetsDTO() {
	}
	
	
	public UserTargetsDTO(UserDTO user, List<Goal> targets) {
		super();
		this.user = user;
		this.targets = targets;
	}

	private UserDTO user;
	
	private List<Goal> targets;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<Goal> getTargets() {
		return targets;
	}

	public void setTargets(List<Goal> targets) {
		this.targets = targets;
	}
	
	

}
