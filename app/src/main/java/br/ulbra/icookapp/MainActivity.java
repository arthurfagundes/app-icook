package br.ulbra.icookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText edEmail, edSenha;
    private TextView txt_erro;
    private Button btEntrar, btRegistrar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        IniciarComponentes();

        btRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormCadastroActivity.class);
                startActivity(intent);
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String senha = edSenha.getText().toString();

                if (email.isEmpty() || senha.isEmpty()) {
                    txt_erro.setText("Preencha todos os campos!");
                }else {
                    txt_erro.setText("");
                    AutenticarUsuario();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void AutenticarUsuario(){
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IniciarTelaProdutos();
                        }
                    },3000);
                }else {
                    String erro;

                    try {
                        throw task.getException();
                    }catch (Exception e){
                        erro = "Erro ao logar usuário!";
                    }
                    txt_erro.setText(erro);
                }
            }
        });
    }

    public void IniciarTelaProdutos(){
        Intent intent = new Intent(MainActivity.this,ListaProdutosActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioAtual != null){
            IniciarTelaProdutos();
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void IniciarComponentes(){

        edEmail = findViewById(R.id.edtEmail);
        edSenha = findViewById(R.id.edtSenha);
        btEntrar = findViewById(R.id.btEntrar);
        btRegistrar = findViewById(R.id.btRegistrar);
        txt_erro = findViewById(R.id.txt_mensagemErro);
        progressBar = findViewById(R.id.progressBar);
    }
}