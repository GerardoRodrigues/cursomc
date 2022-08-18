package com.example.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;

	@GetMapping
	public List<Categoria> buscarTudo(){
		List<Categoria> lista = new ArrayList<>();
		lista.addAll(service.buscarTudo());
		
		return lista;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) {
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@RequestBody Categoria obj) {
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizar(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
