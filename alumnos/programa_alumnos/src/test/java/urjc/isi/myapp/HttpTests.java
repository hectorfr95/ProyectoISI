package urjc.isi.myapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import wiremock.com.google.common.collect.Iterables;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.eclipse.jgit.api.errors.GitAPIException;

public class HttpTests {
	
    WireMockServer wireMockServer;
    private static HttpRequests requestToServer = new HttpRequests("http://localhost:8080");
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
    
    
	
    @Test
    public void testStatusCodePositive() throws Exception {
    	try {
			stubFor(get(urlEqualTo("/fin/.*"))
				.willReturn(aResponse()
						.withBody("1")
						.withStatus(200)));
			assertEquals(1, requestToServer.sendGet(".*"));
			verify(getRequestedFor(urlEqualTo("/fin/.*")));
			
			Main.setPeriodicRequests();
			
			Main.
			
			File file = new File("../*.zip");
			assertTrue(file.exists());
						
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testStatusCodeNegative() throws Exception {
    	try {
			stubFor(get(urlEqualTo("/fin/.*"))
				.willReturn(aResponse()
						.withBody("0")
						.withStatus(200)));
			assertEquals(0, requestToServer.sendGet(".*"));
			verify(getRequestedFor(urlEqualTo("/fin/.*")));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    static CloseableHttpResponse response;
    
    @Test
    public void testStatusCodePositivePost() throws Exception {
    	try {
			stubFor(post(urlEqualTo("/alumno"))
				.willReturn(aResponse()
						.withStatus(200)));
			response = requestToServer.sendPostAlumno("Pepe", "087954R", ".*", "8080");
			assertEquals(200, response.getStatusLine().getStatusCode());
			//verify(getRequestedFor(urlEqualTo("/alumno")));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testStatusCodeNegativePost() throws Exception {
    	try {
			stubFor(post(urlEqualTo("/alumno"))
				.willReturn(aResponse()
						.withStatus(404)));
			response = requestToServer.sendPostAlumno("Pepe", "087954R", ".*", "8080");
			assertEquals(404, response.getStatusLine().getStatusCode());
			//verify(getRequestedFor(urlEqualTo("/alumno")));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
//    @Test
//	public void testPeriodicGet() throws IOException, GitAPIException, InterruptedException {
//		
//		Main.setPeriodicRequests();
//		
//		
//		Thread.sleep(150000);
//		
//		int counter = Iterables.size(git.log().call());
//		
//		Main.getTimer().cancel();
//		assertEquals(counter, 2);
//		
//	}
}
