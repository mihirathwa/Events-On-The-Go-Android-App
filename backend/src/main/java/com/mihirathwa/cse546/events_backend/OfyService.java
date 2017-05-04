package com.mihirathwa.cse546.events_backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by Mihir on 05/02/2017.
 */

public class OfyService {

    static {
        ObjectifyService.register(Quote.class);
        ObjectifyService.register(FacebookGraph.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
