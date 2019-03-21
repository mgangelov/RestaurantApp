package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.background.controller.FsaDataController;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.AuthorityEntity;
import com.bham.restaurantapp.model.db.entities.BusinessTypeEntity;
import com.bham.restaurantapp.model.db.entities.RegionEntity;
import com.bham.restaurantapp.model.fsa.Authority;
import com.bham.restaurantapp.model.fsa.BusinessType;
import com.bham.restaurantapp.model.fsa.Region;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

public class RefreshDbAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "RefreshDbAsyncTask";
    private WeakReference<Context> applicationContext;
    private AlertDialog.Builder successAlert;

    public RefreshDbAsyncTask(Context applicationContext, AlertDialog.Builder successAlert) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.successAlert = successAlert;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.i(TAG, "Deleting all DB entries");
            FsaDatabase db = Room.databaseBuilder(
                    applicationContext.get(),
                    FsaDatabase.class,
                    "database")
                    .build();
            FsaDataController fsaDataController = new FsaDataController();
            db.authorityDAO().deleteAllAuthorityEntries();
            db.businessTypeDAO().deleteAllBusinessTypeEntities();
            db.regionDAO().deleteRegionEntities();
            Log.i(TAG, "Adding businessTypes to DB");
            List<BusinessType> bts = fsaDataController.getBusinessTypes()
                    .getBusinessTypes();
            for (BusinessType bt : bts) {
                db.businessTypeDAO().insertBusinessTypeEntity(
                        new BusinessTypeEntity(
                                bt.getbTypeId(),
                                bt.getbTypeName()
                        )
                );
                Log.i(TAG, String.format("Inserted %s", bt.getbTypeName()));
            }
            Log.i(TAG, "Adding regions to DB");
            List<Region> regions = fsaDataController.getRegions().getRegions()
                    .stream()
                    .filter(region -> !region.getName().equals("Scotland"))
                    .collect(Collectors.toList());

            for (Region region : regions) {
                db.regionDAO().insertRegionEntity(new RegionEntity(
                        region.getId(),
                        region.getName(),
                        region.getNameKey(),
                        region.getCode()
                        ));
                Log.i(TAG, String.format("Inserted %s", region.getName()));
            }
            Log.i(TAG, "Adding entry for All regions");
            db.regionDAO().insertRegionEntity(new RegionEntity(
                    99,
                    "All",
                    "All",
                    "ALL"
            ));
            Log.i(TAG, "Adding authorities and mapping their relations to Regions");
            List<Authority> authorities = fsaDataController.getAuthorities()
                    .getAuthorities();
            for (Authority a : authorities) {
                Region re = regions.stream()
                        .filter(region -> a.getRegionName().equals(region.getName()))
                        .findAny()
                        .orElse(null);
                if (re != null) {
                    db.authorityDAO().insertAuthorityEntity(
                            new AuthorityEntity(
                                    a.getId(),
                                    a.getName(),
                                    re.getId()
                            )
                    );
                    Log.i(TAG, String.format("Inserted %s", a.getName()));
                }
            }
            Log.i(TAG, "Adding entry for All authorities");
            db.authorityDAO().insertAuthorityEntity(
                    new AuthorityEntity(
                            8999,
                            "All",
                            null
                    )
            );
            Log.i(TAG, "Adding default All entry for each region");
            int initialID = 9000;
            for (Region r : regions) {
                Log.i(TAG, String.format("Processing %s", r.getName()));
                db.authorityDAO().insertAuthorityEntity(
                        new AuthorityEntity(
                                initialID,
                                "All",
                                r.getId()
                        )
                );
                initialID++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        successAlert.show();
    }
}
