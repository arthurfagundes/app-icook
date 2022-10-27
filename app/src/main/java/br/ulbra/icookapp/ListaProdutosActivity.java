package br.ulbra.icookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ListaProdutosActivity extends AppCompatActivity {
    Button btsair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        btsair = findViewById(R.id.btSair);
        btsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ListaProdutosActivity.this,"Usuário Deslogado!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaProdutosActivity.this, FormCadastroActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }
}
