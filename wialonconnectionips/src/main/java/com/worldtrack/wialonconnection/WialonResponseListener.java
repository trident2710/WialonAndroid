package com.worldtrack.wialonconnection;

/**
 * Worldtrack 07.07.15.
 */
/**
 * Wialon IPS protocol request response listener
 */
public interface WialonResponseListener
{
    int LOGIN_SUCCESSFUL = 0x200;
    int LOGIN_UNSUCCESSFUL = 0x201;
    int LOGIN_INCORRECT_PASSWORD = 0x202;

    int SHORT_DATA_STRUCTURE_PACKAGE_ERROR = 0x210;
    int SHORT_DATA_STRUCTURE_INCORRECT_TIME = 0x211;
    int SHORT_DATA_SUCCESSFUL = 0x212;
    int SHORT_DATA_GET_COORDINATES_ERROR = 0x213;
    int SHORT_DATA_GET_SCH_ERROR = 0x214;
    int SHORT_DATA_GET_SATELLITE_COUNT_ERROR = 0x215;

    int DATA_STRUCTURE_PACKAGE_ERROR = 0x220;
    int DATA_STRUCTURE_INCORRECT_TIME = 0x221;
    int DATA_SUCCESSFUL = 0x222;
    int DATA_GET_COORDINATES_ERROR = 0x223;
    int DATA_GET_SCH_ERROR = 0x224;
    int DATA_GET_SATELLITE_COUNT_ERROR = 0x225;
    int DATA_GET_IO_ERROR = 0x226;
    int DATA_GET_ADC_ERROR = 0x227;
    int DATA_GET_ADDITIONAL_PARAMETERS_ERROR = 0x228;

    int PING_ANSWER = 0x230;

    int BLACK_BOX_SUCCESSFUL = 0x240;
    int BLACK_BOX_UNSUCCESSFUL = 0x241;

    int MESSAGE_SUCCESSFUL = 0x250;
    int MESSAGE_UNSUCCESSFUL = 0x251;

    int IMAGE_SUCCESSFUL = 0x260;
    int IMAGE_UNSUCCESSFUL = 0x261;

    int REQUEST_TIMEOUT = 0x270;
    int UNKNOWN_ERROR = 0x271;

    int CONNECT_SUCCESSFUL = 0x280;
    int DISCONNECT_SUCCESSFUL = 0x281;

    int SENT_SUCCESSFUL = 0x290;


    /**
     *
     * @param connectionAnswer - one of CONNECT constants
     */
    void onConnected(int connectionAnswer);
    /**
     *
     * @param disconnectionAnswer - one of DISCONNECT constants
     */
    void onDisconnected(int disconnectionAnswer);
    /**
     *
     * @param loginAnswer - one of LOGIN constants
     */
    void onLoginAnswer(int loginAnswer);

    /**
     *
     * @param shortDataAnswer - one of SHORT_DATA constants
     */
    void onShortDataAnswer(int shortDataAnswer);

    /**
     *
     * @param dataAnswer - one of DATA constants
     */
    void onDataAnswer(int dataAnswer);

    /**
     *
     * @param pingAnswer - one of PING constants
     */
    void onPingAnswer(int pingAnswer);

    /**
     *
     * @param blackBoxAnswer - one of BLACK_BOX constants
     */
    void onBlackBoxAnswer(int blackBoxAnswer);

    /**
     *
     * @param messageAnswer - one of MESSAGE constants
     */
    void onMessageAnswer(int messageAnswer);

    /**
     *
     * @param imageAnswer - one of IMAGE constants
     */
    void onImageAnswer(int imageAnswer);

    void onUnkownError();

}
