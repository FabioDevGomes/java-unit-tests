package br.fabio.builders;

import br.fabio.entidades.Locacao;
import java.util.Calendar;
import java.util.Date;

public class LocacaoBuilder {

  Locacao locacao;

  private LocacaoBuilder(){}

  public static LocacaoBuilder umaLocacao(){
    LocacaoBuilder locacaoBuilder = new LocacaoBuilder();

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2020);
    calendar.set(Calendar.MONTH, 03);
    calendar.set(Calendar.DAY_OF_MONTH, 10);
    locacaoBuilder.locacao = new Locacao();
    locacaoBuilder.locacao.setDataLocacao(calendar.getTime());

    calendar.set(Calendar.DAY_OF_MONTH, 11);
    locacaoBuilder.locacao.setDataRetorno(calendar.getTime());
    locacaoBuilder.locacao.setUsuario(UsuarioBuilder.umUsuario().comNome("Fábio Alves").agora());

    return locacaoBuilder;
  }

  public Locacao agora(){
    return this.locacao;
  }

}
