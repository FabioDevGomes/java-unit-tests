package br.fabio.servicos;

import br.fabio.entidades.Filme;
import br.fabio.entidades.Locacao;
import br.fabio.entidades.Usuario;
import br.fabio.utils.DataUtils;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class LocacaoServiceTest {

  @Test
  public void teste(){
    Usuario usuario = new Usuario("Fábio");
    Filme filme = new Filme("Exterminador", 2, 5.0);

    LocacaoService locacaoService = new LocacaoService();
    Locacao locacao = locacaoService.alugarFilme(usuario, filme);

    Assert.assertTrue(locacao.getValor() == 5);
    Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
    Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)));

    Assert.assertEquals(1, 1);
  }

}