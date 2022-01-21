package com.aariyan.briefcase.Interface;


import com.aariyan.briefcase.Model.CustomerModel;

import java.util.List;

public interface CustomerListInterface {

    void customerList(List<CustomerModel> list);
    void error(String message);
}
