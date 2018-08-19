package com.example.wazesupermarket.wazesupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class Cadastro2Activity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputName;
    private Button btnSignUp;
    private FirebaseAuth auth;
    private DatabaseReference firebase;
    private Produtos produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);

        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.createUserBtn);
        inputEmail = (EditText) findViewById(R.id.emailEditTextCreate);
        inputPassword = (EditText) findViewById(R.id.passEditTextCreate);
        inputName = (EditText) findViewById(R.id.nomeEditTextCreate);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verificador = inputEmail.getText().toString().equals("");
                boolean verificador2 = inputPassword.getText().toString().equals("");
                boolean verificador3 = inputName.getText().toString().equals("");

                if(!verificador && !verificador2 && !verificador3 ) {

                   final String email = inputEmail.getText().toString().trim();
                   final String password = inputPassword.getText().toString().trim();
                   final String name = inputName.getText().toString().trim();


                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Senha curta, minimo 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Cadastro2Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    Toast.makeText(Cadastro2Activity.this, "Conta Criada :" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Cadastro2Activity.this, " Conta jรก existe",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        produtos = new Produtos();
                                        String x = String.valueOf(produtos.getRand());
                                        firebase = ConfiguracaoFirebase.getFirebase().child("emailCadastro");
                                        firebase.child(x +"/nome").setValue(name);
                                        firebase.child(x +"/email").setValue(email);
                                        firebase.child(x +"/senha").setValue(password);
                                        firebase.child(x +"/uid").setValue(auth.getCurrentUser().getUid());
                                        firebase.child(x +"/provedor").setValue("Email");
                                        startActivity(new Intent(Cadastro2Activity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(Cadastro2Activity.this, "Campo vazio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



}
