package br.fabio.servicos;

import br.fabio.entidades.Filme;
import br.fabio.entidades.Locacao;
import br.fabio.entidades.Usuario;
import br.fabio.exceptions.FilmeSemEstoqueException;
import br.fabio.exceptions.LocadoraException;
import br.fabio.utils.DataUtils;
import java.util.Date;
import java.util.regex.Matcher;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LocacaoServiceTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void teste(){
    Usuario usuario = new Usuario("Fábio");
    Filme filme = new Filme("Exterminador", 2, 5.0);

    LocacaoService locacaoService = new LocacaoService();
    Locacao locacao = null;
    try {
      locacao = locacaoService.alugarFilme(usuario, filme);
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
    LocacaoService locacaoService = new LocacaoService();
    Usuario usuario = new Usuario("Fábio");
    Filme filme = new Filme("Vingadores", 0,5.0);

    locacaoService.alugarFilme(usuario, filme);
  }

  //Forma robusta (preferencial)
  @Test
  public void testeFilmeSemEstoque2() {
    LocacaoService locacaoService = new LocacaoService();
    Usuario usuario = new Usuario("Fábio");
    Filme filme = new Filme("Vingadores", 0,5.0);

    try {
      locacaoService.alugarFilme(usuario, filme);
      Assert.fail("Deveria lançar uma exceção");
    } catch (Exception  e) {
      Assert.assertThat(e.getMessage(), Is.is("Filme sem estoque"));
    }
  }

  //Forma nova
  @Test
  public void testeFilmeSemEstoque3() throws Exception {
    LocacaoService locacaoService = new LocacaoService();
    Usuario usuario = new Usuario("Fábio");
    Filme filme = new Filme("Vingadores", 0,5.0);

    exception.expect(Exception.class);
    exception.expectMessage("Filme sem estoque");

    locacaoService.alugarFilme(usuario, filme);
  }

  //Forma robusta (preferencial)
  //Checa também a mensagem e segue o fluxo de execução
  @Test
  public void testeUsuarioVazio() throws FilmeSemEstoqueException {
    LocacaoService locacaoService = new LocacaoService();
    Filme filme = new Filme("Vingadores", 2,5.0);

    try {
      locacaoService.alugarFilme(null, filme);
      Assert.fail();
    } catch (LocadoraException e) {
      Assert.assertThat(e.getMessage(), Is.is("Usuário vazio"));
    }
  }

  //Forma nova
  //Interrompe a execução após o lançamento da exception
  @Test
  public void testeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
    LocacaoService locacaoService = new LocacaoService();
    Usuario usuario = new Usuario("Fábio");

    exception.expect(Exception.class);
    exception.expectMessage("Filme vazio");

    locacaoService.alugarFilme(usuario, null);
  }

}