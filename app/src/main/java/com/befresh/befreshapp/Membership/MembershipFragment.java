package com.befresh.befreshapp.Membership;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Membership.MembershipModel.Category;
import com.befresh.befreshapp.Membership.MembershipModel.getMembershipCheck;
import com.befresh.befreshapp.Membership.MembershipModel.getMembershipJoin;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MembershipFragment extends MasterFragment {
    private NavigationActivity mContext;

    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;
    String result;
    //String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2VtYWlsIjoid2hkcWhkNTQwMkBnbWFpbC5jb20iLCJpYXQiOjE0OTg5Mzk4ODQsImV4cCI6MTQ5OTgwMzg4NH0.Aqnf0ELNtb0DWkZCX83Ty38V3AisiOjkc8NAJQv4YAg";
    ImageView wellbing, vegan, wellbing_vegan;

    NetworkService networkService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_item_membership, container, false);
        mContext = (NavigationActivity) getMasterActivity();

        networkService = ApplicationController.getInstance().getNetworkService();

        wellbing = (ImageView) view.findViewById(R.id.wellbing_btn);
        vegan = (ImageView) view.findViewById(R.id.vegan_btn);
        wellbing_vegan = (ImageView) view.findViewById(R.id.wellbing_vegan_btn);

        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token", null);
        Log.i("mytoken", token);


        wellbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoLogin.putString("menuName", "웰빙메뉴");
                autoLogin.putInt("menuCategory", 1);
                autoLogin.commit();
                membershipJoin(1);
            }
        });
        vegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoLogin.putString("menuName", "채식메뉴");
                autoLogin.putInt("menuCategory", 2);
                autoLogin.commit();
                membershipJoin(2);
            }
        });
        wellbing_vegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoLogin.putString("menuName", "웰빙메뉴+채식메뉴");
                autoLogin.putInt("menuCategory", 3);
                autoLogin.commit();
                membershipJoin(3);
            }
        });
        membershipCheck();
        return view;
    }

    public void membershipCheck() {
        Log.i("MyTag", "token : " + token);

        final Call<getMembershipCheck> getMembershipCheckCall = networkService.getMembershipCheck(token);

        getMembershipCheckCall.enqueue(new Callback<getMembershipCheck>() {
            @Override
            public void onResponse(Call<getMembershipCheck> call, Response<getMembershipCheck> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {
                    getMembershipCheck getMembershipCheck = response.body();
                    Log.i("MyTag", "category : " + getMembershipCheck.data.category);
                    if (!getMembershipCheck.data.category.equals("N")) {
                        mContext.changeFragment(30);
                    } else {
                        result = getMembershipCheck.data.category;
                        Log.i("MyTag", "result : " + result);
                    }
                }
            }

            @Override
            public void onFailure(Call<getMembershipCheck> call, Throwable t) {
                Toast.makeText(mContext, "Failed to load membership check", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void membershipJoin(int type) {

        Log.i("MyTag", "type : " + type);
        Category category = new Category(type);
        final Call<getMembershipJoin> getMembershipJoinCall = networkService.getMembershipJoin(token, category);

        getMembershipJoinCall.enqueue(new Callback<getMembershipJoin>() {
            @Override
            public void onResponse(Call<getMembershipJoin> call, Response<getMembershipJoin> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {
                    getMembershipJoin getMembershipJoin = response.body();

                    switch (getMembershipJoin.data.category) {
                        case "W":
                            Log.d("MyTag", "selected W");
                            break;
                        case "V":
                            Log.d("MyTag", "selected V");
                            break;
                        case "B":
                            Log.d("MyTag", "selected B");
                            break;
                    }
                    Toast.makeText(mContext, "선택 완료", Toast.LENGTH_SHORT).show();
                    mContext.changeFragment(28);
                    Bundle bundle = new Bundle(1);
                    bundle.putSerializable("category", getMembershipJoin.data.category);
                    //mContext.changeFragment(28, );

                } else {
                    int statusCode = response.code();
                    Log.e("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getMembershipJoin> call, Throwable t) {
                Toast.makeText(mContext, "Failed to load membership join", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
