package DSCatolog.DTO;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import DSCatolog.entities.Category;
import DSCatolog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Size(min = 2, message = "Minimum 2 characters" )
	@NotBlank(message = "Required field")
	private String name;
	
	@Size(min = 2, message = "Minimum 2 characters" )
	@NotBlank(message = "Required field")
	private String description;
	
	@Positive(message = "put positive value")
	private Double price;
	private String imgUrl;
	
	@PastOrPresent(message = "invalid date")
	private Instant date;

	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO() {

	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		date = entity.getDate();
	}

	public ProductDTO(Product entity, Set<Category> categories) {
		this(entity);
		for (Category category : categories) {
			CategoryDTO cat = new CategoryDTO(category);
			this.categories.add(cat);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getdate() {
		return date;
	}

	public void setdate(Instant date) {
		this.date = date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

}
