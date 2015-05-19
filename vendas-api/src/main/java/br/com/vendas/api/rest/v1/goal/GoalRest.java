package br.com.vendas.api.rest.v1.goal;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.target.Goal;
import br.com.vendas.domain.wrapper.TargetsWrapper;
import br.com.vendas.helper.ObjectMapperHelper;
import br.com.vendas.pojo.UserTargetsDTO;
import br.com.vendas.services.goal.GoalService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping("/private/v1/goal")
@Controller
public class GoalRest {

	private static final Logger LOG = Logger.getLogger(GoalRest.class);

	@Inject
	private GoalService targetsService;

	@RequestMapping(value="getGoalRegistered", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByFilter( Integer organizationID, Integer branchID ) {
		try {
			ServiceResponse<List<UserTargetsDTO>> payload = targetsService.findNextTargets(organizationID, branchID);

			LOG.debug("getTargetsRegistered - Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse save(@RequestHeader(value="userID") Integer userID, @RequestBody String targetsWrapper ) {
		try {

			TargetsWrapper readValue = new ObjectMapperHelper().readValue( targetsWrapper, TargetsWrapper.class );

			ServiceResponse<List<UserTargetsDTO>> payload = targetsService.saveUserTargets(userID, readValue.getUserTargetsDTO() );

			LOG.debug("save - Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	@RequestMapping(value="getAllByChangeGreaterThan", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		try {
			ServiceResponse<List<Goal>> payload =  targetsService.findAllByChangeGreaterThan(date, organizationID, offset);
			LOG.debug("getAllByChangeGreaterThan - List<Goal> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

}
