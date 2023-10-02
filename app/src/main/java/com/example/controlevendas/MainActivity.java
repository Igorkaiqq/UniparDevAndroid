package com.example.controlevendas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCadastrarCliente;
    private Button btCadastrarProduto;
    private Button btRealizarVenda;

    private Button btVisualizarVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCadastrarCliente = findViewById(R.id.btCadastrarCliente);
        btCadastrarProduto = findViewById(R.id.btCadastrarProduto);
        btRealizarVenda = findViewById(R.id.btRealizarVenda);
        btVisualizarVenda = findViewById(R.id.btVisualizarVendas);

        btCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroClienteActivity.class);
            }
        });

        btCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroProdutoActivity.class);
            }
        });

        btRealizarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroVendaActivity.class);
            }
        });

        btVisualizarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(VendaActivity.class);
            }
        });

    }

    private void abrirActivity(Class<?> activity){
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    protected void onRestart(){
        super.onRestart();
    }

}