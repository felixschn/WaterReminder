package com.example.waterreminder2.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.waterreminder2.IMainActivity;
import com.example.waterreminder2.R;
import com.example.waterreminder2.models.Water;
import com.example.waterreminder2.persistence.WaterRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class editwaterFragment extends DialogFragment {
    private TextView editWaterTextView;
    private EditText editWaterEditText;
    private ImageButton editWaterButton;
    private String time;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public IMainActivity iMainActivity;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editwater, container, false);
        editWaterTextView = view.findViewById(R.id.DialogHeading);
        editWaterTextView.setText("Welche Menge Wasser möchtest du hinzufügen");
        editWaterEditText = view.findViewById(R.id.DialogEditText);
        editWaterButton = view.findViewById(R.id.DialogAddButton);




        editWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: editWaterButton");
                String input = editWaterEditText.getText().toString();
                time = dateFormat.format(new Date());
                if (!input.equals("")) {
                    iMainActivity.sendInput(input, time);
                }
                getDialog().dismiss();
            }
        });
        return view;


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iMainActivity = (IMainActivity) getTargetFragment();
        }
        catch (ClassCastException e){
            Log.e(TAG, "on attach: ClassCastException : " + e.getMessage());
        }

    }
}


