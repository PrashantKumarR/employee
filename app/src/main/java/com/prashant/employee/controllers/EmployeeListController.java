package com.prashant.employee.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.prashant.employee.Employee;
import com.prashant.employee.EmployeeAdapter;
import com.prashant.employee.R;
import com.prashant.employee.databinding.EmployeeListBinding;
import com.prashant.employee.util.BundleBuilder;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListController extends BaseController {
    private static final String TAG = EmployeeListController.class.getSimpleName();
    private static final String KEY_SELECTED_INDEX = "MasterDetailListController.selectedIndex";
    private static final String KEY_EMPLOYEE_LIST = "MasterDetailListController.employeeList";

    EmployeeListBinding binding;
    List<Employee> employees;
    EmployeeAdapter employeeAdapter;
    private boolean twoPaneView;
    private int selectedIndex;

    public EmployeeListController(List<Employee> employees) {
        this(new BundleBuilder(new Bundle()).putParcelableArrayList(KEY_EMPLOYEE_LIST, new ArrayList<>(employees)).build());
    }

    public EmployeeListController(Bundle bundle) {
        super(bundle);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = EmployeeListBinding.inflate(inflater, container, false);
        binding.listEmployees.setHasFixedSize(true);
        binding.listEmployees.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        employees = getArgs().getParcelableArrayList(KEY_EMPLOYEE_LIST);
        employeeAdapter = new EmployeeAdapter(employees, employeeClickedListener);
        binding.listEmployees.setAdapter(employeeAdapter);
        binding.fabAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouter().pushController(RouterTransaction.with(new EmployeeController(new Employee()))
                        .pushChangeHandler(new HorizontalChangeHandler())
                        .popChangeHandler(new HorizontalChangeHandler()));
            }
        });
        twoPaneView = (binding.detailContainer != null);

        return binding.getRoot();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_SELECTED_INDEX, selectedIndex);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX);
    }

    @Override
    protected String getTitle() {
        return "Employees";
    }

    EmployeeAdapter.ItemClickedListener employeeClickedListener = new EmployeeAdapter.ItemClickedListener() {
        @Override
        public void onItemClicked(int position) {
            onRowSelected(position);
        }
    };

    void onRowSelected(int index) {
        selectedIndex = index;

        Employee employee = employees.get(selectedIndex);
        EmployeeDetailController controller = new EmployeeDetailController(employee);

        if (twoPaneView) {
            getChildRouter(binding.detailContainer).setRoot(RouterTransaction.with(controller));
        } else {
            getRouter().pushController(RouterTransaction.with(controller)
                    .pushChangeHandler(new HorizontalChangeHandler())
                    .popChangeHandler(new HorizontalChangeHandler()));
        }
    }

}
