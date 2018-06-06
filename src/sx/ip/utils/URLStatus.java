/*
 * Copyright 2018 IP Exchange : https://ip.sx/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sx.ip.utils;

/**
 * Enum data type to store the HTTP response code.
 */
public enum URLStatus {
    
    /**
     * HTTP CODE 200 - Everything OK
     */
    HTTP_OK(200, "OK", "SUCCESS"), 
    
    /**
     * HTTP CODE 204 - No Content
     */
    NO_CONTENT(204, "No Content", "SUCCESS"),
    
    /**
     * HTTP CODE 301 - Moved Permanently
     */
    MOVED_PERMANENTLY(301, "Moved Permanently", "SUCCESS"),
    
    /**
     * HTTP CODE 304 - Not modified
     */
    NOT_MODIFIED(304, "Not modified", "SUCCESS"),
    
    /**
     * HTTP CODE 305 - Use Proxy
     */
    USE_PROXY(305, "Use Proxy", "SUCCESS"), 
    
    /**
     * HTTP CODE 500 - Internal Server Error
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "ERROR"),
    
    /**
     * HTTP CODE 400 - Not Found
     */
    NOT_FOUND(404, "Not Found", "ERROR");
    
    /** The status code. */
    private int statusCode;
    
    /** The HTTP message. */
    private String httpMessage;
    
    /** The HTTP result. */
    private String result;
    
    /**
     * Return the status code
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }
    
    /**
    * Create a <code>URLStatus</code> that will hold all URL status for the system.
    *
    * @param code
    *            The response code
    * @param message
    *            The response message
    * @param status
    *            The response status
    */
    private URLStatus(int code, String message, String status) {
        statusCode = code;
        httpMessage = message;
        result = status;
    }
    
    /**
    * Return the response message for a determined http code.
    *
    * @param code
    *            The response code
    * 
    * @return 
    */
    public static String getStatusMessageForStatusCode(int code) {
        String returnStatusMessage = "Status Not Defined";
        for (URLStatus object : URLStatus.values()) {
            if (object.statusCode == code) {
                returnStatusMessage = object.httpMessage;
            }
        }
        return returnStatusMessage;
    }
    
    /**
    * Return the response result for a determined http code.
    *
    * @param code
    *            The response code
    * 
    * @return 
    */
    public static String getResultForStatusCode(int code) {
        String returnResultMessage = "Result Not Defined";
        for (URLStatus object : URLStatus.values()) {
            if (object.statusCode == code) {
                returnResultMessage = object.result;
            }
        }
        return returnResultMessage;
    }
    
}
