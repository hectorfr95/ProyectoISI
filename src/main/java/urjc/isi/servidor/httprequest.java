package urjc.isi.servidor;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class httprequest {

    // one instance, reuse
    public final CloseableHttpClient httpClient = HttpClients.createDefault();


    public void close() throws IOException {
        httpClient.close();
    }

    public void sendGetprueba() throws Exception {

        HttpGet request = new HttpGet("http://localhost:4567/profesor");

        // add request headers
       // request.addHeader("custom-key", "mkyong");
      //  request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        }

    }
    
    public void sendGetAlumno(String ip, int puerto) throws Exception {
    	String url = "http://"+ip+":"+puerto+"/fin";
    	System.out.println(url);
        HttpGet request = new HttpGet(url);

        // add request headers
       // request.addHeader("custom-key", "mkyong");
      //  request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        }

    }

    

}