package com.example.controlevendas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlevendas.modelo.Cliente;
import com.example.controlevendas.modelo.Produto;
import com.example.controlevendas.modelo.ItemVenda;
import com.example.controlevendas.modelo.Parcela;

import java.util.ArrayList;

public class CadastroVendaActivity extends AppCompatActivity {

    private Spinner spListaClientes, spListaProdutos;
    private EditText edQuantidade, edQuantidadeParcelas;
    private Button btAdicionarItem, btConcluirPedido;
    private RadioButton rbAVista, rbAprazo;
    private ListView lvItens, lvParcelas;
    private RadioGroup rgCondicaoPagamento;
    private TextView tvValorTotal, tvItens;
    private ArrayAdapter<Cliente> clienteAdapter;
    private ArrayAdapter<Produto> produtoAdapter;
    private ArrayAdapter<ItemVenda> itemAdapter;
    private ArrayAdapter<Parcela> parcelaAdapter;
    private ArrayList<ItemVenda> itensVenda;
    private ArrayList<Parcela> parcelas;
    private double valorTotal;
    private boolean isAVista = true; // Default: à vista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        spListaClientes = findViewById(R.id.spListaClientes);
        spListaProdutos = findViewById(R.id.spListaProdutos);
        edQuantidade = findViewById(R.id.edQuantidade);
        edQuantidadeParcelas = findViewById(R.id.edQuantidadeParcelas);
        btAdicionarItem = findViewById(R.id.btAdicionarItem);
        btConcluirPedido = findViewById(R.id.btConcluirPedido);
        lvItens = findViewById(R.id.lvItens);
        lvParcelas = findViewById(R.id.lvParcelas);
        rgCondicaoPagamento = findViewById(R.id.rgCondicaoPagamento);
        tvValorTotal = findViewById(R.id.tvValorTotal);
        rbAVista = findViewById(R.id.rbAVista);
        rbAprazo = findViewById(R.id.rbAPrazo);

        itensVenda = new ArrayList<>();
        parcelas = new ArrayList<>();

        // Inicialize os adapters para os spinners
        clienteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Controller.getInstance().retornarClientes());
        clienteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spListaClientes.setAdapter(clienteAdapter);

        produtoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Controller.getInstance().retornarProdutos());
        produtoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spListaProdutos.setAdapter(produtoAdapter);

        // Inicialize o adapter para a lista de itens
        itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itensVenda);
        lvItens.setAdapter(itemAdapter);

        // Inicialize o adapter para a lista de parcelas
        parcelaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, parcelas);
        lvParcelas.setAdapter(parcelaAdapter);

        // Listener para o botão "Adicionar Item"
        btAdicionarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItem();
            }
        });

        // Listener para o botão "Concluir Pedido"
        btConcluirPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirPedido();
            }
        });

        // Listener para o RadioGroup "Condição de Pagamento"
        rgCondicaoPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbAVista) {
                    isAVista = true;
                    edQuantidadeParcelas.setVisibility(View.GONE);
                    lvParcelas.setVisibility(View.GONE);
                } else if (checkedId == R.id.rbAPrazo) {
                    isAVista = false;
                    edQuantidadeParcelas.setVisibility(View.VISIBLE);
                    lvParcelas.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void adicionarItem() {
        Cliente clienteSelecionado = (Cliente) spListaClientes.getSelectedItem();
        Produto produtoSelecionado = (Produto) spListaProdutos.getSelectedItem();

        if (clienteSelecionado == null || produtoSelecionado == null) {
            Toast.makeText(this, "Selecione um cliente e um produto.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edQuantidade.getText().toString().isEmpty()) {
            Toast.makeText(this, "Informe a quantidade.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(edQuantidade.getText().toString());

        if (quantidade <= 0) {
            Toast.makeText(this, "A quantidade deve ser maior que zero.", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorItem = produtoSelecionado.getPreco() * quantidade;

        ItemVenda itemVenda = new ItemVenda(clienteSelecionado, produtoSelecionado, quantidade, valorItem);
        itensVenda.add(itemVenda);

        valorTotal += valorItem;
        tvValorTotal.setText("Valor Total: R$ " + String.format("%.2f", valorTotal));

        itemAdapter.notifyDataSetChanged();
    }

    private void concluirPedido() {
        if (itensVenda.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos um item ao pedido.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isAVista) {
            if (edQuantidadeParcelas.getText().toString().isEmpty()) {
                Toast.makeText(this, "Informe a quantidade de parcelas.", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantidadeParcelas = Integer.parseInt(edQuantidadeParcelas.getText().toString());

            if (quantidadeParcelas <= 0) {
                Toast.makeText(this, "A quantidade de parcelas deve ser maior que zero.", Toast.LENGTH_SHORT).show();
                return;
            }

            double valorParcela = valorTotal / quantidadeParcelas;

            parcelas.clear();
            for (int i = 1; i <= quantidadeParcelas; i++) {
                parcelas.add(new Parcela(i, valorParcela));
            }

            parcelaAdapter.notifyDataSetChanged();
        }

        if (isAVista) {
            valorTotal *= 0.95; // Aplicar desconto de 5% para vendas à vista
        } else {
            valorTotal *= 1.05; // Aplicar acréscimo de 5% para vendas a prazo
        }

        Toast.makeText(this, "Pedido de venda cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
