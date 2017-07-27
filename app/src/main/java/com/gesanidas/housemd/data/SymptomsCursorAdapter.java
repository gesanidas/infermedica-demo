package com.gesanidas.housemd.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gesanidas.housemd.R;
import com.gesanidas.housemd.models.Symptom;

/**
 * Created by gesanidas on 7/27/2017.
 */

public class SymptomsCursorAdapter extends RecyclerView.Adapter<SymptomsCursorAdapter.SymptomsCursorAdapterViewHolder>
{
    private Cursor mCursor;
    private Context mContext;
    private final ListItemClickListener  mOnClickListener;



    public interface ListItemClickListener
    {
        void onClick(int id);
    }


    public SymptomsCursorAdapter(Context context,ListItemClickListener listItemClickListener)
    {
        mOnClickListener=listItemClickListener;
        this.mContext=context;
    }

    @Override
    public SymptomsCursorAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context=parent.getContext();
        int layoutIdForListItem = R.layout.item_symptom;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new SymptomsCursorAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SymptomsCursorAdapterViewHolder holder, int position)
    {
        mCursor.moveToPosition(position);
        holder.textView.setText(mCursor.getString(2));
    }



    @Override
    public int getItemCount()
    {
        if (mCursor == null)
        {
            return 0;
        }
        return mCursor.getCount();
    }


    public Cursor swapCursor(Cursor c)
    {
        if (mCursor == c)
        {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null)
        {
            this.notifyDataSetChanged();
        }
        return temp;
    }



    public class SymptomsCursorAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public final TextView textView;


        public SymptomsCursorAdapterViewHolder(View view)
        {
            super(view);
            textView=(TextView)view.findViewById(R.id.sym_view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view)
        {
            int adapterPosition=getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int ID =mCursor.getInt(0);
            mOnClickListener.onClick(ID);
        }

    }
}
