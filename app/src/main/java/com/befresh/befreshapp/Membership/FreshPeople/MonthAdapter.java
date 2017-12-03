package com.befresh.befreshapp.Membership.FreshPeople;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.befresh.befreshapp.Membership.FreshPeople.FreshPeopleFragment.mItems;
import static com.befresh.befreshapp.Membership.FreshPeople.FreshPeopleFragment.togglebtn;
import static com.befresh.befreshapp.Membership.FreshPeople.FreshPeopleFragment.weekindex;
import static com.befresh.befreshapp.Membership.FreshPeople.FreshPeopleFragment.weekstatus;

/**
 * Created by iagomendesfucolo on 24/03/17.
 */

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {
    private GregorianCalendar mCalendar;
    private Calendar mCalendarToday;
    private NavigationActivity mContext;
    private int mMonth;
    private int mYear;
    private int mDaysShown;
    private int mDaysLastMonth;
    private int mDaysNextMonth;
    private final String[] mDays = { "월", "화", "수", "목", "금", "토", "일" };
    private final int[] mDaysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private ArrayList<Mood> moods;
    private ArrayList<LinearLayout> lllist = new ArrayList<>();
    static ArrayList<TextView> tvlist = new ArrayList<>();
    private ArrayList<CheckBox> cblist = new ArrayList<>();
    private boolean criteria = false;
    private int flag =0;
    CheckBox checkBox;
    View current;
    static boolean togglestatus;
    private View.OnClickListener clickListener;
    static ArrayList<Integer> selectable = new ArrayList<>();
    ArrayList<Integer> delivery;
    ArrayList<String> dateee = new ArrayList<>();
    int statusIndex;
    public MonthAdapter(NavigationActivity context, int month, int year, ArrayList<Mood> moodies, View.OnClickListener clickListener, ArrayList<Integer> delivery)
    {
        mContext = context;
        mMonth = month;
        mYear = year;
        mCalendar = new GregorianCalendar(mYear, mMonth, 1);
        mCalendarToday = Calendar.getInstance();
        this.moods = moodies;
        this.clickListener = clickListener;
        this.statusIndex = weekindex;
        this.delivery = delivery;
        populateMonth();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.img2)
        CheckBox img2;
        @BindView(R.id.ll1)
        LinearLayout ll;
        String id;
        boolean status;
        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
        ViewHolder myview = new ViewHolder(view);
        view.setOnClickListener(clickListener);
        checkBox = (CheckBox)view.findViewById(R.id.img2);
        cblist.add(checkBox);
        return myview;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.day.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        holder.day.setText(mItems.get(position));
        holder.day.setTextColor(Color.DKGRAY);
        lllist.add(holder.ll);
        tvlist.add(holder.day);
        cblist.add(holder.img2);
        dateee.add(mItems.get(position));
        int[] date = getDate(position);

        if(position == weekindex)
        {

            if(weekstatus = true)
            {
                holder.img2.setChecked(false);
            }
            else if(weekstatus = false)
            {
                holder.img2.setChecked(false);
            }
        }

        if (date != null)
        {
            holder.day.setTextColor(Util.resolveDate(date[1], mMonth) ? Color.BLACK : Color.GRAY);

            if (isToday(date[0], date[1], date[2]))
            {
                holder.day.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            }

            // 오늘 이후의 목요일 날짜에만 색을 표기해줘야 한다.
            // criteria를 기준으로 확인
            if(isToday(date[0],date[1],date[2]))
                criteria = true;

            // 날짜가 목요일이고 현재 이후의 목요일 3개만 표시한다.
            // selectable은 표기하는 목요일을 제외한 나머지의 이벤트를 막는다.
            if(position % 7 == 3 && flag < 3 && criteria == true)
            {
                selectable.add(1);
                holder.day.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                holder.day.setTypeface(holder.day.getTypeface(), Typeface.BOLD);
                holder.img2.setVisibility(View.VISIBLE);
                if(delivery.get(flag) == 1)
                {
                    holder.img2.setChecked(true);
                }
                else
                {
                    holder.img2.setChecked(false);
                }
                flag++;
            }
            else
            {
                selectable.add(0);
            }
        }
        else
        {
            selectable.add(1);
        }
        holder.status = holder.img2.isChecked();
        holder.id = holder.day.getText().toString();
        holder.day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(position % 7 == 3 && selectable.get(position) == 1)
                {
                    togglestatus = holder.status;
                    togglebtn.setChecked(togglestatus);
                    int flag2 = 1;
                    weekindex = position;
                    weekstatus = true;
                    for (int i = 7; i < lllist.size(); i++)
                    {
                        lllist.get(i).setBackgroundColor(Color.parseColor("#ffffff"));
                        if (i / 7 == position / 7)
                        {
                            if (flag2 == 1)
                            {
                                lllist.get(i).setBackgroundResource(R.drawable.backgorund);
                            }
                            else if (flag2 == 7)
                            {
                                lllist.get(i).setBackgroundResource(R.drawable.background2);
                            }
                            else
                                lllist.get(i).setBackgroundColor(Color.parseColor("#11166B36"));
                            flag2++;
                        }
                    }
                }
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                togglestatus = holder.status;
//            }
//        });
//        holder.ll.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return 40;
    }


    private void populateMonth() {
        mItems = new ArrayList<String>();
        for (String day : mDays) {
            mItems.add(day);
            mDaysShown++;
        }

        int firstDay = getDay(mCalendar.get(Calendar.DAY_OF_WEEK));
        int prevDay;
        if (mMonth == 0)
            prevDay = daysInMonth(11) - firstDay + 1;
        else
            prevDay = daysInMonth(mMonth - 1) - firstDay + 1;
        for (int i = 0; i < firstDay; i++) {
            mItems.add(String.valueOf(prevDay + i));
            mDaysLastMonth++;
            mDaysShown++;
        }

        int daysInMonth = daysInMonth(mMonth);
        for (int i = 1; i <= daysInMonth; i++) {
            mItems.add(String.valueOf(i));
            mDaysShown++;
        }

        mDaysNextMonth = 1;
        while (mDaysShown % 7 != 0) {
            mItems.add(String.valueOf(mDaysNextMonth));
            mDaysShown++;
            mDaysNextMonth++;
        }
    }

    private int daysInMonth(int month) {
        int daysInMonth = mDaysInMonth[month];
        if (month == 1 && mCalendar.isLeapYear(mYear))
            daysInMonth++;
        return daysInMonth;
    }


    private int getDay(int day)
    {
        switch (day) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return 0;
        }
    }

    private boolean isToday(int day, int month, int year)
    {
        return mCalendarToday.get(Calendar.MONTH) == month
                && mCalendarToday.get(Calendar.YEAR) == year
                && mCalendarToday.get(Calendar.DAY_OF_MONTH) == day;
    }

    private int[] getDate(int position) {
        int date[] = new int[3];
        if (position <= 6) {
            return null; // day names
        } else if (position <= mDaysLastMonth + 6) {
            // previous month
            date[0] = Integer.parseInt(mItems.get(position));
            if (mMonth == 0) {
                date[1] = 11;
                date[2] = mYear - 1;
            } else {
                date[1] = mMonth - 1;
                date[2] = mYear;
            }
        } else if (position <= mDaysShown - mDaysNextMonth  ) {
            // current month
            date[0] = position - (mDaysLastMonth + 6);
            date[1] = mMonth;
            date[2] = mYear;
        } else {
            // next month
            date[0] = Integer.parseInt(mItems.get(position));
            if (mMonth == 11) {
                date[1] = 0;
                date[2] = mYear + 1;
            } else {
                date[1] = mMonth + 1;
                date[2] = mYear;
            }
        }
        return date;
    }



}
