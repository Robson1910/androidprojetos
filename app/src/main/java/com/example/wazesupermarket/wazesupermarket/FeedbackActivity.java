package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FeedbackActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private Produtos produtos;
    private Button salvar;
    private EditText mensagem;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        salvar = (Button) findViewById(R.id.feedback);
        mensagem = (EditText) findViewById(R.id.feed);

        firebaseAuth = FirebaseAuth.getInstance();
        firebase = ConfiguracaoFirebase.getFirebase().child("Feedback");

        // essa linha do codigo e para nao iniciar o teclado junto com aplicativo
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean verificador = mensagem.getText().toString().equals("");
                if(!verificador){

                    firebase = ConfiguracaoFirebase.getFirebase().child("Feedback");

                    produtos = new Produtos();
                    String x = String.valueOf(produtos.getRand());
                    firebase = ConfiguracaoFirebase.getFirebase().child("Feedback");
                    firebase.child(x +"/mensagem").setValue(mensagem.getText().toString());
                    firebase.child(x +"/email").setValue(firebaseAuth.getCurrentUser().getEmail());
                    firebase.child(x +"/uid").setValue(firebaseAuth.getCurrentUser().getUid());


                    finish();

                }
                else{
                    Toast.makeText(FeedbackActivity.this, "Campo vazio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}