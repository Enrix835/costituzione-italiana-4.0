  /*
   * Costituzione Italiana 4.0 
   * Copyright (C) 2012 
   *	 Gnufabio <me@gnufabio.com>, Enrix835 <enrico.trt@gmail.com>
   *
   * This program is free software: you can redistribute it and/or modify
   * it under the terms of the GNU General Public License as published by
   * the Free Software Foundation, either version 3 of the License, or
   * (at your option) any later version.
   *
   * This program is distributed in the hope that it will be useful,
   * but WITHOUT ANY WARRANTY; without even the implied warranty of
   * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   * GNU General Public License for more details.
   *
   * You should have received a copy of the GNU General Public License
   * along with this program.  If not, see <http://www.gnu.org/licenses/>.
   */

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
