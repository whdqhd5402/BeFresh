package com.befresh.befreshapp.Account;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Login.LoginPage;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.facebook.login.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by 고혜민 on 2017-06-30.
 */

public class AccountFragment extends MasterFragment {

    NavigationActivity mContext;
    ImageView changepw;
    TextView logout;
    TextView name,email,point,phone,address,card;
    NetworkService networkService;public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);

        mContext = (NavigationActivity) getMasterActivity();
        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token",null);
        Log.i("mytoken",token);

        changepw = (ImageView) view.findViewById(R.id.account_change_pw);
        logout = (TextView) view.findViewById(R.id.account_logout);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        point = (TextView) view.findViewById(R.id.point);
        phone = (TextView) view.findViewById(R.id.phone);
        address = (TextView) view.findViewById(R.id.address);
        card = (TextView) view.findViewById(R.id.card);



        changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mContext.changeFragment(91);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(mContext, LoginPage.class);
                startActivity(intent);
            }
        });
        login();
        return view;
    }

    public void login()
    {
        Call<AccountInfo> communityMain = networkService.AccountInfo(token);
        communityMain.enqueue(new Callback<AccountInfo>()
        {
            @Override
            public void onResponse(Call<AccountInfo> call, Response<AccountInfo> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    Log.i("MyTag", "메인 네트워크");
                    AccountInfo getCommunityMain = response.body();
                    name.setText(getCommunityMain.data.account.name);
                    email.setText(getCommunityMain.data.account.email);
                    point.setText(String.valueOf(getCommunityMain.data.account.point));
                    phone.setText(getCommunityMain.data.membership.phone);
                    address.setText(getCommunityMain.data.membership.address);
                    card.setText(getCommunityMain.data.membership.card);
                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<AccountInfo> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }
}
