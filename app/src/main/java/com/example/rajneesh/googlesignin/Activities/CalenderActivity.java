package com.example.rajneesh.googlesignin.Activities;

import android.annotation.TargetApi;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.CalendarResponse;
import com.example.rajneesh.googlesignin.R;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderActivity extends AppCompatActivity {
    android.widget.CalendarView calendarView;
   // CalendarView calendarView;
    TextView description,title,initiative,location;
    SQLiteDatabase databaseread;
    SQLiteDatabase databasewrite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        description= findViewById(R.id.description);
        initiative= findViewById(R.id.initiative);
        title= findViewById(R.id.title);
        location= findViewById(R.id.location);
//        ContentResolver contentResolver = CalenderActivity.this.getContentResolver();
//        ContentValues cv = new ContentValues();
//        cv.put(CalendarContract.Events.TITLE, "event1");
//        cv.put(CalendarContract.Events.DESCRIPTION, "event1 described");
//        cv.put(CalendarContract.Events.EVENT_LOCATION, "delhi");
//        cv.put(CalendarContract.Events.DTSTART, Calendar.getInstance().getTimeInMillis());
//        cv.put(CalendarContract.Events.DTEND, Calendar.getInstance().getTimeInMillis() + 60 * 60 * 1000);
//        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
//        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
//        if (ActivityCompat.checkSelfPermission(CalenderActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, cv);


//        Openhelper openhelper= Openhelper.getInstance(CalenderActivity.this);
//          databaseread= openhelper.getReadableDatabase();
//          databasewrite= openhelper.getWritableDatabase();
        calendarView= findViewById(R.id.calendar);

//        description= findViewById(R.id.description);
//      //  databasewrite.execSQL("INSERT INTO events VALUES(456,\'2018-06-26\',\'blabla\',\'eyrc\')");
//         putindb();
        calendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView calendarView, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                int i1d=i1+1;
                calendar.set(i, i1d, i2);
                String date_selected = i + "-" + i1d+ "-" + i2;

                Toast.makeText(CalenderActivity.this, date_selected, Toast.LENGTH_LONG).show();

                Call<List<CalendarResponse>> call = APIClient.getInstance().getApi().getCalendar(date_selected);
                call.enqueue(new Callback<List<CalendarResponse>>() {
                    @Override
                    public void onResponse(Call<List<CalendarResponse>> call, Response<List<CalendarResponse>> response) {
                        Toast.makeText(CalenderActivity.this, "init", Toast.LENGTH_SHORT).show();
                        try{

                            CalendarResponse response1 =response.body().get(0);
                            Toast.makeText(CalenderActivity.this, "inloop", Toast.LENGTH_SHORT).show();
                            title.setText(response1.getTitle());
                            location.setText(response1.getLocation());
                            description.setText(response1.getDescription());
                            initiative.setText(response1.getInitiative());
                            title.setVisibility(View.VISIBLE);
                            location.setVisibility(View.VISIBLE);
                            description.setVisibility(View.VISIBLE);

                        }
                        catch (Exception e){
                            Toast.makeText(CalenderActivity.this,"No events", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CalendarResponse>> call, Throwable t) {
                        Toast.makeText(CalenderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });



//        calendarView.setOnDayClickListener(new OnDayClickListener() {
//            @Override
//            public void onDayClick(EventDay eventDay) {
//                Calendar clickedDayCalendar = eventDay.getCalendar();
//                clickedDayCalendar.get(eventDay)
//
//            }
//        });

//                if (ActivityCompat.checkSelfPermission(CalenderActivity.this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                 //   Toast.makeText(CalenderActivity.this, "Kindly Provide Permission", Toast.LENGTH_SHORT).show();
//                }
//                Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
//
//                while (cursor.moveToNext()) {
//                    if (cursor != null) {
//                        String id = cursor.getString(cursor.getColumnIndex(CalendarContract.Events._ID));
//                        String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
//                        String description = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION));
//                        String event = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
//                    //    Toast.makeText(CalenderActivity.this, title, Toast.LENGTH_SHORT).show();
//                    } else {
//                    //    Toast.makeText(CalenderActivity.this, "no event", Toast.LENGTH_SHORT).show();
//                    }
//                }

//                Date date_selected= new Date(calendar.getTimeInMillis());


//                String[] args={date_selected};
//                Cursor cursor= databaseread.rawQuery("SELECT * FROM events WHERE date=?",args);
//                if(cursor!=null)
//                Toast.makeText(CalenderActivity.this, "came till here", Toast.LENGTH_SHORT).show();
//
//
//                if (cursor.moveToFirst()){
//                    Toast.makeText(CalenderActivity.this, "in if", Toast.LENGTH_SHORT).show();
//                    String desc= cursor.getString(cursor.getColumnIndex(Contract.Events.DESCRIPTION));
//                    description.setText(desc);
//
//                }
//                else{
//                    Toast.makeText(CalenderActivity.this, "nothing got yet", Toast.LENGTH_SHORT).show();
//                }
//
//


            }
//    public void putindb(){
//      //  Toast.makeText(this, "top", Toast.LENGTH_SHORT).show();
//
//        Call<List<CalenderResponse>> call= APIClient.getInstance().getApi().getEvents();
//        responses=new ArrayList<>();
//        call.enqueue(new Callback<List<CalenderResponse>>() {
//            @Override
//            public void onResponse(Call<List<CalenderResponse>> call, Response<List<CalenderResponse>> response) {
//                responses=(ArrayList<CalenderResponse>) response.body();
//                Toast.makeText(CalenderActivity.this, "response came", Toast.LENGTH_SHORT).show();
//                for(int i=0;i<responses.size();i++){
//                    String date= responses.get(i).getDat();
//                    String desc= responses.get(i).getDesc();
//                    String ini= responses.get(i).getInit();
//                    ContentValues cv= new ContentValues();
//                    cv.put(Contract.Events.ID,i);
//                    cv.put(Contract.Events.DATE,date);
//                    cv.put(Contract.Events.DESCRIPTION,desc);
//                    cv.put(Contract.Events.INITIATIVE,ini);
//                    databasewrite.insert("events",null,cv);
//                 //   databasewrite.execSQL("INSERT INTO events VALUES('"+i+"','"+date+"','"+desc+"','"+ini+"')");
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CalenderResponse>> call, Throwable t) {
//               // Toast.makeText(CalenderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
        } );
    }
}
