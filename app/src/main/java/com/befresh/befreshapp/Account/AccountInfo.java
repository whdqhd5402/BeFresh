package com.befresh.befreshapp.Account;

/**
 * Created by idongsu on 2017. 7. 8..
 */

public class AccountInfo
{
    public String msg;
    public ResultData data;

    public class ResultData
    {
        public Account2 account;
        public Membership membership;
    }

    public class Account2
    {
        public String email;
        public String name;
        public int point;
        public boolean checkMembership;
    }

    public class Membership
    {
        public String phone;
        public String address;
        public String card;
    }

}
