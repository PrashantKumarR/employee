package com.prashant.employee.controllers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.prashant.employee.Employee;
import com.prashant.employee.EmployeeRepository;
import com.prashant.employee.R;
import com.prashant.employee.databinding.ControllerHomeBinding;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomeController extends BaseController {

    private static final String TAG = HomeController.class.getSimpleName();
    EmployeeRepository employeeRepository;

    ControllerHomeBinding binding;
    List<Employee> employees;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = ControllerHomeBinding.inflate(inflater, container, false);
        if (employeeRepository == null) {
            employeeRepository = EmployeeRepository.getInstance();
        }
        createEmployeesObservable();
        return binding.getRoot();
    }

    public void createEmployeesObservable() {
        employeeRepository.getEmployeesObservable()
                .subscribe(new Observer<List<Employee>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        binding.determinateBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(List<Employee> employees1) {
                        employees = employees1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Number of employees: " + employees.size());
                        binding.determinateBar.setVisibility(View.GONE);

                        getRouter().pushController(RouterTransaction.with(new EmployeeListController(employees))
                                .pushChangeHandler(new HorizontalChangeHandler())
                                .popChangeHandler(new HorizontalChangeHandler()));
                    }
                });
    }
}