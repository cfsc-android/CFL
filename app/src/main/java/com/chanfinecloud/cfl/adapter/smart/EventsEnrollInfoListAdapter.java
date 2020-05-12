package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.EventEnrollInfoEntity;
import com.chanfinecloud.cfl.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Loong on 2020/2/23.
 * Version: 1.0
 * Describe:
 */
public class EventsEnrollInfoListAdapter extends RecyclerView.Adapter<EventsEnrollInfoListAdapter.EnrollViewHolder> {

    private Context context;
    private List<EventEnrollInfoEntity> eventEnrollInfoEntityList;
    private OnItemTextChangeListener onItemTextChangeListener;

    public EventsEnrollInfoListAdapter(Context context, List<EventEnrollInfoEntity> data) {
        this.context = context;
        this.eventEnrollInfoEntityList = eventEnrollInfoEntityList;
    }

    public void setOnItemTextChangeListener(OnItemTextChangeListener onItemTextChangeListener){
        this.onItemTextChangeListener  = onItemTextChangeListener;
    }
    @NonNull
    @Override
    public EnrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events_enroll_info, parent, false);
        return new EnrollViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnrollViewHolder holder, int position) {

        EventEnrollInfoEntity item = eventEnrollInfoEntityList.get(position);
        if (item != null) {

            if ( !Utils.isEmpty(item.getName()) )
                holder.itemEventsEnrollName.setText(item.getName() + "");
            if ( !Utils.isEmpty(item.getMobile()) )
                holder.itemEventsEnrollPhone.setText( item.getMobile() + "");

            holder.itemEventsEnrollName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    onItemTextChangeListener.OnItemTextChanged(holder.itemEventsEnrollName.getText().toString(), position, 1);
                }
            });

            holder.itemEventsEnrollPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    onItemTextChangeListener.OnItemTextChanged(holder.itemEventsEnrollPhone.getText().toString(), position, 2);

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return eventEnrollInfoEntityList.size();
    }

    public static class EnrollViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_events_enroll_name)
        EditText itemEventsEnrollName;
        @BindView(R.id.item_events_enroll_phone)
        EditText itemEventsEnrollPhone;

        public EnrollViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface  OnItemTextChangeListener{

        void OnItemTextChanged(String mContont, int nPosition, int nType);
    }
}
