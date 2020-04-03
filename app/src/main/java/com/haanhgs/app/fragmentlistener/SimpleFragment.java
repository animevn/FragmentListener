package com.haanhgs.app.fragmentlistener;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.haanhgs.app.fragmentlistener.Status.No;
import static com.haanhgs.app.fragmentlistener.Status.None;
import static com.haanhgs.app.fragmentlistener.Status.Yes;

public class SimpleFragment extends Fragment {

    private static final String CHOICE = "choice";
    @BindView(R.id.tvFragment)
    TextView tvFragment;
    @BindView(R.id.rbnYes)
    RadioButton rbnYes;
    @BindView(R.id.rbnNo)
    RadioButton rbnNo;
    @BindView(R.id.rgFragment)
    RadioGroup rgFragment;
    @BindView(R.id.lnFragment)
    LinearLayout lnFragment;


    private int rbChoice = None.state;
    private StatusChanged listener;

    public static SimpleFragment instance(int choice) {
        SimpleFragment simpleFragment = new SimpleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CHOICE, choice);
        simpleFragment.setArguments(bundle);
        return simpleFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //one way to set listner but the activity must implement StatusChanged interface
        if (context instanceof StatusChanged) {
            listener = (StatusChanged) context;
        } else {
            throw new ClassCastException(
                    context.toString() + getResources().getString(R.string.exception_message)
            );
        }
    }

    private void setupRadioGroup(){
        if (getArguments() != null && getArguments().containsKey(CHOICE)){
            rbChoice = getArguments().getInt(CHOICE);
            if (rbChoice == Yes.state){
                rgFragment.check(R.id.rbnYes);
            }else if (rbChoice == No.state){
                rgFragment.check(R.id.rbnNo);
            }
        }
    }

    private void handleRadioButtons(){
        rgFragment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbnYes){
                tvFragment.setText(getResources().getString(R.string.yes_message));
                rbChoice = Yes.state;
            }else if (checkedId == R.id.rbnNo){
                tvFragment.setText(getResources().getString(R.string.no_message));
                rbChoice = No.state;
            }else {
                rbChoice = None.state;
            }
            listener.onRadioButtonStatus(rbChoice);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        setupRadioGroup();
        handleRadioButtons();
        return view;
    }
}
