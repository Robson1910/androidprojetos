package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;



public class Cadastroproduto2Activity extends AppCompatActivity {
    private Button cadastrar,excluir;
    private TextView produt,valo,mercado,loca,marca;
    private Produtos produtos;
    private DatabaseReference firebase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastroproduto2);

        cadastrar = (Button) findViewById(R.id.cadastros);
        excluir = (Button) findViewById(R.id.delete);
        produt = (TextView) findViewById(R.id.ViewNome);
        valo = (TextView) findViewById(R.id.ViewValor);
        mercado = (TextView) findViewById(R.id.ViewSupermecado);
        loca = (TextView) findViewById(R.id.ViewLocalizar);
        marca = (TextView) findViewById(R.id.ViewMarca);



        Bundle extra = getIntent().getExtras();
        final String supeNome= extra.getString("supNome");
        final String prod = extra.getString("cat");
        final String valor = extra.getString("valor");
        final String local = extra.getString("local");
        final String marcar = extra.getString("marca");

        produt.setText(prod);
        valo.setText(valor);
        mercado.setText(supeNome);
        loca.setText(local);
        marca.setText(marcar);

        firebaseAuth = FirebaseAuth.getInstance();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produtos = new Produtos();
                produtos.setNome(prod);
                produtos.setValor(String.valueOf(valor));
                produtos.setSupermercado(supeNome);
                produtos.setLocal(local);
                produtos.setMarca(marcar);
                produtos.setUsuariouid(firebaseAuth.getCurrentUser().getUid());
                salvarProduto(produtos);

                startActivity(new Intent(Cadastroproduto2Activity.this,MainActivity.class));

                salvarP();

                finish();
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cadastroproduto2Activity.this,MainActivity.class));
                finish();
                Toast.makeText(Cadastroproduto2Activity.this, "Produto Cancelado", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean salvarProduto(Produtos produtos) {
        Bundle extra = getIntent().getExtras();
        final String prod = extra.getString("cat");


        try {
            firebase = ConfiguracaoFirebase.getFirebase().child(prod);
            firebase.child(String.valueOf(produtos.getRand())).setValue(produtos);


            Toast.makeText(Cadastroproduto2Activity.this, "Produto inserido com sucesso", Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarP() {
        Bundle extra = getIntent().getExtras();
        final String local = extra.getString("local");
        final String supeNome= extra.getString("supNome");
        final String prod = extra.getString("cat");


        try {
            firebase = ConfiguracaoFirebase.getFirebase().child(supeNome);
            firebase.child(local.replace(".","").replace("-","")+("/"+String.valueOf(produtos.getRand()))).setValue(prod);



            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
