package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProdutosActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Produtos> adapter;
    private ArrayList<Produtos> produtos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerProdutos;
    private EditText theFilter;
    private Button filtar;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        produtos = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewProdutos);
        adapter = new ProdutosAdapter(this,produtos);
        listView.setAdapter(adapter);
        filtar = (Button) findViewById(R.id.filter);
        theFilter = (EditText) findViewById(R.id.theFilter);

        // essa linha do codigo e para nao iniciar o teclado junto com aplicativo
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        filtar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean verificador = theFilter.getText().toString().equals("");
                if(!verificador){
                    Bundle extra = getIntent().getExtras();
                    String produto = extra.getString("categori");
                    String theFiletr = theFilter.getText().toString();
                    Intent intent = new Intent(ProdutosActivity.this,listviewfilterActivity.class);
                    intent.putExtra("filter",theFiletr);
                    intent.putExtra("categor",produto);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ProdutosActivity.this, "Digite nome do produto para filtrar ", Toast.LENGTH_LONG).show();
                }
            }
        });

        Bundle extra = getIntent().getExtras();
        String produto = extra.getString("categori");
        firebase = ConfiguracaoFirebase.getFirebase().child(produto);

        valueEventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Produtos produtosNovo = dados.getValue(Produtos.class);

                    produtos.add(produtosNovo);

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
                Intent intent = new Intent(ProdutosActivity.this,MapsActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_excluir,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()== R.id.menuExcluir){

            auth = FirebaseAuth.getInstance();
            String Auth = auth.getCurrentUser().getUid().toString();

            Bundle extra = getIntent().getExtras();
            String produto = extra.getString("categori");

            Intent intent = new Intent(ProdutosActivity.this,exculirProdutoCadastradoActivity.class);
            intent.putExtra("excluirProduto",produto);
            intent.putExtra("Auth",Auth);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
