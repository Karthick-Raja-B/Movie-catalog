package com.movie.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.movie.moviecatalogservice.models.CatalogItem;
import com.movie.moviecatalogservice.models.Movie;
import com.movie.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
//	@Autowired
//	private WebClient.Builder webClientBuilder;
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getcatalog(@PathVariable("userId") String userId){
		//get all rated movieId	
		UserRating ratings= restTemplate.getForObject("http://ratings-data-service/ratingdata/users/"+userId, UserRating.class);
		return ratings.getUserRating().stream()
				.map(rating -> {
					//for each movieId call movie info and ratings data service
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            		//collate them together
                    return new CatalogItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());
	}
}

//Movie movie= webClientBuilder.build()
//.get()
//.uri("http://localhost:8082/movies/" + rating.getMovieId())
//.retrieve().bodyToMono(Movie.class).block();
