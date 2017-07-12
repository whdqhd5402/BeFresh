package com.befresh.befreshapp.Recipe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.Adpater.HomePagerAdapter;
import com.befresh.befreshapp.Recipe.RecipeModel.Material;
import com.befresh.befreshapp.Recipe.RecipeModel.Overthirty;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeMainWellbeing;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.befresh.befreshapp.Navigationmain.NavigationActivity.bundle;
import static com.befresh.befreshapp.Navigationmain.NavigationActivity.filterFlag;
import static com.befresh.befreshapp.Navigationmain.NavigationActivity.kind1;
import static com.befresh.befreshapp.Navigationmain.NavigationActivity.kind2;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by idongsu on 2017. 5. 17..
 */

public class SearchDialog extends Dialog
{

    private Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomePagerAdapter homePagerAdapter;
    private NavigationActivity mContext;
    private TextView source, time,cancel,complete;
    private LinearLayout left,right;
    String strColor,strColor2;
    CheckBox worry1,worry2,worry3,worry4,worry5,worry6,worry7,worry8;
    ArrayList<CheckBox> boxlist;
    ArrayList<CheckBox> boxtime;
    int[] checkstatus = {0,0,0,0,0,0};
    String filter;
    Context context;
    NetworkService networkService;public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;
    private OnDismissListener onDismissListener;
    // In the constructor, you set the callback


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.0f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.filterdialog);
        boxlist = new ArrayList<>();
        boxtime = new ArrayList<>();
        source = (TextView)findViewById(R.id.source);
        time = (TextView)findViewById(R.id.time);
        cancel = (TextView)findViewById(R.id.cancel);
        complete = (TextView)findViewById(R.id.complete);
        left = (LinearLayout)findViewById(R.id.left_filter);
        right = (LinearLayout)findViewById(R.id.right_filter);

        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token",null);


        strColor = "#166B36";
        strColor2 = "#000000";


        worry1 = (CheckBox)findViewById(R.id.gluten); boxlist.add(worry1);
        worry2 = (CheckBox)findViewById(R.id.egg);boxlist.add(worry2);
        worry3 = (CheckBox)findViewById(R.id.nuts);boxlist.add(worry3);
        worry4 = (CheckBox)findViewById(R.id.dairy);boxlist.add(worry4);
        worry5 = (CheckBox)findViewById(R.id.fish);boxlist.add(worry5);
        worry6 = (CheckBox)findViewById(R.id.meat);boxlist.add(worry6);
        worry7 = (CheckBox)findViewById(R.id.less);boxtime.add(worry7);
        worry8 = (CheckBox)findViewById(R.id.more);boxtime.add(worry8);


        worry7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worry8.setChecked(false);
                worry7.setChecked(true);
                Log.i("underCheck","");
            }
        });
        worry8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worry7.setChecked(false);
                worry8.setChecked(true);
                Log.i("overCheck","");
            }
        });

        time.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.GONE);
                worry7.setChecked(true);
                worry8.setChecked(false);
                source.setTextColor(Color.parseColor(strColor2));
                time.setTextColor(Color.parseColor(strColor));
                kind2 = 1;
            }
        });

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right.setVisibility(View.VISIBLE);
                left.setVisibility(View.GONE);
                source.setTextColor(Color.parseColor(strColor));
                time.setTextColor(Color.parseColor(strColor2));
                for(int i=0; i<boxlist.size(); i++)
                {
                    boxlist.get(i).setChecked(false);
                }
                kind2 = 0;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                filterFlag =1;
//                웰빙 재료
                if(kind1 == 0 && kind2 ==0)
                {
                    login1();
                }
                // 채식 재료
                else if(kind1 ==1 && kind2 == 0)
                {
                    login3();
                }
                // 웰빙 시간
                else if(kind1 ==0 && kind2 == 1)
                {
                    //웰빙 시간
                    login2();
                }
                //채식 시간
                else if(kind1==1 && kind2 ==1 )
                {
                    login4();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Toast.makeText(activity, "검색결과 입니다.", Toast.LENGTH_SHORT).show();
                        onDismissListener.onDismiss(SearchDialog.this);
                    }
                }, 500);
            }

        });

    }


    public SearchDialog(Activity activity)
    {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
    }

    public void setOnDismissListener( OnDismissListener $listener ) {
        onDismissListener = $listener ;
    }


    public void login1()
    {
        for(int i=0; i<boxlist.size(); i++)
        {
            if(boxlist.get(i).isChecked())
            {
                checkstatus[i] = 1;
            }
        }
        Material material = new Material();
        material.gluten = checkstatus[0];
        material.egg = checkstatus[1];
        material.dryfruit = checkstatus[2];
        material.milk = checkstatus[3];
        material.fish = checkstatus[4];
        material.meat = checkstatus[5];
        Log.i("MyTag", "필터링 1번");
        Call<getRecipeMainWellbeing> communityMain = networkService.getRecipeMainWellBeingfilter2(token, material);
        communityMain.enqueue(new Callback<getRecipeMainWellbeing>()
        {
            @Override
            public void onResponse(Call<getRecipeMainWellbeing> call, Response<getRecipeMainWellbeing> response)
            {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    getRecipeMainWellbeing getRecipeMainWellbeing = response.body();
                    bundle = getRecipeMainWellbeing.data;

                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getRecipeMainWellbeing> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }
    public void login2()
    {
        Log.i("MyTag", "필터링 2번");
        Overthirty overthirty = new Overthirty();
        if(worry7.isChecked())
        {
            Log.i("worry check","under체크");
            overthirty.over = 0;
            overthirty.under = 1;
        }
        else
        {
            Log.i("worry check","over체크");
            overthirty.over = 1;
            overthirty.under =0;
        }
        Log.i("worrycheck","over"+overthirty.over+"under"+overthirty.under );
        Call<getRecipeMainWellbeing> communityMain = networkService.getRecipeMainWellbeingfilter1(token, overthirty);
        communityMain.enqueue(new Callback<getRecipeMainWellbeing>()
        {
            @Override
            public void onResponse(Call<getRecipeMainWellbeing> call, Response<getRecipeMainWellbeing> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    getRecipeMainWellbeing getRecipeMainWellbeing = response.body();
                    bundle = getRecipeMainWellbeing.data;
                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getRecipeMainWellbeing> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }
    public void login3()
    {
        Log.i("MyTag", "필터링 3번");
        for(int i=0; i<boxlist.size(); i++)
        {
            if(boxlist.get(i).isChecked())
            {
                checkstatus[i] = 1;
            }

        }
        Material material = new Material();
        material.gluten = checkstatus[0];
        material.egg = checkstatus[1];
        material.dryfruit = checkstatus[2];
        material.milk = checkstatus[3];
        material.fish = checkstatus[4];
        material.meat = checkstatus[5];

        Call<getRecipeMainWellbeing> communityMain = networkService.getRecipeMainVegfilter2(token,material);
        communityMain.enqueue(new Callback<getRecipeMainWellbeing>()
        {
            @Override
            public void onResponse(Call<getRecipeMainWellbeing> call, Response<getRecipeMainWellbeing> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    getRecipeMainWellbeing getRecipeMainWellbeing = response.body();
                    bundle = getRecipeMainWellbeing.data;
                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getRecipeMainWellbeing> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }
    public void login4()
    {
        Overthirty overthirty = new Overthirty();
        if(worry7.isChecked())
        {
            overthirty.over = 0;
            overthirty.under = 1;
        }
        else {
            overthirty.over = 1;
            overthirty.under = 0;
        }
        Log.i("MyTag", "필터링 4번");
        Log.i("worrycheck","over"+overthirty.over+"under"+overthirty.under );
        Call<getRecipeMainWellbeing> communityMain = networkService.getRecipeMainVegfilter1(token, overthirty);
        communityMain.enqueue(new Callback<getRecipeMainWellbeing>()
        {
            @Override
            public void onResponse(Call<getRecipeMainWellbeing> call, Response<getRecipeMainWellbeing> response)
            {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    getRecipeMainWellbeing getRecipeMainWellbeing = response.body();
                    bundle = getRecipeMainWellbeing.data;
                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getRecipeMainWellbeing> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }
}
