package br.com.vendas.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;

public class ClientDetailsServiceImpl implements ClientDetailsService{

	public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {
		
		System.out.println("*********************");
		System.out.println(clientId);
		
		if(clientId.equals("vendas-api") ){

			List<String> authorizedGrantTypes=new ArrayList<String>();
			authorizedGrantTypes.add("password");
			authorizedGrantTypes.add("refresh_token");
			authorizedGrantTypes.add("client_credentials");
		
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId("vendas-api");
			clientDetails.setClientSecret("1234");
			clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
			
			return clientDetails;	
			
		} else {
			throw new NoSuchClientException("Nenhum cliente com o ID informado: " + clientId);
		}		
	}

}
