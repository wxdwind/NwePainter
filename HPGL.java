// HPGL.java: Class for export of HP-GL files.

// Copied from Section 6.8 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.

import java.io.*;

class HPGL
{  FileWriter fw;
   HPGL(Obj3D obj)
   {  String plotFileName = "", fName = obj.getFName();
      for (int i=0; i<fName.length(); i++)
      {  char ch = fName.charAt(i);
         if (ch == '.') break;
         plotFileName += ch;
      }
      plotFileName += ".plt";
      try
      {  fw = new FileWriter(plotFileName);
         fw.write("IN;SP1;\n");
      }
      catch (IOException ioe){}
   }

   void write(String s)
   {  try {fw.write(s); fw.flush();}catch (IOException ioe){}
   }
}
