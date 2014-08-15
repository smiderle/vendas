package br.com.vendas.services.user;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.user.UserAccessDAO;
import br.com.vendas.domain.user.UserAccess;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserAccessServiceImpl implements UserAccessService{
	
	@Inject
	private UserAccessDAO userAccessDAO;

	@Transactional(readOnly=false)
	@Override
	public void save(Long userID) {
		UserAccess userAccess = new UserAccess();
		userAccess.setAccessTime(new Date());
		userAccess.setUserID(userID);
		userAccessDAO.save(userAccess);
	}
}
