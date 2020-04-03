package com.haanhgs.app.fragmentlistener.view;

import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Button;
import com.haanhgs.app.fragmentlistener.R;
import com.haanhgs.app.fragmentlistener.viewmodel.MyViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.bnOpen)
    Button bnOpen;

    private MyViewModel viewModel;
    private boolean isOpen;


    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getData().observe(this, model -> {
            isOpen = model.isOpen();
            if (model.isOpen()){
                bnOpen.setText(getResources().getString(R.string.close));
            }else {
                bnOpen.setText(getResources().getString(R.string.open));
            }
        });
    }

    private void setFullScreenInLandscape(){
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90){
            if (getSupportActionBar() != null) getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setFullScreenInLandscape();
        initViewModel();
    }

    private void openFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment");
        if (fragment == null){
            SimpleFragment simpleFragment = new SimpleFragment();
            ft.add(R.id.flFragment, simpleFragment, "fragment");
            ft.commit();
        }else {
            ft.attach(fragment);
        }
    }

    private void closeFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment");
        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
    }


    @OnClick(R.id.bnOpen)
    public void onViewClicked() {
        if (isOpen){
            closeFragment();
        }else {
            openFragment();
        }
        viewModel.toggleFragment();
    }

}
