package br.fabio.builders;

import br.fabio.entidades.Usuario;

public class UsuarioBuilder {

  private Usuario usuario;

  private UsuarioBuilder(){
  }

  public static UsuarioBuilder umUsuario(){
    UsuarioBuilder usuarioBuilder = new UsuarioBuilder();
    usuarioBuilder.usuario = new Usuario();
    usuarioBuilder.usuario.setNome("Fábio");
     return usuarioBuilder;
  }

  public Usuario agora(){
    return usuario;
  }

  public UsuarioBuilder comNome(String nome){
    this.usuario.setNome(nome);
    return this;
  }

}
