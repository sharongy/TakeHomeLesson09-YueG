package com.example.takehomelesson09_yueg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mrAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.edit_text_email);
        passwordEditText = findViewById(R.id.edit_text_password);
        mrAuth = FirebaseAuth.getInstance();
    }

    public void logIn(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mrAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LogInActivity.this, task.getResult().getUser().getEmail() + " logged in successful",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    public void signUp(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mrAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LogInActivity.this, task.getResult().getUser().getEmail() + " signed up successful",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}

