package br.com.vendas.services.support;

public class ServiceResponse<DOMAIN> {
	private DOMAIN value;
	private Long rowCount;
	
	public ServiceResponse() {
	}
	public ServiceResponse(DOMAIN value, Long rowCount) {
		this.value = value;
		this.rowCount = rowCount;
	}
	public DOMAIN getValue() {
		return value;
	}
	public void setValue(DOMAIN value) {
		this.value = value;
	}
	public Long getRowCount() {
		return rowCount;
	}
	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}
	
}
