package io.github.andyradionov.gelcitytour.data;

/**
 * @author Andrey Radionov
 */

public class Sight {
    private String category;
    private String name;
    private String address;
    private String description;
    private String image;

    public Sight(String category, String name, String address, String description, String image) {
        this.category = category;
        this.name = name;
        this.address = address;
        this.description = description;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Sight{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
