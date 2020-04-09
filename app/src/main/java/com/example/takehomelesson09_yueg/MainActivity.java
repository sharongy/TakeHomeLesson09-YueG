package com.example.takehomelesson09_yueg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference;

    EditText questionField;
    EditText answerField;

    private FirebaseAuth msAuth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionField = findViewById(R.id.question_field);
        answerField = findViewById(R.id.answer_field);

        userReference = database.getReference("HardCodedUser");

        msAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userReference = database.getReference(user.getUid());
                } else {
                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
                }
            }
        };
    }

    protected void onStart() {
        super.onStart();
        msAuth.addAuthStateListener(authListener);
    }

    public void onStop() {
        super.onStop();
        msAuth.removeAuthStateListener(authListener);
    }

    public void logOut(View view) {
        msAuth.signOut();
    }

    public void sendToFirebase(View view) {
        String q = questionField.getText().toString();
        String a = answerField.getText().toString();
        TestItem testItem = new TestItem(q, a);
        userReference.push().setValue(testItem);
    }

    public void remove(View view){
        questionField.setText("");
        answerField.setText("");
    }
}

