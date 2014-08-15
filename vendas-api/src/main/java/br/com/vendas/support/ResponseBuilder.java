package br.com.vendas.support;

import br.com.vendas.services.support.ServiceResponse;


public class ResponseBuilder {
	
	
	public static <T> ApiResponse build(ServiceResponse<T> serviceResponse){
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setPayload(serviceResponse);
		apiResponse.setCode(HTTPStatusCode.SUCESS_200.getCode());
		apiResponse.setStatus(ApiResponse.STATUS_SUCCESS);
		apiResponse.setMessage(ApiResponse.STATUS_SUCCESS);
		return apiResponse;
	}
	
	
	public static ApiResponse build(VendasExceptionWapper e){
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setPayload(e);
		apiResponse.setStatus(HTTPStatusCode.SERVER_ERROR_500.getCode());
		apiResponse.setCode(ApiResponse.STATUS_FAILURE);
		apiResponse.setMessage(e.getMessage());
		return apiResponse;
	}

}
