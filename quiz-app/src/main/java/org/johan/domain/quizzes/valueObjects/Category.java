package org.johan.domain.quizzes.valueObjects;

public enum Category {

    ANY("Any Category", "any"),
    GENERAL("General Knowledge", "9"),
    BOOKS("Entertainment: Books", "10"),
    FILM("Entertainment: Film", "11"),
    MUSIC("Entertainment: Music", "12"),
    THEATRES("Entertainment: Musicals & Theatres", "13"),
    TELEVISION("Entertainment: Television", "14"),
    VIDEO_GAMES("Entertainment: Video Games", "15"),
    BOARD_GAMES("Entertainment: Board Games", "16"),
    NATURE("Science & Nature", "17"),
    COMPUTERS("Science: Computers", "18"),
    MATHEMATICS("Science: Mathematics", "19"),
    MYTHOLOGY("Mythology", "20"),
    SPORTS("Sports", "21"),
    GEOGRAPHY("Geography", "22"),
    HISTORY("History", "23"),
    POLITICS("Politics", "24"),
    ART("Art", "25"),
    CELEBRITIES("Celebrities", "26"),
    ANIMALS("Animals", "27"),
    VEHICLES("Vehicles", "28"),
    COMICS("Entertainment: Comics", "29"),
    GADGETS("Science: Gadgets", "30"),
    ANIME_MANGA("Entertainment: Japanese Anime & Manga", "31"),
    CARTOON("Entertainment: Cartoon & Animations", "32");

    private final String text;

    private final String code;

    Category(String text, String code) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }

    public static Category parse(String text) {
        for (Category category: Category.values()) {
            if(category.getText().equals(text)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return text;
    }

}
