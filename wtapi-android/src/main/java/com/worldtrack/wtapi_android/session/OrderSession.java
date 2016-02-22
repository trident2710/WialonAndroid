package com.worldtrack.wtapi_android.session;

import android.util.Log;

import com.worldtrack.wtapi_android.callback.order.AssignCallback;
import com.worldtrack.wtapi_android.callback.CRUD.CreateCallback;
import com.worldtrack.wtapi_android.callback.CRUD.DeleteCallback;
import com.worldtrack.wtapi_android.callback.CRUD.UpdateCallback;
import com.worldtrack.wtapi_android.callback.Callback;
import com.worldtrack.wtapi_android.callback.order.RegisterCallback;
import com.worldtrack.wtapi_android.callback.other.LoginCallback;
import com.worldtrack.wtapi_android.callback.other.RequestTaskCallback;
import com.worldtrack.wtapi_android.callback.other.SearchCallback;
import com.worldtrack.wtapi_android.core.HttpRequestTask;
import com.worldtrack.wtapi_android.core.RequestBuilder;
import com.worldtrack.wtapi_android.core.ResponseParser;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.dataformat.*;
import com.worldtrack.wtapi_android.models.SessionData;
import com.worldtrack.wtapi_android.models.other.WialonError;
import com.worldtrack.wtapi_android.models.resource.Order;
import com.worldtrack.wtapi_android.search.SearchSpecification;

/**
 * Worldtrack 17.09.15.
 */
final class OrderSession extends SessionPrototype{

    public OrderSession(ResponseParser parser, RequestBuilder builder, CredentialHolder holder) {
        super(parser, builder, holder);
    }

    public void searchOrders(final SearchCallback callback, final String mask, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    searchOrderRequest(callback,mask);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            searchOrderRequest(callback,mask);
    }

    private void searchOrderRequest(final SearchCallback callback,String mask) {
        SearchSpecification specification = new SearchSpecification(SearchSpecification.ElementType.resource,
                SearchSpecification.ItemProperty.name,
                SearchSpecification.ItemProperty.name,mask);
        String[] request = builder.buildSearchRequest(specification, ResourceDataFlag.orders.getValue(), credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject[] objects = parser.parseSearchAsOrders(parser.parseJsonObjectFromString(response));
                if(objects.length>0)
                {
                    if (!isWialonError(objects[0])) {
                        callback.onSearchSuccessful(objects);
                    } else callback.onError(((WialonError) objects[0]).getError());
                } else callback.onError(com.worldtrack.wtapi_android.dataformat.Error.NO_ITEMS_FOUND);
            }
        });
        task.execute();
    }

    public void performOrderAction(final Order order, final long resourceId, final OrderActionType actionType,final Callback callback, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {

                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    performOrderActionRequest(order,resourceId,actionType,callback);
                }
                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }

            });
        else
            performOrderActionRequest(order,resourceId,actionType,callback);
    }

    private void performOrderActionRequest(final Order order,long resourceId, final OrderActionType actionType,final Callback callback) {

        String[] request = builder.buildOrderRequest(order, resourceId,actionType,credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                if (actionType == OrderActionType.CREATE || actionType == OrderActionType.UPDATE) {
                    WialonObject object = parser.parseCreateUpdateOrder(parser.parseJsonObjectFromString(response));
                    if (!isWialonError(object)) {
                        if (actionType == OrderActionType.CREATE) {
                            try {
                                ((CreateCallback) callback).onObjectCreated(object);
                            } catch (Exception e) {
                                Log.e("error", "unable to cast to CreateCallback");
                            }
                        }
                        if (actionType == OrderActionType.UPDATE) {
                            try {
                                ((UpdateCallback) callback).onObjectUpdated(object);
                            } catch (Exception e) {
                                Log.e("error", "unable to cast to UpdateCallback");
                            }
                        }
                    } else callback.onError(((WialonError) object).getError());

                }
                if (actionType == OrderActionType.DELETE || actionType == OrderActionType.ASSIGN || actionType == OrderActionType.REGISTER) {
                    WialonObject object = parser.parseDeleteResponse(parser.parseJsonObjectFromString(response));
                    if (!isWialonError(object)) {
                        if (actionType == OrderActionType.DELETE)
                            try {
                                ((DeleteCallback) callback).onObjectDeleted(order.getId());
                            } catch (Exception e) {
                                Log.e("error", "unable to cast to DeleteCallback");
                            }

                        if (actionType == OrderActionType.ASSIGN)
                            try {
                                ((AssignCallback) callback).onOrderAssigned(order.getUnitId());
                            } catch (Exception e) {
                                Log.e("error", "unable to cast to AssignCallback");
                            }
                        if (actionType == OrderActionType.REGISTER)
                            try {
                                ((RegisterCallback) callback).onRegistered(order);
                            } catch (Exception e) {
                                Log.e("error", "unable to cast to RegisterCallback");
                            }
                    } else callback.onError(((WialonError) object).getError());
                }
            }
        });
        task.execute();
    }
}
