package com.befresh.befreshapp.Membership.FreshPeople;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.Account.AccountInfo;
import com.befresh.befreshapp.Community.CommRcyAdapter;
import com.befresh.befreshapp.Community.CommunityModel.getCommunityMain;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.R;

import retrofit2.Call;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FreshPeopleSettingsFragment extends MasterFragment {
    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    private NavigationActivity mContext;
    TextView menuName;
    TextView description;
    TextView priceInfo;
    LinearLayout leave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        mContext = (NavigationActivity) getMasterActivity();
        View view = inflater.inflate(R.layout.fragment_fresh_people_settings, container, false);
        menuName = (TextView) view.findViewById(R.id.menu_name_fresh_people_settings);
        description = (TextView) view.findViewById(R.id.description_fresh_people_settings);
        priceInfo = (TextView) view.findViewById(R.id.price_info_fresh_people_settings);

        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();

        menuName.setText(auto.getString("menuName", null));
        description.setText("매주 제공되는 요리 1가지(요리당 2~3인분)");
        priceInfo.setText("첫 주 결제금액 19,800");

        leave = (LinearLayout) view.findViewById(R.id.leave_membership_fresh_people_settings);


        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.changeFragment(32);
                Log.i("MyTag", "leave layout clicked");
                //Toast.makeText(mContext, "leave layout clicked", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
