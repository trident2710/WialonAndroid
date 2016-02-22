package com.worldtrack.wtapi_android.callback.CRUD;

import com.worldtrack.wtapi_android.callback.Callback;

/**
 * Worldtrack 22.08.15.
 */
public interface DeleteCallback  extends Callback {
    void onObjectDeleted(long id);
}
