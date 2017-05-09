package app.com.newsfeed.UI.MVP_Contracter;


import app.com.newsfeed.Data.Pojo.NewsResponse;

public interface NewsContracter {

    interface Presenter{
        void getData();
    }

    interface View{

        void failure(String error);

        void result(NewsResponse newsResponse);
    }

    interface Model{
        void getData(Listener listener);
        interface Listener{
            void result(NewsResponse newsResponse);
            void failure(String error);
        }
    }
}
