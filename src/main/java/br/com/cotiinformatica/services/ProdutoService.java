package br.com.cotiinformatica.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.ProdutoRequestDto;
import br.com.cotiinformatica.dtos.ProdutoResponseDto;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoResponseDto create(ProdutoRequestDto dto) {
		
		var produto = modelMapper.map(dto, Produto.class);
		produto.setId(UUID.randomUUID());
		
		produtoRepository.save(produto);
		
		var response = modelMapper.map(produto, ProdutoResponseDto.class);		
		return response;
	}
	
	public ProdutoResponseDto update(UUID id, ProdutoRequestDto dto) {
		
		var produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
		
		produto.setNome(dto.getNome());
		produto.setPreco(BigDecimal.valueOf(dto.getPreco()));
		produto.setQuantidade(dto.getQuantidade());
		
		produtoRepository.save(produto);
		
		var response = modelMapper.map(produto, ProdutoResponseDto.class);
		return response;
	}
	
	public ProdutoResponseDto delete(UUID id) {
		
		var produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
		
		produtoRepository.delete(produto);
		
		var response = modelMapper.map(produto, ProdutoResponseDto.class);
		return response;
	}
	
	public List<ProdutoResponseDto> getAll() {
		
		var response = new ArrayList<ProdutoResponseDto>();
		
		for(var produto : produtoRepository.findAll()) {
			response.add(modelMapper.map(produto, ProdutoResponseDto.class));
		}
		
		return response;
	}
	
	public ProdutoResponseDto getById(UUID id) {
		
		var produto = produtoRepository.findById(id).get();
		
		var response = modelMapper.map(produto, ProdutoResponseDto.class);
		return response;
	}
}















