package com.befresh.befreshapp.Community;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Community.CommunityModel.RecipePhoto;
import com.befresh.befreshapp.Community.CommunityModel.getCommunityMain;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class CommunityFragment extends MasterFragment {

    private NavigationActivity mContext;
//    private OnFragmentInteractionListener mListener;
    NetworkService networkService;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CommRcyAdapter adapter;
    ArrayList<RecipePhoto> photo;
    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;
    ArrayList<CommRcyItem> items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        mContext = (NavigationActivity)getMasterActivity();
        networkService = ApplicationController.getInstance().getNetworkService();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_community);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token",null);
        Log.i("mytoken",token);
        photo = new ArrayList<>();
        items = new ArrayList<>();
        login();
        return view;
    }

public void login()
{
    Call<getCommunityMain> communityMain = networkService.getCommunityMain(token);
    communityMain.enqueue(new Callback<getCommunityMain>()
    {
        @Override
        public void onResponse(Call<getCommunityMain> call, Response<getCommunityMain> response)
        {
            Log.i("MyTag", response.message());
            Log.i("MyTag", "response code : " + response.code());

            if (response.isSuccessful())
            {
                Log.i("MyTag", "메인 네트워크");
                getCommunityMain getCommunityMain = response.body();
                adapter = new CommRcyAdapter(mContext, items, getCommunityMain.data);
                recyclerView.setAdapter(adapter);
            }
            else
            {
                int statusCode = response.code();
                Log.i("MyTag", "응답코드 : " + statusCode);
            }
        }

        @Override
        public void onFailure(Call<getCommunityMain> call, Throwable t)
        {
            Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
            Log.i("MyTag", "에러내용 : " + t.getMessage());
        }
    });
}
}
