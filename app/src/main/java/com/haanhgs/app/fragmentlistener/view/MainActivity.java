package com.haanhgs.app.fragmentlistener.view;

import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bnOpen)
    Button bnOpen;

    private MyViewModel viewModel;
    private boolean open = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setFullScreenInLandscape();
        initViewModel();

    }

    @SuppressWarnings("deprecation")
    private void setFullScreenInLandscape(){
        if (getDisplay() != null){
            int rotation = getDisplay().getRotation();
            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90){
                if (getSupportActionBar() != null) getSupportActionBar().hide();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    final WindowInsetsController controller = getWindow().getInsetsController();
                    if (controller != null){
                        controller.hide(WindowInsets.Type.statusBars());
                    }
                }else {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        }
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getLiveData().observe(this, model -> {
            open = model.isOpen();
            if (open){
                bnOpen.setText(getResources().getString(R.string.close));
            }else {
                bnOpen.setText(getResources().getString(R.string.open));
            }
        });
    }

    @OnClick(R.id.bnOpen)
    public void onViewClicked() {
        if (open){
            closeFragment();
        }else {
            openFragment();
        }
        viewModel.toggle();
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


}
