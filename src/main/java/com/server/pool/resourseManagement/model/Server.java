
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// File: Server
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

package com.server.pool.resourseManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Server implements Serializable
{

    @Id
    private UUID id;
    private String name;
    private int freeSize;

    public Server()
    {
    }

    public Server(@JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("freesize") int freeSize)
    {
        this.id = id;
        this.name = name;
        this.freeSize = freeSize;
    }

    public Server(@JsonProperty("id") UUID id,
            @JsonProperty("name") String name)
    {
        this.id = id;
        this.name = name;
        this.freeSize = 100;
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getFreeSize()
    {
        return freeSize;
    }

    public void setFreeSize(int freeSize)
    {
        this.freeSize = freeSize;
    }

    @Override
    public String toString()
    {
        return "Server{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", freeSize=" + freeSize +
                '}';
    }
}
