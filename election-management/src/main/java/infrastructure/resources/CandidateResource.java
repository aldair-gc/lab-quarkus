package infrastructure.resources;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.Candidate;
import io.quarkus.micrometer.runtime.MicrometerCounted;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/candidates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CandidateResource {

    private final CandidateApi api;

    public CandidateResource(CandidateApi api) {
        this.api = api;
    }

    @POST
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    @MicrometerCounted(value = "candidates.created")
    public Uni<Void> create(CreateCandidate dto) {
        return api.create(dto);
    }

    @PUT
    @Path("/{id}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    @MicrometerCounted(value = "candidates.updated")
    public Uni<Candidate> update(@PathParam("id") String id, UpdateCandidate dto) {
        return api.update(id, dto);
    }

    @GET
    @ResponseStatus(RestResponse.StatusCode.OK)
    @MicrometerCounted(value = "candidates.listed")
    public Uni<List<Candidate>> list(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        return api.list(page, size);
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    @MicrometerCounted(value = "candidates.deleted")
    public Uni<Boolean> delete(@PathParam("id") String id) {
        api.delete(id);
        return Uni.createFrom().item(true);
    }
}
