package com.example.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomc.domain.Produto;
import com.example.cursomc.dto.ProdutoDTO;
import com.example.cursomc.resources.utils.URL;
import com.example.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping
	public List<Produto> buscarTudo(){
		List<Produto> lista = new ArrayList<>();
		lista.addAll(service.buscarTudo());
		return lista;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> buscarPorID(@PathVariable Integer id){
		Produto obj = service.buscarPorId(id);
		return ResponseEntity.ok(obj);
	}
	
	@GetMapping(value = "/pagina")
	public ResponseEntity<Page<ProdutoDTO>> buscarPagina(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(value = "ordem", defaultValue = "nome") String ordem,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcao){
			
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> page = service.search(nomeDecoded, ids, pagina, linhasPorPagina, ordem, direcao);
		Page<ProdutoDTO> pageDTO = page.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(pageDTO);
	}
}
