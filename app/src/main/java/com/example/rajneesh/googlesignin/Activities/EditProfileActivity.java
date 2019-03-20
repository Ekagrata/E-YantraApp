package com.example.rajneesh.googlesignin.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.intResponse;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    Spinner dropdown;
    String optionselected;
    EditText college,name,email,phone,jobtitle,company,year,branch;
    TextView changephoto;
    ImageView profilephoto;
    int Gallery_Pick=1;
    Bitmap bitmap;
    Bundle bundle1;
    Intent intent1;
    Uri ImageUri;
    String str_name,str_email,str_photo;
    final int STORAGE_PERMISSION_CODE= 123;
    final String UPLOAD_URL="http://ec2-35-154-66-115.ap-south-1.compute.amazonaws.com/server/public/image";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dropdown= findViewById(R.id.spinner);
        college= findViewById(R.id.college);
        name= findViewById(R.id.name);
        email= findViewById(R.id.email);
        phone= findViewById(R.id.phone);
        jobtitle= findViewById(R.id.jobtitle);
        company= findViewById(R.id.company);
        year= findViewById(R.id.year);
        branch= findViewById(R.id.branch);
        changephoto= findViewById(R.id.changephoto);
        profilephoto= findViewById(R.id.picture);
        requestStoragePermission();



//        final Intent intent= getIntent();
//        final Bundle bundle= intent.getExtras();
//        str_name =bundle.getString("name");
//        str_email= bundle.getString("email");
//       str_photo= bundle.getString("photo");

        SharedPreferences sharedPreferences= getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
       str_name= sharedPreferences.getString("name","");
       str_email=sharedPreferences.getString("email","");
       str_photo=sharedPreferences.getString("photo","");


       name.setText(str_name);
       email.setText(str_email);
        Glide.with(this).load(str_photo).into(profilephoto);





        changephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent= new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Pick);
            }
        });

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditProfileActivity.this, R.array.choices_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                optionselected = dropdown.getSelectedItem().toString();
                if (dropdown.getSelectedItem().toString().equals("Student")) {
                    college.setVisibility(View.VISIBLE);
                    branch.setVisibility(View.VISIBLE);
                    year.setVisibility(View.VISIBLE);
                    jobtitle.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                } else if (dropdown.getSelectedItem().toString().equals("Professional")) {
                    college.setVisibility(View.GONE);
                    branch.setVisibility(View.GONE);
                    year.setVisibility(View.GONE);
                    jobtitle.setVisibility(View.VISIBLE);
                    company.setVisibility(View.VISIBLE);
                } else if (dropdown.getSelectedItem().toString().equals("Faculty")) {
                    college.setVisibility(View.VISIBLE);
                    branch.setVisibility(View.VISIBLE);
                    year.setVisibility(View.GONE);
                    jobtitle.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                }
    }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ImageUri!=null) {
                    uploadImage();
                    File file = new File(getPath(ImageUri));
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    Log.d("multipart", filePart + "");
                    String propic = getPath(ImageUri);
                }
                String nm= name.getText().toString();
                String clg = college.getText().toString();
                String em= email.getText().toString();
                String phn= phone.getText().toString();
                String jb= jobtitle.getText().toString();
                String com= company.getText().toString();
                String yr= year.getText().toString();
                String bra= branch.getText().toString();
                String type= dropdown.getSelectedItem().toString();
                Call<intResponse> call= APIClient.getInstance().getApi().putprofile(nm,clg,em,phn,jb,com,yr,bra,type);
                call.enqueue(new Callback<intResponse>() {
                    @Override
                    public void onResponse(Call<intResponse> call, Response<intResponse> response) {
                        intResponse response1 = response.body();
                        try {
                            int uniqueID = response1.getMessage();
                            Log.d("unique edit", uniqueID + "");
                            Intent intent2 = new Intent(EditProfileActivity.this, ProfileActivity.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("uniqueid", uniqueID);
                            Toast.makeText(EditProfileActivity.this, uniqueID + "", Toast.LENGTH_SHORT).show();
                            intent2.putExtras(bundle2);


                            startActivity(intent2);
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        // bundle1.putString("uniqueid",uniqueID)
                        //intent1.putExtras(bundle1);


                    }

                    @Override
                    public void onFailure(Call<intResponse> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });








            }
        });



    }

//    public  void putdata(){
//        String propic= ImageUri.toString();
//        String nm= name.getText().toString();
//        String clg = college.getText().toString();
//        String em= email.getText().toString();
//        String phn= phone.getText().toString();
//        String jb= jobtitle.getText().toString();
//        String com= company.getText().toString();
//        String yr= year.getText().toString();
//        String bra= branch.getText().toString();
//        String type= dropdown.getSelectedItem().toString();
//        Call<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> call= APIClient.getInstance().getApi().putprofile(propic,nm,clg,em,phn,jb,com,yr,bra,type);
//        call.enqueue(new Callback<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response>() {
//            @Override
//            public void onResponse(Call<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> call, Response<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> response) {
//                com.example.rajneesh.googlesignin.ResponseHolderClasses.Response response1= response.body();
//                 uniqueID=response1.getMessage();
//
//               // bundle1.putString("uniqueid",uniqueID);
//                Log.d("unique edit",uniqueID);
//                //intent1.putExtras(bundle1);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> call, Throwable t) {
//                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){
            ImageUri= data.getData();

            try {
             //   Toast.makeText(this, ImageUri+"", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, getPath(ImageUri), Toast.LENGTH_SHORT).show();
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
               // profilephoto.setImageURI(ImageUri);
                Glide.with(this).load(ImageUri).into(profilephoto);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPath(Uri uri){
        Cursor cursor= getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String document_id= cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID+"=?", new String[]{document_id},null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        Log.d("path",path);
        return path;
    }



    private void uploadImage(){
        String path = getPath(ImageUri);


        try{
            String uploadid= UUID.randomUUID().toString();
            new MultipartUploadRequest(this,uploadid,UPLOAD_URL)
                    .addFileToUpload(path,"image")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
        }
        catch( Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}




