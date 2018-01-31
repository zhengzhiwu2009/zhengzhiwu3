package com.whaty.encrypt;
import java.io.*;
import java.util.*;

public class encode implements Serializable {
	private String filepath,urlname,rootpath,listname,filename,courseid;
	boolean isinit,debuged;
	Hashtable pathlist,filelist,dirlist;
	public encode(){
		filepath="/www/htdocs/webapps/perl/";
		urlname="urlmap.txt";
		rootpath="";
		listname="";
		filename="";
		courseid="";
		isinit=false;
		debuged=true;
	        pathlist=new Hashtable();
		filelist=new Hashtable();
		dirlist=new Hashtable();
	}
	public String getFilepath(){
		return filepath;
	}
	public String getUrlname(){
		return urlname;
        }
        public String getRootpath(){
        	return rootpath;
        }
        public String getFilename(){
        	return filename;
        }
        public void setFilepath(String filepath){
        	this.filepath=filepath;
        }
        public void setUrlname(String urlname){
        	this.urlname=urlname;
        }
        public void setRootpath(String rootpath){
        	this.rootpath=rootpath;
        }
        public void setFilename(String filename){
        	this.filename=filename;
        }
        public void setCourseid(String courseid){
        	this.courseid=courseid;
        }
        public boolean Initiate(String rootpath){
        	rootpath=rootpath;
        	try{
        	DataInputStream dis=new DataInputStream(
        	new FileInputStream(filepath+urlname));
        	String input;
        	String strpath;
        	String strlist;
        	int dot;
        	while((input=dis.readLine())!=null){
                     	dot=input.indexOf(';');
        		if(dot>=0){
        		strpath=input.substring(0,dot);
        		strlist=input.substring(dot+1,input.length());
        		pathlist.put(strpath.intern(),strlist);
        		}
        	}
        	dis.close();
        	if(pathlist.containsKey(rootpath)!=true){
        		String str1=courseid+".txt";
        		pathlist.put(rootpath.intern(),str1);
        		DataOutputStream dos = new DataOutputStream(new FileOutputStream(filepath+urlname,true));
        	        dos.writeBytes("\n");
        	        dos.writeBytes(rootpath+';'+str1);
         	        dos.close();
         	        DataOutputStream dos2 = new DataOutputStream(new FileOutputStream(filepath+str1,true));
        	        dos2.writeBytes("generatedbtencoderfor"+rootpath);
        	        dos2.close(); 
        		
        	}
        	        listname=pathlist.get(rootpath.intern()).toString();
        		DataInputStream is=new DataInputStream(
        		new FileInputStream(filepath+listname));
        		while((input=is.readLine())!=null){
        			if(input.length()>=1){
        				if(input.charAt(input.length()-1)=='/'){
        				   dirlist.put(input.intern(),listname);
        			        }
        			        else{
        			          filelist.put(input.intern(),listname);
        		                }
        		     }
        		}
        		is.close();
        	isinit=true;
        	return true;
        }catch(IOException e){
        	//System.err.println("IOerror"+e);
        	return false;
        }        
        }
        public boolean IsFileEncoded(String filename){
        	if(!isinit){
        		Initiate(rootpath);
        	}
        	if(!isinit){
        		return false;
        	}
            	if(filelist.containsKey(filename)== true){
        		return true;
        	}
        	else{
        	return false;
                }
        }
        public boolean IsDirEncoded(String dir){
        	if(!isinit){
        		Initiate(rootpath);
        	}
        	if(!isinit){
        		return false;
        	}
        	if(!(dir.charAt(dir.length()-1)=='/')){
        		dir=dir+'/';
        	}
            	if(dirlist.containsKey(dir)== true){
        		return true;
        	}
        	else{
        	return false;
                }
        }
        public void EncodeFile(String filename){
        	if(!isinit){
        		Initiate(rootpath);
        	}
        	if(!isinit){
        		return;
        	}
         	if(filelist.containsKey(filename)==true){
        		return;
        	}
        	filelist.put(filename.intern(),listname);
        	try{
        	DataOutputStream dos = new DataOutputStream(new FileOutputStream(filepath+listname,true));
        	dos.writeBytes("\n");
        	dos.writeBytes(filename);
        	dos.close();
        	}catch(IOException e){
        	System.err.println("IOerror"+e);
                }
        }
        
        public void EncodeDir(String dir)
        {
        	if(!isinit){
        		Initiate(rootpath);
        	}
        	if(!isinit){
        		return;
        	}
        	if(!(dir.charAt(dir.length()-1)=='/')){
        		dir=dir+'/';
        	}
         	if(dirlist.containsKey(dir)==true){
        		return;
        	}
        	dirlist.put(dir.intern(),listname);
        	try{
        	DataOutputStream dos = new DataOutputStream(new FileOutputStream(filepath+listname,true));
        	dos.writeBytes("\n");
        	dos.writeBytes(dir);
        	dos.close();
        	}catch(IOException e){
        	System.err.println("IOerror"+e);
                }
                File dirf=new File(dir);
                String dirlist[]=dirf.list();
                for(int i=0;i<dirlist.length;i++){
                	File temp=new File(dir,dirlist[i]);
                	if(temp.isFile()){
                		EncodeFile(temp.toString());
                        }
                        else if(temp.isDirectory()){
                        	EncodeDir(temp.toString());
                        }
                        else{
                        ;
                        }
                }
        }
        	
        public void DecodeFile(String filename)
        {
        	if(!isinit)
        	{
        		Initiate(rootpath);
        	}
        	if(!isinit)
        	{
        		return;
        	}
        	if(filelist.containsKey(filename)==true)
        	{
        		filelist.remove(filename);
        		String temp;
        		for(Enumeration d= dirlist.keys();d.hasMoreElements();)
        		{
        			temp=d.nextElement().toString();
        			if(filename.indexOf(temp)!=-1)
        			{
        			dirlist.remove(temp);
        			}        	                        	            
                        }
                }
       }
      public void DecodeDir(String dir)
      {
      	   	if(!isinit){
        		Initiate(rootpath);
        	}
        	if(!isinit){
        		return;
        	}
        	if(!(dir.charAt(dir.length()-1)=='/')){
        		dir=dir+'/';
        	}
        	if(dirlist.containsKey(dir)==true)
        	{
        		dirlist.remove(dir);
        		String temp;
        		for(Enumeration f= filelist.keys();f.hasMoreElements();){
        			temp=f.nextElement().toString();
        			if(temp.indexOf(dir)!=-1){
        				filelist.remove(temp);
        			}
        		}
        	      	
        	      	for(Enumeration d= dirlist.keys();d.hasMoreElements();){
        			temp=d.nextElement().toString();
        			if(dir.indexOf(temp)!=-1 || temp.indexOf(dir)!=-1){
        			        dirlist.remove(temp);
        	                 }
        	        }
        	       
               }
        
      }
      public void updateFile(){
      	  try{
        		File fl=new File(filepath+listname);
        		if(fl.exists()){
        			fl.delete();
        		}
        	        DataOutputStream dos= new DataOutputStream(new FileOutputStream(filepath+listname,true));
        		for (Enumeration e = filelist.keys() ; e.hasMoreElements() ;) {
        			dos.writeBytes("\n");
        			dos.writeBytes(e.nextElement().toString());
        		}
        		for (Enumeration f = dirlist.keys() ; f.hasMoreElements() ;) {
        			dos.writeBytes("\n");
        			dos.writeBytes(f.nextElement().toString());
        		}
        		dos.close();
            			
       
        }catch(IOException e){
        		        	System.err.println("IOerror"+e);
        }     
       }
      	   
 /*     public void debugtxt(String input){
      	 if(!debuged){
      	 	return ;
      	 }
      	 try{
      	 DataOutputStream dos= new DataOutputStream(new FileOutputStream("/www/htdocs/webapps/perl/debug.txt",true));
      	 dos.writeBytes("\n");
      	 dos.writeBytes(input);
      	 dos.close();
      	 }catch(IOException e){
        		        	System.err.println("IOerror"+e);
                }     
      	}
   */   	   
  }
 
        
        
        		
        	

        	 
        	
        	
        
      
      