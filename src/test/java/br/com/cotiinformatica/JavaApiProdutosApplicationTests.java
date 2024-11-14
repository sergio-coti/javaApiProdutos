package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.ProdutoRequestDto;
import br.com.cotiinformatica.dtos.ProdutoResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JavaApiProdutosApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//atributo para armazenar o id do produto cadastrado
	private static UUID id;
	
	@Test
	@Order(1)
	void cadastrarProdutoTest() throws Exception {
		
		//instanciando a biblioteca java-faker
		var faker = new Faker(Locale.forLanguageTag("pt-BR"));
		
		//preenchendo os dados do dto para cadastrar o produto
		var dto = new ProdutoRequestDto();
		dto.setNome(faker.commerce().productName()); //gerando um nome de produto
		dto.setPreco((double) faker.number().numberBetween(1, 1000)); //gerando um valor entre 1 e 1000
		dto.setQuantidade(faker.number().numberBetween(1, 10)); //gerando um valor entre 1 e 10
		
		//fazendo a requisição para o serviço da API
		var result = mockMvc.perform(post("/api/produtos") //endpoint
				.contentType("application/json") //formato dos dados
				.content(objectMapper.writeValueAsString(dto))) //dados enviados
				.andExpect(status().isOk()) //esperando resposta de sucesso
				.andReturn(); //capturando a resposta
		
		//capturando a mensagem devolvida pela API após a realização do cadastro
		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		//ler os dados obtidos da API (Response DTO)
		var response = objectMapper.readValue(content, ProdutoResponseDto.class);
		
		//verificando se os dados da resposta coincidem com os da requisição
		assertNotNull(response.getId()); //verificando se um ID foi gerado para o produto
		assertEquals(response.getNome(), dto.getNome()); //comparando o nome
		assertEquals(response.getPreco(), dto.getPreco()); //comparando o preço
		assertEquals(response.getQuantidade(), dto.getQuantidade()); //comparando a quantidade
		
		//armazenando o ID do produto cadastrado
		id = response.getId();
	}

	@Test
	@Order(2)
	void atualizarProdutoTest() throws Exception {
		
		//instanciando a biblioteca java-faker
		var faker = new Faker(Locale.forLanguageTag("pt-BR"));
		
		//preenchendo os dados do dto para atualizar o produto
		var dto = new ProdutoRequestDto();
		dto.setNome(faker.commerce().productName()); //gerando um nome de produto
		dto.setPreco((double) faker.number().numberBetween(1, 1000)); //gerando um valor entre 1 e 1000
		dto.setQuantidade(faker.number().numberBetween(1, 10)); //gerando um valor entre 1 e 10
		
		//fazendo a requisição para o serviço da API
		var result = mockMvc.perform(put("/api/produtos/" + id) //endpoint
				.contentType("application/json") //formato dos dados
				.content(objectMapper.writeValueAsString(dto))) //dados enviados
				.andExpect(status().isOk()) //esperando resposta de sucesso
				.andReturn(); //capturando a resposta
		
		//capturando a mensagem devolvida pela API após a realização do cadastro
		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		//ler os dados obtidos da API (Response DTO)
		var response = objectMapper.readValue(content, ProdutoResponseDto.class);
		
		//verificando se os dados da resposta coincidem com os da requisição
		assertEquals(response.getId(), id); //comparando o id do produto
		assertEquals(response.getNome(), dto.getNome()); //comparando o nome
		assertEquals(response.getPreco(), dto.getPreco()); //comparando o preço
		assertEquals(response.getQuantidade(), dto.getQuantidade()); //comparando a quantidade		
	}

	@Test
	@Order(3)
	void consultarProdutosTest() throws Exception {
		
		//fazendo a requisição para o serviço da API
		var result = mockMvc.perform(get("/api/produtos")) //endpoint
				.andExpect(status().isOk()) //esperando resposta de sucesso
				.andReturn(); //capturando a resposta
		
		//capturando a mensagem devolvida pela API após a realização do cadastro
		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		//ler os dados obtidos da API (Response DTO)
		var response = objectMapper.readValue(content, new TypeReference<List<ProdutoResponseDto>>() {});
		
		//verificando se a lista contem o produto cadastrado
		response.stream()
	            .filter(produto -> produto.getId().equals(id)) // filtrando pelo id desejado
	            .findFirst()
	            .orElseThrow(() -> new AssertionError("Produto com ID " + id + " não encontrado na lista."));
	}

	@Test
	@Order(4)
	void obterProdutoPorIdTest() throws Exception {
			
		//fazendo a requisição para o serviço da API
		var result = mockMvc.perform(get("/api/produtos/" + id)) //endpoint
				.andExpect(status().isOk()) //esperando resposta de sucesso
				.andReturn(); //capturando a resposta
		
		//capturando a mensagem devolvida pela API após a realização do cadastro
		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		//ler os dados obtidos da API (Response DTO)
		var response = objectMapper.readValue(content, ProdutoResponseDto.class);
		
		//verificando se os dados da resposta coincidem com os da requisição
		assertEquals(response.getId(), id); //comparando o id do produto
		assertNotNull(response.getNome()); //comparando o nome
		assertNotNull(response.getPreco()); //comparando o preço
		assertNotNull(response.getQuantidade()); //comparando a quantidade			
	}
	
	@Test
	@Order(5)
	void excluirProdutoTest() throws Exception {
		
		//fazendo a requisição para o serviço da API
		var result = mockMvc.perform(delete("/api/produtos/" + id)) //endpoint
				.andExpect(status().isOk()) //esperando resposta de sucesso
				.andReturn(); //capturando a resposta
		
		//capturando a mensagem devolvida pela API após a realização do cadastro
		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		//ler os dados obtidos da API (Response DTO)
		var response = objectMapper.readValue(content, ProdutoResponseDto.class);
		
		//verificando se os dados da resposta coincidem com os da requisição
		assertEquals(response.getId(), id); //comparando o id do produto
		assertNotNull(response.getNome()); //comparando o nome
		assertNotNull(response.getPreco()); //comparando o preço
		assertNotNull(response.getQuantidade()); //comparando a quantidade			
	}

}
