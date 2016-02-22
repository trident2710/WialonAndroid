package com.worldtrack.wtapi_android.callback.order;

import com.worldtrack.wtapi_android.callback.Callback;
import com.worldtrack.wtapi_android.models.resource.Order;

/**
 * Worldtrack 18.09.15.
 */
public interface AssignCallback extends Callback {
    void onOrderAssigned(long unitId);
}
