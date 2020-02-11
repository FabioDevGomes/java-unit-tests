package br.fabio.servicos;

import br.fabio.entidades.Filme;
import br.fabio.entidades.Locacao;
import br.fabio.entidades.Usuario;
import br.fabio.utils.DataUtils;
import java.util.Date;
import java.util.regex.Matcher;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class LocacaoServiceTest {

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

  @Test(expected = Exception.class)
  public void testeFilmeSemEstoque() throws Exception {
    LocacaoService locacaoService = new LocacaoService();
    Usuario usuario = new Usuario("Fábio");
    Filme filme = new Filme("Vingadores", 0,5.0);

    locacaoService.alugarFilme(usuario, filme);
  }

  @Test
  public void testeFilmeSemEstoque2() {
    LocacaoService locacaoService = new LocacaoService();
    Usuario usuario = new Usuario("Fábio");
    Filme filme = new Filme("Vingadores", 0,5.0);

    try {
      locacaoService.alugarFilme(usuario, filme);
    } catch (Exception e) {
      Assert.assertThat(e.getMessage(), Is.is("Filme sem estoque"));
    }
  }

}