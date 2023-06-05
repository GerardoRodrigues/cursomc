package com.example.cursomc.dto;

import java.io.Serializable;

import com.example.cursomc.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nome;
	private Integer id;
	
	public EstadoDTO(){
	}
	
	public EstadoDTO(Estado obj) {
		nome = obj.getNome();
		id = obj.getId();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
