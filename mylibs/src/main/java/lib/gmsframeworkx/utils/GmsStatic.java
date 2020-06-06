package lib.gmsframeworkx.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.snackbar.Snackbar;
import androidx.cardview.widget.CardView;

import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import lib.gmsframeworkx.R;

import static lib.gmsframeworkx.utils.GmsConstant.SP_NOTATION;


/**
 * Created by root on 3/9/18.
 */

public class GmsStatic {

    public static SharedPreferences sp;


    public interface OnClickListener{
        void onClick(View v);
    }

    public interface OnEventConfirm{
        void onEventTrue();
        void onEventFalse();
    }

    public interface OnEventChange{
        void onChange();
    }

    static Dialog dialog_loading;
    static Dialog dialog_alert;
    static Dialog dialog_pick_photo;

    static CardView card_gallery;
    static CardView card_camera;
    static LinearLayout lay_photo;

    public interface OnImagePicker{
        void setOnPhotoPick(View v);
        void setOnGalleryPick(View v);
    }


//    static Dialog dialog_pick_photo;

    public static void showLoadingDialog(Context context, int logo){
        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCanceledOnTouchOutside(false);
        dialog_loading.setCancelable(true);
        dialog_loading.setContentView(R.layout.dialog_loading_default);
        final ImageView imgLogo = dialog_loading.findViewById(R.id.logo);
        imgLogo.setImageResource(logo);
//        dialog_loading.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_bg_white));
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_loading.show();
    }

    public static void pickImage(Context context, final OnImagePicker onImagePicker){
        dialog_pick_photo = new Dialog(context);

        dialog_pick_photo.getWindow().getAttributes().windowAnimations = R.style.DialogBottomAnimation;
        dialog_pick_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_pick_photo.setContentView(R.layout.dialog_pick_image);
        dialog_pick_photo.setCanceledOnTouchOutside(true);
        dialog_pick_photo.setCancelable(true);
        dialog_pick_photo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_pick_photo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_pick_photo.setCancelable(true);
        Window window = dialog_pick_photo.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        card_gallery = dialog_pick_photo.findViewById(R.id.card_gallery);
        card_camera = dialog_pick_photo.findViewById(R.id.card_camera);
        lay_photo = dialog_pick_photo.findViewById(R.id.lay_photo);
        lay_photo.setLayoutParams(new FrameLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));

        card_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_pick_photo.dismiss();
                onImagePicker.setOnGalleryPick(v);
            }
        });
        card_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_pick_photo.dismiss();
                onImagePicker.setOnPhotoPick(v);
            }
        });

        dialog_pick_photo.show();
    }

    private static void initAlert(Context context){
        dialog_alert = new Dialog(context);
        dialog_alert.getWindow().getAttributes().windowAnimations = R.style.DialogAnimHorizontal;
        dialog_alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_alert.setContentView(R.layout.dialog_alert);
        dialog_alert.setCanceledOnTouchOutside(true);
        dialog_alert.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog_alert.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /*public static void showAlertWarning(final Context context, String title, String content, String button_content, final OnClickListener onClickListener){
        initAlert(context);
        ImageView alert_img = dialog_alert.findViewById(R.id.alert_img);
        TextView alert_title = dialog_alert.findViewById(R.id.alert_title);
        TextView alert_content = dialog_alert.findViewById(R.id.alert_content);
        TextView alert_content_btn = dialog_alert.findViewById(R.id.alert_content_button);
        LinearLayout alert_lay_button = dialog_alert.findViewById(R.id.alert_lay_button);

        alert_lay_button.setBackgroundColor(ContextCompat.getColor(context, R.color.red_400));
        alert_img.setImageResource(android.R.drawable.ic_dialog_alert);
        alert_img.setColorFilter(ContextCompat.getColor(context, R.color.red_400));

        alert_title.setText(title);
        alert_content.setText(content);
        alert_content_btn.setText(button_content);
        alert_lay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null){
                    dismissAlertDialog(context);
                    onClickListener.onClick(v);
                }else {
                    dismissAlertDialog(context);
                }
            }
        });

        dialog_alert.show();
    }*/

    public static void showAlertCostum(final Context context, String title, String content, String button_content, int image_resurce, final OnClickListener onClickListener){
        initAlert(context);
        ImageView alert_img = dialog_alert.findViewById(R.id.alert_img);
        TextView alert_title = dialog_alert.findViewById(R.id.alert_title);
        TextView alert_content = dialog_alert.findViewById(R.id.alert_content);
        TextView alert_content_btn = dialog_alert.findViewById(R.id.alert_content_button);
        CardView alert_lay_button = dialog_alert.findViewById(R.id.alert_lay_button);

//        alert_lay_button.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_400));
        alert_img.setImageResource(image_resurce);
//        alert_img.setColorFilter(ContextCompat.getColor(context, R.color.blue_400));

        alert_title.setText(title);
        alert_content.setText(content);
        alert_content_btn.setText(button_content);
        alert_lay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null){
                    dismissAlertDialog(context);
                    onClickListener.onClick(v);
                }else {
                    dismissAlertDialog(context);
                }
            }
        });

        dialog_alert.show();
    }

    public static void dismissAlertDialog(Context context) {
        if (dialog_alert!=null && dialog_alert.isShowing()){
            dialog_alert.dismiss();
        }
    }

    public static void hideLoadingDialog(Context context) {
        if (dialog_loading!=null && dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
    }

    public static String DateInMilis() {
        Calendar cal = Calendar.getInstance();
        String calender = String.valueOf(cal.get(Calendar.MILLISECOND));
        return calender;
    }

    public static void SnackLong(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    public static void SnackShort(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void ToastLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void ToastShort(Context context, String text) {
        if(context!=null){
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showGoTroError(Context context, VolleyError error){
        if (error instanceof NetworkError) {
            ToastShort(context, context.getResources().getString(R.string.txt_network_error));
        } else if (error instanceof ServerError) {
            ToastShort(context,context.getResources().getString(R.string.txt_server_error));
        } else if (error instanceof NoConnectionError) {
            ToastShort(context,context.getResources().getString(R.string.txt_NoConnection));
        } else if (error instanceof TimeoutError) {
            ToastShort(context,context.getResources().getString(R.string.txt_server_time_out));
        }
    }

    public static String monthName(int month) {
        String m = "";
        switch (month) {
            case 0:
                m = "Januari";
                break;
            case 1:
                m = "Februari";
                break;
            case 2:
                m = "Maret";
                break;
            case 3:
                m = "April";
                break;
            case 4:
                m = "Mei";
                break;
            case 5:
                m = "Juni";
                break;
            case 6:
                m = "Juli";
                break;
            case 7:
                m = "Agustus";
                break;
            case 8:
                m = "September";
                break;
            case 9:
                m = "Oktober";
                break;
            case 10:
                m = "November";
                break;
            case 11:
                m = "Desember";
                break;
        }
        return m;
    }

    public static void setSPBoolean(Context context, String constant, Boolean val){
        sp = context.getSharedPreferences(SP_NOTATION, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(constant, val);
        editor.commit();
    }

    public static void setSPString(Context context, String constant, String val){
        sp = context.getSharedPreferences(SP_NOTATION, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(constant, val);
        editor.commit();
    }

    public static String getSPString(Context context, String from) {
        sp = context.getSharedPreferences(SP_NOTATION, 0);
        return sp.getString(from, "");
    }

    public static void removeSPString(Context context, String to) {
        sp = context.getSharedPreferences(SP_NOTATION, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(to);
        editor.commit();
    }

    public static boolean getSPBoolean(Context context, String from) {
        if(context!=null){
            sp = context.getSharedPreferences(SP_NOTATION, 0);
            return sp.getBoolean(from, false);
        }else{
            return false;
        }
    }

    public static boolean getSPBooleanTrue(Context context, String from) {
        if(context!=null){
            sp = context.getSharedPreferences(SP_NOTATION, 0);
            return sp.getBoolean(from, true);
        }else{
            return true;
        }
    }

    public static boolean isFormValid(Context context, View view, List<Integer> forms){
        boolean isValid = true;
        for(int id: forms)
        {
            EditText et = view.findViewById(id);
            if(TextUtils.isEmpty(et.getText().toString()))
            {
                et.setError("Tidak boleh kosong");
                isValid = false;
            }
        }
        return isValid;
    }

    public static boolean isFormValid(Context context, View view, List<Integer> forms, String error){
        boolean isValid = true;
        for(int id: forms)
        {
            EditText et = view.findViewById(id);
            if(TextUtils.isEmpty(et.getText().toString()))
            {
                et.setError(error);
                isValid = false;
            }
        }
        return isValid;
    }

    public static String convertToBase64(Bitmap bitmap){
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] byteArray = bao.toByteArray();
        return Base64Util.encodeBytes(byteArray);
    }

    public static Bitmap createBitmapFromLayout(View tv) {
        Bitmap b = Bitmap.createBitmap(tv.getMeasuredWidth(), tv.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate((-tv.getScrollX()), (-tv.getScrollY()));
        tv.draw(c);
        return b;
    }

    public static void openFile(Context context, File url) throws IOException {
        // Create URI
        File file = url;
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "/");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
