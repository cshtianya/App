package com.tnw.entities;

import java.util.List;

/**
 * Created by CSH on 2015/9/16 0016.
 */
public class IndexGridNode {

    private String itemId,itemName;
    private List<GridItem> itemList;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public List<GridItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<GridItem> itemList) {
        this.itemList = itemList;
    }

    public class GridItem {
        private String parentItemId,itemId,itemName,itemPrice,itemImage;

        public String getParentItemId() {
            return parentItemId;
        }

        public void setParentItemId(String parentItemId) {
            this.parentItemId = parentItemId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getItemImage() {
            return itemImage;
        }

        public void setItemImage(String itemImage) {
            this.itemImage = itemImage;
        }
    }

}

