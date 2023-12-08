package com.chernomurov.effectivemobile.test.task.management.system.util;

import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class ResponsePageUtils {

    private static final int PAGE_SIZE = 5;

    @NotNull
    public SortedSet<ResponsePage> getPaginatedObjects(List<Object> objects) {
        SortedSet<ResponsePage> paginatedObjects = new TreeSet<>(Comparator.comparing(ResponsePage::pageNumber));
        ResponsePage page;
        List<Object> pageObjects = new ArrayList<>(PAGE_SIZE);
        for (int i = 1; i <= objects.size(); i++) {
            pageObjects.add(objects.get(i - 1));
            if (i % PAGE_SIZE == 0 || i == objects.size()) {
                int pageNumber = (i - 1) / PAGE_SIZE + 1;
                page = new ResponsePage(pageNumber, pageObjects);
                paginatedObjects.add(page);
                pageObjects = new ArrayList<>(PAGE_SIZE);
            }
        }
        return paginatedObjects;
    }

    public record ResponsePage(int pageNumber, List<Object> objects) {}
}
