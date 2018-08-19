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


public class CadastroActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnSignup, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(CadastroActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_cadastro);


        inputEmail = (EditText) findViewById(R.id.edit_text_email_id);
        inputPassword = (EditText) findViewById(R.id.edit_text_password);

        btnSignup = (Button) findViewById(R.id.button_sign_up);
        btnLogin = (Button) findViewById(R.id.logar);


        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroActivity.this, Cadastro2Activity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verificador = inputEmail.getText().toString().equals("");
                boolean verificador2 = inputPassword.getText().toString().equals("");

                if(!verificador && !verificador2) {

                    String email = inputEmail.getText().toString();
                    final String password = inputPassword.getText().toString();

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (!task.isSuccessful()) {
                                        if (password.length() < 6) {
                                            inputPassword.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(CadastroActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });

                } else {
                    Toast.makeText(CadastroActivity.this, "Campo vazio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
