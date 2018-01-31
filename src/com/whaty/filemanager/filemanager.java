package com.whaty.filemanager;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
public class filemanager {

  public filemanager() {
  }
  public String readFile(String s, boolean flag)
    {
        StringBuffer stringbuffer = new StringBuffer("");
        String s2 = "";
        if(flag)
            s2 = "<br>";
        try
        {
            FileInputStream fileinputstream = new FileInputStream(s);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(fileinputstream));
            String s1;
            while((s1 = bufferedreader.readLine()) != null)
                stringbuffer.append(s2 + s1);
            bufferedreader.close();
            fileinputstream.close();
        }
        catch(Exception _ex)
        {
            return "CANNOT_READ " + s;
        }
        return stringbuffer.toString();
    }
    public boolean dumpFile(String s, OutputStream outputstream)
    {
        byte abyte0[] = new byte[4096];
        boolean flag = true;
        try
        {
            FileInputStream fileinputstream = new FileInputStream(s);
            int i;
            while((i = fileinputstream.read(abyte0)) != -1)
                outputstream.write(abyte0, 0, i);
            fileinputstream.close();
        }
        catch(Exception _ex)
        {
            flag = false;
        }
        return flag;
    }

    public boolean copyFile(String s, String s1)
    {
        boolean flag = true;
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(s1);
            flag = dumpFile(s, fileoutputstream);
            fileoutputstream.close();
        }
        catch(Exception _ex)
        {
            flag = false;
        }
        return flag;
    }

    public boolean copyDirectory(String s, String s1, String s2)
    {
        File file = new File(s, s1);
        File file1 = new File(s2, s1);
        String as[] = file.list();
        int i = 0;
        boolean flag = true;
        if(!file1.isDirectory())
            try
            {
                file1.mkdir();
            }
            catch(Exception _ex)
            {
                return false;
            }
        try
        {
            for(; i >= 0; i++)
                if(!as[i].equals(".") && !as[i].equals(".."))
                {
                    flag = copyObject(s + File.separator + s1, as[i], s2 + File.separator + s1);
                    if(!flag)
                        return false;
                }

        }
        catch(Exception _ex) {
         return false;
         }
        return flag;
    }

    public boolean copyObject(String s, String s1, String s2)
    {
        File file = new File(s, s1);
        File file1 = new File(s2);
        if(!file.canRead())
            return false;
        if(file.isFile())
        {
            if(file1.isDirectory())
                return copyFile(s + File.separator + s1, s2 + (s2.endsWith(File.separator) ? "" : File.separator) + s1);
            file1 = new File(s + File.separator + s2);
            if(file1.isDirectory())
                return copyFile(s + File.separator + s1, s + File.separator + s2 + (s2.endsWith(File.separator) ? "" : File.separator) + s1);
            else
                return copyFile(s + File.separator + s1, s2);
        }
        if(file1.isDirectory())
            return copyDirectory(s, s1, s2);
        file1 = new File(s + File.separator + s2);
        if(file1.isDirectory())
            return copyDirectory(s, s1, s + File.separator + s2);
        try
        {
            file.renameTo(file1);
        }
        catch(Exception _ex)
        {
            return false;
        }
        return true;
    }
  
  public void deleteFile(String s, String s1)
    {
	  	s1=this.changeseperator(s1);
        File file = new File(s1, s);
        //System.out.println(file.toString());
        try{
			if( file.isDirectory() ){
				deleteDirectory( file );
			}else  {
				System.out.print(file.delete());
				System.out.println(file.exists());
			}
        }catch(Exception _ex){_ex.printStackTrace();}
    }

public void deleteDirectory( final File directory ) throws IOException 
{
  	if( !directory.exists() ) return;
  
          if( !directory.isDirectory() )
  	{
      throw new IllegalArgumentException( directory + " is not a directory" );
          }
  
          final File files[] = directory.listFiles();
  
          for( int i = 0; i < files.length; i++ ) 
  	{
  	    final File file = files[i];
  
              if( file.isFile() ) file.delete();
  	    else if( file.isDirectory() ) 
  	    {
                  deleteDirectory( file );
              }
          }
          
  	directory.delete();

  }

public void mkdirFile(String s, String s1)
        throws IOException
    {
        File file = new File(s1, s);
        try
        {
            file.mkdir();
        }
        catch(Exception _ex) { }
    }

public String simplifyDir(String s)
    {
        String s1 = s;
        String s2 = "";
        int i;
        if((i = s1.lastIndexOf("..")) <= 2)
            return s1;
        if(s1.substring(i - 1, i).compareTo(File.separator) != 0)
            return s1;
        int j = i + 2;
        for(i -= 2; i > 0;)
        {
            String s3 = s1.substring(i, i + 1);
            if(s3.equals(File.separator))
            {
                if(s2.length() == 0)
                    return s1;
                if(s2.equals(".."))
                    return s1;
                if(s2.equals("."))
                    return s1;
                s2 = s1.substring(0, i);
                if(j < s1.length())
                    s2 = s2 + s1.substring(j);
                return simplifyDir(s2);
            }
            i--;
            s2 = s3 + s2;
        }

        return s1;
    }
    public void addToVector(Vector vector, File file, String s)
    {
        int i = vector.size();
        if(i == 0)
        {
            vector.addElement(s);
            return;
        }
        for(int j = 0; j <= i - 1; j++)
        {
            String s1 = (String)vector.elementAt(j);
            if(s1.compareTo(file.getName()) < 0)
            {
                vector.insertElementAt(s, j);
                return;
            }
        }

        vector.addElement(s);
    }
    public String parentdir(String s)
    {
          int i=s.lastIndexOf(File.separator);
          String s1=s.substring(0,i);
          return s1;
    }
    public String httpaddress(String httproot,String realroot,String s)
    {
        String s1=httproot+s.substring(realroot.length()+2);
        return s1;
    }
    public String changeseperator(String s)
    {
        s=s.replace('\\','/');
        return s;
    }
    public String getSize(String dirp,String file){
    	File fh=new File(dirp,file);
    	long file_size=fh.length();
    	String flength="";
    	if(file_size >= 1073741824)
 	{
        flength = Long.toString(Math.round(file_size / 1073741824 * 100) / 100) + "g";
	}
        else if(file_size >= 1048576)
	{
         flength =Long.toString( Math.round(file_size / 1048576 * 100) / 100) +"m";
	}
        else if(file_size >= 1024)
	{
        flength = Long.toString( Math.round(file_size / 1024 * 100) / 100)+ "k";
	}
        else{
        flength = Long.toString(file_size) +"b";
	}
        return flength;
     }
     public String getModified(String dirp,String file){
     	File fh=new File(dirp,file);
     	long ftime=fh.lastModified();
     	Time tm=new Time(ftime);
     	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
     	return format1.format(tm);
     	}
}
