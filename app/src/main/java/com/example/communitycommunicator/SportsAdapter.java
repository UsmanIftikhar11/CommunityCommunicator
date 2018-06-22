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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mAni on 16/05/2018.
 */

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ProductViewHolder>{

    private Context mCtx;
    private List<Product> productList;

    private static String URL_LIKE = "http://192.168.10.109/project/likes.php/";
    private static String URL_DISLIKE = "http://192.168.10.109/project/dislike.php/";

    private boolean postLike = false;
    private boolean postDislike = false ;

    public SportsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.post_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);

        holder.txt_userName1.setText(product.getUserName());
        holder.txt_date.setText(product.getDate());
        holder.txt_caption.setText(product.getCaption());
        holder.txt_likes.setText(product.getLikes());
        holder.txt_dislike.setText(product.getDislike());
        //loading the image
        //Glide.with(mCtx).load(product.getImage()).into(holder.imageView);
        holder.setInvitationCard(mCtx , product.getImage());

        holder.btn_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int id = product.getId();
                int like = Integer.parseInt(product.getLikes());
                final int newLike = like + 1;

                postLike = true;
                postDislike = false;

                if (postLike == true) {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LIKE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                String success = jsonObject.getString("success");

                                if (success.equals("1")) {

                                    Toast.makeText(mCtx, "Success", Toast.LENGTH_LONG).show();
                                    ((Sports) mCtx).recreate();
                                    //Education education = new Education();
                                    //education.recreate();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mCtx, "Error" + e.toString(), Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(mCtx, "fail" + error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();
                            params.put("likes", String.valueOf(newLike));
                            params.put("id", String.valueOf(id));


                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                    requestQueue.add(stringRequest);

                }

                else {
                    Toast.makeText(mCtx, "You have already dislke this post" , Toast.LENGTH_LONG).show();

                }

            }
        });

        holder.btn_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int id = product.getId();
                int dislike  = Integer.parseInt(product.getDislike());
                final int newDisLike = dislike + 1 ;

                postDislike = true ;
                postLike = false ;

                if(postDislike== true) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DISLIKE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                String success = jsonObject.getString("success");

                                if (success.equals("1")) {

                                    Toast.makeText(mCtx, "Success", Toast.LENGTH_LONG).show();
                                    ((Sports) mCtx).recreate();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mCtx, "Error" + e.toString(), Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(mCtx, "fail" + error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();
                            params.put("dislike", String.valueOf(newDisLike));
                            params.put("id", String.valueOf(id));

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                    requestQueue.add(stringRequest);
                }

                else {

                    Toast.makeText(mCtx, "You have already liked this post" , Toast.LENGTH_LONG).show();
                }

            }
        });

        holder.btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = product.getId();
                String userName = product.getUserName();
                Intent intent = new Intent(mCtx , Comments.class);
                intent.putExtra("id" , id);
                intent.putExtra("userName" , userName);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_userName1, txt_date, txt_caption, txt_likes , txt_dislike;
        ImageButton btn_likes , btn_dislike , btn_comment ;
        View mView ;

        public ProductViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;

            txt_userName1 = itemView.findViewById(R.id.txt_userName1);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_caption = itemView.findViewById(R.id.txt_caption);
            txt_likes = itemView.findViewById(R.id.txt_noOfLikes);
            txt_dislike = itemView.findViewById(R.id.txt_noOfdisLikes);
            //imageView = itemView.findViewById(R.id.imageView);

            btn_likes = itemView.findViewById(R.id.btn_like);
            btn_dislike = itemView.findViewById(R.id.btn_disLike);
            btn_comment = itemView.findViewById(R.id.btn_comment);
        }

        public void setInvitationCard (Context ctx , String invitationCard){
            ImageView accessories_post_img = (ImageView)mView.findViewById(R.id.imageView);
            Glide.with(ctx).load(invitationCard).into(accessories_post_img);
        }
    }
}
