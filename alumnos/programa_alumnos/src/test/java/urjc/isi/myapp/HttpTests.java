package urjc.isi.myapp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import org.apache.http.client.methods.CloseableHttpResponse;

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
    
    // ????
    
//    @Test
//    public void testStatusNotExist() throws Exception {
//    	try {
//			stubFor(get(urlEqualTo("/noexiste/123"))
//				.willReturn(aResponse()
//						.withStatus(404)));
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    
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
}
