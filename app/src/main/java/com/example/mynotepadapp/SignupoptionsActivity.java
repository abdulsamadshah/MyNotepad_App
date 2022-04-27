package com.example.mynotepadapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotepadapp.Models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupoptionsActivity extends AppCompatActivity {
    EditText rs_name,rs_email,rs_password;
    Button rs_SignUp;
    TextView txt_signin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    CircleImageView profile_image;
    Uri imageuri;
    String IMAGEURI;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    Button google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupoptions);
        rs_name=findViewById(R.id.rs_name);
        rs_email=findViewById(R.id.rs_email);
        rs_password=findViewById(R.id.rs_password);
        rs_SignUp=findViewById(R.id.rs_SignUp);
        txt_signin=findViewById(R.id.txt_signin);
        profile_image=findViewById(R.id.profile_image);
        google=findViewById(R.id.google);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        getSupportActionBar().setTitle("SignUp");

        progressDialog = new ProgressDialog(SignupoptionsActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");



        // 2nd step:   Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(SignupoptionsActivity.this, gso);



        //xml file button onclick method
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        rs_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=rs_name.getText().toString();
                String email=rs_email.getText().toString();
                String password=rs_password.getText().toString();
                String status="He there I am using this application";

                auth.createUserWithEmailAndPassword(rs_email.getText().toString(),rs_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            DatabaseReference reference=database.getReference().child("user").child(auth.getUid());
                            StorageReference storageReference=storage.getReference().child("upload").child(auth.getUid());

                            if (imageuri !=null){
                                storageReference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                        if (task.isSuccessful()){
                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    IMAGEURI=uri.toString();
                                                    Users users=new Users(auth.getUid(),name,email,IMAGEURI,status);
                                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Intent intent = new Intent(SignupoptionsActivity.this,MainActivity.class);
                                                                startActivity(intent);
                                                                Toast.makeText(SignupoptionsActivity.this, "user created succesfully", Toast.LENGTH_SHORT).show();
                                                            }else{
                                                                Toast.makeText(SignupoptionsActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                });
                            }else{
                                IMAGEURI="https://firebasestorage.googleapis.com/v0/b/fir-retreivedata.appspot.com/o/avatar.png?alt=media&token=14a4879f-1697-41fb-903f-c681d8d515b4";
                                Users users=new Users(auth.getUid(),name,email,IMAGEURI,status);
                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(SignupoptionsActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(SignupoptionsActivity.this, "user created succesfully", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(SignupoptionsActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            Toast.makeText(SignupoptionsActivity.this, "user created succesfully", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignupoptionsActivity.this, "something wrong users", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignupoptionsActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //images
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,20);
            }
        });

    }




    //  3rd:step:
    int RC_SIGN_IN = 65;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
         if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
       else if (data !=null){
            imageuri=data.getData();
            profile_image.setImageURI(imageuri);

        }
    }



    //5th step:
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();
                            users.setUid(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setEmail(user.getEmail());
                            users.setImageUri(user.getPhotoUrl().toString());
                            database.getReference().child("User").child(user.getUid()).setValue(users);

                            Intent intent = new Intent(SignupoptionsActivity.this,MainActivity.class);
                            startActivity(intent);


                            //  updateUI(user);
                        } else {
                            Toast.makeText(SignupoptionsActivity.this, "error", Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            //  Log.w("TAG", "signInWithCredential:failure", task.getException());
                            // updateUI(null);
                        }
                    }
                });
    }
}