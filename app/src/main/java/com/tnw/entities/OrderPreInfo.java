package com.tnw.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CSH on 2015/9/22 0022.
 */
public class OrderPreInfo extends ResultMsg{

    /**
     * data : {"shopList":[{"shopId":"1432258957735499242","shopName":"特农网","itemList":[{"shopId":"1432258957735499242","itemId":"1437405821614394328","itemCommodityId":"1437405821614394327","itemCommodityName":"冷鲜猪肉新鲜生猪肉蹄髈 肘子500克","itemStandardNumber":"1","itemStandardFirst":"规格","itemFirstName":"件","itemStandardSecond":"","itemSecondName":"","itemCostPrice":"18.50","itemPrice":"18.50","itemStock":"1","itemStockAmount":"15","itemImage":"http://tenongwang.oss-cn-qingdao.aliyuncs.com/commodity/1437405797798094228.jpg"}]}]}
     */

    private DataEntity data;
    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }



    public class DataEntity {
        /**
         * shopList : [{"shopId":"1432258957735499242","shopName":"特农网","itemList":[{"shopId":"1432258957735499242","itemId":"1437405821614394328","itemCommodityId":"1437405821614394327","itemCommodityName":"冷鲜猪肉新鲜生猪肉蹄髈 肘子500克","itemStandardNumber":"1","itemStandardFirst":"规格","itemFirstName":"件","itemStandardSecond":"","itemSecondName":"","itemCostPrice":"18.50","itemPrice":"18.50","itemStock":"1","itemStockAmount":"15","itemImage":"http://tenongwang.oss-cn-qingdao.aliyuncs.com/commodity/1437405797798094228.jpg"}]}]
         */

        private List<ShopListEntity> shopList;

        public void setShopList(List<ShopListEntity> shopList) {
            this.shopList = shopList;
        }

        public List<ShopListEntity> getShopList() {
            return shopList;
        }

        private DeliveryNode delivery;

        public DeliveryNode getDelivery() {
            return delivery;
        }

        public void setDelivery(DeliveryNode delivery) {
            this.delivery = delivery;
        }

    }

    public class ShopListEntity {
        /**
         * shopId : 1432258957735499242
         * shopName : 特农网
         * itemList : [{"shopId":"1432258957735499242","itemId":"1437405821614394328","itemCommodityId":"1437405821614394327","itemCommodityName":"冷鲜猪肉新鲜生猪肉蹄髈 肘子500克","itemStandardNumber":"1","itemStandardFirst":"规格","itemFirstName":"件","itemStandardSecond":"","itemSecondName":"","itemCostPrice":"18.50","itemPrice":"18.50","itemStock":"1","itemStockAmount":"15","itemImage":"http://tenongwang.oss-cn-qingdao.aliyuncs.com/commodity/1437405797798094228.jpg"}]
         */

        private String shopId;
        private String shopName;
        private List<ItemListEntity> itemList;

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public void setItemList(List<ItemListEntity> itemList) {
            this.itemList = itemList;
        }

        public String getShopId() {
            return shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public List<ItemListEntity> getItemList() {
            return itemList;
        }


    }

    public class ItemListEntity {
        private String shopId;
        private String itemId;
        private String itemCommodityId;
        private String itemCommodityName;
        private String itemStandardNumber;
        private String itemStandardFirst;
        private String itemFirstName;
        private String itemStandardSecond;
        private String itemSecondName;
        private String itemCostPrice;
        private String itemPrice;
        private String itemStock;
        private String itemStockAmount;
        private String itemImage;

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public void setItemCommodityId(String itemCommodityId) {
            this.itemCommodityId = itemCommodityId;
        }

        public void setItemCommodityName(String itemCommodityName) {
            this.itemCommodityName = itemCommodityName;
        }

        public void setItemStandardNumber(String itemStandardNumber) {
            this.itemStandardNumber = itemStandardNumber;
        }

        public void setItemStandardFirst(String itemStandardFirst) {
            this.itemStandardFirst = itemStandardFirst;
        }

        public void setItemFirstName(String itemFirstName) {
            this.itemFirstName = itemFirstName;
        }

        public void setItemStandardSecond(String itemStandardSecond) {
            this.itemStandardSecond = itemStandardSecond;
        }

        public void setItemSecondName(String itemSecondName) {
            this.itemSecondName = itemSecondName;
        }

        public void setItemCostPrice(String itemCostPrice) {
            this.itemCostPrice = itemCostPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public void setItemStock(String itemStock) {
            this.itemStock = itemStock;
        }

        public void setItemStockAmount(String itemStockAmount) {
            this.itemStockAmount = itemStockAmount;
        }

        public void setItemImage(String itemImage) {
            this.itemImage = itemImage;
        }

        public String getShopId() {
            return shopId;
        }

        public String getItemId() {
            return itemId;
        }

        public String getItemCommodityId() {
            return itemCommodityId;
        }

        public String getItemCommodityName() {
            return itemCommodityName;
        }

        public String getItemStandardNumber() {
            return itemStandardNumber;
        }

        public String getItemStandardFirst() {
            return itemStandardFirst;
        }

        public String getItemFirstName() {
            return itemFirstName;
        }

        public String getItemStandardSecond() {
            return itemStandardSecond;
        }

        public String getItemSecondName() {
            return itemSecondName;
        }

        public String getItemCostPrice() {
            return itemCostPrice;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public String getItemStock() {
            return itemStock;
        }

        public String getItemStockAmount() {
            return itemStockAmount;
        }

        public String getItemImage() {
            return itemImage;
        }
    }

}
