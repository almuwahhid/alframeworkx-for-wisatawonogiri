package lib.gmsframeworkx.Activity;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import lib.gmsframeworkx.SuperUser.RequestHandler;
import lib.gmsframeworkx.R;


/**
 * Created by root on 2/26/18.
 */

public class ActivityGeneral extends AppCompatActivity {

    protected Context getContext(){
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_stay);
        Log.d("SHIT", "onCreate: ");
    }


    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.pull_stay, R.anim.push_out_right);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                RequestHandler.getInstance().cancelPendingRequest();
                this.onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        RequestHandler.getInstance().cancelPendingRequest();
        super.onBackPressed();
    }
}
