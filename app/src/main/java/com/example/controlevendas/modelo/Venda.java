package com.example.controlevendas.modelo;

import java.util.ArrayList;
import java.util.Date;

public class Venda {
    private Cliente cliente;
    private ArrayList<ItemVenda> itensVenda;
    private double valorTotal;
    private Date dataVenda;

    public Venda(Cliente cliente, ArrayList<ItemVenda> itensVenda, double valorTotal, Date dataVenda) {
        this.cliente = cliente;
        this.itensVenda = itensVenda;
        this.valorTotal = valorTotal;
        this.dataVenda = dataVenda;
    }

    public Venda() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(ArrayList<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }
}
