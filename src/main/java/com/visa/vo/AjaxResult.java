package com.visa.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Ajax返回值的封装类
 * 
 * @author zxwu
 */
public class AjaxResult {
    /**
     * 操作结果。成功：1，失败：0
     */
    private int result;
    /**
     * 附加信息。
     */
    private List<String> msgs = new ArrayList<String>();
    /**
     * 附加数据
     */
    private Object data;

    /**
     * Constructor
     * 
     * @param result result
     */
    public AjaxResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 添加信息
     * 
     * @param m 消息内容
     */
    public void addMsg(String m) {
        msgs.add(m);
    }

    /**
     * 清空消息
     */
    public void clearMsg() {
        msgs.clear();
    }

    /**
     * 消息是否为空
     * 
     * @return result
     */
    public boolean empty() {
        return msgs.isEmpty();
    }

    /**
     * 消息数量
     * 
     * @return result
     */
    public int size() {
        return msgs.size();
    }

}
