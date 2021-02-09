package urjc.isi.myapp;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;


import static org.mockito.Mockito.*;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import java.io.File;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import wiremock.com.google.common.collect.Iterables;

import org.junit.Before;
import org.junit.Rule;



public class Tests {
	Git git; 
	File directory;
	File zip;
	
	WireMockServer wireMockServer;
	
    private static HttpRequests requestToServer;
	
	
	@Before
	public void setup() {
		git = Main.createRepo();
		Main.setGit(git);
		Main.alumno = new Alumno("Pepe", "02223478L", "Pepe@gmail.com");
		Main.setIdEx("4211");
		Main.setReqToServer("http://localhost:8080");
		requestToServer = Main.getReqToServer();
	}
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8080);
	
	@Test
	public void testCreateRepository()
			throws IOException, GitAPIException {
	
			assertNotNull(git.getRepository());
		
	}
	@Test
	public void testDeleteRepository()
			throws IOException, GitAPIException {
			
			
			File file = new File("../examen/");
			Main.deleteRepo(file);
			assertFalse(file.exists());
		
	}
	
	@Test
	public void testCreateZip()
			throws IOException{
		zip =Main.compressRepo();
		File test = new File("../" + Main.getIdEx() + "_" + Main.alumno.getDni() + ".zip");
		
		assertEquals(zip.exists(), true);
		assertEquals(zip, test);
		
	}
	
	
	@Test
	public void testDoCommit() throws IOException, GitAPIException {
		String name = null;
		String email = null;
		String message = null;
		Main.doCommit("Soy el primer commit");
		Iterable <RevCommit> log = null;
		log=git.log().all().call();
		for (RevCommit rev : log){
			name = rev.getAuthorIdent().getName();
			email = rev.getAuthorIdent().getEmailAddress();
			message = rev.getFullMessage();
		}
		assertEquals(Main.alumno.getName(), name);
		assertEquals(Main.alumno.getMail(), email);
		assertEquals("Soy el primer commit", message);

		
	}
	
/*	@Test
	public void testFinEx() throws IOException, Exception{
		Main.finEx();
		
		
	}*/
	
	@Test
	public void testCommits() throws IOException, GitAPIException, InterruptedException {
		
		Main.setCommitRate(1);
		Main.setAlarm();
		
		Thread.sleep(150000);
		
		int counter = Iterables.size(git.log().call());
		
		Main.getTimer().cancel();
		assertEquals(counter, 2);
		
	}
	

	
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
			
			
			
			File file = new File("../"+Main.getIdEx()+ "_"+Main.alumno.getDni()+".zip");
			assertTrue(file.exists());
						
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testStatusCodeNegative() throws Exception {
    	try {
			stubFor(get(urlEqualTo("/fin/"+Main.getIdEx()))
				.willReturn(aResponse()
						.withBody("0")
						.withStatus(200)));
			assertEquals(0, requestToServer.sendGet(Main.getIdEx()));
			verify(getRequestedFor(urlEqualTo("/fin/"+Main.getIdEx())));
			
			Main.getTimerReq();
			Thread.sleep(150000);
			Main.getTimerReq().cancel();
			assertEquals(3, requestToServer.contador);
			
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
