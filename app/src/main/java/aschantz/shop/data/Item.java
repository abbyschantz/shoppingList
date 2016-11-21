package aschantz.shop.data;

import android.widget.CheckBox;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

import aschantz.shop.R;

/**
 * Created by aschantz on 11/10/16.
 */
public class Item extends SugarRecord implements Serializable {

    public enum ItemType {
        LANDSCAPE(0, R.drawable.food),
        CITY(1, R.drawable.drink), FOOD(2, R.drawable.clothes),
        REC(3, R.drawable.electronic), DRUG(4, R.drawable.drug),
        RECR(5, R.drawable.rec), OTHER(6, R.drawable.other);


        private int value;
        private int iconId;

        private ItemType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static ItemType fromInt(int value) {
            for (ItemType p : ItemType.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return LANDSCAPE;
        }
    }

    private String itemName;
    private String description;
    private String price;
    private ItemType itemType;
    private boolean isBought;

    public Item() {

    }

    public Item(String itemName, String description, String price, ItemType itemType, boolean isBought) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.itemType = itemType;
        this.isBought = isBought;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }
}
