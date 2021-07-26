package com.lquirin.apprc_android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lquirin.apprc_android.R;
import com.lquirin.apprc_android.controller.ConnexionController;
import com.lquirin.apprc_android.utils.JWTUtils;

public class LoginActivity extends AppCompatActivity {

    TextView textViewEmail;
    TextView textViewPassword;
    Button boutonConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (JWTUtils.isTokenValide(this)) {
            startActivity(new Intent(this, AccueilActivity.class));
        } else {

            setContentView(R.layout.activity_login);

            textViewEmail = findViewById(R.id.email);
            textViewPassword = findViewById(R.id.password);
            boutonConnexion = findViewById(R.id.button_connexion);

            boutonConnexion.setOnClickListener((View v) -> {
                ConnexionController.getInstance().connexion(
                        this,
                        textViewEmail.getText().toString(),
                        textViewPassword.getText().toString(),
                        () -> startActivity(new Intent(this, AccueilActivity.class)),
                        (String messageErreur) -> Toast.makeText(this, messageErreur, Toast.LENGTH_LONG).show()
                );
            });

        }
    }
}
