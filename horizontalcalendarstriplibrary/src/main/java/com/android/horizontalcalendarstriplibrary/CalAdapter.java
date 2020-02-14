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


@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class CalAdapter extends RecyclerView.Adapter<CalAdapter.MyViewHolder> {

    private Context context;
    private Object toCallBack;
    private DayDateMonthYearModel lastDaySelected;

    private ArrayList<DayDateMonthYearModel> dayModelList;
    private ArrayList<TextView> dateArrayList;
    private ArrayList<TextView> dayArrayList;

    CalAdapter(Context context, ArrayList<DayDateMonthYearModel> dayModelList) {
        this.context = context;
        this.dayModelList = dayModelList;
        dateArrayList = new ArrayList<>();
        dayArrayList = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (dayModelList.get(position).isToday) {
            holder.date.setBackground(context.getResources().getDrawable(R.drawable.currect_date_background));
            holder.date.setTextColor(context.getResources().getColor(R.color.white));
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
            public void onClick(View view) {
                lastDaySelected = dayModelList.get(position);
                try {
                    CallBack cb = new CallBack(toCallBack, "newDateSelected");
                    cb.invoke(lastDaySelected);
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayModelList.size();
    }

   /* void add(DayDateMonthYearModel DDMYModel) {
        dayModelList.add(DDMYModel);
        notifyItemInserted(dayModelList.size() - 1);
    }*/


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
