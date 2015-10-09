package com.tnw.entities;

import java.util.List;

/**
 * Created by Administrator on 2015/7/22 0022.
 */
public class _TEstEntity {



    /**
     * result : 0
     * toHuanxinIds : ["zhangyuan_10158","zhangyuan_109005"]
     */
    private String result;
    private List<String> toHuanxinIds;

    public void setResult(String result) {
        this.result = result;
    }

    public void setToHuanxinIds(List<String> toHuanxinIds) {
        this.toHuanxinIds = toHuanxinIds;
    }

    public String getResult() {
        return result;
    }

    public List<String> getToHuanxinIds() {
        return toHuanxinIds;
    }
}
