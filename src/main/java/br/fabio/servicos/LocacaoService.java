package br.fabio.servicos;

import static br.fabio.utils.DataUtils.adicionarDias;

import br.fabio.dao.LocacaoDAO;
import br.fabio.entidades.Filme;
import br.fabio.entidades.Locacao;
import br.fabio.entidades.Usuario;
import br.fabio.exceptions.FilmeSemEstoqueException;
import br.fabio.exceptions.LocadoraException;
import br.fabio.utils.DataUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LocacaoService {

	private LocacaoDAO locacaoDAO;
	private SPCService spcService;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme : filmes){
			if(filme.getEstoque() == 0){
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}

		if(usuario == null){
			throw new LocadoraException("Usuário vazio");
		}

		if(spcService.possuiNegativacao(usuario)){
			throw new LocadoraException("Possui negativação");
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorLocacao = 0.0;
		for (Filme filme : filmes){
			valorLocacao += filme.getPrecoLocacao();
		}
		locacao.setValor(valorLocacao);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		locacaoDAO.salvar(locacao);
		
		return locacao;
	}

	public void setLocacaoDAO(LocacaoDAO locacaoDAO){
		this.locacaoDAO = locacaoDAO;
	}

	public void setSpcService(SPCService spcService){
		this.spcService = spcService;
	}

}