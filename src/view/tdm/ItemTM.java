package view.tdm;
/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class ItemTM implements Comparable<ItemTM>{
    private String code;
    private String Description;
    private String packSize;
    private double unitPrice;
    private int qtyOnHand;
    private double discount;

    public ItemTM() {
    }

    public ItemTM(String code, String description, String packSize, double unitPrice, int qtyOnHand, double discount) {
        this.code = code;
        Description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ItemTm{" +
                "code='" + code + '\'' +
                ", Description='" + Description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", discount=" + discount +
                '}';
    }

    @Override
    public int compareTo(ItemTM o) {
        return code.compareTo(o.getCode ());
    }
}
