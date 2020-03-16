package br.fabio.servicos;

import br.fabio.builders.FilmeBuilder;
import br.fabio.builders.UsuarioBuilder;
import br.fabio.entidades.Filme;
import br.fabio.entidades.Locacao;
import br.fabio.entidades.Usuario;
import br.fabio.exceptions.FilmeSemEstoqueException;
import br.fabio.exceptions.LocadoraException;
import br.fabio.utils.DataUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LocacaoServiceTest {

  LocacaoService locacaoService;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Before
  public void before(){
    locacaoService = new LocacaoService();
  }

  @Test
  public void teste(){
    Usuario usuario = UsuarioBuilder.umUsuario().agora();
    Filme filme = FilmeBuilder.umFilme().comEstoque(1);

    Locacao locacao = null;
    try {
      locacao = locacaoService.alugarFilme(usuario, Arrays.asList(filme));
      Assert.assertTrue(locacao.getValor() == 5);
      Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
      Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail("Não deveria lançar a exceção");
    }

    Assert.assertEquals(1, 1);
  }

  //Forma elegante
  //Recomendada para exceções específicas pois não conseguimos verificar a msg
  @Test(expected = FilmeSemEstoqueException.class)
  public void testeFilmeSemEstoque() throws Exception {
    Usuario usuario = UsuarioBuilder.umUsuario().agora();
    Filme filme = FilmeBuilder.umFilme().semEstoque();

    locacaoService.alugarFilme(usuario, Arrays.asList(filme));
  }

  //Forma robusta (preferencial)
  @Test
  public void testeFilmeSemEstoque2() throws LocadoraException {
    Usuario usuario = UsuarioBuilder.umUsuario().agora();
    Filme filme = FilmeBuilder.umFilme().semEstoque();

    try {
      locacaoService.alugarFilme(usuario, Arrays.asList(filme));
      Assert.fail("Deveria lançar uma exceção");
    } catch (FilmeSemEstoqueException  e) {
      Assert.assertThat(e.getMessage(), Is.is("Filme sem estoque"));
    }
  }

  //Forma nova
  @Test
  public void testeFilmeSemEstoque3() throws Exception {
    Usuario usuario = UsuarioBuilder.umUsuario().agora();
    Filme filme = FilmeBuilder.umFilme().semEstoque();

    exception.expect(Exception.class);
    exception.expectMessage("Filme sem estoque");

    locacaoService.alugarFilme(usuario, Arrays.asList(filme));
  }

  //Forma robusta (preferencial)
  //Checa também a mensagem e segue o fluxo de execução
  @Test
  public void testeUsuarioVazio() throws FilmeSemEstoqueException {
    Filme filme = FilmeBuilder.umFilme().comEstoque(2);;

    try {
      locacaoService.alugarFilme(null, Arrays.asList(filme));
      Assert.fail();
    } catch (LocadoraException e) {
      Assert.assertThat(e.getMessage(), Is.is("Usuário vazio"));
    }
  }

  //Forma nova
  //Interrompe a execução após o lançamento da exception
  @Test
  public void testeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
    Usuario usuario = UsuarioBuilder.umUsuario().agora();

    exception.expect(Exception.class);
    exception.expectMessage("Filme vazio");

    locacaoService.alugarFilme(usuario, null);
  }

}