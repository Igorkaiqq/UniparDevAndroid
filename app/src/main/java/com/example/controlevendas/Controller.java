package com.example.controlevendas;

import com.example.controlevendas.modelo.Cliente;
import com.example.controlevendas.modelo.Produto;

import java.util.ArrayList;

public class Controller {

    private static Controller instancia;
    private static ArrayList<Cliente> listaClientes;
    private static ArrayList<Produto> listaProdutos;
    public static Controller getInstance(){
        if (instancia == null)
            return instancia = new Controller();
        else
            return instancia;
    }

    private Controller(){
        listaClientes = new ArrayList<>();
        listaProdutos = new ArrayList<>();
    }
    public void salvarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }
    public void salvarProdutos(Produto produto){
        listaProdutos.add(produto);
    }

    public ArrayList<Cliente> retornarClientes() {
        return listaClientes;
    }
    public ArrayList<Produto> retornarProdutos() {
        return listaProdutos;
    }

    public ArrayList<Produto> retornarProdutosNome() {
        return listaProdutos;
    }

}
