package com.mihirathwa.cse546.eventson_the_go;

import java.util.List;

/**
 * Created by Mihir on 05/04/2017.
 */

public interface EndpointResponse {
    void processFinish(List<RestFBEventsObject> restFBEventsObjects);
}
