package com.jonasmagno.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jonasmagno.cursomc.domain.Categoria;
import com.jonasmagno.cursomc.domain.Cidade;
import com.jonasmagno.cursomc.domain.Cliente;
import com.jonasmagno.cursomc.domain.Endereco;
import com.jonasmagno.cursomc.domain.Estado;
import com.jonasmagno.cursomc.domain.ItemPedido;
import com.jonasmagno.cursomc.domain.PagamentoComBoleto;
import com.jonasmagno.cursomc.domain.PagamentoComCartao;
import com.jonasmagno.cursomc.domain.Pedido;
import com.jonasmagno.cursomc.domain.Produto;
import com.jonasmagno.cursomc.domain.enums.EstadoPagamento;
import com.jonasmagno.cursomc.domain.enums.TipoCliente;
import com.jonasmagno.cursomc.services.CategoriaService;
import com.jonasmagno.cursomc.services.CidadeService;
import com.jonasmagno.cursomc.services.ClienteService;
import com.jonasmagno.cursomc.services.EnderecoService;
import com.jonasmagno.cursomc.services.EstadoService;
import com.jonasmagno.cursomc.services.ItemPedidoService;
import com.jonasmagno.cursomc.services.PagamentoService;
import com.jonasmagno.cursomc.services.PedidoService;
import com.jonasmagno.cursomc.services.ProdutoService;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaService catService;
	@Autowired
	private ProdutoService prodService;
	@Autowired
	private EstadoService estService;
	@Autowired
	private CidadeService cidService;
	@Autowired
	private ClienteService cliService;
	@Autowired
	private EnderecoService endService;
	@Autowired
	private PedidoService pedService;
	@Autowired
	private PagamentoService pagService;
	@Autowired
	private ItemPedidoService itemPedService;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().add(cat1);
		
		catService.salvar(Arrays.asList(cat1, cat2));
		prodService.salvar(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estService.salvar(Arrays.asList(est1,est2));
		cidService.salvar(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "mari@fmail.com","36378912377", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apt 303", "jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		cliService.salvar(cli1);
		endService.salvar(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		PagamentoComCartao pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		PagamentoComBoleto pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedService.salvar(Arrays.asList(ped1, ped2));
		pagService.salvar(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 20000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().add(ip3);
		
		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);
		
		itemPedService.salvar(Arrays.asList(ip1,ip2,ip3));
		
	}
}
