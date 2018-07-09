package com.example.meesh.dronaid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener{

    private Button login;
    private EditText edtemail,edtpassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), admin.class));
        }

        login=(Button)findViewById(R.id.login);
        edtemail=(EditText)findViewById(R.id.email);
        edtpassword=(EditText)findViewById(R.id.password);

        progressDialog=new ProgressDialog(this);

        login.setOnClickListener(this);

    }
    private void userlogin(){
        String email=edtemail.getText().toString().trim();
        String password=edtpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("User Logging In");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                                progressDialog.dismiss();
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), admin.class));
                            }
                            else {

                                Toast.makeText(login.this, "Invaild Email or Password.", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == login){
            userlogin();
        }

    }
}
