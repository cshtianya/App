package com.tnw.entities;

import java.util.ArrayList;

/**
 * 商品详情
 * @author SHChen
 * 
 */
public class ProductDetial {

    private String itemId;
    private String itemName;
    private String itemSourceUrl;
    private String itemSellCount;
    private String itemPrice;
    private String itemCostPrice;
    private String itemIsCollect;
    private String itemIsSource;
    private String itemImage;
    private String itemFirstId;
    private ItemShopEntity itemShop;
    private ArrayList<ItemAttributeEntity> itemAttribute;

    public String getItemFirstId() {
        return itemFirstId;
    }

    public void setItemFirstId(String itemFirstId) {
        this.itemFirstId = itemFirstId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemSourceUrl(String itemSourceUrl) {
        this.itemSourceUrl = itemSourceUrl;
    }

    public void setItemShop(ItemShopEntity itemShop) {
        this.itemShop = itemShop;
    }

    public void setItemSellCount(String itemSellCount) {
        this.itemSellCount = itemSellCount;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemCostPrice(String itemCostPrice) {
        this.itemCostPrice = itemCostPrice;
    }

    public void setItemIsCollect(String itemIsCollect) {
        this.itemIsCollect = itemIsCollect;
    }

    public void setItemIsSource(String itemIsSource) {
        this.itemIsSource = itemIsSource;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public void setItemAttribute(ArrayList<ItemAttributeEntity> itemAttribute) {
        this.itemAttribute = itemAttribute;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemSourceUrl() {
        return itemSourceUrl;
    }

    public String getItemSellCount() {
        return itemSellCount;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemCostPrice() {
        return itemCostPrice;
    }

    public String getItemIsCollect() {
        return itemIsCollect;
    }

    public String getItemIsSource() {
        return itemIsSource;
    }

    public String getItemImage() {
        return itemImage;
    }

    public ItemShopEntity getItemShop() {
        return itemShop;
    }

    public ArrayList<ItemAttributeEntity> getItemAttribute() {
        return itemAttribute;
    }

    public class ItemShopEntity {
        /**
         * itemId : 1430894932994736104
         * itemName : 华臣仕旗舰店
         * itemAddress : 河北省, 石家庄市辛集市斯宾赛服饰贸易有限公司
         * itemTelephone : 4008702717
         */
        private String itemId;
        private String itemName;
        private String itemAddress;
        private String itemTelephone;

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setItemAddress(String itemAddress) {
            this.itemAddress = itemAddress;
        }

        public void setItemTelephone(String itemTelephone) {
            this.itemTelephone = itemTelephone;
        }

        public String getItemId() {
            return itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemAddress() {
            return itemAddress;
        }

        public String getItemTelephone() {
            return itemTelephone;
        }
    }

    public class ItemAttributeEntity {
        /**
         * itemName : 品牌
         * itemContent : 华臣仕
         */
        private String itemName;
        private String itemContent;

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setItemContent(String itemContent) {
            this.itemContent = itemContent;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemContent() {
            return itemContent;
        }
    }
}
