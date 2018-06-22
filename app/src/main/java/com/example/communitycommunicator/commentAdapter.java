package com.example.communitycommunicator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by mAni on 08/05/2018.
 */

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<CommentVariables> productList;

    public commentAdapter(Context mCtx, List<CommentVariables> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.comment_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        final CommentVariables product = productList.get(position);

        holder.txt_userName2.setText(product.getUserName());
        holder.txt_comment.setText(product.getComment());

        holder.btn_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = product.getId();
                String userName = product.getUserName();
                String comment = product.getComment();
                Intent intent = new Intent(mCtx , Replies.class);
                intent.putExtra("id" , id);
                intent.putExtra("userName" , userName);
                intent.putExtra("comment" , comment);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_userName2,  txt_comment ;
        ImageButton btn_reply  ;
        View mView ;

        public ProductViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;

            txt_userName2 = itemView.findViewById(R.id.txt_userName2);
            txt_comment = itemView.findViewById(R.id.txt_comment);
            //imageView = itemView.findViewById(R.id.imageView);
            btn_reply = itemView.findViewById(R.id.btn_reply);
        }
    }
}
