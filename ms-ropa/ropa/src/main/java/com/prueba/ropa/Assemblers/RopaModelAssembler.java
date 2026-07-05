package com.prueba.ropa.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.prueba.ropa.Controller.RopaControllerV2;
import com.prueba.ropa.Model.Ropa;

@Component
public class RopaModelAssembler implements RepresentationModelAssembler<Ropa, EntityModel<Ropa>> {

    @Override
    public EntityModel<Ropa> toModel(Ropa ropa) {
        return EntityModel.of(ropa,
                linkTo(methodOn(RopaControllerV2.class).getRopaById(ropa.getId())).withSelfRel(),
                // Cambiamos listar por getAllRopas
                linkTo(methodOn(RopaControllerV2.class).getAllRopas()).withRel("ropas"));
    }
}
