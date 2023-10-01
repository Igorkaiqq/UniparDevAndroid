package com.example.controlevendas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controlevendas.modelo.Cliente;
import com.example.controlevendas.modelo.Produto;

import java.util.List;

public class CadastroProdutoActivity extends AppCompatActivity {

    private EditText edDescricao;
    private EditText edPreco;
    private EditText edCodigo;
    private TextView tvProdutosCadastrados;
    private Button btProdutoCadastrar;

    protected void onCreate(Bundle savedOnInstance) {
        super.onCreate(savedOnInstance);
        setContentView(R.layout.activity_produtos);
        edDescricao = findViewById(R.id.edProdutoDescricao);
        edPreco = findViewById(R.id.edProdutoPreco);
        edCodigo = findViewById(R.id.edProdutoCodigo);
        tvProdutosCadastrados = findViewById(R.id.tvProdutosCadastrados);
        btProdutoCadastrar = findViewById(R.id.btProdutoCadastrar);

        atualizarProdutos();

        btProdutoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarProduto();
            }
        });

    }

    private void atualizarProdutos() {
        String texto = "";
        for (Produto produto: Controller.getInstance().retornarProdutos()){
            texto += "Código: "+produto.getId()+" - Descrição: "+produto.getDescricao()+" - Preço:"+produto.getPreco()+"\n";
        }
        tvProdutosCadastrados.setText(texto);
    }

    private void salvarProduto(){
        validaCampos();

        String precoStr = edPreco.getText().toString().trim();
        precoStr = precoStr.replace(',', '.');
        double preco = Double.parseDouble(precoStr);

        Produto produto = new Produto();
        produto.setId(new Integer(edCodigo.getText().toString()));
        produto.setDescricao(edDescricao.getText().toString());
        produto.setPreco(preco);

        Controller.getInstance().salvarProdutos(produto);
        Toast.makeText(CadastroProdutoActivity.this, "Produto cadastrado com sucesso!",
                Toast.LENGTH_LONG).show();
        this.finish();
    }

    private void validaCampos() {
        String codigoStr = edCodigo.getText().toString().trim();
        int codigo = Integer.parseInt(codigoStr);
        String descricao = edDescricao.getText().toString().trim();
        String precoStr = edPreco.getText().toString().trim();
        double preco = Double.parseDouble(precoStr);
        List<Produto> produtos = Controller.getInstance().retornarProdutos();

        if (codigoStr.isEmpty()) {
            edCodigo.setError("Preencha o campo código");
            return;
        }
        if (!validaCodigo(codigoStr)) {
            edCodigo.setError("O campo código deve conter apenas números inteiros");
            return;
        }
        if (codigo < 0) {
            edCodigo.setError("O código informado é menor que 0");
            return;
        }
        for (Produto produto : produtos) {
            if (produto.getId() == codigo) {
                edCodigo.setError("Este código já está em uso!");
                return;
            }
        }
        if (descricao.isEmpty()) {
            edDescricao.setError("Preencha o campo Descrição");
            return;
        }
        if (precoStr.isEmpty()) {
            edPreco.setError("Preencha o campo Preço");
            return;
        }
        if (!validaPreco(precoStr)) {
            edPreco.setError("O campo Preço contém caracteres inválidos");
            return;
        }
        if (preco < 0) {
            edPreco.setError("O preço não pode ter valor negativo");
            return;
        }
    }

    private boolean validaCodigo(String codigoStr) {
        String regex = "^[0-9]+$";

        return codigoStr.matches(regex);
    }


    private boolean validaPreco(String precoStr) {
        String regex = "^[0-9.,]+$";

        if (precoStr.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }



}
