package com.whaty.platform.entity.bean;



/**
 * PrBzzTchCourseTeacher generated by MyEclipse - Hibernate Tools
 */

public class PrBzzTchCourseTeacher extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {


    // Fields    

     private String id;
     private EnumConst enumConstByFlagBak;
     private PeBzzTchCourse peBzzTchCourse;
     private PeTeacher peTeacher;
     private EnumConst enumConstByFlagTeacherType;


    // Constructors

    /** default constructor */
    public PrBzzTchCourseTeacher() {
    }

    
    /** full constructor */
    public PrBzzTchCourseTeacher(EnumConst enumConstByFlagBak, PeBzzTchCourse peBzzTchCourse, PeTeacher peTeacher, EnumConst enumConstByFlagTeacherType) {
        this.enumConstByFlagBak = enumConstByFlagBak;
        this.peBzzTchCourse = peBzzTchCourse;
        this.peTeacher = peTeacher;
        this.enumConstByFlagTeacherType = enumConstByFlagTeacherType;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public EnumConst getEnumConstByFlagBak() {
        return this.enumConstByFlagBak;
    }
    
    public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
        this.enumConstByFlagBak = enumConstByFlagBak;
    }

    public PeBzzTchCourse getPeBzzTchCourse() {
        return this.peBzzTchCourse;
    }
    
    public void setPeBzzTchCourse(PeBzzTchCourse peBzzTchCourse) {
        this.peBzzTchCourse = peBzzTchCourse;
    }

    public PeTeacher getPeTeacher() {
        return this.peTeacher;
    }
    
    public void setPeTeacher(PeTeacher peTeacher) {
        this.peTeacher = peTeacher;
    }

    public EnumConst getEnumConstByFlagTeacherType() {
        return this.enumConstByFlagTeacherType;
    }
    
    public void setEnumConstByFlagTeacherType(EnumConst enumConstByFlagTeacherType) {
        this.enumConstByFlagTeacherType = enumConstByFlagTeacherType;
    }
   








}