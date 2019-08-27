package com.swapnil.revolut.resources;

import com.swapnil.revolut.exceptions.AccountNotFoundException;
import com.swapnil.revolut.pojo.Account;
import com.swapnil.revolut.services.AccountService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/api/v1/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    private AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{id}")
    public Response getAccount(@PathParam("id") Long id) {
        try {
            Account account = accountService.getAccountById(id)
                    .orElseThrow(() -> new AccountNotFoundException(id.toString()));
            return Response.ok().entity(account).build();
        } catch (AccountNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response createAccount(@QueryParam("balance") BigDecimal balance) {
        return Response.ok().entity(accountService.createAccount(balance)).build();
    }
}