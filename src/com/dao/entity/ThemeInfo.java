package com.dao.entity;

public class ThemeInfo {
    private Long sum;

    private String theme;

    private String clazz;
    
    public ThemeInfo(){}

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

	@Override
	public String toString() {
		return "ThemeInfo [sum=" + sum + ", theme=" + theme + ", clazz=" + clazz + "]";
	}
    
}