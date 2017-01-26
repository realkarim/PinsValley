package dagger;

import android.os.Handler;

import javax.inject.Inject;

/**
 * Created by Karim Mostafa on 1/27/17.
 */

public class DependenciesProvider {

    @Inject
    Handler handler;

    public Handler getHandlerWithMainLooper(){
        return handler;
    }
}
