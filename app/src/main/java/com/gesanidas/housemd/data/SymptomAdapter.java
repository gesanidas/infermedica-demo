package com.gesanidas.housemd.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gesanidas.housemd.R;
import com.gesanidas.housemd.models.Symptom;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 21/7/2017.
 */

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomAdapterViewHolder>
{
    private Symptom[] symptoms;
    private final ListItemClickListener  mOnClickListener;


    public interface ListItemClickListener
    {
        void onClick(Symptom symptom);
    }


    public SymptomAdapter(Symptom[] symptoms,ListItemClickListener listItemClickListener)
    {
        this.symptoms=symptoms;
        mOnClickListener=listItemClickListener;
    }

    @Override
    public SymptomAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context=parent.getContext();
        int layoutIdForListItem = R.layout.item_symptom;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new SymptomAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SymptomAdapterViewHolder holder, int position)
    {
        Symptom symptom=symptoms[position];
        holder.textView.setText(symptom.getName());
    }

    @Override
    public int getItemCount()
    {
        if (symptoms!=null)
            return symptoms.length;
        else
            return 0;
    }

    public void setSymptoms(Symptom[] symptoms)
    {
        this.symptoms=symptoms;
        notifyDataSetChanged();
    }

















    public class SymptomAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public final TextView textView;


        public SymptomAdapterViewHolder(View view)
        {
            super(view);
            textView = (TextView) view.findViewById(R.id.sym_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {

            int adapterPosition = getAdapterPosition();
            Symptom symptom = symptoms[adapterPosition];
            mOnClickListener.onClick(symptom);

        }
    }

}
