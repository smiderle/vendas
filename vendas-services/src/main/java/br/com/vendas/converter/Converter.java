package br.com.vendas.converter;

public interface Converter<T, I> {


	void converteDtoParaOrm(T objetoORM, I objetoDTO);

	void converteOrmParaDto(I objetoDTO, T objetoORM);

}
