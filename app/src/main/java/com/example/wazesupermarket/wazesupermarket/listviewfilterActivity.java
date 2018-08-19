package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listviewfilterActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Produtos> adapter;
    private ArrayList<Produtos> produtos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewfilter);

        produtos = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewfilter);
        adapter = new ProdutosAdapter(this,produtos);
        listView.setAdapter(adapter);

        Bundle extra = getIntent().getExtras();
        String por= extra.getString("categor");

        firebase= ConfiguracaoFirebase.getFirebase().child(por);

        valueEventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Produtos produtosNovo = dados.getValue(Produtos.class);
                    Bundle extra = getIntent().getExtras();
                    String filter= extra.getString("filter");
                    String produtosNome = produtosNovo.getMarca().toLowerCase();
                    String produtoPesquisa = filter.toString().toLowerCase();

                    if(produtoPesquisa.contains(produtosNome)) {

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
                TextView local = (TextView) view.findViewById(R.id.textViewLocalizar);
                Intent intent = new Intent(listviewfilterActivity.this,MapsActivity.class);
                intent.putExtra("loca",local.getText().toString());
                startActivity(intent);
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
