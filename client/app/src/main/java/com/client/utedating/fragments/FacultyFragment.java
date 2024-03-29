package com.client.utedating.fragments;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.client.utedating.R;
import com.client.utedating.activities.InitialActivity;
import com.client.utedating.models.User;
import com.client.utedating.utils.MySharedPreferences;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FacultyFragment extends Fragment {
    ChipGroup chipGroup;
    Chip chip;
    Button buttonSubmitFaculty;
    TextView textViewStep;
    InitialActivity initialActivity;
    public FacultyFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_falcuty, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialActivity = (InitialActivity) getActivity();
        textViewStep = view.findViewById(R.id.textViewStep);
        Shader shader = new LinearGradient(0,0,0,textViewStep.getLineHeight(),
                getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent), Shader.TileMode.REPEAT);
        textViewStep.getPaint().setShader(shader);
        chipGroup = view.findViewById(R.id.chipGroupFaculty);
        buttonSubmitFaculty = view.findViewById(R.id.buttonSubmitFaculty);
        buttonSubmitFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedChipId = chipGroup.getCheckedChipId();
                chip = view.findViewById(checkedChipId);

                if(chip == null)
                {
                    Toast.makeText(initialActivity, "Bạn chưa chọn khoa", Toast.LENGTH_LONG).show();
                    return;
                }
                User user = MySharedPreferences.getUserInfo(getActivity(),"user");

                initialActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, InterestFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();

                user.setFaculty(chip.getText().toString());
                MySharedPreferences.putUserInfo(getActivity(),"user", user);
                Log.e("TAG", user.toString());
            }
        });


    }
}