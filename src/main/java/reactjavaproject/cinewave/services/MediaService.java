package reactjavaproject.cinewave.services;

import java.util.List;
import java.util.Optional;

import reactjavaproject.cinewave.models.Media;

public interface MediaService {
    List<Media> getHeroSectionItems(int limit);
    List<Media> getFeaturedMovies(int limit, String tag);
    List<Media> getFeaturedTVShows(int limit, String tag);
    List<Media> getAllMovies();
    List<Media> getAllTVShows();
    Optional<Media> getMediaById(String id);
    List<Media> getSimilarMedia(String currentId, boolean sameTypeOnly);
 
    Media createMedia(Media media);
    Media updateMedia(Media media);
    void deleteMedia(String id);
    List<Media> searchByName(String query);
    List<Media> findByRentPriceRange(double min, double max);
    List<Media> findByPurchasePriceRange(double min, double max);
    List<Media> findByReleaseYear(int year);
    
 
    List<Media> findByTag(String tag);
    List<Media> findAllSortedByName();
    List<Media> getSimilarMoviesByTag(String tag, String excludeId);
    List<Media> getSimilarTVShowsByTag(String tag, String excludeId);
}