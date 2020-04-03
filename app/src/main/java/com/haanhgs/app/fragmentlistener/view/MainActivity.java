package com.haanhgs.app.fragmentlistener.view;

import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Button;

import com.haanhgs.app.fragmentlistener.R;
import com.haanhgs.app.fragmentlistener.model.Status;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements StatusDidChange {

    @BindView(R.id.bnOpen)
    Button bnOpen;

    private static final String STATUS = "status";
    private static final String CHOICE = "choice";
    private boolean isFragmentOn = false;
    private int statusChoice = Status.None.state;


    @Override
    public void onChange(int choice) {
        statusChoice = choice;
    }



    private void setFullScreenInLandscape(){
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90){
            if (getSupportActionBar() != null) getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void loadInstanceState(Bundle bundle){
        if (bundle != null){
            isFragmentOn = bundle.getBoolean(STATUS);
            if (isFragmentOn) bnOpen.setText(getResources().getString(R.string.close));
            statusChoice = bundle.getInt(CHOICE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setFullScreenInLandscape();
        loadInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATUS, isFragmentOn);
        outState.putInt(CHOICE, statusChoice);
    }

    private void openFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SimpleFragment simpleFragment = SimpleFragment.getInstance(statusChoice);
        ft.add(R.id.flFragment, simpleFragment);
        ft.addToBackStack(null);
        ft.commit();
        bnOpen.setText(getResources().getString(R.string.close));
        isFragmentOn = true;
    }

    private void closeFragment(){
        SimpleFragment fragment = (SimpleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.flFragment);
        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
            bnOpen.setText(getResources().getString(R.string.open));
            isFragmentOn = false;
        }
    }

    @OnClick(R.id.bnOpen)
    public void onViewClicked() {
        if (isFragmentOn){
            closeFragment();
        }else {
            openFragment();
        }
    }

}
