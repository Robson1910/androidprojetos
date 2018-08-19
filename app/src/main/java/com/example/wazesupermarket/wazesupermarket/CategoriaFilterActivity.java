package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CategoriaFilterActivity extends AppCompatActivity {

    private TextView categoria;
    private Button buttonCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_filter);

        categoria = (TextView) findViewById(R.id.textfilter);
        buttonCategoria = (Button) findViewById(R.id.filter_button);

        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean verificador4 = categoria.getText().toString().equals("");

                if(!verificador4){
                    String categoriaProduto =  categoria.getText().toString();

                    Intent intent = new Intent(CategoriaFilterActivity.this,ProdutosActivity.class);
                    intent.putExtra("categori",categoriaProduto);
                    startActivity(intent);
                    Toast.makeText(CategoriaFilterActivity.this,"Clica no Item para chamar o mapa", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(CategoriaFilterActivity.this, "Escolhe uma Categoria", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClickView(View rdio){
        RadioGroup rd_group = (RadioGroup) findViewById(R.id.radio_filter);
        switch (rd_group.getCheckedRadioButtonId()){
            case R.id.filter_feijao:
                categoria.setText("Feijão");
                break;
            case R.id.filter_arroz:
                categoria.setText("Arroz");
                break;
            case R.id.filter_carne:
                categoria.setText("Carne");
                break;
            case R.id.filter_bebidas:
                categoria.setText("Bebidas");
                break;
            case R.id.filter_macarrao:
                categoria.setText("Macarrão");
                break;

            case R.id.filter_sacolao:
                categoria.setText("Sacolão");
                break;

            case R.id.filter_biscoito:
                categoria.setText("Biscoito");
                break;


        }
    }
}
