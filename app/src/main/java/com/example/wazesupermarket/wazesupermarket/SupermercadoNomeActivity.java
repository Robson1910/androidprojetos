package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class SupermercadoNomeActivity extends AppCompatActivity {

    private TextView supermercadoNome;
    private Button buttonSupermercado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermercado_nome);
        supermercadoNome = (TextView) findViewById(R.id.textSuper);
        buttonSupermercado = (Button) findViewById(R.id.supermercado_button);

        buttonSupermercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean verificador4 = supermercadoNome.getText().toString().equals("");

                if(!verificador4){
                    Bundle extra = getIntent().getExtras();
                    String produ = extra.getString("categoria");

                    String superNome =  supermercadoNome.getText().toString();

                    Intent intent = new Intent(SupermercadoNomeActivity.this,Cadastroproduto.class);
                    intent.putExtra("supermercadoProdutoNome",superNome);
                    intent.putExtra("cate",produ);

                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(SupermercadoNomeActivity.this, "Escolhe uma Categoria", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void onClickView(View rdio){
        RadioGroup rd_group = (RadioGroup) findViewById(R.id.radio_super);
        switch (rd_group.getCheckedRadioButtonId()){
            case R.id.radio_epa:
                supermercadoNome.setText("Epa Plus");
                break;
            case R.id.radio_carrefour:
                supermercadoNome.setText("Carrefour");
                break;
            case R.id.radio_apoio:
                supermercadoNome.setText("Apoio Mineiro");
                break;
            case R.id.radio_bh:
                supermercadoNome.setText("Supermercado BH");
                break;
            case R.id.radio_extra:
                supermercadoNome.setText("Extra");
                break;

            case R.id.radio_via:
                supermercadoNome.setText("Via Brasil");
                break;

            case R.id.radio_central:
                supermercadoNome.setText("Mercado Central");
                break;
        }
    }
}
