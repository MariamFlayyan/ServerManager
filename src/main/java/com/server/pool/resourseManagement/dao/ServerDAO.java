////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// File: ServerDAO
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

package com.server.pool.resourseManagement.dao;

import com.server.pool.resourseManagement.model.Server;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ServerDAO
{

    CompletableFuture<Integer> insertServer(UUID id, Server server)
            throws InterruptedException;

    default CompletableFuture<Integer> insertServer(Server server)
            throws InterruptedException
    {
        UUID id = UUID.randomUUID();
        return insertServer(id, server);
    }

    CompletableFuture<List<Server>> listAllServers();

    CompletableFuture<Optional<Server>> selectServerById(UUID id);

    CompletableFuture<Integer> updateServerById(UUID id, Server server,
            int size);

    public CompletableFuture<Integer> getServerSize(int size)
            throws InterruptedException, ExecutionException;

}
