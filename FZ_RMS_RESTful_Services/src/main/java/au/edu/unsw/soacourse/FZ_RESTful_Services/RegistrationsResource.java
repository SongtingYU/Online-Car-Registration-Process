package au.edu.unsw.soacourse.FZ_RESTful_Services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.FZ_RESTful_Services.dao.RegistrationsDao;
import au.edu.unsw.soacourse.FZ_RESTful_Services.model.EntryBean;

@Path("/Registrations")
public class RegistrationsResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<EntryBean> getRegistrations() {
		List<EntryBean> results = RegistrationsDao.instance.getRegistrations();
		if (results.size() == 0)
			return null;
		return results;
	}

	@GET
	@Path("{_rid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public EntryBean getEntry(@PathParam("_rid") String _rid) {
		EntryBean e = RegistrationsDao.instance.queryEntry(_rid);
		if (e == null)
			return null;
		return e;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createEntry(EntryBean entry) {
		Response res;
		if (RegistrationsDao.instance.insertEntry(entry)) {
			res = Response.created(uriInfo.getAbsolutePath()).build();
			System.out.println(uriInfo.getAbsolutePath());
		} else
			res = Response.noContent().build();
		return res;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateUser(EntryBean entry) {
		if (!RegistrationsDao.instance.updateEntry(entry))
			throw new RuntimeException("PUT: User with" + entry.get_rid()
					+ " error when update.");
	}

	@DELETE
	@Path("{_rid}")
	public void deleteUser(@PathParam("_rid") String _rid) {
		if (!RegistrationsDao.instance.deleteEntry(_rid))
			throw new RuntimeException("DELETE: User with " + _rid
					+ " not found when delete.");
	}
}
