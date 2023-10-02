package com.example.controlevendas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.controlevendas.modelo.Venda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VendaActivity extends AppCompatActivity {

    private TextView tvListaVendas;
    private ArrayList<Venda> vendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarvenda);
        tvListaVendas = findViewById(R.id.tvListaVendas);
        vendas = Controller.getInstance().retornarVenda();

        carregarVendas();

    }

    private void carregarVendas() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String texto = "";

        for (Venda venda : vendas) {
            String cliente = venda.getCliente() != null ? venda.getCliente().getNome() : "Cliente não encontrado";
            String data = dateFormat.format(venda.getDataVenda());
            String valorTotal = String.format("Valor Total: R$ %.2f", venda.getValorTotal());

            texto += "Cliente: "+ venda.getCliente().getNome()+
                    "- Itens: "+ venda.getItensVenda()+
                    "- Valor Total: "+ venda.getValorTotal()+
                    "- Data: "+venda.getDataVenda()+
                    "\n";

        }

        tvListaVendas.setText(texto);
    }

}