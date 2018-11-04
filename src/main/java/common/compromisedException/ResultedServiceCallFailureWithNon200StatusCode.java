package common.compromisedException;

public class ResultedServiceCallFailureWithNon200StatusCode extends RuntimeException {

    public ResultedServiceCallFailureWithNon200StatusCode(String s) {
        super(s);
    }
}
