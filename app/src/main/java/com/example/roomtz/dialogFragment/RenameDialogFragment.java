package com.example.roomtz.dialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.roomtz.R;


public class RenameDialogFragment extends DialogFragment {

    private static final String TAG = "logcat";

    private IRename iRename;

    private EditText mInput;
    private TextView mActionOk, mActionCancel;
    private String mName;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_my_custom, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mInput = view.findViewById(R.id.input);
        mInput.setText("" + mName);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = mInput.getText().toString();
                if(!input.equals("")){
                    iRename.renameName(input);
                }


                getDialog().dismiss();
            }
        });

        return view;

    }
    public void setName(String name){
        mName = name;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                //do your stuff
                if(!mInput.getText().toString().equals(mName)){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
                    dialog.setMessage("  ");
                    dialog.setTitle(" Save Change");
                    dialog.setPositiveButton("Save Change",
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    String input = mInput.getText().toString();
                                    if(!input.equals("")){
                                        iRename.renameName(input);
                                        getDialog().dismiss();

                                    }
                                }
                            });
                    dialog.setNegativeButton("revert",new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog=dialog.create();
                    alertDialog.show();
                } else {
                    super.onBackPressed();
                }
            }
        };
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            iRename = (IRename) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implements MyDialogFragment");
        }
    }
        public interface IRename{
        void renameName(String rename);
    }

}
