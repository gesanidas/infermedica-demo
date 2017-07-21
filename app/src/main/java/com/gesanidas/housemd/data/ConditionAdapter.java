package com.gesanidas.housemd.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gesanidas.housemd.R;
import com.gesanidas.housemd.models.Condition;
import com.gesanidas.housemd.models.Symptom;

/**
 * Created by gesanidas on 7/22/2017.
 */

public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.ConditionAdapterViewHolder>
{
    private Condition[] conditions;

    public ConditionAdapter(Condition[] conditions)
    {
        this.conditions=conditions;
    }

    @Override
    public ConditionAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context=parent.getContext();
        int layoutIdForListItem = R.layout.item_condition;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new ConditionAdapterViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ConditionAdapter.ConditionAdapterViewHolder holder, int position)
    {
        Condition condition=conditions[position];
        holder.textView.setText(condition.getCommonName()+"  "+condition.getProbability());
    }


    @Override
    public int getItemCount()
    {
        if (conditions!=null)
            return conditions.length;
        else
            return 0;
    }


    public void setConditions(Condition[] conditions)
    {
        this.conditions=conditions;
        notifyDataSetChanged();
    }


    public class ConditionAdapterViewHolder extends RecyclerView.ViewHolder
    {

        public final TextView textView;


        public ConditionAdapterViewHolder(View view)
        {
            super(view);
            textView = (TextView) view.findViewById(R.id.cond_view);
        }


    }



}
