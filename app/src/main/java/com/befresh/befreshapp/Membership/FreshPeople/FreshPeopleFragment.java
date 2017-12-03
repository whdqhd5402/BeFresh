package com.befresh.befreshapp.Membership.FreshPeople;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Membership.FreshPeople.FreshPeople.FreshPeopleModel.UpdateResult;
import com.befresh.befreshapp.Membership.Memberdialog;
import com.befresh.befreshapp.Membership.MembershipModel.MembershipModel.ScheduleInfo;
import com.befresh.befreshapp.Membership.MembershipModel.ScheduleResult;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.befresh.befreshapp.Membership.FreshPeople.MonthAdapter.togglestatus;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FreshPeopleFragment extends MasterFragment {
    @BindView(R.id.gridview)
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    @BindView(R.id.currentMonth)
    TextView currentMonth;
    TextView delivery_date;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearlayoutmanaer;
    ScrollView scrollview;
    ArrayList<Mood> moods;
    DisplayMetrics metrics;
    static int position;
    int month = 0;
    int year = 0;
    NavigationActivity mContext;
    static Switch togglebtn;
    static int weekindex = 0;
    static boolean weekstatus;
    Calendar mCalendar;
    static String date;
    ArrayList<FreshPeopleItem> item;
    MenuShowAdapter menushowadapter;
    ArrayList<Integer> delivery;
    TextView menuName;
    ImageView modifyBtn, btnbefore, btnnext;
    static List<String> mItems;
    NetworkService networkService;
    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    static String index="3";
    Memberdialog memberdialog;
    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fresh_people, container, false);
        mContext = (NavigationActivity) getMasterActivity();
        ButterKnife.bind(mContext);
        recyclerView = (RecyclerView) view.findViewById(R.id.gridview);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_fresh_people);
        delivery_date = (TextView) view.findViewById(R.id.delivery_date_fresh_people);
        togglebtn = (Switch) view.findViewById(R.id.switch_delivery_fresh_people);
        currentMonth = (TextView) view.findViewById(R.id.currentMonth);
        menuName = (TextView) view.findViewById(R.id.menu_name_fresh_people);
        btnbefore = (ImageView) view.findViewById(R.id.btnBefore);
        btnnext = (ImageView) view.findViewById(R.id.btnNext);
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();

        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token", null);
        Log.i("mytoken", token);


        modifyBtn = (ImageView) view.findViewById(R.id.modify_fresh_people);

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "modify button clicked", Toast.LENGTH_SHORT).show();
                mContext.changeFragment(31);
            }
        });

        scrollview = (ScrollView) view.findViewById(R.id.totalscroll);
        ArrayList<FreshPeopleItem> items = new ArrayList<>();
        ButterKnife.bind(mContext);

        MyListener mylistener = new MyListener();
        togglebtn.setOnCheckedChangeListener(mylistener);
        menu();
        scrollview.fullScroll(View.FOCUS_DOWN);
        Toast.makeText(mContext, "숫자선택 -> 배송여부 선택 \n체크선택 -> 메뉴 확인하기", Toast.LENGTH_LONG).show();

        btnbefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBefore();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNext();
            }
        });
        return view;
    }

    public synchronized void setCalendar() {

        mCalendar = Calendar.getInstance();
        month = mCalendar.get(Calendar.MONTH); // zero based
        year = mCalendar.get(Calendar.YEAR);
        this.moods = new ArrayList<>();
        metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        gridLayoutManager = new GridLayoutManager(mContext, 7);
        linearlayoutmanaer = new LinearLayoutManager(mContext);

        recyclerView2.setLayoutManager(linearlayoutmanaer);
        recyclerView2.setHasFixedSize(false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(false);

        currentMonth.setText(String.valueOf(year) + "." + Util.getMonth(month + 1));

        populateMoods(mCalendar);
    }

    public void populateMoods(Calendar mCalendar) {
        Mood mood = new Mood();
        mood.day = mCalendar.get(Calendar.DAY_OF_MONTH);
        mood.month = mCalendar.get(Calendar.MONTH) + 1;
        mood.year = mCalendar.get(Calendar.YEAR);
        this.moods.add(mood);

        recyclerView.setAdapter(new MonthAdapter(mContext, month, year, this.moods, clicklistener1, delivery));
    }

    public void onClickNext() {
        Log.i("MyTag", "Next Month");
        month = month + 1;

        if (month > 11) {
            month = 0;
            year = year + 1;
        }
        currentMonth.setText(String.valueOf(year) + "." + Util.getMonth(month + 1));
        //currentMonth.setText(Util.getMonth(month + 1) + " / " + String.valueOf(year));
        recyclerView.setAdapter(new MonthAdapter(mContext, month, year, this.moods, clicklistener1, delivery));
    }

    @OnClick(R.id.btnBefore)
    public void onClickBefore() {
        Log.i("MyTag", "Last Month");
        month = month - 1;

        if (month < 0) {
            month = 11;
            year = year - 1;
        }
        currentMonth.setText(String.valueOf(year) + "." + Util.getMonth(month + 1));
        //currentMonth.setText(Util.getMonth(month + 1) + " / " + String.valueOf(year));
        recyclerView.setAdapter(new MonthAdapter(mContext, month, year, this.moods, clicklistener1, delivery));
    }


    public View.OnClickListener clicklistener1 = new View.OnClickListener() {
        @Override
        public synchronized void onClick(View v) {
            int position = recyclerView.getChildPosition(v);
            index = mItems.get(position);
            menu();
        }
    };

    public class MyListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ArrayList<Mood> moods = new ArrayList<>();
            Mood mood = new Mood();
            mood.day = mCalendar.get(Calendar.DAY_OF_MONTH);
            mood.month = mCalendar.get(Calendar.MONTH) + 1;
            mood.year = mCalendar.get(Calendar.YEAR);
            if(togglebtn.isChecked() == false)
            {
                buttonView.setChecked(false);
            }

            if(togglebtn.isChecked() != togglestatus)
                toggle();
        }
    }


    public void menu() {
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.date = String.valueOf(year) + "-" + Util.getMonth(month + 1) + "-" + index;
        Log.i("mytag",scheduleInfo.date+"");
        Call<ScheduleResult> communityMain = networkService.ScheduleResult(token, scheduleInfo);
        communityMain.enqueue(new Callback<ScheduleResult>() {
            @Override
            public void onResponse(Call<ScheduleResult> call, Response<ScheduleResult> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {
                    ScheduleResult scheduleResult = response.body();
                    menushowadapter = new MenuShowAdapter(mContext, scheduleResult.data);
                    recyclerView2.setAdapter(menushowadapter);
                    delivery_date.setText(scheduleResult.data.week);
                    scrollview.fullScroll(View.FOCUS_DOWN);
                    delivery = scheduleResult.data.delivery;
                    setCalendar();
                    Log.i("MyTag", delivery.get(0) + "-" + delivery.get(1) + "-" + delivery.get(2));
                    String k = scheduleResult.data.category;
                    if (k.equals("V")) {
                        menuName.setText("채식메뉴");
                    } else if (k.equals("W")) {
                        menuName.setText("웰빙메뉴");
                    } else if (k.equals("B")) {
                        menuName.setText("채식+웰빙메뉴");
                    }

                } else {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<ScheduleResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }


    public void toggle() {
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        Log.i("index", index + "eeeeeeindex");
        scheduleInfo.date = String.valueOf(year) + "-" + Util.getMonth(month + 1) + "-" + index;
        Call<UpdateResult> communityMain = networkService.UpdateResult(token, scheduleInfo);
        communityMain.enqueue(new Callback<UpdateResult>() {
            @Override
            public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {

                    UpdateResult scheduleResult = response.body();
                    Log.i("MyTag", delivery.get(0) + "-" + delivery.get(1) + "-" + delivery.get(2));
                    memberdialog = new Memberdialog(mContext);
                    memberdialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            memberdialog.dismiss();
                            mContext.changeFragment(30);
                        }
                    }, 2000);

                }
                else {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<UpdateResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }
}
