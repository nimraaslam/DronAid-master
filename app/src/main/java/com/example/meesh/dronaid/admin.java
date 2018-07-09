package com.example.meesh.dronaid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class admin extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mfirebaseDatabase;
    private TextView name;
    private TextView uid;
    private TextView phoneNumber;
    private Button logout;
    private Button gallery;
    private DatabaseReference mDatabase;
    private String userID;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        firebaseAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mfirebaseDatabase.getReference();


        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, login.class));
        }

        FirebaseUser user  = firebaseAuth.getCurrentUser();
        assert user != null;
        userID = user.getUid();

        logout = (Button) findViewById(R.id.logout);

        img= (ImageView)findViewById(R.id.img);
        name=(TextView)findViewById(R.id.name);
        gallery=(Button) findViewById(R.id.gallery);
        uid=(TextView)findViewById(R.id.uid);
        phoneNumber=(TextView)findViewById(R.id.phoneNumber);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    adminInfo uInfo = new adminInfo();
                    uInfo.setName(ds.child(userID).getValue(adminInfo.class).getName());
                    uInfo.setUid(ds.child(userID).getValue(adminInfo.class).getUid());
                    uInfo.setPhoneNumber(ds.child(userID).getValue(adminInfo.class).getPhoneNumber());
                    uInfo.setImg(ds.child(userID).getValue(adminInfo.class).getImg());
                    name.setText("Name: " + uInfo.getName());
                    uid.setText("User ID: "+uInfo.getUid());
                    phoneNumber.setText("Phone: "+uInfo.getPhoneNumber());
                    Picasso.with(admin.this).load(uInfo.getImg().toString()).into(img);





                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        logout.setOnClickListener(this);
        gallery.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        if(view == logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, login.class));
        }

        if(view == gallery){
            finish();
            startActivity(new Intent(this, add.class));


        }

    }
}
