////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// File: ServerDataAccessService
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Repository("postgres")
public class ServerDataAccessService implements ServerDAO
{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ServerDataAccessService(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * This Method is Used to insert new server to db
     *
     * @param aInID id for server
     * @param aInServer server to be added
     * @return CompletableFuture.completedFuture(row)
     * @throws InterruptedException
     */
    @Override
    @Async
    public CompletableFuture<Integer> insertServer(UUID aInID, Server aInServer)
            throws InterruptedException
    {
        final String sql =
                "insert into server (id, name, freesize) values(?,?,?)";

        Thread.sleep(20 * 1000);
        int row = jdbcTemplate.update(sql, aInID, aInServer.getName(),
                aInServer.getFreeSize());
        return CompletableFuture.completedFuture(row);
    }

    /**
     * This Method is Used to list all servers in db
     *
     * @return CompletableFuture.completedFuture(servers)
     */
    @Override
    @Async
    public CompletableFuture<List<Server>> listAllServers()
    {
        final String sql = "SELECT id, name, freesize from server";
        List<Server> servers = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            int freeSize = Integer.valueOf(resultSet.getString("freesize"));
            return new Server(id, name, freeSize);
        });
        return CompletableFuture.completedFuture(servers);
    }

    /**
     * This Method is Used to select server from db by its ID
     *
     * @param aInID id of searched server
     * @return CompletableFuture.completedFuture(Optional.ofNullable ( server))
     */
    @Override
    @Async
    public CompletableFuture<Optional<Server>> selectServerById(UUID aInID)
    {
        final String sql =
                "SELECT id, name , freesize from server where id = ?";
        Server server = jdbcTemplate
                .queryForObject(sql, new Object[]{aInID}, (resultSet, i) -> {
                    String name = resultSet.getString("name");
                    int freeSize =
                            Integer.valueOf(resultSet.getString("freesize"));
                    return new Server(aInID, name, freeSize);
                });
        return CompletableFuture.completedFuture(Optional.ofNullable(server));
    }

    /**
     * This Method is Used to update server data in db given its ID
     *
     * @param aInID id for server
     * @param aInServer server to be updated
     * @param aInSize server new size
     * @return CompletableFuture.completedFuture(row)
     */
    @Override
    @Async
    public CompletableFuture<Integer> updateServerById(UUID aInID,
            Server aInServer, int aInSize)
    {
        System.out.println("here");
        int free = aInServer.getFreeSize() - aInSize;
        final String sql = "update server set freesize = ? where id = ?";
        int row = jdbcTemplate.update(sql, free, aInID);
        return CompletableFuture.completedFuture(row);
    }

    /**
     * This Method is Used to reserve a size in some db server size if
     * available, or if not to create a new server in db and reserve the needed
     * size
     *
     * @param aInID id for server
     * @param aInServer server to be added
     * @return updateServerById(randId, newServer, aInSize)
     * @throws InterruptedException and ExecutionException
     */
    @Override
    @Async
    public synchronized CompletableFuture<Integer> getServerSize(int aInSize)
            throws InterruptedException, ExecutionException
    {
        List<Server> servers = listAllServers().get();
        Server availableServer = servers.stream()
                .filter(server -> server.getFreeSize() >= aInSize).findFirst()
                .orElse(null);
        if (availableServer != null)
        {
            return updateServerById(availableServer.getId(), availableServer,
                    aInSize);
        }
        UUID randId = UUID.randomUUID();
        String name = "server" + randId.toString();
        Server newServer = new Server(randId, name, 100);
        insertServer(randId, newServer);
        return updateServerById(randId, newServer, aInSize);
    }

}
