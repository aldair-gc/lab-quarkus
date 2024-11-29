package infrastructure.resources;

import api.dto.out.Election;
import api.ElectionApi;
import io.quarkus.micrometer.runtime.MicrometerCounted;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/elections")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ElectionResource {

    private final ElectionApi api;

    public ElectionResource(ElectionApi api) {
        this.api = api;
    }

    @POST
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    @Transactional
    @MicrometerCounted(value = "elections.created")
    public void submit() {
        api.submit();
    }

    @GET
    @ResponseStatus(RestResponse.StatusCode.OK)
    @MicrometerCounted(value = "elections.listed")
    public List<Election> list() {
        return api.findAll();
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    @Transactional
    @MicrometerCounted(value = "elections.deleted")
    public boolean delete(@PathParam("id") String id) {
        api.delete(id);
        return true;
    }
}
