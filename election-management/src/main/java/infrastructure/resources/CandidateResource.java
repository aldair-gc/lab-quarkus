package infrastructure.resources;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.Candidate;
import io.quarkus.micrometer.runtime.MicrometerCounted;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Transactional
    @MicrometerCounted(value = "candidates.created")
    public void create(CreateCandidate dto) {
        api.create(dto);
    }

    @PUT
    @Path("/{id}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    @Transactional
    @MicrometerCounted(value = "candidates.updated")
    public Candidate update(@PathParam("id") String id, UpdateCandidate dto) {
        return api.update(id, dto);
    }

    @GET
    @ResponseStatus(RestResponse.StatusCode.OK)
    @MicrometerCounted(value = "candidates.listed")
    public List<Candidate> list(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        return api.list(page, size);
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    @Transactional
    @MicrometerCounted(value = "candidates.deleted")
    public void delete(@PathParam("id") String id) {
        api.delete(id);
    }
}
