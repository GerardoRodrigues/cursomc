package com.example.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.dto.ClienteNewDTO;
import com.example.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public List<ClienteDTO> buscarTudo() {
		List<Cliente> lista = new ArrayList<>();
		lista.addAll(service.buscarTudo());
		List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return listaDTO;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
		Cliente obj = service.buscar(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNewDTO objDTO){
		Cliente obj = service.fromDTO(objDTO);
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/pagina")
	public ResponseEntity<Page<ClienteDTO>> buscarPagina(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina, 
			@RequestParam(value = "ordem", defaultValue = "nome") String ordem,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcao){
		
		Page<Cliente> page = service.buscarPorPagina(pagina, linhasPorPagina, ordem, direcao);
		Page<ClienteDTO> pageDTO = page.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(pageDTO);
	}
}
