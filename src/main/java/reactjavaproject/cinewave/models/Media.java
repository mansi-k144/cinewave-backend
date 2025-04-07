package reactjavaproject.cinewave.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Document(collection = "media")
public class Media {
    @Id
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private Double price;

    @NotBlank(message = "Synopsis cannot be blank")
    @Size(max = 1000, message = "Synopsis cannot exceed 1000 characters")
    private String synopsis;

    @NotNull(message = "isMovie flag must be specified")
    private Boolean isMovie;

    @NotBlank(message = "Small poster path cannot be blank")
    private String smallPosterPath;

    @NotBlank(message = "Large poster path cannot be blank")
    private String largePosterPath;

    /* Price Fields with Validation */
    @DecimalMin(value = "0.0", message = "Rent price cannot be negative")
    @DecimalMax(value = "100.0", message = "Rent price cannot exceed $100")
    private Double rentPrice;

    @DecimalMin(value = "0.0", message = "Purchase price cannot be negative")
    @DecimalMax(value = "500.0", message = "Purchase price cannot exceed $500")
    private Double purchasePrice;

    /* New Release Year Field */
    @Min(value = 1900, message = "Release year must be after 1900")
    @Max(value = 2100, message = "Release year must be before 2100")
    private Integer releaseYear;

    private Boolean isFeatured;

    @Size(max = 50, message = "Tag cannot exceed 50 characters")
    private String tag;

    // Constructors
    public Media() {
    }

    public Media(String id, String name, String synopsis, Boolean isMovie,
            String smallPosterPath, String largePosterPath, Double rentPrice,
            Double purchasePrice, Double price, Integer releaseYear,
            Boolean isFeatured, String tag) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.synopsis = synopsis;
        this.isMovie = isMovie;
        this.smallPosterPath = smallPosterPath;
        this.largePosterPath = largePosterPath;
        this.rentPrice = rentPrice;
        this.purchasePrice = purchasePrice;
        this.releaseYear = releaseYear;
        this.isFeatured = isFeatured;
        this.tag = tag;
    }

    // Getters and Setters (unchanged from your original)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Boolean getIsMovie() {
        return isMovie;
    }

    public void setIsMovie(Boolean isMovie) {
        this.isMovie = isMovie;
    }

    public String getSmallPosterPath() {
        return smallPosterPath;
    }

    public void setSmallPosterPath(String smallPosterPath) {
        this.smallPosterPath = smallPosterPath;
    }

    public String getLargePosterPath() {
        return largePosterPath;
    }

    public void setLargePosterPath(String largePosterPath) {
        this.largePosterPath = largePosterPath;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", synopsis='" + synopsis + '\'' +
                ", isMovie=" + isMovie +
                ", smallPosterPath='" + smallPosterPath + '\'' +
                ", largePosterPath='" + largePosterPath + '\'' +
                ", rentPrice=" + rentPrice +
                ", purchasePrice=" + purchasePrice +
                ", releaseYear=" + releaseYear +
                ", isFeatured=" + isFeatured +
                ", tag='" + tag + '\'' +
                '}';
    }
}