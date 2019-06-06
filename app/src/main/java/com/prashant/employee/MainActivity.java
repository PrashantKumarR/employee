package com.prashant.employee;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.prashant.employee.controllers.HomeController;
import com.prashant.employee.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ActionBarProvider{

    private static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;

    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        router = Conductor.attachRouter(this, binding.controllerContainer, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new HomeController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
