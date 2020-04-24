package model;

public class Region {
    private Long id;
    private String name;

    public Region() {
        this("UNKNOWN");
    }

    public Region(String name) {
        this(null, name);
    }

    public Region(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
