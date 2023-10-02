package com.example.controlevendas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlevendas.modelo.Cliente;
import com.example.controlevendas.modelo.Produto;
import com.example.controlevendas.modelo.ItemVenda;
import com.example.controlevendas.modelo.Parcela;
import com.example.controlevendas.modelo.Venda;

import java.util.ArrayList;
import java.util.Date;

public class CadastroVendaActivity extends AppCompatActivity {

    private AutoCompleteTextView atClientes, atProdutos;
    private EditText edQuantidade, edQuantidadeParcelas, edPrecoVenda;
    private Button btAdicionarItem, btConcluirPedido;
    private RadioButton rbAVista, rbAprazo;
    private RadioGroup rgCondicaoPagamento;
    private TextView tvValorTotal, tvItens, tvParcela;
    private ArrayList<ItemVenda> itensItemVenda;
    private ArrayList<Parcela> parcelas;
    private double valorTotal;

    private double valorSoma;
    private int posProd;
    private int posCliente;
    private boolean isAVista = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);



        atProdutos = findViewById(R.id.atProduto);
        atClientes = findViewById(R.id.atClientes);
        carregarProdutos();
        carregarClientes();
        edQuantidade = findViewById(R.id.edQuantidade);
        edQuantidadeParcelas = findViewById(R.id.edQuantidadeParcelas);
        btAdicionarItem = findViewById(R.id.btAdicionarItem);
        btConcluirPedido = findViewById(R.id.btConcluirPedido);
        tvItens = findViewById(R.id.tvItens);
        tvParcela = findViewById(R.id.tvValorParcela);
        rgCondicaoPagamento = findViewById(R.id.rgCondicaoPagamento);
        tvValorTotal = findViewById(R.id.tvValorTotal);
        rbAVista = findViewById(R.id.rbAVista);
        rbAprazo = findViewById(R.id.rbAPrazo);
        edPrecoVenda = findViewById(R.id.edPrecoVenda);
        String textoItens = "";
        itensItemVenda = new ArrayList<>();



        posCliente = -1;
        atClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clienteSelecionado = atClientes.getText().toString();
                ArrayList<Cliente> listaClientes = Controller.getInstance().retornarClientes();
                for (int j = 0; j < listaClientes.size(); j++) {
                    if (clienteSelecionado.equals(listaClientes.get(j).getNome())) {
                        posCliente = j;
                        return;
                    }
                }

                Toast.makeText(CadastroVendaActivity.this, "Cliente não encontrado na lista.", Toast.LENGTH_SHORT).show();
            }
        });


        posProd = -1;
        atProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String produtoSelecionado = atProdutos.getText().toString();
                ArrayList<Produto> listaProdutos = Controller.getInstance().retornarProdutos();
                for (int j = 0; j < listaProdutos.size(); j++) {
                    if (produtoSelecionado.equals(listaProdutos.get(j).getDescricao())) {
                        posProd = j;
                        return;
                    }
                }
                Toast.makeText(CadastroVendaActivity.this, "Produto não encontrado na lista.", Toast.LENGTH_SHORT).show();
            }
        });

        btAdicionarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItem();
                atualizarItens();
                atClientes.setEnabled(false);
            }
        });

        btConcluirPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirPedido();
            }
        });

        rgCondicaoPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbAVista) {
                    double valorAVista = valorTotal * 0.95;
                    tvParcela.setVisibility(View.GONE);
                    edQuantidadeParcelas.setVisibility(View.GONE);
                    tvValorTotal.setText("Valor Total: R$ " + String.format("%.2f", valorAVista));
                } else if (checkedId == R.id.rbAPrazo) {
                    edQuantidadeParcelas.setVisibility(View.VISIBLE);
                    double valorParcelado = valorTotal * 1.05;
                    tvParcela.setVisibility(View.VISIBLE);
                    tvValorTotal.setText("Valor Total: R$ " + String.format("%.2f", valorParcelado));
                }
            }
        });

        edQuantidadeParcelas.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isUpdating) {
                    isUpdating = true;
                    if (!charSequence.toString().isEmpty()) {
                        int qntParcela = Integer.parseInt(charSequence.toString());
                        double valorParcela = valorTotal / qntParcela;
                        tvParcela.setText(String.format(qntParcela + "x de %.2f", valorParcela));
                        edQuantidadeParcelas.setSelection(edQuantidadeParcelas.getText().length());
                    }

                    isUpdating = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void carregarProdutos(){
        int tamanhoListaProdutos = Controller.getInstance().retornarProdutos().size();
        String[] vetProdutos = new String[tamanhoListaProdutos];
        for (int i = 0; i < tamanhoListaProdutos; i++) {
           Produto produto = Controller.getInstance().retornarProdutos().get(i);
           vetProdutos[i] = produto.getDescricao();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CadastroVendaActivity.this, android.R.layout.simple_dropdown_item_1line, vetProdutos);
        atProdutos.setAdapter(adapter);
    }

    private void carregarClientes(){
        int tamanhoListaClientes = Controller.getInstance().retornarClientes().size();
        String[] vetClientes = new String[tamanhoListaClientes];
        for (int i = 0; i < tamanhoListaClientes; i++) {
            vetClientes[i] = Controller.getInstance().retornarClientes().get(i).getNome();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CadastroVendaActivity.this, android.R.layout.simple_dropdown_item_1line, vetClientes);
        atClientes.setAdapter(adapter);
    }

    private void adicionarItem() {
        if (posCliente == -1) {
            Toast.makeText(this, "Selecione um cliente antes de adicionar um item.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(edQuantidade.getText().toString());
        double precoVenda = Double.parseDouble(edPrecoVenda.getText().toString());

        validaCampos(quantidade, precoVenda);

        ItemVenda itemVenda = new ItemVenda();
        Produto produto = Controller.getInstance().retornarProdutos().get(posProd);

        valorSoma = (precoVenda * quantidade);

        itemVenda.setProduto(produto);
        itemVenda.setQuantidade(quantidade);
        itemVenda.setValorUnitario(precoVenda);
        itemVenda.setValor(valorSoma);
        Controller.getInstance().salvarItemVenda(itemVenda);
        itensItemVenda.add(itemVenda);

        posProd = -1;

        valorTotal += valorSoma;

        tvValorTotal.setText("Valor Total: R$ " + String.format("%.2f", valorTotal));

        limparCampos(false);
    }


    private void atualizarItens() {
        String texto = "";
        for (ItemVenda itemVenda : Controller.getInstance().retornarItemVenda()) {
            texto += "Produto: " + itemVenda.getProduto().getDescricao() + // Obtenha a descrição do produto
                    " - Quantidade: " + itemVenda.getQuantidade() +
                    " - ValorUnitario: R$ " + String.format("%.2f", itemVenda.getValorUnitario()) +
                    " - Valor: R$ " + String.format("%.2f", itemVenda.getValor()) +
                    "\n---------------------------" +
                    "\n";
        }
        tvItens.setText(texto);
    }


    public void validaCampos(int quantidade, double precoVenda){

        if (edQuantidade.getText().toString().isEmpty()) {
            Toast.makeText(this, "Informe a quantidade.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantidade <= 0) {
            Toast.makeText(this, "A quantidade informada deve ser maior que 0.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edPrecoVenda.getText().toString().isEmpty()){
            Toast.makeText(this, "O campo de preço unitario deve ser preenchido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (precoVenda <= 0) {
            Toast.makeText(this, "O preço informado deve ser maior que 0.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void limparCampos(boolean condicao){
        edQuantidade.setText(null);
        edPrecoVenda.setText(null);
        atProdutos.setText(null);

        if (condicao){
            atClientes.setText(null);
            tvItens.setText(null);
        }

    }

    private void concluirPedido() {
        if (itensItemVenda.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos um item ao pedido.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (posCliente == -1) {
            Toast.makeText(this, "Selecione um cliente válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente clienteSelecionado = Controller.getInstance().retornarClientes().get(posCliente);

        Venda venda = new Venda();
        venda.setCliente(clienteSelecionado);
        venda.setItensVenda(itensItemVenda);
        venda.setValorTotal(valorTotal);
        venda.setDataVenda(new Date());

        Controller.getInstance().salvarVenda(venda);

        Toast.makeText(this, "Pedido de venda cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

        atClientes.setEnabled(true);
        limparCampos(true);
        itensItemVenda.clear();
        valorTotal = 0.0;
        tvValorTotal.setText("Valor Total: R$ 0.00");

        this.finish();
    }


}
