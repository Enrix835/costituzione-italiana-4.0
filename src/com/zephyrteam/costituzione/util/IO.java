package com.zephyrteam.costituzione.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

public class IO {
	
	public static boolean copyDatabase(Context context) {
		try{
			  InputStream in = context.getAssets().open("costituzione.db");
			  String dbPath = "/data/data/" + context.getPackageName() + "/databases/";
			  String outPath = dbPath + "costituzione.db";
			  
			  File dbDir = new File(dbPath);
			  if (!dbDir.exists()) dbDir.mkdir();
			  
			  OutputStream out = new FileOutputStream(new File(outPath));

			  byte[] buf = new byte[2048];
			  int len;
			  while ((len = in.read(buf)) > 0){
				  out.write(buf, 0, len);
			  }
		  
			  in.close();
			  out.close();
			  return true;
		  }
		  catch(FileNotFoundException ex){
			  ex.printStackTrace();
			  return false;
		  }
		  catch(IOException e){
			 e.printStackTrace();
			 return false;
		  }
	}
}
