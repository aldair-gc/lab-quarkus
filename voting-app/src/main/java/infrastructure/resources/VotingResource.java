package infrastructure.resources;

import api.ElectionApi;
import api.dto.out.Election;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/voting")
@Produces(MediaType.APPLICATION_JSON)
public class VotingResource {
    private final ElectionApi api;

    public VotingResource(ElectionApi api) {
        this.api = api;
    }

    @GET
    public List<Election> elections() {
        return api.findAll();
    }

    @POST
    @Path("/elections/{electionId}/candidates/{candidateId}")
    @ResponseStatus(RestResponse.StatusCode.ACCEPTED)
    public void vote(
            @PathParam("electionId") String electionId,
            @PathParam("candidateId") String candidateId
    ) {
        api.vote(electionId, candidateId);
    }
}
