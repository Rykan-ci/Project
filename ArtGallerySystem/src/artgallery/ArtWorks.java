package artgallery;

public class ArtWorks {
    private int id;
    private String title;
    private String artist;
    private String description;
    private int likes;
    private int dislikes;

    public ArtWorks(int id, String title, String artist, String description) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
    }

    // Overloaded constructor including likes/dislikes
    public ArtWorks(int id, String title, String artist, String description, int likes, int dislikes) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getDescription() { return description; }
    public int getLikes() { return likes; }
    public int getDislikes() { return dislikes; }

    public void setLikes(int likes) { this.likes = likes; }
    public void setDislikes(int dislikes) { this.dislikes = dislikes; }
}
