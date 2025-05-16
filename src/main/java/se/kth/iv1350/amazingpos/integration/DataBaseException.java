package se.kth.iv1350.amazingpos.integration;

/**
 * Contains exception regarding the database error
 */
public class DataBaseException extends RuntimeException {

    /**
     * Creates an instance of the DataBaseException with an error message.
     * @param errorMessage The error message.
     */
    public DataBaseException(String errorMessage){
        super(errorMessage);
    }

}
