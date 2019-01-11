//-----------------------------------------------------------------------------
//  List.java
//  An integer queue ADT
//  Tarik Zeid
//  Cruzid: tzeid
//  Id number: 1499437
//  PA1
//-----------------------------------------------------------------------------

class List{

   private class Node{
      // Fields
      int data;
      Node next;
      Node prev;
      
      // Constructor
      Node(int data) { this.data = data; next = null; prev = null; }
      
      Node(int data, Node prev, Node next)
      {
    	 this.data = data; this.next = next; this.prev = prev;
      }
      
      // toString():  overrides Object's toString() method
      public String toString() { 
         return String.valueOf(data); 
      }
      
      // equals(): overrides Object's equals() method
      public boolean equals(Object x){
         boolean eq = false;
         Node that;
         if(x instanceof Node){
            that = (Node) x;
            eq = (this.data==that.data);
         }
         return eq;
      }
   }

   // Fields
   private Node front;
   private Node back;
   private Node cursor;
   private int index; 
   private int length;

   // Constructor
   List() { 
      front = back = null; 
      cursor = null;
      index = -1;
      length = 0; 
   }


   // Access Functions -----------------

   // isEmpty()
   // Returns true if this Queue is empty, false otherwise.
   boolean isEmpty() { 
      return length==0; 
   }

   // getLength()
   // Returns length of this Queue.
   int length() { 
      return length; 
   }
// If cursor is defined, returns the index of the cursor element,
   int index() {
	   return index;
   }

   // getFront() 
   // Returns front element.
   // Pre: !this.isEmpty()
   int front(){
      if( this.isEmpty() ){
         throw new RuntimeException(
            "Queue Error: getFront() called on empty Queue");
      }
      return front.data;
   }
// Returns back element. Pre: length()>0
   int back() {
	   if(this.isEmpty() ){
		   throw new RuntimeException("Queue Error: back() called on empty Queue");
	   }
	   return back.data;
   }

   // Manipulation Procedures -------------------------------------------------

   // Enqueue()
   // Appends data to back of this Queue.
   void Enqueue(int data){
      Node N = new Node(data);
      if( this.isEmpty() ) { 
         front = back = N;
      }else{ 
         back.next = N; 
         back = N; 
      }
      length++;
   }

   // Dequeue()
   // Deletes front element from this Queue
   void Dequeue(){
      if(this.isEmpty()){
         throw new RuntimeException(
            "Queue Error: Dequeue() called on empty Queue");
      }
      if(this.length>1){
         front = front.next;
      }else{
         front = back = null;
      }
      length--;
   }
// Returns cursor element. Pre: length()>0, index()>=0
   int get()
   {
	   if(length  < 1)
		   throw new RuntimeException(
				   "Queue Error: get() cannot be called on empty queue");
	   if(index < 0)
		   throw new RuntimeException(
				   "Queue Error: get() cannot be called because index does not exist");
	   return cursor.data;
   }


   // Other Functions ---------------------------------------------------------

   // toString()
   // Overides Object's toString() method.
   public String toString(){
      StringBuffer sb = new StringBuffer();
      Node N = front;
      while(N!=null){
         sb.append(" ");
         sb.append(N.toString());
         N = N.next;
      }
      return new String(sb);
   }

   // equals()
   // Overrides Object's equals() method.  Returns true if x is a Queue storing
   // the same integer sequence as this Queue, false otherwise.
   public boolean equals(Object x){
      boolean eq  = false;
      List Q;
      Node N, M;

      if(x instanceof List){
         Q = (List)x;
         N = this.front;
         M = Q.front;
         eq = (this.length==Q.length);
         while( eq && N!=null ){
            eq = N.equals(M);
            N = N.next;
            M = M.next;
         }
      }
      return eq;
   }

   // copy()
   // Returns a new Queue identical to this Queue.
   List copy(){
      List Q = new List();
      Node N = this.front;

      while( N!=null ){
         Q.Enqueue(N.data);
         N = N.next;
      }
      return Q;
   }
   
// Resets this List to its original empty state.
   void clear()
   {
	   cursor = front = back = null;
	   index = 0;
	   cursor = back;
   }
   
   // If List is non-empty, places the cursor under the back element,

   void moveBack()
   {
	   if(length > 0){
		   cursor = back;
		   index = length - 1;
	   }
   }
   
// If cursor is defined and not at back, moves cursor one step toward
 // back of this List, if cursor is defined and at back, cursor becomes
// undefined.

   void moveNext()
   {
	   if(cursor != null && index == length - 1)
	   {
		   cursor = cursor.next;
	   ++index;
	   }
	   
	   else if(cursor != null && index == length - 1)
	   {
		   index = -1;
			cursor = null;	   
	   }
   }
   
   
 
   void moveFront(){
	   if(length > 0){
		   index = 0;
		   cursor = back; 
	   }
   }
   
// If cursor is defined and not at front, moves cursor one step toward
 // front of this List, if cursor is defined and at front, cursor becomes
// undefined.

   void movePrev()
   {
	   if(cursor != null && index != 0)
	   {
		   cursor = cursor.prev; 
		   --index;
	   }
	   else if(index ==0 && cursor != null)
	   {
		   cursor = null;
		   index = -1;
	   }
   }
   
   // Insert new element into this List. If List is non-empty,

   void prepend(int data)
   {
	   Node temp = new Node(data,null,front);
	   if(front == null)
		   back = temp;
	   else
		   front.prev = temp;
	   front = temp;
	   ++length;
	   
   }
// Insert new element into this List. If List is non-empty,
   void append(int data)
   {
	   Node temp = new Node(data, null,back);
	   if(front == null)
		   front = temp;
	   else 
		   back.next = temp;
	   back = temp;
	   ++length; 
   }
   // Insert new element

   void insertBefore(int data)
   {
	   if(index < 0)
		   throw new RuntimeException("Queue Error: index does not exist");
	   if(length < 1)
		   throw new RuntimeException("Queue Error: list is empty");
	   Node temp = new Node(data, cursor.prev, cursor);
	   if(cursor.prev != null)
		   cursor.prev.next = temp; 
	   else
		   front = temp;
	   cursor.prev = temp;
	   ++length; 
	   
   }
// Inserts new element after cursor.
   void insertAfter(int data)
   {
	   if(index < 0)
		   throw new RuntimeException("Queue Error: index does not exist");
	   if(length < 1)
		   throw new RuntimeException("Queue Error: list is empty");
	   Node temp = new Node(data, cursor, cursor.next);
	   if(cursor.next != null)
		   cursor.next.prev = temp; 
	   else 
		   back = temp;
	   cursor.next = temp;
	   ++length;
	   
   }
   
// Deletes the back
   void deleteBack()
   {
	   if(length < 1)
		   throw new RuntimeException("Queue error: empty list");
	   if(cursor == back)
	   {
		   cursor = null;
		   index = -1; 
	   }
	   back = back.prev; 
	   back.next = null; 
	   --length; 
   }
   // Deletes the front
   void deleteFront()
   {
	   if(length < 1)
		   throw new RuntimeException("Queue error: list empty");
	   if(cursor == back)
	   {
		   cursor = null;
		   index = -1; 
	   }
	   front = front.prev;
	   front.prev = null;
	   --length;
   }
// Deletes cursor
   void delete()
   {
	   if(index < 0)
		   throw new RuntimeException("Queue Error: index does not exist");
	   if(length < 1)
		   throw new RuntimeException("Queue Error: empty list");
	   if(cursor == back)
		   deleteBack();
	   else if(cursor == front)
		   deleteFront();
	   else
	   {
		   cursor.prev.next = cursor.next;
		   cursor.next.prev = cursor.prev; 
		   index = -1;
		   cursor = null;
		   --length;
	   }
   }
   

   

   
}
