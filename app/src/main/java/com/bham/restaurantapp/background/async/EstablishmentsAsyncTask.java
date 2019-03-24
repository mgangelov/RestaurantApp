package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.background.controller.FsaDataController;
import com.bham.restaurantapp.model.fsa.Establishment;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;


public class EstablishmentsAsyncTask extends AsyncTask<String, Void, EstablishmentResult> {
    private static final String TAG = "EstablishmentsAsyncTask";
    private WeakReference<Context> applicationContext;
    private WeakReference<RecyclerView> rView;
    private WeakReference<TextView> pageNumberTextView;
    private SelectionTracker<Establishment> selectionTracker;

    public EstablishmentsAsyncTask(
            Context applicationContext,
            RecyclerView rView,
            TextView pageNumberTextView
    ) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.rView = new WeakReference<>(rView);
        this.pageNumberTextView = new WeakReference<>(pageNumberTextView);
    }

    @Override
    protected EstablishmentResult doInBackground(String... strings) {
        FsaDataController fsaAPI = new FsaDataController(
                applicationContext.get()
        );
        try {
            if (strings.length == 3) // ViewAllEstablishments
                return fsaAPI.getEstablishments(
                        Integer.valueOf(strings[0]), // pageNumber
                        Integer.valueOf(strings[1]), // pageSize
                        strings[2] // sortOptionKey
                );
            else if (strings.length == 8)
                return fsaAPI.getEstablishments(
                        strings[0], // searchValue
                        Integer.valueOf(strings[1]), // businessType
                        Integer.valueOf(strings[2]), // region
                        Integer.valueOf(strings[3]), // authority
                        Integer.valueOf(strings[4]), // pageNumber
                        Integer.valueOf(strings[5]), // pageSize
                        strings[6], // sortOptionKey
                        Integer.valueOf(strings[7]) // ratingKey
                );
            else if (strings.length == 10)
                return fsaAPI.getEstablishments(
                        strings[0], // Longitude
                        strings[1], // Latitude
                        Integer.valueOf(strings[2]), // businessType
                        Integer.valueOf(strings[3]), // region
                        Integer.valueOf(strings[4]), // authority
                        Float.valueOf(strings[5]), // maxDistanceLimit
                        Integer.valueOf(strings[6]), // pageNumber
                        Integer.valueOf(strings[7]), // pageSize
                        strings[8], // sortOptionKey
                        Integer.valueOf(strings[9]) // ratingKey
                );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

//    @Override
//    protected void onProgressUpdate() {
//        super.onProgressUpdate();
//        // TODO: Add loading
//    }


    @Override
    protected void onPostExecute(EstablishmentResult establishments) {
        super.onPostExecute(establishments);
        EstablishmentAdapter establishmentAdapter = new EstablishmentAdapter(establishments.getEstablishments());
        this.rView.get().setAdapter(establishmentAdapter);


//        selectionTracker = new SelectionTracker.Builder<>(
//                "my-selection-id",
//                rView.get(),
//                new EstablishmentKeyProvider(1, establishments.getEstablishments()),
//                new EstablishmentLookup(rView.get()),
//                StorageStrategy.createParcelableStorage(Establishment.class)
//        ).withOnItemActivatedListener((item, e) -> {
//
//            Log.i(TAG, "onItemActivated: item activated is " + item.toString());
//            Intent viewEstablishmentIntent = new Intent(applicationContext.get(), EstablishmentView.class);
//            viewEstablishmentIntent.putExtra("id", Objects.requireNonNull(item.getSelectionKey()).getBusinessName());
//            applicationContext.get().startActivity(viewEstablishmentIntent);
//            return true;
//        }).build();
//        establishmentAdapter.setSelectionTracker(selectionTracker);

        String pageNumber = String.valueOf(establishments.getMeta().getPageNumber());
        String totalPages = String.valueOf(establishments.getMeta().getTotalPages());
        this.pageNumberTextView.get().setText(String.format("%s/%s", pageNumber, totalPages));
        // TODO: This is when I return the results
    }
}
