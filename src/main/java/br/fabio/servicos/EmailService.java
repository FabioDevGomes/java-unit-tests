package br.fabio.servicos;

import br.fabio.entidades.Usuario;

public interface EmailService {

  public void notificarAtraso(Usuario usuario);

}
