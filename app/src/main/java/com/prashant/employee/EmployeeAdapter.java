package com.prashant.employee;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.prashant.employee.databinding.ListItemEmployeeBinding;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private static final String TAG = EmployeeAdapter.class.getSimpleName();
    List<Employee> employees;
    ItemClickedListener itemClickedListener;

    public EmployeeAdapter(List<Employee> employees) {
        this.employees = employees;
    }

    public EmployeeAdapter(List<Employee> employees, ItemClickedListener itemClickedListener) {
        this.employees = employees;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemEmployeeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_employee, parent, false);
        return new EmployeeViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ListItemEmployeeBinding binding;

        public EmployeeViewHolder(ListItemEmployeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.setEmployee(employees.get(position));
            binding.setPosition(position);
            binding.setListener(itemClickedListener);
            binding.executePendingBindings();
        }

    }

    public interface ItemClickedListener {
        void onItemClicked(int position);
    }
}
