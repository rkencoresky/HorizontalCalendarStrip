package com.android.horizontalcalendarstriplibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class CalAdapter extends RecyclerView.Adapter<CalAdapter.MyViewHolder> {

    private Context context;
    private Object toCallBack;
    private DayDateMonthYearModel lastDaySelected;

    private ArrayList<DayDateMonthYearModel> dayModelList;
    private TextView clickedTextView = null;
    private ArrayList<TextView> dateArrayList = new ArrayList<>();
    private ArrayList<TextView> dayArrayList = new ArrayList<>();

    CalAdapter(Context context, ArrayList<DayDateMonthYearModel> dayModelList) {
        this.context = context;
        this.dayModelList = dayModelList;
    }

    void setCallback(Object toCallBack) {
        this.toCallBack = toCallBack;
    }

    @NonNull
    @Override
    public CalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_day_layout, parent, false);
        return new CalAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (dayModelList.get(position).isToday) {
            holder.date.setBackground(context.getResources().getDrawable(R.drawable.currect_date_background));
            holder.date.setTextColor(context.getResources().getColor(R.color.white));
            try {
                CallBack cb = new CallBack(toCallBack, "newDateSelected");
                cb.invoke(dayModelList.get(position));
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else if (dayModelList.get(position).equals(lastDaySelected)) {
            holder.date.setBackground(context.getResources().getDrawable(R.drawable.background_selected_day));
            holder.date.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        holder.day.setText(dayModelList.get(position).day);
        holder.date.setText(dayModelList.get(position).date);
        holder.date.setTag(position);
        dateArrayList.add(holder.date);
        dayArrayList.add(holder.day);
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.valueOf(v.getTag().toString());
                if (clickedTextView == null) {
                    clickedTextView = (TextView) v;
                    clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.background_selected_day));
                    clickedTextView.setTextColor(context.getResources().getColor(android.R.color.black));
                } else {
//                    if(!dayModelList.get(pos).isToday) {
                    if (lastDaySelected != null && lastDaySelected.isToday) {
                        clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.currect_date_background));
                        clickedTextView.setTextColor(context.getResources().getColor(R.color.white));
                    } else {
                        clickedTextView.setBackground(null);
                        clickedTextView.setTextColor(context.getResources().getColor(android.R.color.black));
                    }
                    clickedTextView = (TextView) v;
                    clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.background_selected_day));
                    clickedTextView.setTextColor(context.getResources().getColor(android.R.color.black));
                }

                try {
                    CallBack cb = new CallBack(toCallBack, "newDateSelected");
                    cb.invoke(dayModelList.get(pos));
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                lastDaySelected = dayModelList.get(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayModelList.size();
    }

    void add(DayDateMonthYearModel DDMYModel) {
        dayModelList.add(DDMYModel);
        notifyItemInserted(dayModelList.size() - 1);
    }

    @Override
    public void onViewAttachedToWindow(MyViewHolder holder) {
        holder.setIsRecyclable(false);
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        holder.setIsRecyclable(false);
        super.onViewDetachedFromWindow(holder);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView day, date;

        MyViewHolder(View view) {
            super(view);
            day = view.findViewById(R.id.day);
            date = view.findViewById(R.id.date);
        }
    }
}
