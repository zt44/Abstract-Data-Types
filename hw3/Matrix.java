public class Matrix {
   private class Entry {
      int col;
      double val;
      
      Entry(int col, double val) {
         this.col = col;
         this.val = val;
      }
      
      // Returns String representation of the data property.
      public String toString() {
         return "(" + col + ", " + val + ")";
      }
      
      // Returns true if two Entries col and val are equal.
      public boolean equals(Object x) {
         boolean eq = false;
         Entry that;
         if(x instanceof Entry) {
            that = (Entry) x;
            eq = (this.col == that.col && this.val == that.val);
         }
         return eq;
      }
   }

   List[] row;
   
   // Makes a new n x n zero Matrix
   Matrix(int n) {
      if(n < 1)
         throw new RuntimeException("Matrix Error: Matrix() called on invalid column");
      row = new List[n + 1];
      for(int i = 1; i < (n + 1); ++i) {
         row[i] = new List();
      }
   }

   // Returns n, the number of rows and columns of this Matrix.
   int getSize() {
      return row.length - 1;
   }
   
   // Returns the number of non-zero entries in this Matrix.
   int getNNZ() {
      int count = 0;
      for(int i = 1; i <= getSize(); ++i) {
         count += row[i].length();
      }
      return count;
   }
   
   // Overrides Object's equals() method.
   public boolean equals(Object x) {
      Matrix that;
      if(x instanceof Matrix) {
         that = (Matrix) x;
         if(getSize() != that.getSize())
            return false;
         for(int i = 1; i <= getSize(); ++i) {
            if(!(row[i].equals(that.row[i])))
               return false;
         }
      }
      return true;
   }
   
   // Sets this Matrix to the zero
   void makeZero() {
      for(int i = 1; i <= getSize(); ++i) {
         row[i] = new List();
      }
   }

   // Returns a new Matrix having the same entries as this Matrix.
   Matrix copy() {
      Matrix M = new Matrix(getSize());
      for(int i = 1; i <= getSize(); ++i) {
         row[i].moveFront();
         while(row[i].index() >= 0) {
            Entry temp = (Entry) row[i].get();
            M.changeEntry(i, temp.col, temp.val);
            row[i].moveNext();
         }
      }
      return M;
   }
   
   // Changes the ith row, jth column of this Matrix to x.
   void changeEntry(int i, int j, double x) {
      if(i < 1 || i > getSize() || j < 1 || j > getSize())
         throw new RuntimeException("changeEntry() called on undefined position");
      
      boolean found = false;
      row[i].moveFront();
      while(row[i].index() >= 0) {
         Entry entry = (Entry) row[i].get();
         found = (entry.col == j ? true : false);
         if(found) {
            if(x == 0.0) {
               row[i].delete(); return;
            } else { 
               entry.val = x; return;
            }
         }
         row[i].moveNext();
      }

      if(!found && x != 0.0) {
         row[i].moveFront();
         if(row[i].index() == -1) {
            row[i].append(new Entry(j, x)); return;
         } else {
            while(row[i].index() > -1 && ((Entry)row[i].get()).col < j) {
               row[i].moveNext();
            }
            if(row[i].index() > -1) {
               row[i].insertBefore(new Entry(j, x)); return;
            } else {
               row[i].append(new Entry(j, x)); return;
            }
         }
      }
   }

   // Returns a new Matrix that is the scalar product of this Matrix with x.
   Matrix scalarMult(double x) {
      Matrix M = this.copy();
      for(int i = 1; i <= M.getSize(); ++i) {
         M.row[i].moveFront();
         while(M.row[i].index() >= 0) {
            Entry temp = (Entry) M.row[i].get();
            M.changeEntry(i, temp.col, (x * temp.val));
            M.row[i].moveNext();
         }
      }
      return M;
   }

   // Returns sum of matrices
   Matrix add(Matrix M) {
      if(getSize() != M.getSize())
         throw new RuntimeException("add() called on Matricies with different sizes");
      if(M == this)
         return this.copy().scalarMult(2);
      Matrix A = new Matrix(getSize());
      for(int i = 1; i <= getSize(); ++i) {
          A.row[i] = oper(row[i], M.row[i], true);
      }
      return A;
   }
   
   // Returns a difference of matrices
   Matrix sub(Matrix M) {
      if(getSize() != M.getSize())
         throw new RuntimeException("sub() called on Matricies with different sizes");
      if(M == this) {
         return new Matrix(getSize());
      }
      Matrix A = new Matrix(getSize());
      for(int i = 1; i <= getSize(); ++i) {
         A.row[i] = oper(row[i], M.row[i], false);
      }
      return A;
   }

   // Returns transpose
   Matrix transpose() {
      Matrix M = new Matrix(getSize());
      for(int i = 1; i <= getSize(); ++i) {
         row[i].moveFront();
         while(row[i].index() >= 0) {
            M.changeEntry(((Entry)row[i].get()).col, i, ((Entry)row[i].get()).val);
            row[i].moveNext();
         }
      }
      return M;
   }

   // Returns a new Matrix that is the product after multiplication with matrix m
   Matrix mult(Matrix M) {
      if(getSize() != M.getSize())
         throw new RuntimeException("mult() called on two Matricies with different sizes");
      Matrix A = new Matrix(getSize());
      Matrix Mtr = M.transpose();
      for(int i = 1; i <= getSize(); ++i) {
         if(row[i].length() == 0) continue;
         for(int j = 1; j <= getSize(); ++j) {
            if(Mtr.row[j].length() == 0) continue;
            A.changeEntry(i, j, dot(row[i], Mtr.row[j]));
         }
      }
      return A;   
   }

   public String toString() {
      String out = "";
      for(int i = 1; i <= getSize(); ++i) {
         if(row[i].length() > 0)
            out += (i + ": " + row[i] + "\n"); 
      }
      return out;
   }
   

   private static double dot(List P, List Q) {
      double product = 0.0;
      P.moveFront();
      Q.moveFront();
      while(P.index() >= 0 && Q.index() >= 0) {
         Entry a = (Entry) P.get();
         Entry b = (Entry) Q.get();
         if(a.col > b.col) {
            Q.moveNext();
         } else if(a.col < b.col) {
            P.moveNext();
         } else {
            product += (a.val * b.val);
            P.moveNext();
            Q.moveNext();
         }
      }
      return product;
   }

   private List oper(List P, List Q, boolean doAdd) {
      List L = new List();
      P.moveFront();
      Q.moveFront();
      while(P.index() >= 0 || Q.index() >= 0) {
         if(P.index() >= 0 && Q.index() >= 0) {
            Entry a = (Entry) P.get();
            Entry b = (Entry) Q.get();
            if(a.col > b.col) {
               L.append(new Entry(b.col, (doAdd ? 1.0 : -1.0) * b.val));
               Q.moveNext();
            } else if(a.col < b.col) {
               L.append(new Entry(a.col, a.val));
               P.moveNext();
            } else if(a.col == b.col) {
               if((doAdd && a.val + b.val != 0) || (!doAdd && a.val - b.val != 0))
                  L.append(new Entry(a.col, (doAdd ? a.val + b.val : a.val - b.val)));
               P.moveNext();
               Q.moveNext();
            }
         } else if(P.index() >= 0) {
            Entry a = (Entry) P.get();
            L.append(new Entry(a.col, a.val));
            P.moveNext();
         } else {
            Entry b = (Entry) Q.get();
            L.append(new Entry(b.col, (doAdd ? 1.0 : -1.0) * b.val));
            Q.moveNext();
         }
      }
      return L;
   }
}
