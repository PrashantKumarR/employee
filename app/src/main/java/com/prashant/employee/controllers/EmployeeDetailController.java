package com.prashant.employee.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.google.android.material.snackbar.Snackbar;
import com.prashant.employee.Employee;
import com.prashant.employee.EmployeeRepository;
import com.prashant.employee.R;
import com.prashant.employee.databinding.EmployeeDetailBinding;
import com.prashant.employee.util.BundleBuilder;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Delete this employee record?")
                            .setTitle("Delete Record")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EmployeeRepository.getInstance().deleteEmployee(employee).subscribe(observer);
                                }
                            }).setNegativeButton("No", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }

    Observer observer = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object o) {

        }

        @Override
        public void onError(Throwable e) {
            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Failed to delete employee record.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            snackbar.show();
        }

        @Override
        public void onComplete() {
            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Employee deleted.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            snackbar.show();
        }
    };
}
