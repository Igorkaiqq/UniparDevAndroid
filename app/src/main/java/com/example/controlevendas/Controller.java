package com.example.controlevendas;

import com.example.controlevendas.modelo.Cliente;
import com.example.controlevendas.modelo.ItemVenda;
import com.example.controlevendas.modelo.Produto;
import com.example.controlevendas.modelo.Venda;

import java.util.ArrayList;

public class Controller {

    private static Controller instancia;
    private static ArrayList<Cliente> listaClientes;
    private static ArrayList<Produto> listaProdutos;
    private static ArrayList<ItemVenda> listaItemVenda;

    private static ArrayList<Venda> listaVenda;

    public static Controller getInstance(){
        if (instancia == null)
            return instancia = new Controller();
        else
            return instancia;
    }

    private Controller(){
        listaClientes = new ArrayList<>();
        listaProdutos = new ArrayList<>();
        listaItemVenda = new ArrayList<>();
        listaVenda = new ArrayList<>();
    }
    public void salvarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }
    public void salvarProdutos(Produto produto){
        listaProdutos.add(produto);
    }
    public void salvarItemVenda(ItemVenda itemVenda){
        listaItemVenda.add(itemVenda);
    }
    public void salvarVenda(Venda venda) {listaVenda.add(venda);}


    public ArrayList<Cliente> retornarClientes() {
        return listaClientes;
    }
    public ArrayList<Produto> retornarProdutos() {
        return listaProdutos;
    }


    public ArrayList<ItemVenda> retornarItemVenda() {return listaItemVenda;}
    public ArrayList<Venda> retornarVenda() {return listaVenda;}

}
