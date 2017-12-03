package com.befresh.befreshapp.Recipe;

/**
 * Created by idongsu on 2017. 7. 5..
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

public class RecipeShare extends Dialog
{

    private Activity activity;
    Context context;
    NetworkService networkService;public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;
    private OnDismissListener onDismissListener;
    ImageView facebook22;
    ImageView kakaotalk;
    getRecipeContent getRecipeContent;
    TextView cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.3f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.share);
        facebook22 = (ImageView)findViewById(R.id.facebook_btn);
        cancel = (TextView)findViewById(R.id.cancel);
        kakaotalk = (ImageView)findViewById(R.id.kakao_btn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        facebook22.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shareFacebook();
                dismiss();
            }
        });

        kakaotalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareKakao();
                dismiss();
            }
        });


    }

    public RecipeShare(Activity activity, getRecipeContent getRecipeContent,Context context)
    {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        this.getRecipeContent = getRecipeContent;
        this.context = context;
    }

    public void setOnDismissListener( OnDismissListener $listener ) {
        onDismissListener = $listener ;
    }


    public void shareFacebook()
    {
        String url = getRecipeContent.data.recipe.imageSet.image.get(0).url;
        Log.i("URL",url);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setImageUrl(Uri.parse(url))
                .setContentTitle("title")
                .setContentDescription("description")
                .setContentUrl(Uri.parse(url))
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(content, ShareDialog.Mode.FEED);

    }

    public void shareKakao()
    {
        Log.i("mytag","dfdf");
        try
        {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(context);
            final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();;
            kakaoTalkLinkMessageBuilder.addText(getRecipeContent.data.recipe.title);
            String url = getRecipeContent.data.recipe.imageSet.image.get(0).url;
            kakaoTalkLinkMessageBuilder.addImage(url,160,160);
            kakaoTalkLinkMessageBuilder.addAppButton("다운로드 하고 포인트 받자!");
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder,context);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
