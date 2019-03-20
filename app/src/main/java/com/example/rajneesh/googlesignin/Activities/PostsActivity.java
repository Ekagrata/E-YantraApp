package com.example.rajneesh.googlesignin.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.CustomAdapters.PostsRecyclerAdapter;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.PostsResponse;
import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PostsActivity extends AppCompatActivity {

//EditText postdesc;
ImageButton addimage;
Uri ImageUri;
View view1,view2;
    String photo;
String optionselected;
    SharedPreferences sharedPreferences1;
    String pref;
ImageButton upload;
LayoutInflater inflater;
ImageView photouploaded;
Bitmap bitmap;
String thestring;
EditText feedtext;
TextView username;
RecyclerView recyclerView;
PostsRecyclerAdapter adapter;
ArrayList<PostsResponse> posts;


static int Gallery_Pick =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat Forum");
        inflater= this.getLayoutInflater();
        posts= new ArrayList<>();
        //postdesc=findViewById(R.id.postdescription);
    //    addimage= findViewById(R.id.addimage);
      //  photouploaded= findViewById(R.id.photoUploaded);
       // upload= findViewById(R.id.upload);
      //  username= findViewById(R.id.username);
        recyclerView= findViewById(R.id.postlist);
      //  SharedPreferences sharedPreferences= getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
       // String name= sharedPreferences.getString("name","");
       // username.setText(name);


      sharedPreferences1= getSharedPreferences("choiceSelected",Context.MODE_PRIVATE);
         pref= sharedPreferences1.getString("optionSelected","null");

        SharedPreferences sharedPreferences= getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
        final String name= sharedPreferences.getString("name","");
         photo= sharedPreferences.getString("photo","");
       if(photo==null){
           photo= "https://www.daviker.co.uk/wp-content/uploads/profile-blank.png";
       }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (pref.equals("null")) {
                    Log.d("prefget", pref);
                    AlertDialog alertDialog = new AlertDialog.Builder(PostsActivity.this).create();
                    alertDialog.setMessage("Please fill the form to enable posting...");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Fill Form", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            display_form();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    alertDialog.show();
                } else {

                    view1 = inflater.inflate(R.layout.newsfeed_alertdialog_layout, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostsActivity.this);
                    builder.setView(view1);

                    builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            feedtext = view1.findViewById(R.id.feedText);
                            String feed = feedtext.getText().toString();
                            Call<Response> call = APIClient.getInstance().getApi().putPost(name, feed,photo);
                            call.enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    Response response1= response.body();
                                 //   Toast.makeText(PostsActivity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {
                                    Toast.makeText(PostsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            fetchPosts();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();

                }
            }
        });


//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
////                AsyncTask task = new AsyncTask();
////                task.execute(bitmap);
//                //  String im=imagetostring(bitmap);
//               // Toast.makeText(PostsActivity.this, imagetostring(bitmap), Toast.LENGTH_LONG).show();
////                while (task.getStatus() != AsyncTask.Status.FINISHED){
////
////                }
//
//              //  String usernm= username.getText().toString();
//              //  Toast.makeText(PostsActivity.this, usernm, Toast.LENGTH_SHORT).show();
//             //   String desc= postdesc.getText().toString();
////                Calendar calDate= Calendar.getInstance();
////                SimpleDateFormat curentdate= new SimpleDateFormat("dd-MMMM-yyyy");
////                String currentDate=curentdate.format(calDate.getTime());
////
////                Calendar calTime= Calendar.getInstance();
////                SimpleDateFormat curenttime= new SimpleDateFormat("HH:mm");
////                String currentTime=curenttime.format(calTime.getTime());
////
////                String postnm= currentDate+currentTime;
//                Call<Response> call = APIClient.getInstance().getApi().putPost(usernm,desc);
//                call.enqueue(new Callback<Response>() {
//                    @Override
//                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                        Response response1= response.body();
//                        Toast.makeText(PostsActivity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Response> call, Throwable t) {
//                        Toast.makeText(PostsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            //    fetchPosts();






//                try {
//                    File file=FileUtils.toFile(new URL(ImageUri.toString()));
//                    Toast.makeText(PostsActivity.this, new URL(ImageUri.toString())+"", Toast.LENGTH_SHORT).show();
//                    RequestBody filepart= RequestBody.create(MediaType.parse(getContentResolver().getType(ImageUri)),file);
//                    MultipartBody.Part mainfile=MultipartBody.Part.createFormData("photo",file.getName(),filepart);
//                    Toast.makeText(PostsActivity.this, mainfile+"", Toast.LENGTH_SHORT).show();
//                    Call<ResponseBody> call= APIClient.getInstance().getApi().uploadphoto(mainfile);
//                    call.enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                            Toast.makeText(PostsActivity.this,response.message(),Toast.LENGTH_LONG);
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Toast.makeText(PostsActivity.this,t.getMessage(),Toast.LENGTH_LONG);
//
//                        }
//                    });
//
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    Toast.makeText(PostsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }




//                    Call<Response> call = APIClient.getInstance().getApi().getImageuploadResponse(imagetostring(bitmap));
//                    call.enqueue(new Callback<Response>() {
//                        @Override
//                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//
//                            Response response1;
//                            response1 = response.body();
//                            Toast.makeText(PostsActivity.this, "init", Toast.LENGTH_SHORT).show();
//                            if (response1 != null) {
//                                Toast.makeText(PostsActivity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(PostsActivity.this, "response was null", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Response> call, Throwable t) {
//                            Toast.makeText(PostsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });

          //  }
        //});

//        addimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent galleryIntent= new Intent();
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent,Gallery_Pick);
//            }
//        });
        fetchPosts();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());





    }

    private void fetchPosts() {
        Call<List<PostsResponse>> call= APIClient.getInstance().getApi().getPosts();
        call.enqueue(new Callback<List<PostsResponse>>() {
            @Override
            public void onResponse(Call<List<PostsResponse>> call, retrofit2.Response<List<PostsResponse>> response) {
                posts= (ArrayList) response.body();
                adapter= new PostsRecyclerAdapter(PostsActivity.this, posts, new PostsRecyclerAdapter.OnItemClickListner() {
                    @Override
                    public void OnItemClicked(int position) {
                       // Toast.makeText(PostsActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnCommentSelected(int position) {
                        Intent  intent= new Intent(PostsActivity.this,CommentActivity.class);
                        int id= posts.get(position).getId();
                        Bundle bundle= new Bundle();
                        bundle.putInt("id",id);
                        bundle.putString("token","post");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                },getWindowManager());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PostsResponse>> call, Throwable t) {
                Toast.makeText(PostsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){
            ImageUri= data.getData();
         //   Toast.makeText(this, ImageUri+"", Toast.LENGTH_SHORT).show();

            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                photouploaded.setImageURI(ImageUri);
                photouploaded.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



    public class AsyncTask extends android.os.AsyncTask<Bitmap,Void,String>{

        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] imgBytes=byteArrayOutputStream.toByteArray();
            thestring=Base64.encodeToString(imgBytes,Base64.DEFAULT);
            return Base64.encodeToString(imgBytes,Base64.DEFAULT);
           // return null;
        }

        @Override
        protected void onPostExecute(String s) {
            thestring= s;

            super.onPostExecute(s);
        }
    }


    public void display_form(){
        view2= inflater.inflate(R.layout.fieldcheckdialogbox_layout,null);
        AlertDialog.Builder builder= new AlertDialog.Builder(PostsActivity.this);
        builder.setView(view2);
        final Spinner dropdown=view2.findViewById(R.id.spinner1);
        final EditText college= view2.findViewById(R.id.college);
        final EditText jobtitle= view2.findViewById(R.id.jobtitle);
        final EditText company= view2.findViewById(R.id.company);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PostsActivity.this,R.array.choices_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                optionselected= dropdown.getSelectedItem().toString();
                if(dropdown.getSelectedItem().toString().equals("Student")){
                    college.setVisibility(View.VISIBLE);
                    jobtitle.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                }
                else if(dropdown.getSelectedItem().toString().equals("Professional")){
                    college.setVisibility(View.GONE);
                    jobtitle.setVisibility(View.VISIBLE);
                    company.setVisibility(View.VISIBLE);
                }
                else if(dropdown.getSelectedItem().toString().equals("Facalty")){
                    college.setVisibility(View.VISIBLE);
                    jobtitle.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //   Toast.makeText(PostsActivity.this, "ok selected", Toast.LENGTH_SHORT).show();
//                sharedPreferences1= getSharedPreferences("choiceSelected",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences1.edit();
                editor.putString("optionSelected",optionselected);
                editor.apply();
                 pref= sharedPreferences1.getString("optionSelected","");
                Log.d("putinshare",pref);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }
}
