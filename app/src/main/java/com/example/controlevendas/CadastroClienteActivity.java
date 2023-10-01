package com.example.controlevendas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlevendas.modelo.Cliente;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText edNome;
    private EditText edCPF;
    private Button btCadastrar;

    private TextView tvClientesCadastrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        btCadastrar = findViewById(R.id.btClienteCadastrar);
        edNome = findViewById(R.id.edClienteNome);
        edCPF = findViewById(R.id.edClienteCpf);
        tvClientesCadastrados = findViewById(R.id.tvClientesCadastrado);

        atualizarClientes();

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });

    }

    private void salvarCliente(){
        if(edNome.getText().toString().isEmpty()){
            edNome.setError("Informe o campo nome");
            return;
        }
        if(edCPF.getText().toString().isEmpty()){
            edCPF.setError("Informe o campo CPF");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(edNome.getText().toString());
        cliente.setCpf(edCPF.getText().toString());

        Controller.getInstance().salvarCliente(cliente);
        Toast.makeText(CadastroClienteActivity.this, "Cliente cadastrado com sucesso!",
                Toast.LENGTH_LONG).show();
        this.finish();

    }

    private void atualizarClientes() {
        String texto = "";
        for (Cliente cliente: Controller.getInstance().retornarClientes()){
            texto += "Nome: "+cliente.getNome()+" - CPF: "+cliente.getCpf()+"\n";
        }
        tvClientesCadastrados.setText(texto);
    }

}