package com.tnw.mvp;

import com.tnw.entities.ProductDetial;

/**
 * Created by CSH on 2015/7/31 0031.
 */
public interface ProductDetailView extends  MVPView{

    void showProduct(ProductDetial pInfo);

    void showAttr(ProductDetial pInfo);

}
