package com.prashant.employee.dagger;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    Retrofit getRetrofit();
}
