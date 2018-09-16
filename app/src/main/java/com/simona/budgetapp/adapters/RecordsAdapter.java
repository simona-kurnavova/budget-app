package com.simona.budgetapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simona.budgetapp.R;
import com.simona.budgetapp.entities.Category;
import com.simona.budgetapp.entities.Record;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;


public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder> {

    private List<Record> mRecords;
    private List<Category> mCategories;
    private Context mContext;

    public RecordsAdapter(Context context, List<Record> records, List<Category> categories) {
        mContext = context;
        mRecords = records;
        mCategories = categories;
    }

    public void updateRecords(List<Record> records) {
        mRecords = records;
    }

    public void updateCategories(List<Category> categories) {
        mCategories = categories;
    }

    @Override
    public RecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.records_list_item, parent, false);
        return new RecordsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordsViewHolder holder, int position) {
        Log.v("adapter", mRecords.get(position).toString());
        double price_value = BigDecimal.valueOf(mRecords.get(position).getPrice())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        String price = Double.toString(price_value);
        if (Objects.equals(mRecords.get(position).getType(), mContext.getString(R.string.expense))){
            price = "- " + price;
            holder.mPriceView.setTextColor(mContext.getResources().getColor((R.color.colorRed)));
        }
        else {
            price = "+ " + price;
            holder.mPriceView.setTextColor(mContext.getResources().getColor((R.color.colorGreen)));
        }
        holder.mPriceView.setText(price);

        int category_id = mRecords.get(position).getCategory();
        for (Category category: mCategories) {
            if(category.getId() == category_id) {
                holder.mCategoryView.setText(category.getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mRecords != null) {
            return mRecords.size();
        }
        return 0;
    }

    public static class RecordsViewHolder extends RecyclerView.ViewHolder {

        public TextView mPriceView;

        public TextView mCategoryView;

        public RecordsViewHolder(View view) {
            super(view);
            mPriceView = view.findViewById(R.id.price_text);
            mCategoryView = view.findViewById(R.id.category_text);
        }
    }
}
