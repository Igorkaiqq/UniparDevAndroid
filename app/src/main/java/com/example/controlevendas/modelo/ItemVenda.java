package com.example.controlevendas.modelo;

public class ItemVenda {
    private Cliente cliente;
    private Produto produto;
    private int quantidade;
    private double valorItem;

    public ItemVenda(Cliente cliente, Produto produto, int quantidade, double valorItem) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorItem = valorItem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorItem() {
        return valorItem;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente.getNome() +
                ", Produto: " + produto.getDescricao() +
                ", Quantidade: " + quantidade +
                ", Valor: R$ " + String.format("%.2f", valorItem);
    }
}
