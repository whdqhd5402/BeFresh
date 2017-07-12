package com.befresh.befreshapp.Recipe;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.Adpater.HomePagerAdapter;
import com.befresh.befreshapp.Recipe.RecipeAdapter.ReviewDetailAdapter;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeReview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.befresh.befreshapp.Navigationmain.NavigationActivity.setid;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by idongsu on 2017. 6. 26..
 */
public class ReviewDetailFragment extends MasterFragment {
    private NavigationActivity mContext;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomePagerAdapter homePagerAdapter;
    private TextView recipe_detail, review_write, review_detail;
    private ReviewDetailAdapter adapterProduct;
    private RecyclerView recyclerView;
    //private FloatingActionButton fab;
    NetworkService networkService;
    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("mytag", "start home fragment");
        mContext = (NavigationActivity) getMasterActivity();
        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token", null);
        Log.i("mytoken", token);
        View view = inflater.inflate(R.layout.reviewdetail, container, false);

        //fab = (FloatingActionButton) view.findViewById(R.id.fab_write_review);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.changeFragment(8);
            }
        });*/

        recyclerView = (RecyclerView) view.findViewById(R.id.review_detail_rcv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        login();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void login() {
        Call<getRecipeReview> communityMain = networkService.getRecipeReview(token, setid);
        communityMain.enqueue(new Callback<getRecipeReview>() {
            @Override
            public void onResponse(Call<getRecipeReview> call, Response<getRecipeReview> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {
                    Log.i("MyTag", "메인 네트워크");
                    getRecipeReview getRecipeReview = response.body();
                    adapterProduct = new ReviewDetailAdapter(mContext, getRecipeReview.data.review);
                    recyclerView.setAdapter(adapterProduct);

                } else {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getRecipeReview> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }

}

