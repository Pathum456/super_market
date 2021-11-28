package view.tdm;
/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class CustomerTM implements Comparable<CustomerTM>  {
    private String id;
    private String name;
    private String address;
    private String nic;
    private String title;
    private String city;
    private String province;
    private String postalCode;

    public CustomerTM() {
    }

    public CustomerTM(String id, String name, String address, String nic, String title, String city, String province, String postalCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.title = title;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
    @Override
    public int compareTo(CustomerTM o) {
        return id.compareTo(o.getId());
    }
}
