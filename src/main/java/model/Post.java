package model;


public class Post {
    private Long id;
    private String content;
    private Long created;
    private Long updated;

    public Post() {

    }

    public Post(Long id, String content, Long created, Long updated) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    public Post(String content, Long created, Long updated) {
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}
