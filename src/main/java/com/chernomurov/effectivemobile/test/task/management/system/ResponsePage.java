package com.chernomurov.effectivemobile.test.task.management.system;

import java.util.Set;

public record ResponsePage(long pageNumber,
                           Set<Object> objects)
{}
