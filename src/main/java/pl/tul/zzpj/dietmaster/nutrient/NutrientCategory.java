package pl.tul.zzpj.dietmaster.nutrient;

public enum NutrientCategory {

    CALORIES("calories"),
    CARBOHYDRATE("carbohydrate"),
    PROTEIN("protein"),
    FAT("fat"),
    MINERAL("mineral"),
    VITAMIN("vitamin");

    private final String name;

    public String getName() {
        return name;
    }

    NutrientCategory(String nutrientCategory) {
        this.name = nutrientCategory;
    }
}
