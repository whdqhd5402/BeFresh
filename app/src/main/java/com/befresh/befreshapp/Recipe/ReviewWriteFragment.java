package com.befresh.befreshapp.Recipe;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.RecipeModel.WriteRecipeInfo;
import com.befresh.befreshapp.Recipe.RecipeModel.WriteRecipeReview;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by idongsu on 2017. 6. 26..
 */
public class ReviewWriteFragment extends MasterFragment
{
    private NavigationActivity mContext;
    NetworkService networkService;public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    RatingBar ratingBar;
    TextView content;
    String token;
    TextView compelete;
//    private
    ImageView back;
    int id;String title,image;
    ImageView imageview;
    TextView title2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("mytag","start home fragment");
        mContext = (NavigationActivity)getMasterActivity();
        View view = inflater.inflate(R.layout.review_write, container, false);
        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        back = (ImageView)view.findViewById(R.id.back);
        autoLogin = auto.edit();
        token = auto.getString("token",null);

        title2 = (TextView)view.findViewById(R.id.title);
        ratingBar = (RatingBar)view.findViewById(R.id.write_recipe_ratingbar);
        content = (TextView)view.findViewById(R.id.write_recipe_content);
        compelete = (TextView)view.findViewById(R.id.toolbar_complete);
        imageview = (ImageView)view.findViewById(R.id.image);

        id = getArguments().getInt("id");
        image = getArguments().getString("image");
        title = getArguments().getString("title");

        Glide.with(this).load(image).into(imageview);
        title2.setText(title);
        compelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onBackPressed();
            }
        });

        mContext.findViewById(R.id.titlebar_layout).setVisibility(View.GONE);
        mContext.findViewById(R.id.titlebar_layout2).setVisibility(View.GONE);
        Log.i("mytoken",token);

        return view;
    }


    public void login()
    {
        WriteRecipeInfo writeRecipeInfo = new WriteRecipeInfo();
        Log.i("content", content.getText().toString());
        writeRecipeInfo.content = content.getText().toString();
        writeRecipeInfo.score = ratingBar.getNumStars();
        writeRecipeInfo.id = id;

        Call<WriteRecipeReview> communityMain = networkService.WriteRecipeReview(token,writeRecipeInfo);
        communityMain.enqueue(new Callback<WriteRecipeReview>()
        {
            @Override
            public void onResponse(Call<WriteRecipeReview> call, Response<WriteRecipeReview> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    Log.i("MyTag", "리뷰 등록 성공");
                    mContext.changeFragment(4);
                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<WriteRecipeReview> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }

}

