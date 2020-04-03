package com.haanhgs.app.fragmentlistener;

import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.haanhgs.app.fragmentlistener.Status.None;

public class MainActivity extends AppCompatActivity implements StatusChanged {

    @BindView(R.id.flFragment)
    FrameLayout flFragment;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.bnOpen)
    Button bnOpen;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvArticle)
    TextView tvArticle;
    @BindView(R.id.svScroll)
    ScrollView svScroll;

    private int rbChoice = None.state;
    private boolean isFragmentOn = false;
    private static final String STATUS = "status";
    private static final String CHOICE = "choice";

    private void setFullScreenInLandscapeMode(){
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90){
            if (getSupportActionBar() != null) getSupportActionBar().hide();
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void loadInstanceState(Bundle bundle){
        if (bundle != null){
            isFragmentOn = bundle.getBoolean(STATUS);
            if (isFragmentOn) bnOpen.setText(getResources().getString(R.string.close));
            rbChoice = bundle.getInt(CHOICE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setFullScreenInLandscapeMode();
        loadInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATUS, isFragmentOn);
        outState.putInt(CHOICE, rbChoice);
    }

    //transfer data from fragment to Main
    @Override
    public void onRadioButtonStatus(int choice) {
        rbChoice = choice;
    }

    private void openFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SimpleFragment simpleFragment = SimpleFragment.instance(rbChoice);
        ft.add(R.id.flFragment, simpleFragment);
        ft.addToBackStack(null).commit();
        bnOpen.setText(getResources().getString(R.string.close));
        isFragmentOn = true;
    }

    private void closeFragment(){
        SimpleFragment fragment = (SimpleFragment)
                getSupportFragmentManager().findFragmentById(R.id.flFragment);
        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment).commit();
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
