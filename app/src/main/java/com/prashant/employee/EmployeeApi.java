package com.prashant.employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeApi {
    @GET("/api/v1/employees")
    Observable<List<Employee>> getEmloyeesList();

    @GET("/api/v1/employees/{id}")
    Observable<Employee> getEmloyeeById(@Path("id") String id);

    @POST("/api/v1/create")
    Observable<NewEmployee> createEmployee(@Body NewEmployee employee);

    @PUT("/api/v1/update/{id}")
    Observable<NewEmployee> updateEmployee(@Path("id") long id, @Body NewEmployee employee);

    @DELETE("/api/v1/update/{id}")
    Observable<Employee> deleteEmployee(@Path("id") String id);
}
