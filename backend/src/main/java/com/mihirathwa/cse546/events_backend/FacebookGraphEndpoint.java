package com.mihirathwa.cse546.events_backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "facebookGraphApi",
        version = "v1",
        resource = "facebookGraph",
        namespace = @ApiNamespace(
                ownerDomain = "events_backend.cse546.mihirathwa.com",
                ownerName = "events_backend.cse546.mihirathwa.com",
                packagePath = ""
        )
)
public class FacebookGraphEndpoint {

    private static final Logger logger = Logger.getLogger(FacebookGraphEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(FacebookGraph.class);
    }

    /**
     * Returns the {@link FacebookGraph} with the corresponding ID.
     *
     * @param accessToken the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code FacebookGraph} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "facebookGraph/{accessToken}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public FacebookGraph get(@Named("accessToken") String accessToken) throws NotFoundException {
        logger.info("Getting FacebookGraph with ID: " + accessToken);
        FacebookGraph facebookGraph = ofy().load().type(FacebookGraph.class).id(accessToken).now();
        if (facebookGraph == null) {
            throw new NotFoundException("Could not find FacebookGraph with ID: " + accessToken);
        }
        return facebookGraph;
    }

    /**
     * Inserts a new {@code FacebookGraph}.
     */
    @ApiMethod(
            name = "insert",
            path = "facebookGraph",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FacebookGraph insert(FacebookGraph facebookGraph) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that facebookGraph.accessToken has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(facebookGraph).now();
        logger.info("Created FacebookGraph with ID: " + facebookGraph.getAccessToken());

        return ofy().load().entity(facebookGraph).now();
    }

    /**
     * Updates an existing {@code FacebookGraph}.
     *
     * @param accessToken   the ID of the entity to be updated
     * @param facebookGraph the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code accessToken} does not correspond to an existing
     *                           {@code FacebookGraph}
     */
    @ApiMethod(
            name = "update",
            path = "facebookGraph/{accessToken}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public FacebookGraph update(@Named("accessToken") String accessToken, FacebookGraph facebookGraph) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(accessToken);
        ofy().save().entity(facebookGraph).now();
        logger.info("Updated FacebookGraph: " + facebookGraph);
        return ofy().load().entity(facebookGraph).now();
    }

    /**
     * Deletes the specified {@code FacebookGraph}.
     *
     * @param accessToken the ID of the entity to delete
     * @throws NotFoundException if the {@code accessToken} does not correspond to an existing
     *                           {@code FacebookGraph}
     */
    @ApiMethod(
            name = "remove",
            path = "facebookGraph/{accessToken}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("accessToken") String accessToken) throws NotFoundException {
        checkExists(accessToken);
        ofy().delete().type(FacebookGraph.class).id(accessToken).now();
        logger.info("Deleted FacebookGraph with ID: " + accessToken);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "facebookGraph",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<FacebookGraph> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<FacebookGraph> query = ofy().load().type(FacebookGraph.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<FacebookGraph> queryIterator = query.iterator();
        List<FacebookGraph> facebookGraphList = new ArrayList<FacebookGraph>(limit);
        while (queryIterator.hasNext()) {
            facebookGraphList.add(queryIterator.next());
        }
        return CollectionResponse.<FacebookGraph>builder().setItems(facebookGraphList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String accessToken) throws NotFoundException {
        try {
            ofy().load().type(FacebookGraph.class).id(accessToken).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find FacebookGraph with ID: " + accessToken);
        }
    }
}