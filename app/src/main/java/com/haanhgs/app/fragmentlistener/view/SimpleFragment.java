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
import com.haanhgs.app.fragmentlistener.viewmodel.MyViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleFragment extends Fragment {

    @BindView(R.id.tvFragment)
    TextView tvFragment;
    @BindView(R.id.rbnYes)
    RadioButton rbnYes;
    @BindView(R.id.rbnNo)
    RadioButton rbnNo;
    @BindView(R.id.rgFragment)
    RadioGroup rgFragment;

    private FragmentActivity activity;
    private MyViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        initViewModel();
        initRadioGroup();
        handleRadioGroup();
        return view;
    }

    public void initViewModel(){
        viewModel = new ViewModelProvider(activity).get(MyViewModel.class);
        viewModel.getLiveData().observe(this, model -> {
            if (model.getStatus() == Status.Yes){
                tvFragment.setText(getResources().getString(R.string.yes_message));
            }else if (model.getStatus() == Status.No){
                tvFragment.setText(getResources().getString(R.string.no_message));
            }
            rgFragment.refreshDrawableState();
        });
    }

    public void initRadioGroup(){
        if (viewModel.getLiveData().getValue() != null){
            Status status = viewModel.getLiveData().getValue().getStatus();
            if (status == Status.Yes){
                rgFragment.check(R.id.rbnYes);
            }else if (status == Status.No){
                rgFragment.check(R.id.rbnNo);
            }
        }
    }

    public void handleRadioGroup(){
        rgFragment.setOnCheckedChangeListener((group, checkId)->{
            if (checkId == R.id.rbnYes){
                viewModel.setStatus(Status.Yes);
            }else if (checkId == R.id.rbnNo){
                viewModel.setStatus(Status.No);
            }
        });
    }



}
