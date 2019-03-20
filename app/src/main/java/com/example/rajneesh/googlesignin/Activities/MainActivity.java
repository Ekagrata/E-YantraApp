package com.example.rajneesh.googlesignin.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

     ImageButton signIn;
     GoogleSignInClient googleSignInClient;
     String name,email,photo;

     ViewFlipper viewFlipper;
     LoginButton login;
     CallbackManager callbackManager;
     GoogleApiClient googleApiClient;
     GoogleSignInAccount account;
     LinearLayout profile;
     ImageView animview;
    // int image[]= {R.drawable.ey1,R.drawable.ey2,R.drawable.ey5,R.drawable.ey4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences= getSharedPreferences("googlesignin", Context.MODE_PRIVATE);

        signIn= findViewById(R.id.googlebutton);
        VerifySignIn verifySignIn= new VerifySignIn();
        verifySignIn.execute();
        //animview= findViewById(R.id.animimage);
       // animview.setImageResource(R.drawable.anim);
      //  Glide.with(this).asDrawable().load(R.drawable.anim).into(animview);
       // animview.setBackgroundResource(R.drawable.anim);
        //animview.setImageBitmap(image.decodeSampledBitmapFromResource(this.getResources(),R.drawable.anim,100,100));

       //AnimationDrawable  animationDrawable= (AnimationDrawable)animview.getDrawable();

        //animationDrawable.start();






//        if(name!=null && email!=null){
//            Intent intent= new Intent(this,Main2Activity.class);
//           // Intent intent= new Intent(this,GalleryActivity.class);
//            Bundle bundle= new Bundle();
//            bundle.putString("name",name);
//            bundle.putString("email",email);
//            intent.putExtras(bundle);
//          //  startActivity(intent);
//        }

        GoogleSignInOptions signInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,1);
            }
        });
//        else{
//            signIn.setVisibility(View.VISIBLE);
//        }
     //   FacebookSdk.sdkInitialize(getApplicationContext());













       // login= findViewById(R.id.fblogin);
        callbackManager= CallbackManager.Factory.create();
//        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(MainActivity.this, "login successful "+loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(MainActivity.this, "login canceled", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(MainActivity.this, "error in login fb", Toast.LENGTH_SHORT).show();
//            }
//        });




//        for(int i=0;i<image.length;i++){
//            flipimages(image[i]);
//        }







    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Check your Internet Connection",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                account= result.getSignInAccount();
                 name= account.getDisplayName();
                 email= account.getEmail();
                if(account.getPhotoUrl()!=null) {
                    photo = account.getPhotoUrl().toString();
                    //  Toast.makeText(this, photo, Toast.LENGTH_LONG).show();
                }
                else{
                    photo=null;
                }
                String method="signup";
                Call<ResponseBody> call= APIClient.getInstance().getApi().gettesting(name,email);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     //   Toast.makeText(MainActivity.this, response.message()+"bla", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
               // BackgroundTask backgroundTask= new BackgroundTask(this);
                //backgroundTask.execute(method,email,name);



                SharedPreferences sharedPreferences= getSharedPreferences("googlesignin",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("name",name);
                editor.putString("email",email);
                editor.putString("photo",photo);
                editor.apply();

                Intent intent= new Intent(this,Main2Activity.class);
                Bundle bundle= new Bundle();
                bundle.putString("name",name);
                bundle.putString("email",email);
                bundle.putString("photo",photo);
                intent.putExtras(bundle);
                startActivity(intent);

             //   String imgurl= account.getPhotoUrl().toString();
             //   Name.setText(name);
             //   Glide.with(this).load(imgurl).into(photo);
             //   profile.setVisibility(View.VISIBLE);
               // signIn.setVisibility(View.GONE);



            }
//            else{
//                //profile.setVisibility(View.GONE);
//                signIn.setVisibility(View.VISIBLE);
//            }
        }
        else{
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    public  void flipimages(int image){
        ImageView imageView= new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);

        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    private class VerifySignIn extends AsyncTask<Void, Void, String> {

        public VerifySignIn() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            googleSignInClient = GoogleSignIn.getClient(MainActivity.this,gso);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
            if(account == null){
                return "n";
            }else{
                return "y";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("n")){
                //Show signIn
            }else if(s.equals("y")){
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                Bundle bundle= new Bundle();
           bundle.putString("name",name);
           bundle.putString("email",email);
           bundle.putString("photo",photo);
           intent.putExtras(bundle);
            startActivity(intent);
                finish();
            }
        }
    }


}
