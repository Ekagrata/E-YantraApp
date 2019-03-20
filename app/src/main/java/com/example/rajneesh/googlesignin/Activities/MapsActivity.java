package com.example.rajneesh.googlesignin.Activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.MapResponse;
import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.mapclgResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
   List<MapResponse> responses;
   View view1;
   ArrayList<String> clgnames;
   ArrayList<mapclgResponse> clgs;
//    ArrayList<String> clg_names;
//    String lats=null;
//    String clg="";
//    ArrayList<Double> longs;
//    ArrayList<String> addresses;
//    ArrayList<LatLng> latslngs;
//    MapsOpenHelper openHelper;
//    SQLiteDatabase databaseRead;
//    SQLiteDatabase databaseWrite;
//    String clg_name;
   // LatlongDao lldao;
    //latlngTable obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       // lldao=AppDatabase.getInstance(MapsActivity.this).getlatlngDao();


//        openHelper= new MapsOpenHelper(this);
//         databaseRead= openHelper.getReadableDatabase();
//         databaseWrite= openHelper.getWritableDatabase();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        clgs= new ArrayList<>();
        fetchclg();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


       LatLng base = new LatLng(22.9734, 78.6569);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Kerela"));
       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(base,4));

        fetchColleges();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                LayoutInflater inflater = MapsActivity.this.getLayoutInflater();
                view1= inflater.inflate(R.layout.alertdialog_maps,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                TextView clgname;
                TextView grade;
                clgname= view1.findViewById(R.id.clg_name);
                grade= view1.findViewById(R.id.grade);
                int pos= Integer.parseInt(marker.getTitle());
             //   Toast.makeText(MapsActivity.this,responses.get(pos).getClg_name() , Toast.LENGTH_SHORT).show();
                clgname.setText(responses.get(pos).getClg_name());
                grade.setText(responses.get(pos).getGrade());
                // Get the layout inflater


                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                builder.setView(view1).show();
                return false;
            }
        });

    }

    private void fetchColleges(){
        retrofit2.Call<List<MapResponse>> call= APIClient.getInstance().getApi().getMaps();
        responses= new ArrayList<>();
        call.enqueue(new Callback<List<MapResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<MapResponse>> call, Response<List<MapResponse>> response) {
               // MapResponse response1= response.body();
                responses= response.body();
              //  ArrayList<MapResponse.Response> responses= response1.getResponses();


                if(responses!=null) {
                  //  Toast.makeText(MapsActivity.this,responses.get(1).getClg_name()+"data fetched",Toast.LENGTH_SHORT).show();
                    //latlngAsyncTask asyncTask= new latlngAsyncTask();
                    for (int i = 0; i < responses.size(); i++) {
                       Double lat= responses.get(i).getLat();
                       Double lng= responses.get(i).getLng();
                       LatLng ll= new LatLng(lat,lng);
                       String clgnm= responses.get(i).getClg_name();
                     //  clgnames.add(i,clgnm);

                    //   BitmapDescriptor bitmap= BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_black_24dp);
                        Drawable circleDrawable = getResources().getDrawable(R.drawable.ic_location_on_black_24dp);
                        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
                        mMap.addMarker(new MarkerOptions().icon(markerIcon).position(ll).title(i+""));
                    }


                         // getLocationFromAddress(add,clg_name);
                       // Mapss m= new Mapss(clg_name,add);



//                       latlngAsyncTask asyncTask= new latlngAsyncTask(new latlngAsyncTask.latlngListner() {
//                           @Override
//                           public void onlatlngGet(ArrayList<LatLng> latLngs) {
//                               if(latLngs!=null){
//                                   Toast.makeText(MapsActivity.this,"in async return",Toast.LENGTH_SHORT).show();
//                                   for(int i=0;i<latLngs.size();i++){
//                                       mMap.addMarker(new MarkerOptions().position(latLngs.get(i)).title(clg_names.get(i)));
//                                   }
//                               }
//                           }
//                       },MapsActivity.this);

                       //  getLocationFromAddress(add,clg_name);
                       // clg_names.add(responses.get(i).getClg_name());

//                    String log="";
//                    for(int i=0;i<lats.size();i++){
//                        log=log+lats.get(i)+",";
//                    }
//                    Toast.makeText(MapsActivity.this,log,Toast.LENGTH_SHORT).show();
                  // Log.d("latss",lats);
//                  //  asyncTask.execute(addresses);
                  //  Toast.makeText(MapsActivity.this,"fuck",Toast.LENGTH_SHORT).show();


                }
            //    Toast.makeText(MapsActivity.this,responses.get(1).getClg_name(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(retrofit2.Call<List<MapResponse>> call, Throwable t) {
                Toast.makeText(MapsActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void getLocationFromAddress(String strAddress, String clg_name) {
//
//        Geocoder coder = new Geocoder(this);
//        List<Address> address;
//        LatLng p1=null;
//
//        try {
//            // May throw an IOException
//             address = coder.getFromLocationName(strAddress, 5);
//            if (address.isEmpty()) {
//                Toast.makeText(MapsActivity.this,"address is null cant find latlong",Toast.LENGTH_SHORT).show();
//            }
//            else {
//
//                Address location = address.get(0);
//                p1 = new LatLng(location.getLatitude(), location.getLongitude());
//
//                Double lat= p1.latitude;
//                Double lng= p1.longitude;
//               // lats.add(lat);
//               // longs.add(lng);
//                lats= lats+lat+",";
//
//               // mMap.addMarker(new MarkerOptions().position(p1).title(clg_name));
//            }
//
//        } catch (IOException ex) {
//
//            ex.printStackTrace();
//        }
//
//
//
//
//    }
//
//    public class latlngAsyncTask extends AsyncTask<ArrayList<String>,Void,ArrayList<LatLng>> {
//
//        @Override
//        protected ArrayList<LatLng> doInBackground(ArrayList<String>... strings) {
//            Geocoder coder = new Geocoder(MapsActivity.this);
//            ArrayList<String> map = strings[0];
//            //    String addr= map.getAddress();
//            //  String clgname= map.getClg_name();
//            //  String addresses= strings[0];
//            List<Address> address;
//            LatLng p1 = null;
//            ArrayList<LatLng> latlngs = new ArrayList<>();
//
//            for (int i = 0; i < map.size(); i++) {
//                String addr = map.get(i);
//
//                try {
//                    // May throw an IOException
//
//                    address = coder.getFromLocationName(addr, 5);
//                    if (address.isEmpty()) {
//                        //clg_names.remove(i);
//                        return null;
//                        //Toast.makeText(MapsActivity.this,"address is null cant find latlong",Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        Address location = address.get(0);
//                        p1 = new LatLng(location.getLatitude(), location.getLongitude());
//                        latlngs.add(p1);
//                        Double lat= p1.latitude;
//                        //ReturnType ret= new ReturnType(p1,clgname);
//
//
//                    }
//
//                } catch (IOException ex) {
//
//                    ex.printStackTrace();
//                }
//
//
//            }
//
//            return latlngs;
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<LatLng> rett) {
//            if (rett != null) {
//
//                //ArrayList<latlngTable> list = (ArrayList)lldao.getall();
//                for (int i = 0; i < rett.size(); i++) {
//                    LatLng ll = rett.get(i);
//                    mMap.addMarker(new MarkerOptions().position(ll).title(clg_names.get(i)));
//                }
//
////                LatLng latLng= rett.getLatLng();
////                String clgname= rett.getClg_name();
////                String[] Tablecol={"clg_name"};
////                String[] SelectionArgs={latLng.latitude+"",latLng.longitude+""};
////                String clgnm=null;
////                Cursor cursor= databaseRead.query("MapsTable",Tablecol,"lat=? AND long=?",SelectionArgs,null,null,null);
////                while(cursor.moveToNext()){
////                     clgnm= cursor.getString(cursor.getColumnIndex("clg_name"));
////                }
//                // String clgname= lldao.getclgname(latLng.latitude,latLng.longitude);
//
////                if (clgnm!=null) {
////                    Toast.makeText(MapsActivity.this, "db fetch" , Toast.LENGTH_SHORT).show();
////                    String[] clms= {"lat","long"};
////                    String[] selctnarg={clgnm+""};
////                    Double latitude=null,longitude=null;
////                    Cursor cursor1= databaseRead.query("MapsTable",clms,"clg_name=?",selctnarg,null,null,null);
////                    while (cursor1.moveToNext()){
////                        latitude= cursor1.getDouble(cursor1.getColumnIndex("lat"));
////                        longitude= cursor1.getDouble(cursor1.getColumnIndex("long"));
////                    }
////                   // Double[] latlng=lldao.getlatlng(clgname);
////                        LatLng ll= new LatLng(latitude,longitude);
////                        mMap.addMarker(new MarkerOptions().position(ll).title(clgnm));
////
////                } else {
//
////                        Toast.makeText(MapsActivity.this, "in async return", Toast.LENGTH_SHORT).show();
////                    ContentValues contentValues = new ContentValues();
////                    contentValues.put("clg_name",clg_name);
////                    contentValues.put("lat",latLng.latitude);
////                    contentValues.put("long",latLng.longitude);
//                //   long sno= databaseWrite.insert("MapsTable",null,contentValues);
//                // obj = new latlngTable(clgname,latLng.latitude,latLng.longitude);
//                //lldao.insertLatlng(obj);
//
//                // }
//
//            }
//
//            super.onPostExecute(rett);
//        }
////    }
//
////    class ReturnType{
////        LatLng latLng;
////        String clg_name;
////
////        public LatLng getLatLng() {
////            return latLng;
////        }
////
////        public void setLatLng(LatLng latLng) {
////            this.latLng = latLng;
////        }
////
////        public String getClg_name() {
////            return clg_name;
////        }
////
////        public void setClg_name(String clg_name) {
////            this.clg_name = clg_name;
////        }
////
////        public ReturnType(LatLng latLng, String clg_name) {
////            this.latLng = latLng;
////            this.clg_name = clg_name;
////        }
////    }
//    }


    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void fetchclg(){
        Call<List<mapclgResponse>> call= APIClient.getInstance().getApi().getclgs();
        call.enqueue(new Callback<List<mapclgResponse>>() {
            @Override
            public void onResponse(Call<List<mapclgResponse>> call, Response<List<mapclgResponse>> response) {
//                Log.d("init",clgs.get(1).getClg_name());
                clgs= (ArrayList)response.body();
                mapclgResponse clg;
                String clgname="";
                for(int i=177;i<clgs.size();i++){
                    clg= clgs.get(i);
                    clgname= clgname+"\""+clg.getClg_name()+"\",";
                }
                Log.d("clgnames",clgname);

            }

            @Override
            public void onFailure(Call<List<mapclgResponse>> call, Throwable t) {
             //   Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

