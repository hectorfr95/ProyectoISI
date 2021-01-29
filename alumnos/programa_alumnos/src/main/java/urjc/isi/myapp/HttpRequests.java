package urjc.isi.myapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;


public class HttpRequests {

	public final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	public void close() throws IOException {
        httpClient.close();
    }

//	public void sendGet() throws Exception {
//
//        HttpGet request = new HttpGet("https://www.google.com/search?q=mkyong");
//
//        // add request headers
//        request.addHeader("custom-key", "mkyong");
//        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
//
//        try (CloseableHttpResponse response = httpClient.execute(request)) {
//
//            // Get HttpResponse Status
//            System.out.println(response.getStatusLine().toString());
//
//            HttpEntity entity = response.getEntity();
//            Header headers = entity.getContentType();
//            System.out.println(headers);
//
//            if (entity != null) {
//                // return it as a String
//                String result = EntityUtils.toString(entity);
//                System.out.println(result);
//            }
//
//        }
//
//    }

    public void sendPostAlumno(String nombre, String dni, String idex, String puerto) throws Exception {
    	System.out.println(nombre+dni+idex);
        HttpPost post = new HttpPost("https://servidor-proyecto1.herokuapp.com/alumno");// aqui habria que poner https://servidor-hectorfr95.herokuapp.com/alumno

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("nombre", nombre));
        urlParameters.add(new BasicNameValuePair("dni", dni));
        urlParameters.add(new BasicNameValuePair("idex", idex));
        urlParameters.add(new BasicNameValuePair("puerto", puerto));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }
    
    public void sendPostExamen(File file, String nombre, String dni, String idex) throws Exception {
    	CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://servidor-proyecto1.herokuapp.com/examen");// aqui habria que poner https://servidor-hectorfr95.herokuapp.com/examen
               
       
       //  add request parameter, form parameters
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, "file.ext");
        builder.addTextBody("nombre", nombre);
        builder.addTextBody("dni", dni);
        builder.addTextBody("idex", idex);
        
        System.out.println("id del examen en el server: "+idex);
       
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        
        CloseableHttpResponse response = httpClient.execute(post);
        httpClient.close();
        
        //HttpEntity reqEntity = response.getEntity();
        //System.out.println("ToString:" + EntityUtils.toString(reqEntity));
        //System.out.println("--------------"+EntityUtils.toString(response.getEntity()));
        


    }
}
