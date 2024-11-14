package br.com.cotiinformatica.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProdutoRequestDto {

	@NotBlank(message = "Por favor, informe o nome do produto.")
	@Length(min = 8, max = 150, message = "Por favor, informe o nome do produto de 8 a 150 caracteres.")
	private String nome;
	
	@NotNull(message = "Por favor, informe o preço do produto.")
	@Positive(message = "Por favor, informe um valor positivo para o preço do produto.")
	private Double preco;
	
	@NotNull(message = "Por favor, informe a quantidade do produto.")
	@Min(value = 1, message = "Por favor, informe a quantidade com valor maior ou igual a 1.")
	private Integer quantidade;
}
