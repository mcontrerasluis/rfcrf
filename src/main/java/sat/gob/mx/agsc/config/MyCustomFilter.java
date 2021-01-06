package sat.gob.mx.agsc.config;


import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import java.io.*;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class MyCustomFilter extends OncePerRequestFilter {

    private String myCloudPath;


    public MyCustomFilter(String myCloudPath) {
        this.myCloudPath= myCloudPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    
    System.out.println("redo" + request.getRequestURL().toString());

    System.out.println("prueba"+request.getRequestURL().toString().contains("oidc"));
    if(request.getRequestURL().toString().contains("oidc")){
        request = new MyHttpServletRequestWrapper(request);
    }    
    
    filterChain.doFilter(request, response);    
    }

    @Override
    public void destroy() {        
    }
}