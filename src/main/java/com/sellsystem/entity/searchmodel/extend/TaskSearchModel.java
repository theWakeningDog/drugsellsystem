package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;
import org.springframework.util.StringUtils;

/**
 * Created by zhangwei on 2017/3/18.
 */
public class TaskSearchModel extends PageSearchModel {
    private String name;
    private String type;
    private String level;
    private String executor;
    private String createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 按时间搜索时，分解时间获取开始时间，不用写属性，这样mybatis可以获取
     * @return
     */
    public String getCreateTimeStart() {
        if (!StringUtils.isEmpty(createTime)) {
            String[] date = createTime.split(" - ");
            return date[0].trim();
        }
        return null;
    }

    /**
     * 按时间搜索时，分解时间获取结束时间
     * @return
     */
    public String getCreateTimeEnd() {
        if (!StringUtils.isEmpty(createTime)) {
            String[] date = createTime.split(" - ");
            return date[1].trim();
        }
        return null;
    }
}
