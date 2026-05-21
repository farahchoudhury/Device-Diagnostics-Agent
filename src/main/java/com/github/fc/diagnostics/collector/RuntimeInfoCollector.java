package com.github.fc.diagnostics.collector;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RuntimeInfoCollector {

    public Map<String, Object> collect() {

        Map<String, Object> map =
                new LinkedHashMap<>();

        Runtime runtime = Runtime.getRuntime();

        map.put("availableProcessors",
                runtime.availableProcessors());

        map.put("maxMemoryMB",
                runtime.maxMemory() / 1024 / 1024);

        map.put("freeMemoryMB",
                runtime.freeMemory() / 1024 / 1024);

        map.put("totalMemoryMB",
                runtime.totalMemory() / 1024 / 1024);

        OperatingSystemMXBean osBean =
                ManagementFactory.getOperatingSystemMXBean();

        map.put("systemLoadAverage",
                osBean.getSystemLoadAverage());

        MemoryMXBean memoryBean =
                ManagementFactory.getMemoryMXBean();

        map.put("heapUsedMB",
                memoryBean.getHeapMemoryUsage().getUsed()
                        / 1024 / 1024);

        map.put("heapMaxMB",
                memoryBean.getHeapMemoryUsage().getMax()
                        / 1024 / 1024);

        return map;
    }
}
