package com.nc.contentsaver.exceptions;

/**
 * The exception that occurs when the server cannot return the requested content to the client.
 */
public class ContentNotFoundException extends Exception {
    /**
     * Error text in case the content was not found.
     * Probably the link to the content is not correct.
     */
    private static final String MESSAGE_MISMATCH = "Content is not found";
    /**
     * Error text in case the content was not found, for the reason that it has not yet been saved.
     * Such content will be available in some time.
     */
    private static final String MESSAGE_LOADING = "Content is still saving. Try to get it later";
    /**
     * Error message.
     */
    private final String message;

    /**
     * Creates an exception object that occurs if the content was not found.
     *
     * @param saving true if the link to the content exists
     */
    public ContentNotFoundException(boolean saving) {
        if (saving) {
            message = MESSAGE_LOADING;
        } else {
            message = MESSAGE_MISMATCH;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
