package com.befresh.befreshapp.Recipe.Adpater;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Mypage.MypageModel.setMyPageRegist;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.RecipeModel.pastWeekInfo;
import com.befresh.befreshapp.Recipe.RecipeModel.thisWeekInfo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.befresh.befreshapp.Navigationmain.NavigationActivity.setid;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class MainLeftAdapter extends RecyclerView.Adapter<MainLeftViewHolder>
{
    NavigationActivity mContext;
    TextView week_title;
    View.OnClickListener clickListener;
    thisWeekInfo thisWeekInfo;
    ArrayList<pastWeekInfo> pastweek = new ArrayList<>();
    NetworkService networkService;
    private SharedPreferences auto;
    private SharedPreferences.Editor autoLogin;
    String token;

    public MainLeftAdapter(NavigationActivity mContext, View.OnClickListener clickListener, ArrayList<pastWeekInfo> pastweek, thisWeekInfo thisWeekInfo) {
        this.mContext = mContext;
        this.clickListener = clickListener;
        this.pastweek = pastweek;
        this.thisWeekInfo = thisWeekInfo;
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    // 뷰홀더 객체 생성
    @Override
    public MainLeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_left_item, parent, false);
        MainLeftViewHolder productViewHolder = new MainLeftViewHolder(view); // 뷰홀더를 통해 메모리 할당
        view.setOnClickListener(clickListener);
        week_title = (TextView) view.findViewById(R.id.week_title);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final MainLeftViewHolder holder, int position) {
        int position2 = position;
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(holder.id);
                Toast.makeText(mContext, holder.like.isChecked() ? "저장 되었습니다." : "저장 취소 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        if (thisWeekInfo == null) {
            position2 = position + 1;
        }
        if (position == 0 && thisWeekInfo != null) {
            week_title.setText("이번주 레시피");
            holder.id = thisWeekInfo.id;
            Glide.with(mContext).load(thisWeekInfo.image_url).into(holder.img);
            holder.menu_name.setText(thisWeekInfo.title);
            holder.menu_info.setText(thisWeekInfo.subtitle);
            holder.like.setChecked(thisWeekInfo.checkSaveList);
            holder.cooktime.setText(String.valueOf(thisWeekInfo.cookingTime) + "분");
            holder.material.setText(thisWeekInfo.hashtag);
        }
        else if (position == 1)
        {
            week_title.setText("지난 레시피");
            holder.id = pastweek.get(position2 - 1).id;
            holder.like.setChecked(pastweek.get(position2 - 1).checkSaveList);
            Glide.with(mContext).load(pastweek.get(position2 - 1).image_url).into(holder.img);
            holder.menu_name.setText(pastweek.get(position2 - 1).title);
            holder.menu_info.setText(pastweek.get(position2 - 1).subtitle);
            holder.cooktime.setText(String.valueOf(pastweek.get(position2 - 1).cookingTime) + "분");
            holder.material.setText(pastweek.get(position2 - 1).hashtag);
        }
        else if(position > 1)
        {
            holder.week.setVisibility(View.GONE);
//            holder.img.getLayoutParams().height =
//            holder.img.getLayoutParams().height = dpToPx(mContext,230);
//            holder.img.requestLayout();
            holder.id = pastweek.get(position2 - 1).id;
            holder.like.setChecked(pastweek.get(position2 - 1).checkSaveList);
            Glide.with(mContext).load(pastweek.get(position2 - 1).image_url).into(holder.img);
            holder.menu_name.setText(pastweek.get(position2 - 1).title);
            holder.length = pastweek.get(position2-1).subtitle.length();
            if(holder.length > 25)
            {
                holder.menu_info.setTextSize(13);
                holder.menu_info.setText(pastweek.get(position2 - 1).subtitle);
            }
            else
            {
                holder.menu_info.setText(pastweek.get(position2 - 1).subtitle);
            }
            holder.cooktime.setText(String.valueOf(pastweek.get(position2 - 1).cookingTime) + "분");
            holder.material.setText(pastweek.get(position2 - 1).hashtag);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setid = holder.id;
                mContext.changeFragment(6);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        if(pastweek.size() == 0 && thisWeekInfo !=null )
        {
            return 1;
        }
        else if(pastweek.size() >0 && thisWeekInfo != null)
        {
            return pastweek.size() + 1;
        }
        else if(pastweek.size() == 0 && thisWeekInfo == null)
        {
            return 0;
        }
        else if(pastweek.size() >0 && thisWeekInfo == null)
        {
            return pastweek.size();
        }

        return 0;
    }

    public void check(int id) {

        auto = mContext.getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token", null);

        Call<setMyPageRegist> setMyPageRegistCall = networkService.setMyPageRegistCall(token, id, 1);
        setMyPageRegistCall.enqueue(new Callback<setMyPageRegist>() {
            @Override
            public void onResponse(Call<setMyPageRegist> call, Response<setMyPageRegist> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    Log.i("MyTag", "메인 네트워크");
                    setMyPageRegist setMyPageRegist = response.body();

                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<setMyPageRegist> call, Throwable t) {

            }
        });
    }
    private int PxtoDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }
    private int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
