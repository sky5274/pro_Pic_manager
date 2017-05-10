package com.dao.entity;

public class ClassInfo {
    private Long sum;

    private String clazz;
    
    public ClassInfo(){}
    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }
	@Override
	public String toString() {
		return "ClassInfo [sum=" + sum + ", clazz=" + clazz + "]";
	}
    
}