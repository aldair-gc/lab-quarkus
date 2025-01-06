package infrastructure.resources;

import api.dto.out.Election;
import api.ElectionApi;
import io.quarkus.micrometer.runtime.MicrometerCounted;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

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
    @MicrometerCounted(value = "elections.created")
    public Uni<Void> submit() {
        return api.submit();
    }

    @GET
    @ResponseStatus(RestResponse.StatusCode.OK)
    @MicrometerCounted(value = "elections.listed")
    public Uni<List<Election>> list() {
        return api.findAll();
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    @MicrometerCounted(value = "elections.deleted")
    public Uni<Boolean> delete(@PathParam("id") String id) {
        api.delete(id);
        return Uni.createFrom().item(true);
    }
}
