package app.com.newsfeed.UI.MVP_Presenter;

import app.com.newsfeed.Data.Pojo.NewsResponse;
import app.com.newsfeed.UI.MVP_Contracter.NewsContracter;

public class NewsPresenter implements NewsContracter.Presenter, NewsContracter.Model.Listener{
    private NewsContracter.View view;
    private NewsContracter.Model model;

    public NewsPresenter(NewsContracter.View view, NewsContracter.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getData() {
        model.getData(this);
    }

    @Override
    public void result(NewsResponse newsResponse) {
        view.result(newsResponse);
    }

    @Override
    public void failure(String error) {
        view.failure(error);
    }
}
