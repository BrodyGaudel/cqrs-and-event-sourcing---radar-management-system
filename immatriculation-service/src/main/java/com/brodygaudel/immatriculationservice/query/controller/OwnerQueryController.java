package com.brodygaudel.immatriculationservice.query.controller;

import com.brodygaudel.immatriculationservice.common.query.GetAllOwnersQuery;
import com.brodygaudel.immatriculationservice.common.query.GetOwnerByIdQuery;
import com.brodygaudel.immatriculationservice.query.entities.Owner;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query/owners")
public class OwnerQueryController {

    private final QueryGateway queryGateway;

    public OwnerQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public List<Owner> getAllOwners(){
        return queryGateway.query(new GetAllOwnersQuery(),
                ResponseTypes.multipleInstancesOf(Owner.class)).join();
    }

    @GetMapping("get/{id}")
    public Owner getOwnerById(@PathVariable String id){
        return queryGateway.query(new GetOwnerByIdQuery(id),
                ResponseTypes.instanceOf(Owner.class)).join();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
