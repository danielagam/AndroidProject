package com.danielshimon.android_project.model;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class PriceDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("פרטי הנסיעה").setMessage(" כאן יחושב המחיר המומלץ לנסיעה" +"\n"+
                " זמן נסיעה משוער:" +"\n"+
                " מחיר מומלץ: .").setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
