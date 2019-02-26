package tk.zedlabs.artmedia;

import android.app.Application;

import com.parse.Parse;
 /*
 To use the parse backend
 created by - zed
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("key here")
                // if defined
                .clientKey("client key here")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
