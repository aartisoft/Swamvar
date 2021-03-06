package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starling.softwares.swamvar.Model.ProductDescriptionModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/6/2018.
 */

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder> {
    private Context context;
    private List<ProductDescriptionModel.DataBean.ImagesBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnPicSelectedListner listner;
    private SparseBooleanArray sparseBooleanArray;

    public ProductImageAdapter(Context context, OnPicSelectedListner listner) {
        this.context = context;
        this.listner = listner;
        sparseBooleanArray = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pro_pic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductDescriptionModel.DataBean.ImagesBean model = list.get(position);
        Glide.with(context).load(model.getDesign_image()).into(holder.image);
        if (sparseBooleanArray.get(holder.getAdapterPosition(), false)) {
            holder.parent.setBackground(ContextCompat.getDrawable(context, R.drawable.subsub_background));
        } else
            holder.parent.setBackground(ContextCompat.getDrawable(context, R.drawable.subsub__white_background));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onPicSlected(model, holder.getAdapterPosition());
            }
        });
    }


    public void selectItem(int pos) {
        if (sparseBooleanArray.size() > 0) {
            sparseBooleanArray.clear();
        }
        sparseBooleanArray.put(pos, true);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.parent)
        FrameLayout parent;
        @BindView(R.id.image)
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setData(List<ProductDescriptionModel.DataBean.ImagesBean> list) {
        this.list = list;
        notifyDataSetChanged();
        if (this.list.size() > 0) {
            selectItem(0);
            listner.onPicSlected(list.get(0), 0);
        }
    }

    public interface OnPicSelectedListner {
        void onPicSlected(ProductDescriptionModel.DataBean.ImagesBean model, int position);
    }
}
