package br.com.vendas.api.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import br.com.vendas.pojo.UserDTO;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserService;



public class CustomUserAuthenticationProvider implements AuthenticationProvider {
	
	@Inject
	private UserService userService;	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		System.out.println("Usuário "+  authentication.getPrincipal().toString());
		System.out.println("Senha "+  authentication.getCredentials() );
		
		ServiceResponse<UserDTO> findUserByID = userService.findUserByEmail( authentication.getPrincipal().toString() );
		UserDTO user = findUserByID.getValue();
		
		if( user != null && authentication.getCredentials() != null && user.getEmail().equals( authentication.getCredentials() ) ) {
			
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			UserAuthenticate auth=new UserAuthenticate(authentication.getPrincipal(), authentication.getCredentials(),grantedAuthorities);
			
			return auth;		
		} else {
				throw new BadCredentialsException("Usuário ou senha inválidos.");
		}
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		
		return true;
	}


}
