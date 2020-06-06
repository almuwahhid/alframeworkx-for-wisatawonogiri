package lib.gmsframeworkx.utils;

import android.app.Dialog;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.Window;
import android.widget.ImageView;

import lib.gmsframeworkx.R;

public class LoadingDialog {
    static Dialog dialog;

    public static void CreateDialog(Context context){
        OpenLoadingDialog(context, R.drawable.ic_android_black_24dp);
    }

    public static void CreateDialog(Context context, int logo){
        OpenLoadingDialog(context, logo);

    }

    public static void OpenLoadingDialog(Context context, int logo_img) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_loading_default);
        final ImageView imgLogo = dialog.findViewById(R.id.logo);
        imgLogo.setImageResource(logo_img);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.rounded_bg));
        dialog.show();

    }

    public static void CloseLoadingDialog(Context context) {
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }


    }
}
