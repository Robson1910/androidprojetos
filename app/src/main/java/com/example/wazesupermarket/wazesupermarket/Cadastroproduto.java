package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

public class Cadastroproduto extends AppCompatActivity {

    private Button btnGravar;
    private EditText edtMarca, edtValor;
    private static final String LOG_TAG = "<LOG> ";
    private TextView myPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastroproduto);

        edtMarca = (EditText) findViewById(R.id.edtMarca);
        edtValor = (EditText) findViewById(R.id.edtValor);
        btnGravar = (Button) findViewById(R.id.btnGravar);
        myPlace = (TextView) findViewById(R.id.myplace);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: obter informações sobre o local selecionado.
                Log.i(LOG_TAG, "Place: " + place.getName());
                myPlace.setText(place.getAddress());
            }

            @Override
            public void onError(Status status) {
                // TODO: Solucionar o erro.
                Log.i(LOG_TAG, "Ocorreu um erro: " + status);
            }
        });


        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean verificador = edtMarca.getText().toString().equals("");
                boolean verificador2 = edtValor.getText().toString().equals("");
                boolean verificador4 = myPlace.getText().toString().equals("");

                if(!verificador && !verificador2  && !verificador4){
                    Bundle extra = getIntent().getExtras();
                    String supermecado = extra.getString("supermercadoProdutoNome");
                    String produto = extra.getString("cate");

                    String marca = edtMarca.getText().toString();
                    String valor = edtValor.getText().toString();
                    String local =  myPlace.getText().toString();


                    Intent intent = new Intent(Cadastroproduto.this,Cadastroproduto2Activity.class);
                    intent.putExtra("marca",marca);
                    intent.putExtra("valor",valor);
                    intent.putExtra("local",local);
                    intent.putExtra("cat",produto);
                    intent.putExtra("supNome",supermecado);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Cadastroproduto.this, "Campo vazio", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
