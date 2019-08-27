package com.swapnil.revolut.resources;

import com.swapnil.revolut.exceptions.AccountNotFoundException;
import com.swapnil.revolut.exceptions.InsufficientBalanceException;
import com.swapnil.revolut.pojo.TransferRequest;
import com.swapnil.revolut.services.TransferService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    private TransferService transferService;

    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

    @POST
    public Response transfer(@NotNull @Valid TransferRequest transferRequest) {
        try {
            return Response.ok().entity(transferService.transfer(transferRequest)).build();
        } catch (InsufficientBalanceException e) {
            return Response.ok().entity(e.getMessage()).build();
        } catch (AccountNotFoundException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}





