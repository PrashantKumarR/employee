package com.prashant.employee.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.prashant.employee.Employee;
import com.prashant.employee.EmployeeRepository;
import com.prashant.employee.NewEmployee;
import com.prashant.employee.R;
import com.prashant.employee.databinding.EditableEmployeeBinding;
import com.prashant.employee.util.BundleBuilder;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EmployeeController extends BaseController implements View.OnClickListener {
    private static final String TAG = EmployeeController.class.getSimpleName();
    private static final String KEY_EMPLOYEE = "EmployeeController.employee";
    EditableEmployeeBinding binding;
    Employee employee;

    public EmployeeController(Bundle bundle) {
        super(bundle);
    }

    public EmployeeController(Employee employee) {
        this(new BundleBuilder(new Bundle())
                .putParcelable(KEY_EMPLOYEE, employee).build());
    }

    @Override
    protected String getTitle() {
        if (employee.getId() != 0) {
            return "Edit Employee-" + employee.getId();
        } else {
            return "Create Employee";
        }
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = EditableEmployeeBinding.inflate(inflater, container, false);

        employee = getArgs().getParcelable(KEY_EMPLOYEE);
        binding.setEmployee(employee);
        binding.btnSave.setOnClickListener(this);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            switch (v.getTag().toString().toLowerCase()) {
                case "save-employee":
                    double salary = Double.parseDouble(binding.employeeSalary.getText().toString());
                    int age = Integer.parseInt(binding.employeeAge.getText().toString());
                    String name = binding.employeeName.getText().toString();
                    if (employee.getId() != 0) {
                        NewEmployee empTemp = new NewEmployee(employee.getId(), name, salary, age);
                        EmployeeRepository.getInstance().updateEmployeesObservable(empTemp).subscribe(updateObserver);
                    } else {
                        NewEmployee empTemp = new NewEmployee(name, salary, age);
                        EmployeeRepository.getInstance().createEmployeesObservable(empTemp).subscribe(updateObserver);
                    }
                    break;
            }
        }
    }

    Observer<NewEmployee> updateObserver = new Observer<NewEmployee>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(NewEmployee updatedEmployee) {
            String msg = "Employee record created.";
            if (employee.getId() != 0) {
                msg = "Employee record updated";
            }
            employee = new Employee(updatedEmployee.getId(), updatedEmployee.getName(), updatedEmployee.getSalary(), updatedEmployee.getAge());
            binding.setEmployee(employee);
            binding.executePendingBindings();

            Snackbar snackbar = Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            snackbar.show();
        }

        @Override
        public void onError(Throwable e) {
            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please try again with different name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            snackbar.show();
        }

        @Override
        public void onComplete() {

        }
    };
}
