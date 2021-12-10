package main.data;
/**
 * @author : Sapir Anidgar and Sagi Brudni
 */

public class Response<T>
{
    private String errorMessage;
    private T value;

    /**
     * The class's constructors
     */
    public Response(){}
    public Response(String errorMessage){ this.errorMessage = errorMessage;}
    public Response(T value) {this.value = value;}

    /**
     * Getters
     * @return if error occurred, what is the error message and the response's value
     */

    public boolean errorOccurred() { return this.errorMessage != null; }
    public String getErrorMessage() { return  this.errorMessage;}
    public T getValue() { return this.value; }
}

