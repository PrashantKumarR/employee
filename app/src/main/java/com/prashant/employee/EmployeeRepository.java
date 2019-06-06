package com.prashant.employee;

import com.prashant.employee.dagger.DaggerRetrofitComponent;
import com.prashant.employee.dagger.RetrofitComponent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class EmployeeRepository {
    public EmployeeApi employeeApi;
    private static EmployeeRepository _instance;

    private EmployeeRepository() {
        RetrofitComponent retrofitComponent = DaggerRetrofitComponent.create();
        Retrofit retrofit = retrofitComponent.getRetrofit();
        employeeApi = retrofit.create(EmployeeApi.class);
    }

    public static EmployeeRepository getInstance() {
        if (_instance == null) {
            _instance = new EmployeeRepository();
        }
        return _instance;
    }

    public Observable<List<Employee>> getEmployeesObservable() {
        return employeeApi.getEmloyeesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NewEmployee> createEmployeesObservable(NewEmployee employee) {
        return employeeApi.createEmployee(employee)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NewEmployee> updateEmployeesObservable(NewEmployee employee) {
        return employeeApi.updateEmployee(employee.getId(), employee)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Employee> deleteEmployee(Employee employee) {
        return employeeApi.deleteEmployee(employee.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
