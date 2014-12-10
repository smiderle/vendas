package br.com.vendas.services.goal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.targets.GoalDAO;
import br.com.vendas.dao.user.UserBranchOfficeDAO;
import br.com.vendas.domain.target.Goal;
import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.pojo.UserDTO;
import br.com.vendas.pojo.UserTargetsDTO;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;

@Service
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class GoalServiceImpl implements GoalService {
	
	public static final Integer MONTHS = 4;

	@Inject
	private GoalDAO targetsDAO;
	
	@Inject
	private UserBranchOfficeDAO userBranchOfficeDAO;
	
	@Inject
	private UserActionService userActionService;
	
	@Override
	public ServiceResponse<List<UserTargetsDTO>> findNextTargets( Integer organizationID, Integer branchID ) {
		
		List<UserTargetsDTO> usersTargets = new ArrayList<>();
		
		Integer yearMonth = Integer.parseInt( new SimpleDateFormat("YYYYMM").format( new Date() ) );
		
		List<UserBranchOffice> users = userBranchOfficeDAO.findAllByBranchOffice(organizationID, branchID);
		
		for (UserBranchOffice userBranchOffice : users) {
			
			List<Goal> findAllGreaterThen = targetsDAO.findAllGreaterThen( organizationID, branchID, userBranchOffice.getUserID(), yearMonth, MONTHS );
			create( usersTargets, userBranchOffice, findAllGreaterThen );
		}

		return ServiceResponseFactory.create(usersTargets);
	}
	
	private void create( List<UserTargetsDTO> targets, UserBranchOffice user, List<Goal> metasSalvas ) {

		//Caso já esteja salvo a meta para os próxmimos MONTHS meses, os retorna, caso contrario, ira criar os próximos meses restantes
		if( metasSalvas != null && metasSalvas.size() == MONTHS ) {			
			targets.add( new UserTargetsDTO( new UserDTO( user.getUser() ) , metasSalvas ) );
			
		} else {
			List<Goal> metasGeradas = addTargets( metasSalvas, user );
			
			List<Goal> todasAsMetas = new ArrayList<>(); 
			todasAsMetas.addAll( metasSalvas );
			todasAsMetas.addAll( metasGeradas );
			user.getUser().getName();
			targets.add( new UserTargetsDTO( new UserDTO( user.getUser() ) , todasAsMetas ) );
		}
		
	}
	
	/**
	 * Cria as metas que faltam para os proximos 6 meses 
	 * @param metasSalvas
	 * @return
	 */
	private List<Goal> addTargets( List<Goal> metasSalvas, UserBranchOffice user ) {
		List<Goal> targets = new ArrayList<>();
		Integer size = 0;
		
		if( metasSalvas != null ) {
			size = metasSalvas.size();
		}
			
		Calendar calendar = new GregorianCalendar();
		
		for (int i = 0; i < MONTHS; i++) {
			if(i >= size) {
				Goal target = new Goal();
				target.setOrganizationID( user.getBranchOffice().getOrganization().getOrganizationID() );
				target.setBranchID( user.getBranchOffice().getBranchOfficeID() );
				target.setMonth(calendar.get(Calendar.MONTH) + 1 );
				target.setUserID( user.getUserID() );
				target.setYear(calendar.get(Calendar.YEAR));
				target.setYearMonth( Integer.parseInt( new SimpleDateFormat("YYYYMM").format( calendar.getTime() ) ) );
				target.setChangeTime(new Date());
				targets.add(target);
				
			}
			calendar.add(Calendar.MONTH, 1);
		}
		
		return targets;
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<List<UserTargetsDTO>> saveUserTargets(Integer userID, List<UserTargetsDTO> targets) {
		userActionService.save( UserActionHelper.createTargetsSaveUpdate(userID) );
		for (UserTargetsDTO userTargetsDTO : targets) {
			for (Goal target : userTargetsDTO.getTargets()) {
				target.setChangeTime( new Date() );
				targetsDAO.saveOrUpdate( target );
			}			
		}
		return ServiceResponseFactory.create( targets );
	}

	@Override
	public ServiceResponse<Goal> findTargetsByUserAndMonth(Integer userID, Integer yearMonth) {
		return ServiceResponseFactory.create( targetsDAO.findGoalByUserAndMonth( userID, yearMonth ) );
	}

}
