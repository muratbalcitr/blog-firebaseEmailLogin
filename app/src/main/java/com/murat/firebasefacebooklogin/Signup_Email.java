package com.murat.firebasefacebooklogin;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup_Email extends AppCompatActivity {

    private static final String TAG = "Activity";
    EditText etMail, etPassword, etPasswordValid;
    Button btnSignUp;
    TextView tvPasswordStrength;
    String mail, password, passwordvalid;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);
        etMail = findViewById(R.id.etSignupMailAddress);
        etPassword = findViewById(R.id.etSignUpPassword);
        etPasswordValid = findViewById(R.id.etSignUpPasswordRepeat);
        btnSignUp = findViewById(R.id.btnSignUpEmailPasswordLogin);
        tvPasswordStrength = findViewById(R.id.tvStrength);

        mAuth = FirebaseAuth.getInstance();//firebase nesnelerini alıyoruz

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateStrengthPassword(s.toString());
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_Email();
            }
        });
    }
    private void signup_Email() {

        mail = etMail.getText().toString();
        password = etPassword.getText().toString();
        passwordvalid = etPassword.getText().toString();

        if (password.equals(passwordvalid)){
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Signup_Email.this, "Hesap Başarılı bir şekilde oluşturuldu", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                             } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "hesap oluşturulamadı", task.getException());
                                Toast.makeText(Signup_Email.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                             }
                        }
                    });
        }else{
            Toast.makeText(this, "parolalar uyuşmuyor", Toast.LENGTH_SHORT).show();
        }

    }
    private void calculateStrengthPassword(String passwordText) {
        int buyukHarf = 0, kucukHarf = 0, sayilar = 0,
                ozelKarakterler = 0, digerKarakterler = 0, gucSeviyeNotu = 0;
        char c;

        int passwordLength = passwordText.length();

        if (passwordLength == 0) {
            tvPasswordStrength.setText("Invalid Password");
            tvPasswordStrength.setBackgroundColor(Color.RED);
            return;
        }

        //Parola 5 ten küçükse gucSeviyeNotu =1
        if (passwordLength <= 5) {
            gucSeviyeNotu = 1;
        }
        //Eğer parola uzunluğu  5'ten büyük 10 eşitse gucSeviyeNotu=2
        else if (passwordLength <= 10) {
            gucSeviyeNotu = 2;
        }
        //Eğer parola uzunluğu is >10 set gucSeviyeNotu=3
        else
            gucSeviyeNotu = 3;
        // Şifrenin karakterleri arasında dolaşıyoruz
        for (int i = 0; i < passwordLength; i++) {
            c = passwordText.charAt(i);
            // eğer parola küçük harf içeriyorsa
            // o zaman gucSeviyeNotu 1 arttırlır
            if (c >= 'a' && c <= 'z') {
                if (kucukHarf == 0) gucSeviyeNotu++;
                kucukHarf = 1;
            }
            // eğer parola büyük harf içeriyorsa
            // o zaman gucSeviyeNotu 1 arttırılır
            else if (c >= 'A' && c <= 'Z') {
                if (buyukHarf == 0) gucSeviyeNotu++;
                buyukHarf = 1;
            }
            // parola sayilar içeryorsa
            // o zaman gucSeviyeNotu 1 arttır
            else if (c >= '0' && c <= '9') {
                if (sayilar == 0) gucSeviyeNotu++;
                sayilar = 1;
            }
            //  parola  _ or @ içeryorsa
            // gucSeviyeNotu'bu 1 arttır
            else if (c == '_' || c == '@') {
                if (ozelKarakterler == 0) gucSeviyeNotu += 1;
                ozelKarakterler = 1;
            }
            // Parola özel karakterler içeriyorsa
            // gucSeviyeNotu'nu 1 arttırıyoruz.
            else {
                if (digerKarakterler == 0) gucSeviyeNotu += 2;
                digerKarakterler = 1;
            }
        }

        if (gucSeviyeNotu <= 3) {
            tvPasswordStrength.setText("Parola Gücü : DÜŞÜK");
            tvPasswordStrength.setBackgroundColor(Color.RED);
        } else if (gucSeviyeNotu <= 6) {
            tvPasswordStrength.setText("Parola Gücü : ORTA");
            tvPasswordStrength.setBackgroundColor(Color.YELLOW);
        } else if (gucSeviyeNotu <= 9) {
            tvPasswordStrength.setText("Parola Gücü : YÜKSEK");
            tvPasswordStrength.setBackgroundColor(Color.GREEN);
        }
    }
}
