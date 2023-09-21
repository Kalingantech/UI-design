package com.example.uidesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Recycler_card_Adapter_old extends RecyclerView.Adapter<Recycler_card_Adapter_old.ViewHolder> {

private final Recycler_interface recycler_interface;

    //User_recycle_model - comes from model clss & this case used to know how many item/cards need to be created- will be user in getItemCount method.
private List<User_recycle_model> userlist;

    public Recycler_card_Adapter_old(List<User_recycle_model> userlist, Recycler_interface recycler_interface) {
        this.userlist = userlist;
        this.recycler_interface = recycler_interface;
    }


    //this method is inflate the singe view
    @Override
    public Recycler_card_Adapter_old.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recycler,parent,false);
        return new Recycler_card_Adapter_old.ViewHolder(view,recycler_interface);
    }

    //this method is get the all user value of each user and update to the single view with position value & holder class will connect java to xml
    @Override
    public void onBindViewHolder(@NonNull Recycler_card_Adapter_old.ViewHolder holder, int position) {
//values likes userpic,Username,age,Divider are taken from getter & setter in user_recycel_model
//holder.setData(image,name,ageno,dividerline);
        holder.userpic_ref.setImageResource(userlist.get(position).getUserpic());
        holder.username_ref.setText(userlist.get(position).getUsername());
        holder.age_ref.setText(userlist.get(position).getAge());
        holder.divider_ref.setText(userlist.get(position).getDivider());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


    //viewHold class is initalise the xml into Java& similar to oncreate method but only for the single card/user
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView userpic_ref;
        private TextView username_ref;
        private TextView age_ref;
        private TextView divider_ref;

        public ViewHolder(@NonNull View itemView,Recycler_interface recycler_interface) {
            super(itemView);
            userpic_ref=itemView.findViewById(R.id.xml_profile_pic);
            username_ref=itemView.findViewById(R.id.xml_name);
            age_ref=itemView.findViewById(R.id.xml_age);
            divider_ref=itemView.findViewById(R.id.xml_divider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycler_interface != null){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        recycler_interface.usertemplateclick(pos);
                    }
                    }
                }
            });

        }


        /*public void setData(int image, String name, String ageno, String dividerline) {
            userpic.setImageResource(image);
            username.setText(name);
            age.setText(ageno);
            divider.setText(dividerline);
        }*/
    }
}
