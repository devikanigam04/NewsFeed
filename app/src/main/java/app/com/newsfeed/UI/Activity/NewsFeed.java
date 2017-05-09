package app.com.newsfeed.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import app.com.newsfeed.Data.MVP_Model.NewsModel;
import app.com.newsfeed.Data.Pojo.Datum;
import app.com.newsfeed.Data.Pojo.NewsResponse;
import app.com.newsfeed.R;
import app.com.newsfeed.UI.MVP_Contracter.NewsContracter;
import app.com.newsfeed.UI.MVP_Presenter.NewsPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeed extends AppCompatActivity implements NewsContracter.View {

    NewsContracter.Presenter presenter;
    @BindView(R.id.rvNews)
    RecyclerView rvNews;
    @BindView(R.id.ivDisplayPic)
    ImageView ivDisplayPic;
    @BindView(R.id.tvName)
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);

        presenter = new NewsPresenter(this, new NewsModel(this));
        presenter.getData();

        rvNews.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void failure(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void result(NewsResponse newsResponse) {
        if (newsResponse != null) {
            // loading album cover using Glide library
            Glide.with(getApplicationContext())
                    .load(newsResponse.getPicture().getData().getUrl())
                    .into(ivDisplayPic);

            tvName.setText(newsResponse.getName());
            List<Datum> list = newsResponse.getFeed().getData();
            if (list.size()>0){
                rvNews.setAdapter(new NewsAdapter(this,list));
            }
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder>{

        private  List<Datum> datumList;
        private Context context;

        NewsAdapter(Context context, List<Datum> datumList){
            this.context = context;
            this.datumList = datumList;
        }

        class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.tvStory) TextView tvStory;
            @BindView(R.id.tvMessage) TextView tvMessage;
            @BindView(R.id.tvLink) TextView tvLink;
            @BindView(R.id.tvTime) TextView tvTime;
            @BindView(R.id.ivPicture) ImageView ivPicture;

            Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item, parent, false);

            return new Holder(itemView);
        }

        @Override
        public void onBindViewHolder(NewsAdapter.Holder holder, int position) {
            final Datum datum = datumList.get(position);
            holder.tvStory.setText(datum.getStory());
            holder.tvMessage.setText(datum.getMessage());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date=null;
            try {
                date = formatter.parse(datum.getUpdatedTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm aa");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            String date_parsed = formatter.format(date);
            if (date_parsed != null)
                Log.d("Date :" ,date_parsed);

            holder.tvTime.setText(date_parsed);

            if (datum.getLink() != null) {
                holder.tvLink.setVisibility(View.VISIBLE);
                holder.tvLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(datum.getLink()));
                        startActivity(i);
                    }
                });
            }

            if (datum.getPicture() != null) {
                Glide.with(context).load(datum.getPicture()).into(holder.ivPicture);
                holder.ivPicture.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return datumList.size();
        }
    }
}
