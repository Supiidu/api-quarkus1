package acc.br;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import acc.br.model.Fruta;

@Path("/frutas")
public class FrutasResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruta> list() {
        return Fruta.listAll();
    }

    @POST
    @Transactional
    public Response novaFruta(Fruta fruta) {
        if (fruta == null || fruta.nome == null || fruta.qtd <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Dados inválidos").build();
        }

        fruta.persist();
        return Response.status(Response.Status.CREATED).entity(fruta).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarFruta(@PathParam("id") Long id) {
        Fruta fruta = Fruta.findById(id);

        if (fruta == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Fruta não encontrada").build();
        }

        fruta.delete();
        return Response.noContent().build();
    }
}