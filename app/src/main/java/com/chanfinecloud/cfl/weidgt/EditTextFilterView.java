package com.chanfinecloud.cfl.weidgt;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;



public class EditTextFilterView extends androidx.appcompat.widget.AppCompatEditText {


    private Context mContext;
    public EditTextFilterView(Context context) {
        super(context);
        this.mContext = context;
        addWatcher();
    }

    public EditTextFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        addWatcher();
    }

    public EditTextFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        addWatcher();
    }

    private void addWatcher() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setProhibitEmoji(this);

    }


    //EditText特殊符号过滤
    private void setProhibitEmoji(EditText et) {
        InputFilter[] filters = { getInputFilterProhibitEmoji() ,getInputFilterProhibitSP()};
        et.setFilters(filters);
    }

    private InputFilter getInputFilterProhibitEmoji() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsEmoji(codePoint)) {
                        buffer.append(codePoint);
                    } else {

                        Toast.makeText(mContext, "内容不能含有第三方表情", Toast.LENGTH_SHORT).show();
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        return filter;
    }



    private boolean getIsEmoji(char codePoint) {
        if ((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
            return false;
        return true;
    }


    private InputFilter getInputFilterProhibitSP() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                int isHaveFilter = 0;
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsSp(codePoint)) {
                        buffer.append(codePoint);
                    } else {
                        isHaveFilter = 1;

                        i++;
                        continue;
                    }
                }
                if (isHaveFilter == 1){
                    Toast.makeText(mContext, "内容不能含有特殊字符", Toast.LENGTH_SHORT).show();
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        return filter;
    }

    private boolean getIsSp(char codePoint){
        if(Character.getType(codePoint)>Character.CURRENCY_SYMBOL){
            return true;
        }
        return false;

    }




}
