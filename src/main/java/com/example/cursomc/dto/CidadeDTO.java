package com.example.cursomc.dto;

import java.io.Serializable;

import com.example.cursomc.domain.Cidade;

public class CidadeDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer id;
	
	public CidadeDTO() {
	}
	
	public CidadeDTO(Cidade obj) {
		name = obj.getNome();
		id = obj.getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
