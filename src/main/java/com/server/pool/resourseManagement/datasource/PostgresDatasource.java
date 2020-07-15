////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// File: PostgresDatasource
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

package com.server.pool.resourseManagement.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgresDatasource
{

    /**
     * This Method is Used to create a hikari datasource bean using
     * configurations in app.datasource for a postgresql
     *
     * @return DataSourceBuilder
     */
    @Bean
    @ConfigurationProperties("app.datasource")
    public HikariDataSource hikariDataSource()
    {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build()
                ;
    }
}
