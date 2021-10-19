package com.lilu.appcommon.widget.jptabbar.bean;

/**
 * Description:
 *  导航单项实体类
 * @author lilu0916 on 2021/7/8
 * No one knows this better than me
 */
public class TabBean {

    //正常时图片
    private String iconNormal;
    private int defaultNormal;
    //选中时图片
    private String iconSelect;
    private int defaultSelect;
    //文字
    private String title;
    //路由
    private String router;

    public String getIconNormal() {
        return iconNormal;
    }

    public void setIconNormal(String iconNormal) {
        this.iconNormal = iconNormal;
    }

    public String getIconSelect() {
        return iconSelect;
    }

    public void setIconSelect(String iconSelect) {
        this.iconSelect = iconSelect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public int getDefaultNormal() {
        return defaultNormal;
    }

    public void setDefaultNormal(int defaultNormal) {
        this.defaultNormal = defaultNormal;
    }

    public int getDefaultSelect() {
        return defaultSelect;
    }

    public void setDefaultSelect(int defaultSelect) {
        this.defaultSelect = defaultSelect;
    }
}
