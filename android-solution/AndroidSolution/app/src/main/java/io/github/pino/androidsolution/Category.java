package io.github.pino.androidsolution;

/**
 * Created by nadaaver on 2017-02-23.
 */

public enum Category {

    PLACES("places"),
    MYLIFE("my life");

    private final String categoryText;

    Category(String categoryText) {
        this.categoryText = categoryText;
    }

    @Override
    public String toString() {
        return categoryText;
    }

}
