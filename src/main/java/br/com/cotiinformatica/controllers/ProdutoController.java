package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ProdutoRequestDto;
import br.com.cotiinformatica.dtos.ProdutoResponseDto;
import br.com.cotiinformatica.services.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping
	public ProdutoResponseDto post(@RequestBody @Valid ProdutoRequestDto dto) {
		return produtoService.create(dto);
	}
	
	@PutMapping("{id}")
	public ProdutoResponseDto put(@PathVariable UUID id, @RequestBody @Valid ProdutoRequestDto dto) {
		return produtoService.update(id, dto);
	}
	
	@DeleteMapping("{id}")
	public ProdutoResponseDto delete(@PathVariable UUID id) {
		return produtoService.delete(id);
	}
	
	@GetMapping
	public List<ProdutoResponseDto> getAll() {
		return produtoService.getAll();
	}
	
	@GetMapping("{id}")
	public ProdutoResponseDto getById(@PathVariable UUID id) {
		return produtoService.getById(id);
	}
}








