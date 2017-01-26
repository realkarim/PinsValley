package dagger;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Karim Mostafa on 1/26/17.
 */
@Module
public class LibModule{

    @Provides
    Handler providesHandlerWithMainLooper(){
        return new Handler(Looper.getMainLooper());
    }

}
