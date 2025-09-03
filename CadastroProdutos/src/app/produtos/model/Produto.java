package app.produtos.model;

public class Produto {
    private int id;
    private String comida;
    private String bebida;
    private String sobremesa;


    //Construtores
    public Produto(String comida, String bebida, String sobremesa) {
        this.comida = comida;
        this.bebida = bebida;
        this.sobremesa = sobremesa;
    }

    public Produto(int id, String comida, String bebida, String sobremesa) {
        this.id = id;
        this.comida = comida;
        this.bebida = bebida;
        this.sobremesa = sobremesa;
    }

    //Getters
    public int getId() {return id;}
    public String getComida() {return comida;}
    public String getBebida() {return bebida;}
    public String getSobremesa() {return sobremesa;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setComida(String comida) {this.comida = comida;}
    public void setBebida(String bebida) {this.bebida = bebida;}
    public void setSobremesa(String sobremesa) {this.sobremesa = sobremesa;}

    @Override
    public String toString() {
        return "ID: " + id + "\tPrato: " + comida + "\tBebida: " + bebida + "\tSobremesa: " + sobremesa;
    }
}

