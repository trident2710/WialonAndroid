package com.worldtrack.wtapi_android.session;

import android.util.Log;

import com.worldtrack.wtapi_android.callback.CRUD.CreateCallback;
import com.worldtrack.wtapi_android.callback.CRUD.DeleteCallback;
import com.worldtrack.wtapi_android.callback.CRUD.UpdateCallback;
import com.worldtrack.wtapi_android.callback.other.FileUploadCallback;
import com.worldtrack.wtapi_android.callback.other.LoginCallback;
import com.worldtrack.wtapi_android.callback.other.RequestTaskCallback;
import com.worldtrack.wtapi_android.callback.other.SearchCallback;
import com.worldtrack.wtapi_android.core.HttpFileUploadTask;
import com.worldtrack.wtapi_android.core.HttpRequestTask;
import com.worldtrack.wtapi_android.core.RequestBuilder;
import com.worldtrack.wtapi_android.core.ResponseParser;
import com.worldtrack.wtapi_android.core.WialonObject;
import com.worldtrack.wtapi_android.dataformat.*;
import com.worldtrack.wtapi_android.dataformat.Error;
import com.worldtrack.wtapi_android.models.SessionData;
import com.worldtrack.wtapi_android.models.other.HardWareType;
import com.worldtrack.wtapi_android.models.other.WialonError;
import com.worldtrack.wtapi_android.models.unit.CustomField;
import com.worldtrack.wtapi_android.models.unit.Sensor;
import com.worldtrack.wtapi_android.models.unit.Unit;
import com.worldtrack.wtapi_android.search.SearchSpecification;

/**
 * Worldtrack 07.09.15.
 */
final class ItemSession extends SessionPrototype {

    public ItemSession(ResponseParser parser, RequestBuilder builder, CredentialHolder holder) {
        super(parser, builder, holder);
    }

    public void searchUnits(final SearchSpecification specification, final UnitDataFlag[] flags, final SearchCallback callback, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    searchUnitsRequest(specification, flags, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            searchUnitsRequest(specification, flags, callback);
    }

    private void searchUnitsRequest(final SearchSpecification specification, final UnitDataFlag[] flags, final SearchCallback callback) {
        long fl = 0l;
        for (UnitDataFlag f : flags) {
            fl += f.getValue();
        }

        String[] request = builder.buildSearchRequest(specification, fl, credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject[] objects = parser.parseSearchAsUnits(parser.parseJsonObjectFromString(response));
                if(objects.length>0)
                {
                    if (!isWialonError(objects[0])) {
                        callback.onSearchSuccessful(objects);
                    } else callback.onError(((WialonError) objects[0]).getError());
                } else callback.onError(Error.NO_ITEMS_FOUND);

            }
        });
        task.execute();
    }

    public void searchUnitGroups(final SearchSpecification specification, final UnitGroupDataFlag[] flags, final SearchCallback callback, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    searchUnitGroupsRequest(specification, flags, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            searchUnitGroupsRequest(specification, flags, callback);

    }

    private void searchUnitGroupsRequest(final SearchSpecification specification, final UnitGroupDataFlag[] flags, final SearchCallback callback) {
        long fl = 0l;
        for (UnitGroupDataFlag f : flags) {
            fl += f.getValue();
        }

        String[] request = builder.buildSearchRequest(specification, fl, credentialHolder.getSessionData().getSessionID());
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject[] objects = parser.parseSearchAsUnitGroups(parser.parseJsonObjectFromString(response));
                if(objects.length>0)
                {
                    if (!isWialonError(objects[0])) {
                        callback.onSearchSuccessful(objects);
                    } else callback.onError(((WialonError) objects[0]).getError());
                } else callback.onError(Error.NO_ITEMS_FOUND);

            }
        });
        task.execute();
    }

    public void searchHardwareTypes(final HWFilter filterType, final String filterValue, final boolean includeType, final SearchCallback callback, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    searchHardwareTypesRequest(filterType, filterValue, includeType, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            searchHardwareTypesRequest(filterType, filterValue, includeType, callback);

    }

    private void searchHardwareTypesRequest(final HWFilter filterType, final Object filterValue, final boolean includeType, final SearchCallback callback) {

        String[] request = builder.buildGetHwTypesRequest(credentialHolder.getSessionData().getSessionID(), filterType, filterValue, includeType);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject[] objects = parser.parseHardwareTypesResponse(parser.parseJsonObjectFromString(response));
                if(objects.length>0)
                {
                    if (!isWialonError(objects[0])) {
                        callback.onSearchSuccessful(objects);
                    } else callback.onError(((WialonError) objects[0]).getError());
                } else callback.onError(Error.NO_ITEMS_FOUND);
            }
        });
        task.execute();
    }

    public void createUnitGroup(final long creatorId, final String name, final UnitGroupDataFlag[] flags, final CreateCallback callback, boolean shouldLoginBefore) {

        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    createUnitGroupRequest(creatorId, name, flags, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            createUnitGroupRequest(creatorId, name, flags, callback);

    }

    private void createUnitGroupRequest(final long creatorId, final String name, final UnitGroupDataFlag[] flags, final CreateCallback callback) {
        long flag = 0;
        for (UnitGroupDataFlag fl : flags) flag += fl.getValue();

        String[] request = builder.buildCreateUnitGroupRequest(credentialHolder.getSessionData().getSessionID(), creatorId, name, flag);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseCreateUpdateResponseUnitGroup(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectCreated(object);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }

    public void createUnit(final long creatorId, final Unit unit, final UnitDataFlag[] flags, final CreateCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    createUnitRequest(creatorId, unit, flags, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            createUnitRequest(creatorId, unit, flags, callback);
    }

    public void createUnitRequest(final long creatorId, final Unit unit, final UnitDataFlag[] flags, final CreateCallback callback) {
        long flag = 0;
        for (UnitDataFlag fl : flags) flag += fl.getValue();

        String[] request = builder.buildCreateUnitRequest(credentialHolder.getSessionData().getSessionID(), creatorId, unit, flag);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseCreateUpdateResponseUnit(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectCreated(object);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }

    public void createCustomField(final WialonObject object, final String name, final String value, final CreateCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    createCustomFieldRequest(object, name, value, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            createCustomFieldRequest(object, name, value, callback);
    }

    private void createCustomFieldRequest(final WialonObject object, final String name, final String value, final CreateCallback callback) {
        if (object.getJsonObject().has("id")) {
            String[] request = builder.buildCustomFieldAction(ActionType.CREATE, credentialHolder.getSessionData().getSessionID(), name, value, object.getJsonObject().get("id").getAsLong(), 0);
            HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
                @Override
                public void onResult(String response) {
                    WialonObject object = parser.parseCustomFieldResponse(parser.parseJsonObjectFromString(response));
                    if (!isWialonError(object)) {
                        callback.onObjectCreated(object);
                    } else callback.onError(((WialonError) object).getError());
                }
            });
            task.execute();
        } else {
            Log.e("custom field", "unable to create custom field, property id is missing in wialon object");
            callback.onError(Error.INVALID_INPUT);
        }

    }

    public void createSensor(final long creatorId, final Sensor sensor, final CreateCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    createSensorRequest(creatorId, sensor, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            createSensorRequest(creatorId, sensor, callback);
    }

    private void createSensorRequest(final long creatorId, final Sensor sensor, final CreateCallback callback) {
        String[] request = builder.buildCRUDSensorRequest(credentialHolder.getSessionData().getSessionID(), ActionType.CREATE, creatorId, sensor);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseSensor(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectCreated(object);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }


    public void updateCustomField(final WialonObject object, final CustomField customField, final String value, final UpdateCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    updateCustomFieldRequest(object, customField, value, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            updateCustomFieldRequest(object, customField, value, callback);
    }

    private void updateCustomFieldRequest(final WialonObject object, final CustomField customField, final String value, final UpdateCallback callback) {
        if (object.getJsonObject().has("id")) {
            String[] request = builder.buildCustomFieldAction(ActionType.UPDATE, credentialHolder.getSessionData().getSessionID(), customField.getName(), value, object.getJsonObject().get("id").getAsLong(), customField.getId());
            HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
                @Override
                public void onResult(String response) {
                    WialonObject object = parser.parseCustomFieldResponse(parser.parseJsonObjectFromString(response));
                    if (!isWialonError(object)) {
                        callback.onObjectUpdated(object);
                    } else callback.onError(((WialonError) object).getError());
                }
            });
            task.execute();
        } else {
            Log.e("custom field", "unable to update custom field, property id is missing in wialon object");
            callback.onError(Error.INVALID_INPUT);
        }

    }

    public void deleteItem(final long id, final DeleteCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    deleteItemRequest(id, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            deleteItemRequest(id, callback);
    }

    public void deleteItemRequest(final long id, final DeleteCallback callback) {
        String[] request = builder.buildDeleteItem(credentialHolder.getSessionData().getSessionID(), id);
        HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseDeleteResponse(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onObjectDeleted(id);
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }

    public void deleteCustomField(final WialonObject object, final CustomField customField, final DeleteCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    deleteCustomFieldRequest(object, customField, callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            deleteCustomFieldRequest(object, customField, callback);
    }

    private void deleteCustomFieldRequest(final WialonObject object, final CustomField customField, final DeleteCallback callback) {
        if (object.getJsonObject().has("id")) {
            String[] request = builder.buildCustomFieldAction(ActionType.DELETE, credentialHolder.getSessionData().getSessionID(), customField.getName(), customField.getValue(), object.getJsonObject().get("id").getAsLong(), customField.getId());
            HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
                @Override
                public void onResult(String response) {
                    WialonObject object = parser.parseDeleteResponse(parser.parseJsonObjectFromString(response));
                    if (!isWialonError(object)) {
                        callback.onObjectDeleted(customField.getId());
                    } else callback.onError(((WialonError) object).getError());
                }
            });
            task.execute();
        } else {
            Log.e("custom field", "unable to delete custom field, property id is missing in wialon object");
            callback.onError(Error.INVALID_INPUT);
        }
    }

    public void uploadImage(final long itemId, final Object fileToUpload, final FileUploadCallback callback, boolean shouldLoginBefore) {
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    uploadImageRequest(itemId,fileToUpload,callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            uploadImageRequest(itemId, fileToUpload, callback);
    }

    private void uploadImageRequest(final long itemId, final Object fileToUpload, final FileUploadCallback callback) {
        String[] request = builder.buildUploadFileRequest(itemId, credentialHolder.getSessionData().getSessionID());

        HttpFileUploadTask task = new HttpFileUploadTask(fileToUpload, request, new RequestTaskCallback() {
            @Override
            public void onResult(String response) {
                WialonObject object = parser.parseUploadFileResponse(parser.parseJsonObjectFromString(response));
                if (!isWialonError(object)) {
                    callback.onFileUploaded();
                } else callback.onError(((WialonError) object).getError());
            }
        });
        task.execute();
    }

    public void updateHardwareType(final Unit unit, final HardWareType hardWareType, final UpdateCallback callback, boolean shouldLoginBefore){
        if (shouldLoginBefore)
            credentialHolder.tokenLogin(credentialHolder.getToken(), new LoginCallback() {
                @Override
                public void onLoginSuccessful(SessionData sessionData) {
                    updateHardwareTypeRequest(unit,hardWareType,callback);
                }

                @Override
                public void onError(com.worldtrack.wtapi_android.dataformat.Error error) {
                    callback.onError(error);
                }
            });
        else
            updateHardwareTypeRequest(unit, hardWareType, callback);
    }

    private void updateHardwareTypeRequest(Unit unit, HardWareType hardWareType, final UpdateCallback callback){
        try {
            String[] request = builder.buildUpdateHwTypeRequest(unit.getAsBasicFlagData().getId(),hardWareType.getId(),unit.getAsAdvansedProperties().getHardwareUniqueId(),credentialHolder.getSessionData().getSessionID());
            HttpRequestTask task = new HttpRequestTask(request, new RequestTaskCallback() {
                @Override
                public void onResult(String response) {
                    WialonObject object = parser.parseUpdateHardwareType(parser.parseJsonObjectFromString(response));
                    if (!isWialonError(object)) {
                        callback.onObjectUpdated(object);
                    } else callback.onError(((WialonError) object).getError());
                }
            });
            task.execute();
        } catch (Exception e)
        {
            e.printStackTrace();
            callback.onError(Error.UNKNOWN_ERROR);
        }

    }

}