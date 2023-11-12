package app;

public class Cell {  // basic information about a cell
    private boolean isPicked;
    private String imageName;

    public Cell(String imageName, boolean isPicked) {
        this.isPicked = isPicked;
        this.imageName = imageName;
    }

    public Cell(String imageName) {
        this(imageName, false);
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "%s, %b".formatted(getImageName(), isPicked());
    }
}
