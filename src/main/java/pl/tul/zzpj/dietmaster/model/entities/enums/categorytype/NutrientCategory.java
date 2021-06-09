package pl.tul.zzpj.dietmaster.model.entities.enums.categorytype;

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
