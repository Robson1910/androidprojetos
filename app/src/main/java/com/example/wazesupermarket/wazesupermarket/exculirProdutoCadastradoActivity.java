package com.example.wazesupermarket.wazesupermarket;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class exculirProdutoCadastradoActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Produtos> adapter;
    private ArrayList<Produtos> produtos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerProdutos;
    private AlertDialog alerta;
    private Produtos produtosexcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exculir_produto_cadastrado);

        produtos = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewProdutosExcluir);
        adapter = new ProdutosAdapter(this, produtos);
        listView.setAdapter(adapter);

        Bundle extra = getIntent().getExtras();
        final String produto = extra.getString("excluirProduto");
        final String auth = extra.getString("Auth");


        // essa linha do codigo e para nao iniciar o teclado junto com aplicativo
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        firebase = ConfiguracaoFirebase.getFirebase().child(produto);


        valueEventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Produtos produtosNovo = dados.getValue(Produtos.class);
                    String produtosNome = produtosNovo.getUsuariouid();

                    if (auth.equals(produtosNome)) {
                        produtos.add(produtosNovo);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                produtosexcluir = adapter.getItem(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(exculirProdutoCadastradoActivity.this);
                builder.setTitle("Confirma Exclusão?");
                builder.setMessage("Você deseja Excluir - " + produtosexcluir.getNome().toString() + " ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child(produto);
                        firebase.child(String.valueOf(produtosexcluir.getRand())).removeValue();

                        Toast.makeText(exculirProdutoCadastradoActivity.this, "Produto excluido com sucesso", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(exculirProdutoCadastradoActivity.this, "Produto não excluido", Toast.LENGTH_LONG).show();
                    }
                });

                alerta = builder.create();
                alerta.show();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerProdutos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerProdutos);
    }
}
