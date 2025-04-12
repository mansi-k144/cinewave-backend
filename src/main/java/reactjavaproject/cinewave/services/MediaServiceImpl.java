// MediaServiceImpl.java
package reactjavaproject.cinewave.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactjavaproject.cinewave.models.Media;
import reactjavaproject.cinewave.repositories.MediaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    /* ===== Frontend Component Implementations ===== */

   @Override
public List<Media> getHeroSectionItems(int limit) {
    return mediaRepository.findFeaturedContent()
            .stream()
            .filter(media -> media.getReleaseYear() != null && media.getReleaseYear() == 2021)
            .limit(limit)
            .collect(Collectors.toList());
}

    @Override
    public List<Media> getFeaturedMovies(int limit, String tag) {
        List<Media> movies = tag != null ? 
                mediaRepository.findByTag(tag).stream()
                        .filter(Media::getIsMovie)
                        .toList() :
                mediaRepository.findFeaturedMovies();
        
        return movies.stream().limit(limit).toList();
    }

    @Override
    public List<Media> getFeaturedTVShows(int limit, String tag) {
        List<Media> shows = tag != null ?
                mediaRepository.findByTag(tag).stream()
                        .filter(m -> !m.getIsMovie())
                        .toList() :
                mediaRepository.findFeaturedTVShows();
        
        return shows.stream().limit(limit).toList();
    }

    @Override
    public List<Media> getAllMovies() {
        return mediaRepository.findAllMovies();
    }

    @Override
    public List<Media> getAllTVShows() {
        return mediaRepository.findAllTVShows();
    }

    @Override
    public Optional<Media> getMediaById(String id) {
        return mediaRepository.findById(id);
    }

    @Override
    public List<Media> getSimilarMedia(String currentId, boolean sameTypeOnly) {
        return mediaRepository.findById(currentId)
                .map(media -> sameTypeOnly ?
                        media.getIsMovie() ?
                                mediaRepository.findSimilarMoviesByTag(media.getTag(), currentId) :
                                mediaRepository.findSimilarTVShowsByTag(media.getTag(), currentId) :
                        mediaRepository.findSimilarContentByTag(media.getTag(), currentId))
                .orElse(List.of());
    }

    /* ===== Assignment Requirement Implementations ===== */

    @Override
    public Media createMedia(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public Media updateMedia(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public void deleteMedia(String id) {
        mediaRepository.deleteById(id);
    }

    @Override
    public List<Media> searchByName(String query) {
        return mediaRepository.findByNameContaining(query);
    }

    @Override
    public List<Media> findByRentPriceRange(double min, double max) {
        return mediaRepository.findByRentPriceBetween(min, max);
    }

    @Override
    public List<Media> findByPurchasePriceRange(double min, double max) {
        return mediaRepository.findByPurchasePriceRange(min, max);
    }

    @Override
    public List<Media> findByReleaseYear(int year) {
        return mediaRepository.findByReleaseYear(year);
    }

    /* ===== Additional Utilities ===== */

    @Override
    public List<Media> findByTag(String tag) {
        return mediaRepository.findByTag(tag);
    }

    @Override
    public List<Media> findAllSortedByName() {
        return mediaRepository.findAllSortedByName();
    }

    @Override
    public List<Media> getSimilarMoviesByTag(String tag, String excludeId) {
        return mediaRepository.findSimilarMoviesByTag(tag, excludeId);
    }

    @Override
    public List<Media> getSimilarTVShowsByTag(String tag, String excludeId) {
        return mediaRepository.findSimilarTVShowsByTag(tag, excludeId);
    }
}