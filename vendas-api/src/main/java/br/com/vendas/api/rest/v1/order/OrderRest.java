package br.com.vendas.api.rest.v1.order;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.product.Product;
import br.com.vendas.domain.wrapper.OrderWrapper;
import br.com.vendas.dto.OrderDTO;
import br.com.vendas.exception.VendasException;
import br.com.vendas.helper.ObjectMapperHelper;
import br.com.vendas.services.order.OrderService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/v1/order")
@Controller
public class OrderRest {

	private static final Logger LOG = Logger.getLogger(OrderRest.class);

	@Inject
	private OrderService orderService;

	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getAll(@RequestBody String orderWrapper) {
		try {
			OrderWrapper order = new ObjectMapperHelper().readValue(orderWrapper, OrderWrapper.class);
			ServiceResponse<OrderDTO> payload = orderService.save(order);

			LOG.debug("save Size: "+payload.getRowCount());

			return ResponseBuilder.build(payload);
		} catch (VendasException e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
	
	
	@RequestMapping(value="getAllByBranch", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByBranch(Integer organizationID, Integer branchID,Integer offset,Integer limit) {
		try {
			ServiceResponse<List<OrderDTO>> payload =  orderService.findAllByBranch(organizationID, branchID, offset, limit);
			
			LOG.debug("getAllByBranch - List<OrderDTO> Size: " + payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	@RequestMapping(value="getAllByUserAndBranch", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByUserAndBranch(Integer organizationID, Integer branchID,Integer offset,Integer limit, Integer userID) {
		try {
			ServiceResponse<List<OrderDTO>> payload =  orderService.findAllByUserAndBranch(organizationID, branchID, offset, limit, userID);
			
			LOG.debug("getAllByBranch - List<OrderDTO> Size: " + payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	@RequestMapping(value="getByID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByID(Long id) {
		try {
			ServiceResponse<OrderDTO> payload =  orderService.findByID(id);
			
			LOG.debug("getByID - OrderDTO Size: " + payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	

	@RequestMapping(value="getByFilter", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByFilter(String filter, Integer organizationID, Integer branchID,Integer offset, Integer limit) {
		try {
			ServiceResponse<List<OrderDTO>> payload =  orderService.findByIDOrCustomerID(organizationID, branchID, filter, offset, limit);
			LOG.debug("getByFilter - List<OrderDTO> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	@RequestMapping(value="getByFilterAndUserID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByFilterAndUserID(String filter, Integer organizationID, Integer branchID, Integer userID, Integer offset, Integer limit) {
		try {
			ServiceResponse<List<OrderDTO>> payload =  orderService.findByIDOrCustomerIDAndUserID(organizationID, branchID,userID , filter, offset, limit);
			LOG.debug("getByFilterAndUserID - List<OrderDTO> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}

}
