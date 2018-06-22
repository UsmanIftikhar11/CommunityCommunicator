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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mAni on 16/05/2018.
 */

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<CircleVariables> productList;

    private static String URL_ADDMEMBER = "http://192.168.10.109/project/addmember.php/";

    public CircleAdapter(Context mCtx, List<CircleVariables> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.circle_list, null , false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final CircleVariables product = productList.get(position);

        holder.txt_circleName.setText(product.getCirclename());
        holder.txt_noOfUser.setText(""+product.getTotalmembers());
        holder.setImage(mCtx , product.getCircleimage());

        holder.btn_joinCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int id = product.getId();
                int members = product.getTotalmembers();
                final int newMember = members + 1 ;


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDMEMBER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                String success = jsonObject.getString("success");

                                if (success.equals("1")) {

                                    Toast.makeText(mCtx, "Success" + "position:" +position + "\n" + "pos:" + holder.getAdapterPosition(), Toast.LENGTH_LONG).show();

                                    int pos = holder.getAdapterPosition();

                                    if(pos != position){
                                        if(holder.btn_viewCircle.isEnabled()) {

                                            holder.btn_viewCircle.setEnabled(false);
                                        }
                                    }
                                    //holder.btn_viewCircle.setEnabled(true);
                                    //Education education = new Education();
                                    //education.recreate();

                                    ((Circles) mCtx).recreate();
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
                            params.put("members", String.valueOf(newMember));
                            params.put("id", String.valueOf(id));


                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                    requestQueue.add(stringRequest);
            }
        });

        holder.btn_viewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mCtx , SingleCirclePosts.class);
                intent.putExtra("circleName" , product.getCirclename());
                intent.putExtra("userName" , product.getCreatedby());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_noOfUser, txt_circleName;
        ImageButton btn_joinCircle , btn_viewCircle ;
        CircleImageView img_cirlce ;
        View mView ;

        public ProductViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;

            txt_noOfUser = itemView.findViewById(R.id.txt_noOFUsers);
            txt_circleName = itemView.findViewById(R.id.txt_circleName);

            btn_joinCircle = itemView.findViewById(R.id.btn_joinCircle);
            btn_viewCircle = itemView.findViewById(R.id.btn_viewCircle);

        }

        public void setImage (Context ctx , String image){
            img_cirlce = mView.findViewById(R.id.imageCircle);
            Glide.with(ctx).load(image).into(img_cirlce);
        }
    }
}
