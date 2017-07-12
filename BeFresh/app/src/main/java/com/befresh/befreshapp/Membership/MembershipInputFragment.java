package com.befresh.befreshapp.Membership;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.befresh.befreshapp.ApplicationController.ApplicationController;
import com.befresh.befreshapp.Membership.MembershipModel.JoinForm;
import com.befresh.befreshapp.Membership.MembershipModel.getMemberJoinInfo;
import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.Network.NetworkService;
import com.befresh.befreshapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembershipInputFragment extends MasterFragment {

    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String token;
    //String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2VtYWlsIjoid2hkcWhkNTQwMkBnbWFpbC5jb20iLCJpYXQiOjE0OTg5Mzk4ODQsImV4cCI6MTQ5OTgwMzg4NH0.Aqnf0ELNtb0DWkZCX83Ty38V3AisiOjkc8NAJQv4YAg";
    private NavigationActivity mContext;
    NetworkService networkService;

    TextView menuName;
    int menuCategory;

    EditText name;
    EditText postAddress;
    Button findPostAddress;
    EditText address;
    EditText detailAddress;
    EditText phoneNumber;
    EditText deliveryMemo;

    ImageView payment;

    Button btn_payment;
    CheckBox box1;
    CheckBox box2;
    CheckBox box3;
    CheckBox box4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        networkService = ApplicationController.getInstance().getNetworkService();

        mContext = (NavigationActivity) getMasterActivity();
        View view = inflater.inflate(R.layout.fragment_membership_input, container, false);

        menuName = (TextView) view.findViewById(R.id.menu_name_membership_input);
        name = (EditText) view.findViewById(R.id.name_membership_input);
        postAddress = (EditText) view.findViewById(R.id.post_address_membership_input);
        findPostAddress = (Button) view.findViewById(R.id.find_post_address_membership_input);
        address = (EditText) view.findViewById(R.id.address_membership_input);
        detailAddress = (EditText) view.findViewById(R.id.detail_address_membership_input);
        phoneNumber = (EditText) view.findViewById(R.id.phone_number_membership_input);
        deliveryMemo = (EditText) view.findViewById(R.id.delivery_memo_membership_input);

        payment = (ImageView) view.findViewById(R.id.img_payment_membership_input);

        box1 = (CheckBox) view.findViewById(R.id.first_check_membership_input);
        box2 = (CheckBox) view.findViewById(R.id.second_check_membership_input);
        box3 = (CheckBox) view.findViewById(R.id.third_check_membership_input);
        box4 = (CheckBox) view.findViewById(R.id.fourth_check_membership_input);
        btn_payment = (Button) view.findViewById(R.id.btn_payment_membership_input);

        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        token = auto.getString("token", null);
        menuName.setText(auto.getString("menuName", null));
        menuCategory = auto.getInt("menuCategory", 0);
        Log.i("mytoken", token);

        btn_payment.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View v)
                {
//                    if (!isAllChecked()) {
//                        Toast.makeText(mContext, "모두 동의해야 합니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (!isAllFilled()) {
//                        Toast.makeText(mContext, "모든 칸을 채워야 합니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    getMemberJoinInfo();
                }
        });
        return view;
    }

    public boolean isAllChecked() {
        if (box1.isChecked() && box2.isChecked() && box3.isChecked() && box4.isChecked()) {
            return true;
        }
        return false;
    }

    public boolean isAllFilled()
    {
        if (name.getText() != null
                && postAddress.getText() != null
                && address.getText() != null
                && detailAddress.getText() != null
                && phoneNumber.getText() != null)
        {
            return true;
        }
        return false;
    }

    public void getMemberJoinInfo() {
        JoinForm form = new JoinForm();
        form.name = name.getText().toString();
        form.category = menuCategory;
        form.zipcode = postAddress.getText().toString();
        form.address = address.getText().toString();
        form.subAddress = detailAddress.getText().toString();
        form.phone = phoneNumber.getText().toString();
        form.etcInformation = deliveryMemo.getText().toString();
        Call<getMemberJoinInfo> getMemberJoinInfoCall = networkService.getMemeberJoinInfo(token, form);
        getMemberJoinInfoCall.enqueue(new Callback<getMemberJoinInfo>() {
            @Override
            public void onResponse(Call<getMemberJoinInfo> call, Response<getMemberJoinInfo> response) {
                if (response.isSuccessful()) {
                    getMemberJoinInfo getMemberJoinInfo = response.body();
                    Log.i("MyTag", getMemberJoinInfo.msg);
                    mContext.changeFragment(29);
                    Toast.makeText(mContext, "멤버십 가입을 축하드립니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(mContext, "가입 양식을 맞춰주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getMemberJoinInfo> call, Throwable t) {
                Toast.makeText(mContext, "Fail to load", Toast.LENGTH_SHORT).show();
                Log.i("MyTag", t.getMessage());
            }
        });
    }
}
