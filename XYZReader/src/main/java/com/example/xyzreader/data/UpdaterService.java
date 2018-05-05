package com.example.xyzreader.data;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.text.format.Time;
import android.util.Log;

import com.example.xyzreader.remote.Network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdaterService extends IntentService {
    private static final String TAG = "UpdaterService";

    public static final String BROADCAST_ACTION_STATE_CHANGE
            = "com.example.xyzreader.intent.action.STATE_CHANGE";
    public static final String EXTRA_REFRESHING
            = "com.example.xyzreader.intent.extra.REFRESHING";

    public UpdaterService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Time time = new Time();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected()) {
            Log.w(TAG, "Not online, not refreshing.");
            return;
        }

        sendStickyBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));

        // Don't even inspect the intent, we only do one thing, and that's fetch content.
        final ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();

        final Uri dirUri = ItemsContract.Items.buildDirUri();

        // Delete all items
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());

        Call<List<Article>> getArticles = Network.getArticlesRestService().getArticles();

        getArticles.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {

                try {
                    for (Article article : response.body()) {
                        ContentValues values = ArticleMapper.toContentValues(article);
                        cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                    }

                    getContentResolver().applyBatch(ItemsContract.CONTENT_AUTHORITY, cpo);

                } catch (RemoteException | NullPointerException | OperationApplicationException e) {
                    e.printStackTrace();
                }

                sendStickyBroadcast(new Intent(BROADCAST_ACTION_STATE_CHANGE)
                        .putExtra(EXTRA_REFRESHING, false));
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

                sendStickyBroadcast(
                        new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));
            }
        });
    }
}