package org.example.gui.laundromats;

/**
 * Simple DTO for laundromat data.
 */
public class LaundromatData {
    public String name;
    public String address;
    public String distance;
    public String deliveryPeriod;
    public int stars;
    public String description;
    public String imagePath;

    /**
     * Full constructor.
     */
    public LaundromatData(String name,
                          String address,
                          String distance,
                          String deliveryPeriod,
                          int stars,
                          String description,
                          String imagePath) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.deliveryPeriod = deliveryPeriod;
        this.stars = stars;
        this.description = description;
        this.imagePath = imagePath;
    }

    /**
     * Convenience constructor used by the samples (no stars/description).
     */
    public LaundromatData(String name,
                          String address,
                          String distance,
                          String deliveryPeriod,
                          String imagePath) {
        this(name, address, distance, deliveryPeriod, 0, "", imagePath);
    }
}
