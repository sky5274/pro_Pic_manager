package com.dao.entity;

import java.util.Date;

public class Picture {
    private Integer id;

    private String title;

    private String author;

    private String authorurl;

    private String showurl;

    private String url;

    private Date updateda;

    private String clazz;

    private String theme;
    
    public Picture(){
    	
    }
    

    public Picture(Integer id, String title, String author, String authorurl, String showurl, String url, Date updateda,
			String clazz, String theme) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.authorurl = authorurl;
		this.showurl = showurl;
		this.url = url;
		this.updateda = updateda;
		this.clazz = clazz;
		this.theme = theme;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getAuthorurl() {
        return authorurl;
    }

    public void setAuthorurl(String authorurl) {
        this.authorurl = authorurl == null ? null : authorurl.trim();
    }

    public String getShowurl() {
        return showurl;
    }

    public void setShowurl(String showurl) {
        this.showurl = showurl == null ? null : showurl.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getUpdateda() {
        return updateda;
    }

    public void setUpdateda(Date updateda) {
        this.updateda = updateda;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }


	@Override
	public String toString() {
		return "Picture [id=" + id + ", title=" + title + ", author=" + author + ", authorurl=" + authorurl
				+ ", showurl=" + showurl + ", url=" + url + ", updateda=" + updateda + ", clazz=" + clazz + ", theme="
				+ theme + "]";
	}
    
}