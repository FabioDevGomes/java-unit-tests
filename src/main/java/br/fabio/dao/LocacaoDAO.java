package br.fabio.dao;

import br.fabio.entidades.Locacao;
import java.util.List;

public interface LocacaoDAO {

  public void salvar(Locacao locacao);

  List<Locacao> obterLocacaoPendentes();
}
