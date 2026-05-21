package com.github.fc.diagnostics.collector;

import com.github.fc.diagnostics.util.DeviceIdUtils;

import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SystemInfoCollector {

    public Map<String, Object> collect() {

        Map<String, Object> map =
                new LinkedHashMap<>();

        try {
            map.put("hostname",
                    InetAddress.getLocalHost()
                            .getHostName());
        } catch (Exception e) {
            map.put("hostname", "unknown");
        }

        map.put("deviceId",
                DeviceIdUtils.getDeviceId());

        map.put("os.name",
                System.getProperty("os.name"));

        map.put("os.version",
                System.getProperty("os.version"));

        map.put("os.arch",
                System.getProperty("os.arch"));

        map.put("user.home",
                System.getProperty("user.home"));

        map.put("java.version",
                System.getProperty("java.version"));

        map.put("java.vendor",
                System.getProperty("java.vendor"));

        return map;
    }
}
