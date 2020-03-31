package com.chanfinecloud.cfl.weidgt.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.weidgt.wheelview.adapter.NumericWheelAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by zengx on 2019/5/11.
 * Describe:
 */
public class BirthWheelDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private LayoutInflater inflater = null;
    LinearLayout ll;
    private WheelView year;
    private WheelView month;
    private WheelView day;
    private WheelView time;
    private WheelView min;
    private WheelView sec;
    private int mMonth = 0;
    private int mDay = 1;
    boolean isMonthSetted = false, isDaySetted = false;
    private TextView date;
    View view = null;
    private int lastday;
    private int lastitem;
    private Button button;
    private BirthWheelDialog.Click click;
    private BirthWheelDialog.OnDateTimeConfirm onDateTimeConfirm;
    private String dateValue;
    /**
     * 用于判断选择时间初始化位明天的时间
     */
    private boolean isTomorrow;

    public BirthWheelDialog(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * @param context
     * @param theme
     * @Title:WheelDialog
     * @Description:TODO
     */

    public BirthWheelDialog(Context context, int theme, BirthWheelDialog.Click click) {
        super(context, theme);
        this.context = context;
        this.click = click;
    }

    public BirthWheelDialog(Context context, int theme, BirthWheelDialog.OnDateTimeConfirm onDateTimeConfirm){
        super(context,theme);
        this.context=context;
        this.onDateTimeConfirm=onDateTimeConfirm;
    }

    public BirthWheelDialog(Context context, int theme, BirthWheelDialog.Click click, boolean isTomorrow) {
        super(context, theme);
        this.context = context;
        this.click = click;
        this.isTomorrow = isTomorrow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_wheel);
        initview();
    }

    private void initview() {
        inflater = getLayoutInflater().from(context);
        ll = (LinearLayout) findViewById(R.id.ll);
        date = (TextView) findViewById(R.id.date);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        ll.addView(getDataPick());

    }

    /**
     * @return
     * @Title: getDataPick
     * @Description: TODO
     * @return: View
     */

    private View getDataPick() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        int norYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;// 通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DAY_OF_MONTH);
//        int curHour = c.get(Calendar.HOUR_OF_DAY);
//        int curMin = c.get(Calendar.MINUTE);
//        Log.e("DateTime",norYear+"-"+curMonth+"-"+curDate+" "+curHour+":"+curMin);
        int curYear = norYear;
        // int curMonth = mMonth + 1;
        // int curDate = mDay;
        view = inflater.inflate(R.layout.wheel_date_picker, null);

        year = (WheelView) view.findViewById(R.id.year);
        NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(context, norYear - 100, norYear);
        numericWheelAdapter1.setLabel("");
        year.setViewAdapter(numericWheelAdapter1);
        year.setCyclic(true);// 是否可循环滑动
        year.addScrollingListener(scrollListener);

        month = (WheelView) view.findViewById(R.id.month);
        NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(context, 1, 12, "%02d");
        numericWheelAdapter2.setLabel("");
        month.setViewAdapter(numericWheelAdapter2);
        month.setCyclic(true);
        month.addScrollingListener(scrollListener);

        day = (WheelView) view.findViewById(R.id.day);
        initDay(curYear, curMonth);
        day.setCyclic(true);
        day.addScrollingListener(scrollListener);
        // time= (WheelView) view.findViewById(R.id.time);
        // String[] times = {"上午","下午"} ;
        // ArrayWheelAdapter<String> arrayWheelAdapter=new
        // ArrayWheelAdapter<String>(MainActivity.this,times );
        // time.setViewAdapter(arrayWheelAdapter);
        // time.setCyclic(false);
        // time.addScrollingListener(scrollListener);

        min = (WheelView) view.findViewById(R.id.min);
        min.setVisibility(View.GONE);
//        NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(context, 1, 24, "%02d", true);
//        numericWheelAdapter3.setLabel("");
//        min.setViewAdapter(numericWheelAdapter3);
//        min.setCyclic(true);
//        min.addScrollingListener(scrollListener);
//
        sec = (WheelView) view.findViewById(R.id.sec);
        sec.setVisibility(View.GONE);
//        NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(context, 1, 60, "%02d");
//        numericWheelAdapter4.setLabel("");
//        sec.setViewAdapter(numericWheelAdapter4);
//        sec.setCyclic(true);
//        sec.addScrollingListener(scrollListener);
//        sec.setCurrentItem(curMin);

        year.setVisibleItems(7);// 设置显示行数
        month.setVisibleItems(7);
        day.setVisibleItems(7);
        // time.setVisibleItems(7);
//        min.setVisibleItems(7);
//        sec.setVisibleItems(7);

        year.setCurrentItem(curYear-90);
        month.setCurrentItem(curMonth - 1);
        day.setCurrentItem(curDate - 1);
//        min.setCurrentItem(curHour-1);
//        sec.setCurrentItem(curMin-1);
        if (isTomorrow) {
            date.setText(norYear
                    + "-"
                    + ((month.getCurrentItem() + 1 < 10 ? "0" + (month.getCurrentItem() + 1) : month
                    .getCurrentItem() + 1))
                    + "-"
                    + ((day.getCurrentItem() + 1 < 10 ? "0" + (day.getCurrentItem() + 1) : day.getCurrentItem() + 1))
            );
        } else {
            date.setText(norYear
                    + "-"
                    + ((month.getCurrentItem() + 1 < 10 ? "0" + (month.getCurrentItem() + 1) : month
                    .getCurrentItem() + 1))
                    + "-"
                    + ((day.getCurrentItem() + 1 < 10 ? "0" + (day.getCurrentItem() + 1) : day.getCurrentItem() + 1))
            );
        }
        return view;
    }

    public void setBirth(String birth){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        Calendar nowCalendar=Calendar.getInstance();
        try {
            calendar.setTime(format.parse(birth));
            year.setCurrentItem(calendar.get(Calendar.YEAR)+100-nowCalendar.get(Calendar.YEAR));
            month.setCurrentItem(calendar.get(Calendar.MONTH));
            day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            date.setText(calendar.get(Calendar.YEAR)
                    + "-"
                    + ((month.getCurrentItem() + 1 < 10 ? "0" + (month.getCurrentItem() + 1) : month
                    .getCurrentItem() + 1))
                    + "-"
                    + ((day.getCurrentItem() + 1 < 10 ? "0" + (day.getCurrentItem() + 1) : day.getCurrentItem() + 1))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param curYear
     * @param curMonth
     * @Title: initDay
     * @Description: TODO
     * @return: void
     */

    private void initDay(int curYear, int curMonth) {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getDay(curYear, curMonth),
                "%02d");
        numericWheelAdapter.setLabel("");
        day.setViewAdapter(numericWheelAdapter);
    }

    /**
     * @param curYear
     * @param curMonth
     * @return
     * @Title: getDay
     * @Description: TODO
     * @return: int
     */

    private int getDay(int curYear, int curMonth) {
        int day = 30;
        boolean flag = false;
        switch (curYear % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (curMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            Calendar c = Calendar.getInstance();
            int norYear = c.get(Calendar.YEAR);
            int n_year = year.getCurrentItem() + norYear - 100;// 年
            int n_month = month.getCurrentItem() + 1;// 月
            int nowDay = getDay(n_year, n_month);
            initDay(n_year, n_month);
            Log.e("lastday", lastday + "");
            Log.e("lastitem", lastitem + "");
            String birthday = new StringBuilder()
                    .append((year.getCurrentItem() + norYear-100))
                    .append("-")
                    .append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month
                            .getCurrentItem() + 1))
                    .append("-")
                    .append(nowDay < lastday && lastday == (lastitem + 1) ? "0" + (lastday - nowDay) : ((day.getCurrentItem() + 1) < 10) ? "0" + (day.getCurrentItem() + 1) : String.valueOf((day.getCurrentItem() + 1))).toString();
//            int hour = min.getCurrentItem();
//            int hour = 0;
//            if (min.getCurrentItem() + 1 != 24)
//                hour = min.getCurrentItem() + 1;
////            int minute = sec.getCurrentItem();
//            int minute = 0;
//            if (sec.getCurrentItem() + 1 != 60)
//                minute = sec.getCurrentItem() + 1;
//            birthday = birthday
//                    + new StringBuilder().append(" ").append(hour < 10 ? "0" + hour : hour).append(":")
//                    .append(minute < 10 ? "0" + minute : minute).toString();

            date.setText(birthday);
            lastday = nowDay;
            lastitem = day.getCurrentItem();
            // tv1.setText("年龄             " + calculateDatePoor(birthday) +
            // "岁");
            // tv2.setText("星座             " + getAstro(month.getCurrentItem() +
            // 1, day.getCurrentItem() + 1));

        }
    };

    @Override
    public void onClick(View v) {

        if(click!=null){
            click.getvaue(date.getText().toString());
        }
        if(onDateTimeConfirm!=null){
            onDateTimeConfirm.returnData(date.getText().toString(),dateValue);
        }
        this.dismiss();

    }

    public interface Click {
        void getvaue(String value);
    }

    public interface OnDateTimeConfirm{
        void returnData(String dateText, String dateValue);
    }
}

