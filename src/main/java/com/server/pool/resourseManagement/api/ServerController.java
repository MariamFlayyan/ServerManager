////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// File: ServerController
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

package com.server.pool.resourseManagement.api;

import com.server.pool.resourseManagement.model.Server;
import com.server.pool.resourseManagement.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("api/v1/server")
@RestController
public class ServerController
{

    public final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService)
    {
        this.serverService = serverService;
    }

    /**
     * This Method is a rest POST method Used to insert new server to db
     *
     * @param aInID id for server
     * @param aInServer server to be added
     * @throws InterruptedException and ExecutionException
     */
    @PostMapping
    public void addServer(@RequestBody Server aInserver)
            throws ExecutionException, InterruptedException
    {
        serverService.addServer(aInserver);
    }

    /**
     * This Method is a rest GET method Used to list all servers in db
     *
     * @return serverService.getAllServers()
     */
    @GetMapping
    public List<Server> getAllServers()
            throws ExecutionException, InterruptedException
    {
        return serverService.getAllServers();
    }

    /**
     * This Method is a rest GET method Used to select server from db given it's
     * id as a path parameter
     *
     * @param aInID id of searched server
     * @return serverService.getServerById(aInID) or (null)
     */
    @GetMapping(path = "{id}")
    public Server getServerById(@PathVariable("id") UUID aInID)
            throws ExecutionException, InterruptedException
    {
        return serverService.getServerById(aInID)
                .orElse(null);
    }

    /**
     * This Method a rest PUT method that is Used to update server data in db
     * given its ID as a path param
     *
     * @param aInID id for server
     * @param aInServer server to be updated
     * @param aInSize server new size
     * @return serverService.updateServerById(aInID, aInServer, aInSize)
     */
    @PutMapping(path = "{id}")
    public int updateServerById(@PathVariable("id") UUID aInID,
            @RequestBody Server aInServer, int aInSize)
            throws ExecutionException, InterruptedException
    {
        return serverService.updateServerById(aInID, aInServer, aInSize);
    }

    /**
     * This Method is a GET method that's Used to reserve a size in some db
     * server size if available, or if not to create a new server in db and
     * reserve the needed size that is provided as a path param
     *
     * @param aInID id for server
     * @param aInServer server to be added
     * @return serverService.getServerSize(aInSize)
     * @throws InterruptedException and ExecutionException
     */
    @RequestMapping(method = GET, value = "/size/{size}")
    public int getServerSize(@PathVariable("size") int aInSize)
            throws ExecutionException, InterruptedException
    {
        return serverService.getServerSize(aInSize);
    }
}
