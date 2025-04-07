package reactjavaproject.cinewave.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import reactjavaproject.cinewave.models.Media;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {

  /* ===== SIMILAR CONTENT QUERIES ===== */

  // Similar movies by tag (genre)
  @Query("{ isMovie: true, tag: ?0, id: { $ne: ?1 } }")
  List<Media> findSimilarMoviesByTag(String tag, String excludeId);

  // Similar TV shows by tag (genre)
  @Query("{ isMovie: false, tag: ?0, id: { $ne: ?1 } }")
  List<Media> findSimilarTVShowsByTag(String tag, String excludeId);

  // Similar content regardless of type (for "More Like This" sections)
  @Query("{ tag: ?0, id: { $ne: ?1 } }")
  List<Media> findSimilarContentByTag(String tag, String excludeId);


  // HeroSection - Newest featured content
  @Query("{ isFeatured: true }")
  List<Media> findFeaturedContent();


  // Get all movies
  @Query("{ isMovie: true }")
  List<Media> findAllMovies();

  // Get all TV shows
  @Query("{ isMovie: false }")
  List<Media> findAllTVShows();

  // Search by title (name field)
  @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
  List<Media> findByNameContaining(String query);

  // Price range queries
  @Query("{ rentPrice: { $gte: ?0, $lte: ?1 } }")
  List<Media> findByRentPriceBetween(double min, double max);


  // 1. Create/Read/Update/Delete - Inherited from MongoRepository

  // 2. Get all movies
  @Query("{ isMovie: true }")
  List<Media> getAllMovies();

  // 3. Get all TV shows
  @Query("{ isMovie: false }")
  List<Media> getAllTVShows();

  // 4. Search by title (using 'name' field)
  @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
  List<Media> searchByName(String title);

  // 5. Featured movies with query params
  @Query("{ isMovie: true, isFeatured: true }")
  List<Media> findFeaturedMovies();

  // 6. Featured TV shows with query params
  @Query("{ isMovie: false, isFeatured: true }")
  List<Media> findFeaturedTVShows();

  // 7. Get specific media item
  Optional<Media> findById(String id);

  // 8. Price range queries
  @Query("{ rentPrice: { $gte: ?0, $lte: ?1 } }")
  List<Media> findByRentPriceRange(double minPrice, double maxPrice);

  @Query("{ purchasePrice: { $gte: ?0, $lte: ?1 } }")
  List<Media> findByPurchasePriceRange(double minPrice, double maxPrice);

  // 9. Release year filtering
  @Query("{ releaseYear: ?0 }")
  List<Media> findByReleaseYear(int year);

  /* ----- Additional Utility Queries ----- */

  // For similar items (same type + tag)
  @Query("{ isMovie: ?0, tag: ?1, id: { $ne: ?2 } }")
  List<Media> findSimilarMedia(boolean isMovie, String tag, String excludeId);

  // For genre (tag) filtering
  List<Media> findByTag(String tag);

  // For admin dashboard
  @Query(value = "{}", sort = "{ name: 1 }")
  List<Media> findAllSortedByName();
}