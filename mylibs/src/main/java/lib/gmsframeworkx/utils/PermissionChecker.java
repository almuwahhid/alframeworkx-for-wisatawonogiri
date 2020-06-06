package lib.gmsframeworkx.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;



public class PermissionChecker {

    private final int REQUEST_MULTIPLE_PERMISSION = 100;
    private VerifyPermissionsCallback callbackMultiple;

    public void verifyPermissions(Activity activity, String[] permissions, VerifyPermissionsCallback callback) {
        String[] denyPermissions = getDenyPermissions(activity, permissions);
        if (denyPermissions.length > 0) {
            ActivityCompat.requestPermissions(activity, denyPermissions, REQUEST_MULTIPLE_PERMISSION);
            this.callbackMultiple = callback;
        } else {
            if (callback != null) {
                callback.onPermissionAllGranted();
            }
        }
    }

    private String[] getDenyPermissions(Context context, String[] permissions) {
        ArrayList<String> denyPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(permission);
            }
        }
        return denyPermissions.toArray(new String[0]);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSION:
                if (grantResults.length > 0 && callbackMultiple != null) {
                    ArrayList<String> denyPermissions = new ArrayList<>();
                    int i = 0;
                    for (String permission : permissions) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            denyPermissions.add(permission);
                        }
                        i++;
                    }
                    if (denyPermissions.size() == 0) {
                        callbackMultiple.onPermissionAllGranted();
                    } else {
                        callbackMultiple.onPermissionDeny(denyPermissions.toArray(new String[0]));
                    }
                }
                break;
        }
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface VerifyPermissionsCallback {
        void onPermissionAllGranted();

        void onPermissionDeny(String[] permissions);
    }
}
