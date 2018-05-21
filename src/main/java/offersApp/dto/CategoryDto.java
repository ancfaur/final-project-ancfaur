package offersApp.dto;

public class CategoryDto {
    private Long id;
    private String name;
    private boolean checked;
    private int noSubscribers;

    public CategoryDto() {}

    public CategoryDto(String name, int noSubscribers) {
        this.name = name;
        this.noSubscribers = noSubscribers;
        this.checked =false;
    }

    public CategoryDto(Long id, String name, int noSubscribers) {
        this.name = name;
        this.id = id;
        this.noSubscribers = noSubscribers;
        this.checked = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getNoSubscribers() {
        return noSubscribers;
    }

    public void setNoSubscribers(int noSubscribers) {
        this.noSubscribers = noSubscribers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CategoryDto)) {
            return false;
        }
        CategoryDto categoryDto = (CategoryDto) o;
        return categoryDto.name.equals(this.name) && categoryDto.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
