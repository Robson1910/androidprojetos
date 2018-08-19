package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroCategoriaActivity extends AppCompatActivity {

    private TextView categoria;
    private Button buttonCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_categoria);

        categoria = (TextView) findViewById(R.id.textCategoria);
        buttonCategoria = (Button) findViewById(R.id.categoria_button);

        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean verificador4 = categoria.getText().toString().equals("");

                if(!verificador4){
                    String categoriaProduto =  categoria.getText().toString();

                    Intent intent = new Intent(CadastroCategoriaActivity.this,SupermercadoNomeActivity.class);
                    intent.putExtra("categoria",categoriaProduto);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(CadastroCategoriaActivity.this, "Escolhe uma Categoria", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

        public void onClickView(View rdio){
            RadioGroup rd_group = (RadioGroup) findViewById(R.id.radio_grupo);
            switch (rd_group.getCheckedRadioButtonId()){
                case R.id.radio_feijao:
                    categoria.setText("Feijão");
                    break;
                case R.id.radio_arroz:
                    categoria.setText("Arroz");
                    break;
                case R.id.radio_carne:
                    categoria.setText("Carne");
                    break;
                case R.id.radio_bebidas:
                    categoria.setText("Bebidas");
                    break;
                case R.id.radio_macarrao:
                    categoria.setText("Macarrão");
                    break;

                case R.id.radio_sacolao:
                    categoria.setText("Sacolão");
                    break;

                case R.id.radio_biscoito:
                    categoria.setText("Biscoito");
                    break;
            }
        }


    }

