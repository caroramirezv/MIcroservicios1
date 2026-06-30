package com.prueba.ropa.Assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.prueba.ropa.Controller.RopaControllerV2;
import com.prueba.ropa.Model.Ropa;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RopaModelAssembler implements RepresentationModelAssembler <Ropa, EntityModel<Ropa>> {

	@Override
	public EntityModel<Ropa> toModel(Ropa ropa) {
		return EntityModel.of(ropa,
				linkTo(methodOn(RopaControllerV2.class).obtenerCompleto(ropa.getId())).withSelfRel(),
				linkTo(methodOn(RopaControllerV2.class).listar()).withRel("ropa"));
	}
}
