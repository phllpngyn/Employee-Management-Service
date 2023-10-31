package com.employeeapp.employee.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@RequiredArgsConstructor
public class PageBody{

    @NotNull(message = "page number can't be null")
    int pageNo;

    @NotNull(message = "page size can't be null")
    int pageSize;

    @NotNull(message = "sort field can't be null")
    String sortField;

    @NotNull(message = "sort direction can't be null")
    String sortDirection;
}
