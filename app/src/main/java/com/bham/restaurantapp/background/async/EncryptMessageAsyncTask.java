//package com.bham.restaurantapp.background.async;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.bham.restaurantapp.App;
//import com.bham.restaurantapp.model.db.FsaDatabase;
//import com.bham.restaurantapp.model.db.entities.EncryptedMessageEntity;
//import com.bham.restaurantapp.security.AESEncryptor;
//
//import java.nio.charset.StandardCharsets;
//
//public class EncryptMessageAsyncTask extends AsyncTask<String, Void, Long> {
//    private static final String TAG = "EncryptMessageAsyncTask";
//    private FsaDatabase db;
//
//    public EncryptMessageAsyncTask() {
//        this.db = App.getInstance().getDb();
//    }
//
//    @Override
//    protected Long doInBackground(String ... charSequences) {
//        AESEncryptor aes = new AESEncryptor(charSequences[0]);
//        byte[] encryptedText =
//                aes.encrypt(charSequences[1].getBytes(StandardCharsets.UTF_8));
////        return db.encryptedMessageDAO().insertEncryptedMessageEntity(
////                new EncryptedMessageEntity(
////                        aes.getSalt(),
////                        encryptedText,
////                        aes.getIv()
////                )
////        );
//        return 0L;
//
//    }
//
//    @Override
//    protected void onPostExecute(Long aLong) {
//        super.onPostExecute(aLong);
//        Log.i(TAG, "onPostExecute: The encrypted message id is: " + aLong);
//        new DecryptMessageAsyncTask(masterKey)
//                .execute(aLong);
//    }
//}
