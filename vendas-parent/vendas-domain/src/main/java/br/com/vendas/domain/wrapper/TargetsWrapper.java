package br.com.vendas.domain.wrapper;

import java.util.List;

import br.com.vendas.pojo.UserTargetsDTO;

/**
 * Classe utilizada para envelopar o userTargetsDTO.
 * @author Ladair C. Smiderle Junior 04/12/2014 - 20:06:33
 *
 */
public class TargetsWrapper {
	
	public List<UserTargetsDTO> userTargetsDTO;

	public List<UserTargetsDTO> getUserTargetsDTO() {
		return userTargetsDTO;
	}

	public void setUserTargetsDTO(List<UserTargetsDTO> userTargetsDTO) {
		this.userTargetsDTO = userTargetsDTO;
	}
	
	

}
