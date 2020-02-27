package br.fabio.servicos;

import static br.fabio.utils.DataUtils.adicionarDias;

import br.fabio.entidades.Filme;
import br.fabio.entidades.Locacao;
import br.fabio.entidades.Usuario;
import br.fabio.exceptions.FilmeSemEstoqueException;
import br.fabio.exceptions.LocadoraException;
import br.fabio.utils.DataUtils;
import java.util.Date;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {

		if(filme == null){
			throw new LocadoraException("Filme vazio");
		}

		if(filme.getEstoque() == 0){
			throw new FilmeSemEstoqueException();
		}

		if(usuario == null){
			throw new LocadoraException("Usuário vazio");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}