package com.maidangtung.soccernetwork;

import android.app.ProgressDialog;
import android.support.v4.widget.DrawerLayout;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.maidangtung.soccernetwork.adapters.ListFieldAdapter;
import com.maidangtung.soccernetwork.helpers.SqlHelper;
import com.maidangtung.soccernetwork.models.Field;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maidangtung.soccernetwork.R;
//import com.maidangtung.soccernetwork.adapters.RecyclerViewAdapter;
//import com.example.marsch.soccersocial.models.City;
//import com.example.marsch.soccersocial.models.Match;
//import com.google.gson.Gson;
import  com.maidangtung.soccernetwork.FragmentDrawer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.maidangtung.soccernetwork.R.id.fragment_navigation_drawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    private ListView lv_list_story;
    ArrayList<Field> mFields = new ArrayList<>();
    public static ListFieldAdapter mAdapter;

    SqlHelper mSqlHelper;

    public static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Đang khởi tạo dữ liệu ban đầu");
        mProgressDialog.setMessage("Vui lòng chờ...");
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();


        //initData();
        //mSqlHelper=new SqlHelper(this);
        //mFields=mSqlHelper.getAllRows();



        //init UI
        //lv_list_story=(ListView) findViewById(R.id.lv_list_view);
        //mAdapter=new ListFieldAdapter(this,mFields);
        //lv_list_story.setAdapter(mAdapter);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(fragment_navigation_drawer);
        drawerFragment.setUp(fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }


    //TextView tv_result;
    //Button _logOutButton;
    //RecyclerView _rvMatch;
    //List<Match> _listMatch;
    //RecyclerViewAdapter _recyclerAdapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        restoringPreferences();
//        initComponent();
        //City mCity = new City();
//        tv_result = (TextView) findViewById(R.id.tv_result);
//        getApi api = new getApi();
//        api.execute("http://skymarsch.xyz:8080/cndd/api/city/3/");
//        try{
//            mCity = api.get();
//            Log.e("API_GET",mCity.getCity_name());
//        } catch (Exception e){
//            Log.e("ERROR",e.toString());
//        }
//    }
    /*
    private void initComponent(){
        _logOutButton = (Button) findViewById(R.id.btn_logout);
        _rvMatch = (RecyclerView) findViewById(R.id.rv_match);

        _listMatch = Arrays.asList(new Match("1","Khanh Hoa"),new Match("2","Quang Nam"),new Match("3","Da Nang"),new Match("4","Hue"));
        _recyclerAdapter = new RecyclerViewAdapter(this,_listMatch);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _rvMatch.setLayoutManager(layoutManager);

        _rvMatch.setAdapter(_recyclerAdapter);
        _logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }*/

    public void logout()
    {
        SharedPreferences pre=getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        editor.clear();
        editor.putBoolean("rememberMe", false);
        editor.commit();
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
        finish();
    }
    public void restoringPreferences()
    {
        SharedPreferences pre=getSharedPreferences("data",MODE_PRIVATE);
        boolean bchk=pre.getBoolean("rememberMe", false);
        if(!bchk)
        {
            Intent login = new Intent(this,LoginActivity.class);
            startActivity(login);
        }
    }
    /*private  class getApi extends AsyncTask<String,City,City>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this, "Chuẩn bị tải City", Toast.LENGTH_LONG).show();
        }

        @Override
        protected City doInBackground(String... params) {
            //Lấy link facebook từ hàm processDownload truyền vào
            String link=params[0];
            City fb = null;
            try {
                URL url=new URL(link);
//              //đọc stream Json từ internet có đọc UTF8
                InputStreamReader reader=new InputStreamReader(url.openStream(),"UTF-8");

                //chuyển định dạng JSon về java class

                fb=new Gson().fromJson(reader,City.class);
                //gửi qua onProgressUpdate để cập nhật giao diện
                Log.e("getAPI",reader.toString());
                publishProgress(fb);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fb;
        }

        @Override
        protected void onProgressUpdate(City... values) {
            super.onProgressUpdate(values);
            //lấy FaceBook được truyền từ doInBackground
            City fb=values[0];
            Log.e("API", fb.getCity_name());
            //tiến hành đưa thông tin lên giao diện:
        }

        @Override
        protected void onPostExecute(City result) {
            super.onPostExecute(result);
            //Toast.makeText(MainActivity.this,result.getCity_name() , Toast.LENGTH_LONG).show();
        }
    }*/
}
