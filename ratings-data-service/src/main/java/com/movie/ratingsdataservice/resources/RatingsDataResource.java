package com.movie.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.ratingsdataservice.models.Rating;
import com.movie.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingdata")
public class RatingsDataResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId,6);
	}
	

	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> ratings=Arrays.asList(new Rating("1234",4),new Rating("1234",4));
		UserRating userRating= new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
}
