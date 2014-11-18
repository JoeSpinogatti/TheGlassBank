package app.com.cue;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p/>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 *
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class MainActivity extends Activity {

    /**
     * {@link CardScrollView} to use as the main content view.
     */
    private CardScrollView mCardScroller;

    /**
     * "Hello World!" {@link View} generated by {@link #buildView()}.
     */
    private View mView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mView = buildView();

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return mView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mView;
            }

            @Override
            public int getPosition(Object item) {
                if (mView.equals(item)) {
                    return 0;
                }
                return AdapterView.INVALID_POSITION;
            }
        });
        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Plays disallowed sound to indicate that TAP actions are not supported.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.DISALLOWED);
            }
        });
        setContentView(mCardScroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    /**
     * What cue is this card.
     */

    private View buildView() {
        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.TEXT);

        int count;
        try {
            URL url = new URL("http://joespinogatti.com/cuetxt/q.txt");
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();
            InputStream is = url.openStream();
            //File testDirectory = new File(
                    //Environment.getExternalStorageDirectory() + File.separator + "/Download");
            //if (!testDirectory.exists()) {
              //  testDirectory.mkdir();
            //}
            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "/Download"+ "/q.txt");
            byte data[] = new byte[1024];
            long total = 0;
            int progress = 0;
            while ((count = is.read(data)) != -1) {
                total += count;
                int progress_temp = (int) total * 100 / lenghtOfFile;
                    /*publishProgress("" + progress_temp); //only for asynctask
                    if (progress_temp % 10 == 0 && progress != progress_temp) {
                        progress = progress_temp;
                    }*/
                fos.write(data, 0, count);
            }
            is.close();
            fos.close();
        } catch (Exception e) {
            Log.e("ERROR DOWNLOADING",
                    "Unable to download" + e.getMessage());
        }

        //Find the directory for the SD Card using the API

        String sdcard = Environment.getExternalStorageDirectory() + File.separator + "/Download";

//Get the text file
        File file = new File(sdcard, "q.txt");

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        card.setText("This is cue: \n\n" + "              " + text);
        // card.setText(R.string.insert_number);
        return card.getView();
    }
}
