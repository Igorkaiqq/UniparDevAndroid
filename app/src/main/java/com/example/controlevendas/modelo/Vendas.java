package com.example.controlevendas.modelo;

public class Vendas {

    private int id;

    private int idVenda;
    private int idProduto;
    private String idCliente;

    public Vendas(int id, int idVenda, int idProduto, String idCliente) {
        this.id = id;
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.idCliente = idCliente;
    }

    public Vendas(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
