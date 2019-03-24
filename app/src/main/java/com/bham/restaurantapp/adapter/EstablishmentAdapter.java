package com.bham.restaurantapp.adapter;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.activity.EstablishmentViewActivity;
import com.bham.restaurantapp.model.fsa.Establishment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder> {
    private static final String TAG = "EstablishmentAdapter";
    private List<Establishment> establishmentsList;

    public EstablishmentAdapter(List<Establishment> establishmentsList) {
        this.setHasStableIds(true);
        this.establishmentsList = establishmentsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.establishment_list_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Establishment currentEstablishment = establishmentsList.get(position);
        holder.establishmentNameTextView.setText(currentEstablishment.getBusinessName());
        try {
            holder.establishmentRatingBar.setRating(Float.parseFloat(currentEstablishment.getRatingValue()));
        } catch (NumberFormatException n) {
            holder.establishmentRatingBar.setRating(0);
        }
        String establishmentDistance = currentEstablishment.getDistance();
        if (establishmentDistance != null) {
            holder.establishmentDistTextView.setText(establishmentDistance);
        }

        GradientDrawable border = new GradientDrawable();
        border.setColor(ContextCompat.getColor(holder.itemView.getContext(),
                holder.itemView.getResources().getIdentifier(
                        String.format(
                                "btypeid%s",
                                currentEstablishment.getBusinessTypeID()
                        ),
                        "color",
                        holder.itemView.getContext().getPackageName()
                )));
        border.setStroke(1, 0xFF000000);
        holder.itemView.setBackground(border);
        holder.itemView.setOnClickListener(v -> {
            Log.i(TAG, "onClick: clicked " + v.toString());
            v.setSelected(true);
            v.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in));
            Intent i = new Intent(holder.itemView.getContext(), EstablishmentViewActivity.class);
            i.putExtra("businessName", currentEstablishment.getBusinessName());
            i.putExtra("businessType", currentEstablishment.getBusinessType());
            i.putExtra("businessTypeId", Integer.parseInt(currentEstablishment.getBusinessTypeID()));
            StringBuilder addressLine = new StringBuilder();
            if (!currentEstablishment.getAddressLine1().equals(""))
                addressLine.append(currentEstablishment.getAddressLine1()).append(",\n");
            if (!currentEstablishment.getAddressLine2().equals(""))
                addressLine.append(currentEstablishment.getAddressLine2()).append(",\n");
            if (!currentEstablishment.getAddressLine3().equals(""))
                addressLine.append(currentEstablishment.getAddressLine3()).append(",\n");
            if (!currentEstablishment.getAddressLine4().equals(""))
                addressLine.append(currentEstablishment.getAddressLine4()).append(",\n");
            if (!currentEstablishment.getPostCode().equals(""))
                addressLine.append(currentEstablishment.getPostCode());
            i.putExtra(
                    "addressLine",
                    addressLine.toString()
            );
            i.putExtra("ratingValue", Integer.valueOf(currentEstablishment.getRatingValue()));
            i.putExtra("ratingDate", currentEstablishment.getRatingDate());
            i.putExtra("localAuthorityName", currentEstablishment.getLocalAuthorityName());
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return this.establishmentsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView establishmentNameTextView;
        RatingBar establishmentRatingBar;
        TextView establishmentDistTextView;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            establishmentNameTextView = itemView.findViewById(R.id.establishmentNameTextView);
            establishmentRatingBar = itemView.findViewById(R.id.establishmentRatingBar);
            establishmentDistTextView = itemView.findViewById(R.id.establishmentDistTextView);
        }
    }
}

