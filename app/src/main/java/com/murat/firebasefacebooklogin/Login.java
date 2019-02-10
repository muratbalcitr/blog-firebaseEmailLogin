package com.murat.firebasefacebooklogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private static final String TAG = "Activity";

    EditText etMail, etParola;
    String mail, parola;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_email_login);
        etMail = findViewById(R.id.etMailAddress);
        etParola = findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();//firebase nesnelerini alıyoruz

    }

    public void btnSignup(View view) {
        startActivity(new Intent(this, Signup_Email.class));

    }

    public void bntLogin(View view) {
        mail = etMail.getText().toString();
        parola = etParola.getText().toString();
        mAuth.signInWithEmailAndPassword(mail, parola)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "giriş başarılı");
                            startActivity(new Intent(Login.this,MainActivity.class));
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "giriş başarısız.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
