package com.example.carros.domain.dto;

import org.modelmapper.ModelMapper;
import com.example.carros.domain.Carro;
import lombok.Data;

@Data
public class CarroDTO {

	private Long id;
	private String nome;
	private String tipo;
	
	public static CarroDTO criaCarro(Carro carro) {
		//Biblioteca ModelMapper para converter o objeto Carro em CarroDTO 
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(carro, CarroDTO.class);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
