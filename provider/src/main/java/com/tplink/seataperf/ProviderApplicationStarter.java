/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */

package com.tplink.seataperf;

import com.tplink.seataperf.action.service.DeviceUserTransactionService;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProviderApplicationStarter {

    public static void main(String[] args) throws Exception {

        loadConfigFiles();


        ApplicationContext ctx = SpringApplication.run(ProviderApplicationStarter.class, args);

        //DeviceUserTransactionService deviceUserTransactionService = ctx.getBean(DeviceUserTransactionService.class);
        //deviceUserTransactionService.addDeviceUser();

        System.out.println("The seata-perf-test-provider service started");
    }


    public static void loadConfigFiles() throws Exception {
        System.out.println("Loading config files...");
        String confDirString =
            System.getProperty("conf.dir", "src" + File.separator + "main" + File.separator + "resources");
        final File confDir = new File(confDirString);
        if (!confDir.exists()) {
            throw new RuntimeException("Conf directory " + confDir.getAbsolutePath() + "does not exist.");
        }

        // add class loader
        final URL[] urls = new URL[]{confDir.toURI().toURL()};

        ClassLoader loader =
            java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
                }
            });

        Thread.currentThread().setContextClassLoader(loader);
        System.out.println("Loaded config files ok.");
    }
}