package com.befresh.befreshapp.Membership;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.R;

public class MembershipWelcomeFragment extends MasterFragment {

    private NavigationActivity mContext;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContext = (NavigationActivity) getMasterActivity();

        View view = inflater.inflate(R.layout.fragment_membership_welcome, container, false);

        btn = (Button) view.findViewById(R.id.btn_membership_welcome);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "Go to FreshPeople Pages", Toast.LENGTH_SHORT).show();
                mContext.changeFragment(30);
                //mContext.changeFragment();
            }
        });

        return view;
    }

}
