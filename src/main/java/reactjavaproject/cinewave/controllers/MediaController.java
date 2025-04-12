package reactjavaproject.cinewave.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactjavaproject.cinewave.models.Media;
import reactjavaproject.cinewave.services.MediaService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    // Hero Section
    @GetMapping("/hero")
public ResponseEntity<List<Media>> getHeroSectionItems(
        @RequestParam(defaultValue = "5") int limit) {
    // Filter by release year 2021
    return ResponseEntity.ok(mediaService.getHeroSectionItems(limit)
        .stream()
        .filter(media -> media.getReleaseYear() == 2021)
        .collect(Collectors.toList()));
}

    // Featured Movies
    @GetMapping("/featured/movies")
    public ResponseEntity<List<Media>> getFeaturedMovies(
            @RequestParam(defaultValue = "8") int limit,
            @RequestParam(required = false) String tag) {
        return ResponseEntity.ok(mediaService.getFeaturedMovies(limit, tag));
    }

    // Featured TV Shows
    @GetMapping("/featured/tvshows")
    public ResponseEntity<List<Media>> getFeaturedTVShows(
            @RequestParam(defaultValue = "8") int limit,
            @RequestParam(required = false) String tag) {
        return ResponseEntity.ok(mediaService.getFeaturedTVShows(limit, tag));
    }

    // All Movies
    @GetMapping("/movies")
    public ResponseEntity<List<Media>> getAllMovies() {
        return ResponseEntity.ok(mediaService.getAllMovies());
    }

    // All TV Shows
    @GetMapping("/tvshows")
    public ResponseEntity<List<Media>> getAllTVShows() {
        return ResponseEntity.ok(mediaService.getAllTVShows());
    }

    // Media Details with Similar Content
    @GetMapping("/{id}")
    public ResponseEntity<?> getMediaById(
            @PathVariable String id,
            @RequestParam(defaultValue = "true") boolean sameTypeOnly) {
        Optional<Media> media = mediaService.getMediaById(id);
        if (media.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Media> similarMedia = mediaService.getSimilarMedia(id, sameTypeOnly);
        return ResponseEntity.ok(Map.of(
                "media", media.get(),
                "similar", similarMedia));
    }

    // Search by Name
    @GetMapping("/search")
    public ResponseEntity<List<Media>> searchMedia(
            @RequestParam String query) {
        return ResponseEntity.ok(mediaService.searchByName(query));
    }

    // Filter by Rent Price Range
    @GetMapping("/filter/rent")
    public ResponseEntity<List<Media>> findByRentPriceRange(
            @RequestParam double min,
            @RequestParam double max) {
        return ResponseEntity.ok(mediaService.findByRentPriceRange(min, max));
    }

    // Filter by Purchase Price Range
    @GetMapping("/filter/purchase")
    public ResponseEntity<List<Media>> findByPurchasePriceRange(
            @RequestParam double min,
            @RequestParam double max) {
        return ResponseEntity.ok(mediaService.findByPurchasePriceRange(min, max));
    }

    // Filter by Release Year
    @GetMapping("/filter/year")
    public ResponseEntity<List<Media>> findByReleaseYear(
            @RequestParam int year) {
        return ResponseEntity.ok(mediaService.findByReleaseYear(year));
    }

    // Filter by Tag
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Media>> findByTag(@PathVariable String tag) {
        return ResponseEntity.ok(mediaService.findByTag(tag));
    }

    // Get Sorted by Name
    @GetMapping("/sorted/name")
    public ResponseEntity<List<Media>> findAllSortedByName() {
        return ResponseEntity.ok(mediaService.findAllSortedByName());
    }

    // Admin Endpoints
    @PostMapping
    public ResponseEntity<Media> createMedia(@RequestBody Media media) {
        return ResponseEntity.ok(mediaService.createMedia(media));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Media> updateMedia(
            @PathVariable String id,
            @RequestBody Media media) {
        media.setId(id);
        return ResponseEntity.ok(mediaService.updateMedia(media));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedia(@PathVariable String id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.ok(Map.of(
                "message", "Media deleted successfully"));
    }
}