package br.fabio.builders;

import br.fabio.entidades.Filme;

public class FilmeBuilder {

  private Filme filme;

  private FilmeBuilder(){}

  public static FilmeBuilder umFilme(){
    FilmeBuilder filmeBuilder = new FilmeBuilder();
    filmeBuilder.filme = new Filme();
//    filmeBuilder.filme.setEstoque(0);
    filmeBuilder.filme.setNome("Exterminador do futuro 2");
    filmeBuilder.filme.setPrecoLocacao(5.0);

    return filmeBuilder;
  }

  public Filme semEstoque(){
    filme.setEstoque(0);
    return filme;
  }

  public Filme comEstoque(Integer estoque){
    filme.setEstoque(estoque);
    return filme;
  }

}
