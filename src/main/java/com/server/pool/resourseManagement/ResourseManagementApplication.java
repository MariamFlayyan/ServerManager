////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// File: ResourceManagementApplication
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Author: Mariam flayyan
//
// Nokia - Confidential
// Do not use, distribute, or copy without consent of Nokia.
// Copyright (c) 2020 Nokia. All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.server.pool.resourseManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ResourseManagementApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ResourseManagementApplication.class, args);
    }

}
