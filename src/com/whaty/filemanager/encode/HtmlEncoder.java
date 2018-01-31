/****************************************************************************
 * HtmlEncoder.java
 *
 ****************************************************************************/
package com.whaty.filemanager.encode;

public class HtmlEncoder
{
   private HtmlEncoder() {}


   public static final String encode(String text)
   {
if (text == null) 
return ""; 
StringBuffer results = null; 
char[] orig = null; 
int beg = 0, len = text.length(); 
for (int i = 0; i < len; ++i){ 
char c = text.charAt(i); 
switch (c){ 
case 0: 
case '&': 
case '<': 
case '>': 
case '"': 
if (results == null){ 
orig = text.toCharArray(); 
results = new StringBuffer(len+10); 
} 
if (i > beg) 
results.append(orig, beg, i-beg); 
beg = i + 1; 
switch (c){ 
default: // case 0: 
continue; 
case '&': 
results.append("&amp;"); 
break; 
case '<': 
results.append("&lt;"); 
break; 
case '>': 
results.append("&gt;"); 
break; 
case '"': 
results.append("&quot;"); 
break; 
} 
break; 
} 
} 
if (results == null) 
return text; 
results.append(orig, beg, len-beg); 
return results.toString(); 
   

}

}

