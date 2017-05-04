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
        name = "eventPlaceApi",
        version = "v1",
        resource = "eventPlace",
        namespace = @ApiNamespace(
                ownerDomain = "events_backend.cse546.mihirathwa.com",
                ownerName = "events_backend.cse546.mihirathwa.com",
                packagePath = ""
        )
)
public class EventPlaceEndpoint {

    private static final Logger logger = Logger.getLogger(EventPlaceEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(EventPlace.class);
    }

    /**
     * Returns the {@link EventPlace} with the corresponding ID.
     *
     * @param name the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code EventPlace} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "eventPlace/{name}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public EventPlace get(@Named("name") String name) throws NotFoundException {
        logger.info("Getting EventPlace with ID: " + name);
        EventPlace eventPlace = ofy().load().type(EventPlace.class).id(name).now();
        if (eventPlace == null) {
            throw new NotFoundException("Could not find EventPlace with ID: " + name);
        }
        return eventPlace;
    }

    /**
     * Inserts a new {@code EventPlace}.
     */
    @ApiMethod(
            name = "insert",
            path = "eventPlace",
            httpMethod = ApiMethod.HttpMethod.POST)
    public EventPlace insert(EventPlace eventPlace) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that eventPlace.name has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(eventPlace).now();
        logger.info("Created EventPlace with ID: " + eventPlace.getName());

        return ofy().load().entity(eventPlace).now();
    }

    /**
     * Updates an existing {@code EventPlace}.
     *
     * @param name       the ID of the entity to be updated
     * @param eventPlace the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code name} does not correspond to an existing
     *                           {@code EventPlace}
     */
    @ApiMethod(
            name = "update",
            path = "eventPlace/{name}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public EventPlace update(@Named("name") String name, EventPlace eventPlace) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(name);
        ofy().save().entity(eventPlace).now();
        logger.info("Updated EventPlace: " + eventPlace);
        return ofy().load().entity(eventPlace).now();
    }

    /**
     * Deletes the specified {@code EventPlace}.
     *
     * @param name the ID of the entity to delete
     * @throws NotFoundException if the {@code name} does not correspond to an existing
     *                           {@code EventPlace}
     */
    @ApiMethod(
            name = "remove",
            path = "eventPlace/{name}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("name") String name) throws NotFoundException {
        checkExists(name);
        ofy().delete().type(EventPlace.class).id(name).now();
        logger.info("Deleted EventPlace with ID: " + name);
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
            path = "eventPlace",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<EventPlace> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<EventPlace> query = ofy().load().type(EventPlace.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<EventPlace> queryIterator = query.iterator();
        List<EventPlace> eventPlaceList = new ArrayList<EventPlace>(limit);
        while (queryIterator.hasNext()) {
            eventPlaceList.add(queryIterator.next());
        }
        return CollectionResponse.<EventPlace>builder().setItems(eventPlaceList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String name) throws NotFoundException {
        try {
            ofy().load().type(EventPlace.class).id(name).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find EventPlace with ID: " + name);
        }
    }
}