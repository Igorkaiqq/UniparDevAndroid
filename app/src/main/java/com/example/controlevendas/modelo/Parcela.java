package com.example.controlevendas.modelo;
public class Parcela {
    private int numero;
    private double valor;

    public Parcela(int numero, double valor) {
        this.numero = numero;
        this.valor = valor;
    }

    public int getNumero() {
        return numero;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Parcela " + numero + ": R$ " + String.format("%.2f", valor);
    }
}
