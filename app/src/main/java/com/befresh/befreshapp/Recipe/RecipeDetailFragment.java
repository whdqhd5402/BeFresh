package com.befresh.befreshapp.Recipe;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Mypage.MypageModel.setMyPageRegist;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.Adpater.SourceRcAdapter;
import com.befresh.befreshapp.Recipe.RecipeAdapter.RecipeComment_Adapter;
import com.befresh.befreshapp.Recipe.RecipeAdapter.RecipeViewPagerAdapter;
import com.befresh.befreshapp.Recipe.RecipeAdapter.Recipeinfo_Adapter;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeContent;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.befresh.befreshapp.Navigationmain.NavigationActivity.setid;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by idongsu on 2017. 6. 26..
 */
public class RecipeDetailFragment extends MasterFragment {
    private NavigationActivity mContext;
    private TabLayout tabLayout, tabLayout2;
    private ViewPager viewPager, viewPager2;
    private RecipeViewPagerAdapter homePagerAdapter, homePagerAdapter2;

    private CheckBox recipe_like;
    private TextView recipe_detail, review_write, review_detail, reviewcount;
    private TextView recipe_title, recipe_subtitle, recipe_time, recipe_description, hashtag;
    private ImageView recipe_level, source_image, cooksource;
    private ArrayList<com.befresh.befreshapp.Recipe.RecipeModel.getRecipeContent.urlInfo> previewImage;
    private LinearLayoutManager linearLayoutManager1, linearLayoutManager2;
    private RecyclerView recyclerView1, recyclerView2, source_rcv;
    private Recipeinfo_Adapter recipeinfo_Adapter;
    private RecipeComment_Adapter recipeComment_adapter;
    private ScrollView scrollView;
    private ImageView go_top;
    private GridLayoutManager gridLayoutManager;
    private SourceRcAdapter sourceRcAdapter;
    NetworkService networkService;
    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;
    getRecipeContent getRecipeContent;
    ImageView bookmark;
    int id;
    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("mytag", "start home fragment");
        mContext = (NavigationActivity) getMasterActivity();
        View view = inflater.inflate(R.layout.main_recipedetail, container, false);
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.imageButton);
        previewImage = new ArrayList<>();
        recipe_detail = (TextView) view.findViewById(R.id.recipe_detail);
        review_detail = (TextView) view.findViewById(R.id.review_detail);
        recipe_title = (TextView) view.findViewById(R.id.recipe_title);
        recipe_subtitle = (TextView) view.findViewById(R.id.recipe_subtitle);
        recipe_time = (TextView) view.findViewById(R.id.recipe_time);
        recipe_level = (ImageView) view.findViewById(R.id.recipe_level);
        recipe_description = (TextView) view.findViewById(R.id.recipe_description);
        recipe_like = (CheckBox) view.findViewById(R.id.recipe_tool_heart);
        source_image = (ImageView) view.findViewById(R.id.source_image);
        hashtag = (TextView) view.findViewById(R.id.hashtag);
        reviewcount = (TextView) view.findViewById(R.id.review_count);
        cooksource = (ImageView) view.findViewById(R.id.source_image);
        bookmark = (ImageView) view.findViewById(R.id.bookmark);
        back = (ImageView) view.findViewById(R.id.back);
        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token", null);
        Log.i("mytoken", token);

        scrollView = (ScrollView) view.findViewById(R.id.scroll);
        go_top = (ImageView) view.findViewById(R.id.go_top);
        viewPager = (ViewPager) view.findViewById(R.id.homeviewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.hometablayout);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.recipe_info_rcv);
        recyclerView1.setHasFixedSize(true);


        recyclerView2 = (RecyclerView) view.findViewById(R.id.comment_rcv);
        recyclerView2.setHasFixedSize(true);

        source_rcv = (RecyclerView) view.findViewById(R.id.source_rcv);
        gridLayoutManager = new GridLayoutManager(mContext, 2);

        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);


        linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        source_rcv.setLayoutManager(gridLayoutManager);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        recipe_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(id);
                Toast.makeText(mContext, recipe_like.isChecked()?"저장 되었습니다":"삭제 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onBackPressed();
            }
        });
        recipe_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.changeFragment(7);
                setid = getRecipeContent.data.recipe.id;
            }
        });

        review_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecipeContent.data.review.size() != 0) {
                    mContext.changeFragment(9);

                } else {
                    Toast.makeText(mContext, "댓글이 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0, 0);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeShare shareDialog = new RecipeShare(getActivity(), getRecipeContent, mContext);
                shareDialog.show();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.changeFragment(2);
            }
        });
        login();


        return view;
    }

    public void login() {
        Call<getRecipeContent> communityMain = networkService.getRecipeContent(token, setid);
        communityMain.enqueue(new Callback<getRecipeContent>() {
            @Override
            public void onResponse(Call<getRecipeContent> call, Response<getRecipeContent> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {
                    getRecipeContent = response.body();
                    id = getRecipeContent.data.recipe.id;
                    recipe_like.setChecked(getRecipeContent.data.recipe.checkSaveList);
                    recipe_title.setText(getRecipeContent.data.recipe.title);
                    recipe_subtitle.setText(getRecipeContent.data.recipe.subtitle);
                    previewImage = getRecipeContent.data.recipe.imageSet.image;
                    recipe_description.setText(getRecipeContent.data.recipe.description);
                    recipe_time.setText("조리시간 " + getRecipeContent.data.recipe.cookingTime+"분");
                    hashtag.setText(getRecipeContent.data.recipe.hashtag);
                    Glide.with(mContext).load(getRecipeContent.data.recipe.material_image).into(source_image);

                    if (getRecipeContent.data.recipe.difficulty.equals("A")) //상
                    {
                        recipe_level.setImageResource(R.drawable.recipecontents_level3);
                    } else if (getRecipeContent.data.recipe.difficulty.equals("B")) {
                        recipe_level.setImageResource(R.drawable.recipecontents_level2);
                    } else {
                        recipe_level.setImageResource(R.drawable.recipecontents_level1);
                    }


                    recipeinfo_Adapter = new Recipeinfo_Adapter(mContext, getRecipeContent.data.recipe.method.card);
                    recyclerView1.setAdapter(recipeinfo_Adapter);
                    recipeComment_adapter = new RecipeComment_Adapter(mContext, getRecipeContent.data.review);
                    recyclerView2.setAdapter(recipeComment_adapter);
                    reviewcount.setText("후기 " + getRecipeContent.data.recipe.review_count + "개");
                    sourceRcAdapter = new SourceRcAdapter(mContext, getRecipeContent.data.recipe.material_list);
                    source_rcv.setAdapter(sourceRcAdapter);
                    homePagerAdapter = new RecipeViewPagerAdapter(getFragmentManager(), 2, getActivity(), previewImage);
                    homePagerAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(0);
                    viewPager.setOffscreenPageLimit(2);
                    viewPager.setAdapter(homePagerAdapter);
                    viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                } else {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<getRecipeContent> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
    }

    public void check(int id) {
        Call<setMyPageRegist> setMyPageRegistCall = networkService.setMyPageRegistCall(token, id, 1);
        setMyPageRegistCall.enqueue(new Callback<setMyPageRegist>() {
            @Override
            public void onResponse(Call<setMyPageRegist> call, Response<setMyPageRegist> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful()) {
                    Log.i("MyTag", "메인 네트워크");
                    setMyPageRegist setMyPageRegist = response.body();
                    Log.i("MyTag", setMyPageRegist.msg);

                } else {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<setMyPageRegist> call, Throwable t) {

            }
        });
    }
}
