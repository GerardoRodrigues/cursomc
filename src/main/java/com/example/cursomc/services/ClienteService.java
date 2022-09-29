package com.example.cursomc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.services.exceptions.DataIntegrityException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public List<Cliente> buscarTudo(){
		List<Cliente> lista = new ArrayList<>();
		lista.addAll(repo.findAll());
		return lista;
	}
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente de Id: " + id + " não encontrado."
				+ "Do tipo: " + Cliente.class.getName()));
	}
	
	public Cliente atualizar(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		atualizarDados(newObj, obj);
		return repo.save(newObj);
	}
	
	public void deletar(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao eh possivel excluir um cliente que possui pedidos.");
		}
	}
	
	public Page<Cliente> buscarPorPagina(Integer pagina, Integer linhasPorPagina, String ordem, String direcao){
		PageRequest page = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordem);
		return repo.findAll(page);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void atualizarDados(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
