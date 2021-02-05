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
	
	private int port = 8080;
	private static HttpRequests requestToServer;

	@Before
	public void setup() {
		//wireMockRule.start();
		requestToServer = new HttpRequests("http://localhost:" + port);
		System.out.println("OK");
	}
	
	@ClassRule
	@Rule
	public static WireMockRule wireMockRule = new WireMockRule(8080);
	
	@Test
	public void exactUrlOnly() throws Exception {
		try{
			stubFor(get(urlEqualTo("/fin/4529"))
				.willReturn(aResponse()
						.withBody("1")
						.withStatus(200)));
			assertEquals(1, requestToServer.sendGet("4529"));
			verify(getRequestedFor(urlEqualTo("/fin/4529")));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void teardown() {
		wireMockRule.stop();
	}
	
}
