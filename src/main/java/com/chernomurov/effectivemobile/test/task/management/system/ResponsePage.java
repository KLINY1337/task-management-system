package com.chernomurov.effectivemobile.test.task.management.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;


public record ResponsePage(int pageNumber,
                           List<Object> objects)
{
    public ResponsePage(int pageNumber, List<Object> objects) {
        this.pageNumber = pageNumber;
        this.objects = objects;
    }
}
