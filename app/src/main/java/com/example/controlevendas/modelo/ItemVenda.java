package com.example.controlevendas.modelo;

public class ItemVenda {

    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    private double valor;

    public ItemVenda(Produto produto, int quantidade, double valorUnitario, double valor) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valor = valor;

    }

    public ItemVenda(){

    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Produto: " + produto.getDescricao() +
                ", Quantidade: " + quantidade +
                ", ValorUnitario: R$ " + String.format("%.2f", valorUnitario) +
                ", Valor: R$ " + String.format("%.2f", valor);
    }
}
