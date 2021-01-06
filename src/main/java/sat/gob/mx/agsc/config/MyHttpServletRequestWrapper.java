package sat.gob.mx.agsc.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Value;


public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public String redirectUrl="";

    private static final String GET_PROTOCOL = "://.*";
    private static final String LINKED_IN = "linkedin";
    private static final String HTTPS = "https";

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${server.port}")
    private int serverPort;


    public MyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.redirectUrl =request.getRequestURL().toString();       

        System.out.println("nuveo url " + request.getRequestURI());
     
    }

    @Override
    public StringBuffer getRequestURL() {
        return new StringBuffer(redirectUrl.replaceFirst("redirect_uri=http","redirect_uri=https"));
    }

    @Override
    public String getScheme() {
        System.out.println("base" +baseUrl);
        return "https";
    }

    @Override
    public int getServerPort() {
        return 443;
    }

    @Override
    public String getServerName() {
        return "www.cloudb.sat.gob.mx";
    }
}