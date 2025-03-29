package com.ra1n.top.model.enums;

import lombok.Getter;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Getter
public enum MenuCategoryEnum {
    STARTERS("Starters"),
    MAIN_COURSES("Main Courses"),
    DESSERTS("Desserts"),
    BEVERAGES("Beverages"),
    SIDES("Sides");

    private final String displayName;

    MenuCategoryEnum(String displayName) {
        this.displayName = displayName;
    }

}
