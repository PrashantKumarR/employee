package com.prashant.employee.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.prashant.employee.Employee;
import com.prashant.employee.R;
import com.prashant.employee.databinding.EmployeeDetailBinding;
import com.prashant.employee.util.BundleBuilder;

public class EmployeeDetailController extends BaseController implements View.OnClickListener {
    EmployeeDetailBinding binding;
    Employee employee;

    private static final String TAG = EmployeeDetailController.class.getSimpleName();
    private static final String KEY_EMPLOYEE = "EmployeeDetailController.employee";

    public EmployeeDetailController(Employee employee) {
        this(new BundleBuilder(new Bundle()).putParcelable(KEY_EMPLOYEE, employee).build());
    }

    public EmployeeDetailController(Bundle bundle) {
        super(bundle);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = EmployeeDetailBinding.inflate(inflater, container, false);
        employee = getArgs().getParcelable(KEY_EMPLOYEE);
        binding.setEmployee(employee);
        binding.btnDeleteEmployee.setOnClickListener(this);
        binding.btnEditEmployee.setOnClickListener(this);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    protected String getTitle() {
        return "Employee-" + employee.getId();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getTag().toString().toLowerCase()) {
                case "edit-employee":
                    getRouter().pushController(RouterTransaction.with(new EmployeeController(employee))
                            .pushChangeHandler(new HorizontalChangeHandler())
                            .popChangeHandler(new HorizontalChangeHandler()));
                    break;
                case "delete-employee":
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }
}
