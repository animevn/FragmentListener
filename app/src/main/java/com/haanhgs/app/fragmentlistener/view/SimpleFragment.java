package com.haanhgs.app.fragmentlistener.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haanhgs.app.fragmentlistener.R;
import com.haanhgs.app.fragmentlistener.model.Status;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleFragment extends Fragment {


    private static final String CHOICE = "choice";
    @BindView(R.id.rbnYes)
    RadioButton rbnYes;
    @BindView(R.id.rbnNo)
    RadioButton rbnNo;
    @BindView(R.id.rgFragment)
    RadioGroup rgFragment;
    @BindView(R.id.tvFragment)
    TextView tvFragment;
    private StatusDidChange delegate;
    private int rbChoice = Status.None.state;

    public static SimpleFragment getInstance(int choice) {
        SimpleFragment simpleFragment = new SimpleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CHOICE, choice);
        simpleFragment.setArguments(bundle);
        return simpleFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof StatusDidChange) {
            delegate = (StatusDidChange) context;
        } else {
            throw new ClassCastException(
                    context.toString() + getResources().getString(R.string.exception_message)
            );
        }
    }

    private void handleRadioGroup() {
        if (getArguments() != null && getArguments().containsKey(CHOICE)) {
            rbChoice = getArguments().getInt(CHOICE);
            if (rbChoice == Status.Yes.state) {
                rgFragment.check(R.id.rbnYes);
            } else if (rbChoice == Status.No.state) {
                rgFragment.check(R.id.rbnNo);
            }
            delegate.onChange(rbChoice);
        }
    }

    private void handleRadioButtons() {
        rgFragment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbnYes){
                tvFragment.setText(getResources().getString(R.string.yes_message));
                rbChoice = Status.Yes.state;
            }else if (checkedId == R.id.rbnNo){
                tvFragment.setText(getResources().getString(R.string.no_message));
                rbChoice = Status.No.state;
            }
            delegate.onChange(rbChoice);
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        handleRadioGroup();
        handleRadioButtons();
        return view;
    }

}
