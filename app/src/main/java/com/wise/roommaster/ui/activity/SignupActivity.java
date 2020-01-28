package com.wise.roommaster.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;

public class SignupActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_start_signup);
        setTitle("Cadastrar");
        final EditText emailEdt = findViewById(R.id.signup_email_field);
        final EditText passwordEdt1 = findViewById(R.id.signup_password_field1);
        final EditText passwordEdt2 = findViewById(R.id.signup_password_field2);
    }
}
