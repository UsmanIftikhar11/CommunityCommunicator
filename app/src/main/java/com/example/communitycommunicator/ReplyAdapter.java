package com.example.communitycommunicator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mAni on 08/05/2018.
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ProductViewHolder>{

    private Context mCtx;
    private List<ReplyVariables> productList;

    public ReplyAdapter(Context mCtx, List<ReplyVariables> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.replies_list, null);
        return new ReplyAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        final ReplyVariables product = productList.get(position);

        holder.txt_userName3.setText(product.getUserName());
        holder.txt_reply.setText(product.getReply());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_userName3,  txt_reply ;
        View mView ;

        public ProductViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;

            txt_userName3 = itemView.findViewById(R.id.txt_userName3);
            txt_reply = itemView.findViewById(R.id.txt_reply);
        }
    }
}
