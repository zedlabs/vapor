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
                .applicationId("ubo7R1EXmYaB7IWD1L65IfjbQoKd5aEbpy2hnWdf")
                // if defined
                .clientKey("1Z6nW9no1rQoICHA3dOhNu8bCBB4sULZr80bs7cH")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
