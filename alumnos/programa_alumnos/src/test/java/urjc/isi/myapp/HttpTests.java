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

public class HttpTests {
	
    WireMockServer wireMockServer;
    private static HttpRequests requestToServer;
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
	
    @Test
    public void testStatusCodePositive() throws Exception {
    	try {
			stubFor(get(urlEqualTo("/fin/.*"))
				.willReturn(aResponse()
						.withBody("1")
						.withStatus(200)));
			assertEquals(200, requestToServer.sendGet(".*"));
			verify(getRequestedFor(urlEqualTo("/fin/.*")));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testStatusCodeNegative() throws Exception {
    	try {
    		configureFor("localhost", 8080);
			stubFor(get(urlEqualTo("/noexiste/123"))
				.willReturn(aResponse()
						.withStatus(404)));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testStatusCodePositivePost() throws Exception {
    	try {
			stubFor(post(urlEqualTo("/alumno"))
				.willReturn(aResponse()
						.withStatus(200)));
	
			
			assertEquals(200, requestToServer.sendPostAlumno("Pepe", "087954R", ".*", "8080"));
			verify(getRequestedFor(urlEqualTo("/alumno")));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
}
