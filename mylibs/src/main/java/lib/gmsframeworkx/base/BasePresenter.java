package lib.gmsframeworkx.base;

import android.content.Context;

import com.google.gson.Gson;

public class BasePresenter {
    Context context;
    Gson gson;

    public BasePresenter(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }

    public Context getContext() {
        return context;
    }
}
