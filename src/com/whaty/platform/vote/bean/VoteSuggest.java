package com.whaty.platform.vote.bean;

import java.util.Date;


/**
 * VoteSuggest generated by MyEclipse - Hibernate Tools
 */

public class VoteSuggest  implements java.io.Serializable {


    // Fields    

     private String id;
     private String paperId;
     private String note;
     private Date foundDate;
     private String ip;
     private Long status;


    // Constructors

    /** default constructor */
    public VoteSuggest() {
    }

    
    /** full constructor */
    public VoteSuggest(String paperId, String note, Date foundDate, String ip, Long status) {
        this.paperId = paperId;
        this.note = note;
        this.foundDate = foundDate;
        this.ip = ip;
        this.status = status;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getPaperId() {
        return this.paperId;
    }
    
    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    public Date getFoundDate() {
        return this.foundDate;
    }
    
    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
   








}