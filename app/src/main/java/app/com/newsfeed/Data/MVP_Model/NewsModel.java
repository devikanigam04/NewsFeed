package app.com.newsfeed.Data.MVP_Model;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import app.com.newsfeed.Data.Pojo.NewsResponse;
import app.com.newsfeed.R;
import app.com.newsfeed.UI.MVP_Contracter.NewsContracter;

public class NewsModel implements NewsContracter.Model {

    private Activity activity;

    public NewsModel(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getData(final Listener listener) {
        Log.d("NewsResponse : ", activity.getString(R.string.access_token)+" "+
                activity.getString(R.string.facebook_app_id)+" "+activity.getString(R.string.user_id));
        AccessToken accessToken = new AccessToken(
                activity.getString(R.string.access_token),
                activity.getString(R.string.facebook_app_id),activity.getString(R.string.user_id), null, null, null,null, null);
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/118551474822691",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        Log.d("NewsResponse : ", response.getRawResponse());
                        Gson gson = new Gson();
                        NewsResponse newsResponse = gson.fromJson(response.getRawResponse(), NewsResponse.class);
//                        Log.d("NewsResponse : ", newsResponse.getName());
                        if (newsResponse!=null)
                            listener.result(newsResponse);
                        else
                            listener.failure("error");
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,picture,feed{picture,link,story,message,created_time,updated_time}");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
