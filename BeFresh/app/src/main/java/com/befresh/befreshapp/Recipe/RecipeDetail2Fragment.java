package com.befresh.befreshapp.Recipe;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.RecipeAdapter.Recipeinfo_Adapter2;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeCardDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.befresh.befreshapp.Navigationmain.NavigationActivity.setid;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by idongsu on 2017. 6. 26..
 */
public class RecipeDetail2Fragment extends MasterFragment
{
    private NavigationActivity mContext;
    private LinearLayoutManager linearLayoutManager1;
    private RecyclerView recyclerView1;
    private Recipeinfo_Adapter2 recipeinfo_Adapter2;
    NetworkService networkService;public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    TextView page;
    int pageposition;
    getRecipeCardDetail getRecipeCardDetail;
    String token;
    ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("mytag","start home fragment");
        mContext = (NavigationActivity)getMasterActivity();
        View view = inflater.inflate(R.layout.reciptdetail2, container, false);

        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token",null);
        Log.i("mytoken",token);

        recyclerView1 = (RecyclerView)view.findViewById(R.id.recipe_detail_rcv);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        back = (ImageView)view.findViewById(R.id.back);
        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        page = (TextView)view.findViewById(R.id.center_text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onBackPressed();
            }
        });
        login();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    public View.OnTouchListener clicklistener1 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            final int position = recyclerView1.getChildPosition(v);
            pageposition = position;
            page.setText((pageposition+1)+"/"+getRecipeCardDetail.data.card.size());
            return false;
        }
    };

    public void login()
    {
        Call<getRecipeCardDetail> communityMain = networkService.getRecipeCardDetail(token,setid);
        communityMain.enqueue(new Callback<getRecipeCardDetail>()
        {
            @Override
            public void onResponse(Call<getRecipeCardDetail> call, Response<getRecipeCardDetail> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    Log.i("MyTag", "카드 상세 네트워크");
                    getRecipeCardDetail = response.body();
                    recipeinfo_Adapter2 = new Recipeinfo_Adapter2(mContext, getRecipeCardDetail.data.card,clicklistener1);
                    recyclerView1.setAdapter(recipeinfo_Adapter2);
                    page.setText((pageposition+1)+"/"+getRecipeCardDetail.data.card.size());

                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getRecipeCardDetail> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }

}

