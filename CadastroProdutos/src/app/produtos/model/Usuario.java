package app.produtos.model;

public class Usuario {
    private int id;
    private String nome;
    private String senha;


    //Construtores
    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Usuario(int id, String nome, String senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    //Getters
    public int getId() {return id;}
    public String getNome() {return nome;}
    public String getSenha() {return senha;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setSenha(String senha) {this.senha = senha;}
}
