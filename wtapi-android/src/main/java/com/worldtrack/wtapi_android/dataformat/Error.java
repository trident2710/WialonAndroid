package com.worldtrack.wtapi_android.dataformat;

/**
 * Worldtrack 16.08.15.
 */
public enum Error {
    SUCCESSFUL_OPERATION(0,"successful operation"),
    INVALID_SESSION(1,"invalid session"),
    INVALID_SERVICE_NAME(2,"invalid service name"),
    INVALID_RESULT(3,"invalid result"),
    INVALID_INPUT(4,"invalid input"),
    ERROR_PERFORMING_REQUEST(5,"error performing request"),
    UNKNOWN_ERROR(6,"unknown error"),
    ACCESS_DENIED(7,"access denied"),
    INVALID_USERNAME_OR_PASSWORD(8,"invalid username or password"),
    AUTHORIZATION_SERVICE_UNAVAILABLE(9,"autorization service unavailable"),
    REACHED_LIMIT_OF_CONCURRENT_REQUESTS(10,"Reached limit of concurrent requests"),
    NO_MESSAGES_FOR_SELECTED_INTERVAL(1001,"No messages for selected interval"),
    ITEM_CANNOT_BE_CREATED(1002,"Item with such unique property already exists or Item cannot be created according to billing restrictions"),
    ONLY_ONE_REQUEST_IS_ALLOWED_AT_THE_MOMENT(1003,"Only one request is allowed at the moment"),
    LIMIT_OF_MESSAGES_HAS_BEEN_EXCEEDED(1004,"Limit of messages has been exceeded"),
    EXECUTION_TIME_HAS_EXCEEDED_THE_LIMIT(1005,"Execution time has exceeded the limit"),
    USER_CANNOT_BE_BOUND(2014,"Selected user is a creator for some system objects, thus this user cannot be bound to a new account"),
    //custom errors
    NO_ITEMS_FOUND(3000,"No items found");

    int errorCode;
    String message;

    Error(int errorCode,String message)
    {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
