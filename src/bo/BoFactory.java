package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBOImpl ();
            case CUSTOMER:
                return new CustomerBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl ();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, PURCHASE_ORDER
    }
}
