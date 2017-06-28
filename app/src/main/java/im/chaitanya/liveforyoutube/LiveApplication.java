package im.chaitanya.liveforyoutube;

import android.app.Application;

import jonathanfinerty.once.Once;

/**
 * Created by chaitanya on 28-06-2017.
 */

public class LiveApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Once.initialise(this);
    }
}
