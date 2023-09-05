package dao;

public class CityZipDTO {
    private int zip;
    private String name;

    public CityZipDTO(int zip, String name) {
        this.zip = zip;
        this.name = name;
    }

    public int getZip() {
        return zip;
    }

    public String getName() {
        return name;
    }
}
