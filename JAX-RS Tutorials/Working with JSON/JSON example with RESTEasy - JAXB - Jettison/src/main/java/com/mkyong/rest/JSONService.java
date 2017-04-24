package com.mkyong.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

@Path("/json/movie")
public class JSONService {

	@BadgerFish
	@GET
	@Path("/get")
	@Produces("application/json")
	public Movie getMovieInJSON() {

		Movie movie = new Movie();
		movie.setName("Transformers: Dark of the Moon");
		movie.setDirector("Michael Bay");
		movie.setYear(2011);
		
		return movie; 

	}

}