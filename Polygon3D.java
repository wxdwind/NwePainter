// Polygon3D.java: Polygon in 3D, represented by vertex numbers 
//                 referring to coordinates stored in an Obj3D object.

// Copied from Section 5.5 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.

import java.util.*;

class Polygon3D
{  private int[] nrs;
   private double a, b, c, h;
   private Tria[] t;
   Polygon3D(Vector vnrs)
   {  int n = vnrs.size();
      nrs = new int[n];
      for (int i=0; i<n; i++)
         nrs[i] = ((Integer)vnrs.elementAt(i)).intValue();
   }

   int[] getNrs(){return nrs;}
   double getA(){return a;}
   double getB(){return b;}
   double getC(){return c;}
   double getH(){return h;}
   void setAbch(double a, double b, double c, double h)
   {  this.a = a; this.b = b; this.c = c; this.h = h;
   }
   Tria[] getT(){return t;}

   void triangulate(Obj3D obj)   
   // Successive vertex numbers (CCW) in vector nrs.
   // Resulting triangles will be put in array t.
   {  int n = nrs.length;         // n > 2 is required
      int[] next = new int[n];
      t = new Tria[n - 2];
      Point2D[] vScr = obj.getVScr();
      int iA=0, iB, iC;
      int j = n - 1;
      for (int i=0; i<n; i++){next[j] = i; j = i;}
      for (int k=0; k<n-2; k++)
      {  // Find a suitable triangle, consisting of two edges
         // and an internal diagonal:
         Point2D a, b, c;
         boolean found = false;
         int count = 0, nA = -1, nB = 0, nC = 0, nj;
         while (!found && ++count < n)
         {  iB = next[iA]; iC = next[iB];
            nA = Math.abs(nrs[iA]); a = vScr[nA]; 
            nB = Math.abs(nrs[iB]); b = vScr[nB];
            nC = Math.abs(nrs[iC]); c = vScr[nC];
            if (Tools2D.area2(a, b, c) >= 0)
            {  // Edges AB and BC; diagonal AC.
               // Test to see if no vertex (other than A, 
               // B, C) lies within triangle ABC:
               j = next[iC]; nj = Math.abs(nrs[j]);
               while (j != iA && 
                     (nj == nA || nj == nB || nj == nC ||
                     !Tools2D.insideTriangle(a, b, c, vScr[nj])))
                  {  j = next[j]; nj = Math.abs(nrs[j]);
                  }
               if (j == iA) 
               {  // Triangle found:
                  t[k] = new Tria(nA, nB, nC);
                  next[iA] = iC; 
                  found = true;
               }  
            }  
            iA = next[iA];
         }
         if (count == n)
         {  // Degenerated polygon, possibly with all
            // vertices on one line.
            if (nA >= 0) t[k] = new Tria(nA, nB, nC);
            else 
            {  System.out.println("Nonsimple polygon");  
               System.exit(1);
            }
         }
      }
   }
}
