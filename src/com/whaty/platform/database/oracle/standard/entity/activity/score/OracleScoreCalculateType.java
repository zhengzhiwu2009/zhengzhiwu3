/*
 * OracleTeachClass.java
 *
 * Created on 2005��5��17��, ����9:24
 */

package com.whaty.platform.database.oracle.standard.entity.activity.score;
import java.math.BigDecimal;

import com.whaty.platform.database.oracle.standard.entity.activity.OracleElectiveScore;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.score.ScoreCalculateType;

/**
 * 
 * @author Administrator
 */
public class OracleScoreCalculateType extends ScoreCalculateType {

	/** Creates a new instance of OracleTeachClass */
	public OracleScoreCalculateType() {
	}

	public String fix(String str) {
		if (str == null || str.equals("null")||str.equals(""))
			str = "0.0";
		return str;

	}

	
	public  String getTotalScore(String elective_id,String CalculateType,String percent,String percent1,String percent2,String percent3 ,String[] calculate){
		
		String usual_score="0.0";
		String exam_score="0.0";
		String total_score="0.0";
		String total_score1="0";
		String experiment_score="0.0";
		String expend_score="0.0";
		String test_score="0.0";
		String student_id="";
		String teachclass_id="";
		
		OracleElectiveScore elctScore=new OracleElectiveScore();
		try{
		Elective elective=elctScore.getCoreScores(elective_id);
		usual_score=fix(elective.getUsualScore());
		exam_score=fix(elective.getExamScore());
		total_score=fix(elective.getTotalScore());
		experiment_score=fix(elective.getExperimentScore());
		expend_score=fix(elective.getExpendScore());
		student_id=fix(elective.getStudent().getId());
		teachclass_id=fix(elective.getTeachClass().getId());
		test_score=fix(elctScore.getTestScore(student_id,teachclass_id));
		
		if(CalculateType.equalsIgnoreCase(ScoreCalculateType.BILI)&&percent3!=null&!percent3.equals("")){
			total_score1=Double.parseDouble(usual_score)* Double.parseDouble(percent)+Double.parseDouble(experiment_score)*(Double.parseDouble(percent1))+Double.parseDouble(exam_score)*(Double.parseDouble(percent2))+Double.parseDouble(test_score)*(Double.parseDouble(percent3))+"";
			
		}
		
		if(CalculateType.equalsIgnoreCase(ScoreCalculateType.BILI)&&percent3==null){
			total_score1=Double.parseDouble(expend_score)* Double.parseDouble(percent)+Double.parseDouble(experiment_score)*(Double.parseDouble(percent1))+Double.parseDouble(usual_score)*(Double.parseDouble(percent2))+"";
			
		}
		
		if(CalculateType.equalsIgnoreCase(ScoreCalculateType.KAIFANG)){
			if(calculate!=null&&calculate.length>0&&calculate.length==4){
				
				total_score1=Math.pow(Double.parseDouble(usual_score),1/Double.parseDouble(calculate[0]))*Double.parseDouble(calculate[1])+Math.pow(Double.parseDouble(exam_score),1/Double.parseDouble(calculate[2]))*Double.parseDouble(calculate[3])+"";	
			}
			if(calculate!=null&&calculate.length>0&&calculate.length==2){
				
				total_score1=Math.pow(Double.parseDouble(total_score),1/Double.parseDouble(calculate[0]))*Double.parseDouble(calculate[1])+"";	
				
			}
			
			
		}
		}catch ( Exception e ){}
		
		return total_score1;
		
	}
	
public  String getTotalExpendScore(String elective_id,String percent,String percent1,String percent2){
		
		String usual_score="0.0";
		String total_score1="0";
		String experiment_score="0.0";
		String expend_score="0.0";
		
		
		OracleElectiveScore elctScore=new OracleElectiveScore();
		try{
		Elective elective=elctScore.getCoreScores(elective_id);
		usual_score=fix(elective.getUsualScore());
		experiment_score=fix(elective.getExperimentScore());
		expend_score=fix(elective.getExpendScore());
		
	
		if(!percent.equals("")&&!percent1.equals("")&&!percent2.equals("")){
			total_score1=Double.parseDouble(expend_score)* Double.parseDouble(percent)+Double.parseDouble(experiment_score)*(Double.parseDouble(percent1))+Double.parseDouble(usual_score)*(Double.parseDouble(percent2))+"";
			
		}
		
		
		}catch ( Exception e ){}
		
		return total_score1;
		
	}
	
	
	
	public double div(double n1,double n2)
	{
		BigDecimal s1 = new BigDecimal(Double.toString(n1));
		BigDecimal s2 = new BigDecimal(Double.toString(n2));
		
		return s1.divide(s2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	
	

}
