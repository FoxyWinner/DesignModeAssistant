package com.designmodeassistant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.designmodeassistant.R;
import com.designmodeassistant.vo.ContentItem;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ContentItem} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class ContentItemRecyclerViewAdapter extends RecyclerView.Adapter<ContentItemRecyclerViewAdapter.ViewHolder>
{

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private final List<ContentItem> mValues;
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ContentItemRecyclerViewAdapter(List<ContentItem> mValues, OnItemClickLitener mOnItemClickLitener)
    {
        this.mValues = mValues;
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.mItem = mValues.get(position);
        holder.mChapterIdView.setText("第"+mValues.get(position).itemChapterId+"章");
        holder.mChapterNameView.setText(mValues.get(position).itemChapterName);
        holder.mView.setTag(""+mValues.get(position).itemChapterId);
        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != mOnItemClickLitener)
                {
                    mOnItemClickLitener.onItemClick(holder.mView,position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView mChapterIdView;
        public final TextView mChapterNameView;
        public ContentItem mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mChapterIdView = (TextView) view.findViewById(R.id.chapterId);
            mChapterNameView = (TextView) view.findViewById(R.id.chapterName);
        }

        @Override
        public String toString()
        {
            return super.toString() +" '" +mChapterIdView+ "',"+ " '" + mChapterNameView.getText() + "'";
        }
    }
}
