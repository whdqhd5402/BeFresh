package com.befresh.befreshapp.Membership.FreshPeople;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Membership.CancleDialog;
import com.befresh.befreshapp.Membership.FreshPeople.FreshPeople.FreshPeopleModel.getMemberOutInfo;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreshPeopleLeaveFragment extends MasterFragment {

    NetworkService networkService;
    private NavigationActivity mContext;
    TextView menuName;
    TextView description;
    TextView priceInfo;

    TextView cancel;
    TextView leave;

    SharedPreferences auto;
    String token;
    CancleDialog cancleDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        networkService = ApplicationController.getInstance().getNetworkService();

        auto = getContext().getSharedPreferences("auto", Context.MODE_PRIVATE);
        token = auto.getString("token", null);

        mContext = (NavigationActivity) getMasterActivity();

        View view = inflater.inflate(R.layout.fragment_fresh_people_leave, container, false);

        menuName = (TextView) view.findViewById(R.id.menu_name_fresh_people_leave);
        description = (TextView) view.findViewById(R.id.description_fresh_people_leave);
        priceInfo = (TextView) view.findViewById(R.id.price_info_fresh_people_leave);

        cancel = (TextView) view.findViewById(R.id.btn_cancel_fresh_people_leave);
        leave = (TextView) view.findViewById(R.id.btn_leave_fresh_people_leave);

        menuName.setText(auto.getString("menuName", null));
        description.setText("매주 제공되는 요리 1가지(요리당 2~3인분)");
        priceInfo.setText("19,800");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.changeFragment(31);
                //Toast.makeText(mContext, "leave canceled", Toast.LENGTH_SHORT).show();
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveFreshPeople();
                //Toast.makeText(mContext, "leave success", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void leaveFreshPeople() {
        Call<getMemberOutInfo> getMemberOutInfoCall = networkService.getMemberOutInfo(token);

        getMemberOutInfoCall.enqueue(new Callback<getMemberOutInfo>() {
            @Override
            public void onResponse(Call<getMemberOutInfo> call, Response<getMemberOutInfo> response) {
                Log.i("MyTag", "confirm");
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {
                    getMemberOutInfo getMemberOutInfo = response.body();
                    Log.i("MyTag", getMemberOutInfo.msg);
                    cancleDialog = new CancleDialog(mContext);
                    cancleDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cancleDialog.dismiss();
                            mContext.changeFragment(1);
                        }
                    }, 2000);


                } else {
                    int status = response.code();
                    Log.i("MyTag", "response code : " + status);
                }
            }

            @Override
            public void onFailure(Call<getMemberOutInfo> call, Throwable t) {
                Toast.makeText(mContext, "Fail to load", Toast.LENGTH_SHORT).show();
                Log.i("MyTag", "error content : " + t.getMessage());
            }
        });
    }
}
