package au.edu.unsw.soacourse.FZ_RESTful_Services.testClient;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import au.edu.unsw.soacourse.FZ_RESTful_Services.model.DriverBean;
import au.edu.unsw.soacourse.FZ_RESTful_Services.model.EntryBean;

public class RegistrationsClient {

	static final String REST_URI = "http://localhost:8080/FZ_RMS_RESTful_Services";

	public static void main(String[] args) {

		WebClient RegistrationsClient = WebClient.create(REST_URI);
		String s = "";

		// Get all Registrations
		RegistrationsClient.path("/Registrations").accept(
				MediaType.APPLICATION_XML);
		s = RegistrationsClient.get(String.class);
		System.out.println("Get all RegistrationsClient --");
		System.out.println(s);
		System.out.println();

		// Get Entry 001 (current path is /Registrations, go to
		// /Registrations/001)
		RegistrationsClient.path("/001").accept(MediaType.APPLICATION_XML);
		s = RegistrationsClient.get(String.class);
		System.out.println("Get Entry 001 --");
		System.out.println(s);
		System.out.println();

		// Create a Entry with EntryBean 010
		RegistrationsClient = WebClient.create(REST_URI);
		DriverBean driver = new DriverBean("lastname", "firstname", "licenNO",
				"address", "email");
		EntryBean entry = new EntryBean("010", "RegisNo", driver, "RegisTill");
		RegistrationsClient.path("/Registrations").accept(
				MediaType.APPLICATION_XML);
		RegistrationsClient.post(entry);
		System.out.println("POSTed Entry 010 --");
		s = RegistrationsClient.get(String.class);
		System.out.println(s);
		System.out.println();

		// Update a Entry with EntryBean 010
		RegistrationsClient = WebClient.create(REST_URI);
		driver = new DriverBean("****", "****", "****", "****", "****");
		entry = new EntryBean("010", "****", driver, "****");
		RegistrationsClient.path("/Registrations").accept(
				MediaType.APPLICATION_XML);
		RegistrationsClient.put(entry);
		System.out.println("PUTed Entry 010 --");
		s = RegistrationsClient.get(String.class);
		System.out.println(s);
		System.out.println();

		// Delete a Entry 010
		RegistrationsClient = WebClient.create(REST_URI);
		RegistrationsClient.path("/Registrations/010").accept(
				MediaType.APPLICATION_XML);
		RegistrationsClient.delete();
		System.out.println("Deleted Entry 010 --");
		RegistrationsClient = WebClient.create(REST_URI);
		RegistrationsClient.path("/Registrations").accept(
				MediaType.APPLICATION_XML);
		s = RegistrationsClient.get(String.class);
		System.out.println(s);
		System.out.println();
	}

}
