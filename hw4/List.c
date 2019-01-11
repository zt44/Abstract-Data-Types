#include<stdio.h>
#include<stdlib.h>
#include<"List.h">

typedef struct NodeObj 
{
int data;
struct NodeObj* next, prev;
}NodeObj;

typedef struct ListObj
{
Node front, back;
int length;
int index;
}ListObj;

List newList(void)
{
List list;

list = malloc(sizeof(ListObj))
list->front = NULL;
list->back = NULL;
list->index = -1;
list->length = 0;
}

void freeList(List* pL)
{
if(pL != NULL && *pL != NULL)
{
   Node temp = *pL->front;
   while(temp != NULL)
  { 
   Node curr = temp;
   temp = temp->next;
   free(curr);
  }
  free(*pL);
*pL = NULL; 
 }
}
//Returns the number of elements in this List. 
int length(List L)
{
if(L==NULL){
   printf(List Error);
   exit(1);
}
return L->length;
}
//If cursor is defined, returns the index of the cursor element
//otherwise returns 0
int index()
{
if(L == NULL)
{
  printf("List error: Index undefined. ")
  exit(1);
 }
}
//returns front element. Pre: length()>0
int front(List L)
{
if(L == NULL)
 {
 printf("List Error: called on NULL reference");
 exit(1);
 }
if(L->length < 1)
 {
 printf("List error: called on empty list");
 exit(1);
 }
return L->front->data;
}
//returns back element 
int back(List L)
{
if(L->length < 1)
 {
 printf("List error: called on empty list");
 exit(1);
 }
if(L == NULL)
 {
 printf("List error: called on NULL reference");
 exit(1);
 } 
return L->back->data;
}
//returns cursor element. 
int get(List L)
{
if(L->length < 1)
 { 
 printf("List error: called on NULL reference");
 exit(1);
 }
if(L == NULL)
 {
 prinf("List error: called on empty list");
 exit(1);
 }
return L->cursor->data;	
}
//checks for equivalence 
int equals(List A, List B)
{
if(A->length != B->length)
 {
 return 0;
 }
if(A == NULL || B == NULL)
 {
 printf("List error: called in NULL reference");
 exit(1);
 }
Node first = B->front;
Node nextElement = A->front;

while(first->next != NULL && nextElement->next != NULL)
 {
 if(first->data != nextElement->data)
   return 0;
  first=first->next;
  nextElement=nextElement->next;    
 }
   return 1;
 }

void clear(List L)
{
 if(L == NULL)
 {
 printf("List error: called on NULL reference");
 exit(1);
 }
 Node nextElement = L->front;
 while(nextElement != NULL)
 {
 Node cur = nextElement;
 nextElement = nextElement->next;
 free(cur);
 }
 L->front = L->back = L->cursor = NULL;
 L->length = 0;
 L->index = -1;
}

void moveFront(List L)
{
 if(L == NULL)
 { 
 printf("List error: called on NULL reference");
 exit(1);
 }
 if(L->length > 0)
 {
 L->cursor = L->front;
 L->index = 0;
 }

}

void moveBack(List L)
{
if(L == NULL)
 {
 printf("List error: called on NULL reference");
 exit(1);
 }
if(L->length > 0)
 {
 L->cursor = L->front;
 L->index = 0; 
 }

}

void moveBack(List L)
{
 if(L == NULL)
 {
 printf("List error: called on NULL reference");
 exit(1);
 } 
 if(L->length > 0)
 {
 L->cursor = L->back;
 L->index = L->length - 1; 
 }
}

void movePrev(List L)
{
 if(L==NULL)
 {
 printf(List error: called on NULL reference);
 exit(1);
 }
 if(L->cursor != NULL && L->index ! == 0)
 {
 L->cursor = L->cursor->prev;
 --L->index;
 }

 else if(L->cursor != NULL && L->index == 0)
 {
 L->cursor = NULL;
 L->index = -1;
 }
}

void moveNext(List L)
{
 if(L == NULL)
 {
 printf("List error: called on NULL reference");
 exit(1);
 }
 if(L->index != L->length - 1 && L->cursor != NULL)
 {
 L->cursor = L->cursor->next;
 ++L->index;
 }
 else if(L->cursor != NULL && L->index == L->length-1)
 {
 L->index = -1;
 L->cursor = NULL;
 }
}

void append(int data, List L)
{
 if(L == NULL)
 {
 printf(List error: called on NULL reference);
 exit(1);
 }
Node nextElement = newNode(data, L->back, NULL);
 if(L->front == NULL)
    L->front = nextElement; 
 else
    L->back->next = nextElement;
 L->back = nextElement;
 ++L->length;
}

void prepend(int data, List L)
{
 if(L == NULL)
 {
  printf("List error: called on  NULL reference");
  exit(1);
 }
 Node nextElement = newNode(data, NULL, L->front);
 if(L->front == NULL)
 {
 L->back = nextElement;
 }
 else
   L->front->prev = nextElement;
L->front = nextElement;
++L->length;
}

insertBefore(int data, List L)
 {
   if(L == NULL)
   {
      printf("List Error: called on NULL refernce");
      exit(1);
   }
   if(L->index < 0)
   {
      printf("List Error: index undefined");
      exit(1);
   }
   if(L->length < 1)
   {
      printf("List Error: Empty list");
      exit(1);
   }
   Node nextElement = newNode(data, L->cursor->prev, L->cursor);
   if(L->cursor->prev != NULL)
      L->cursor->prev->next = nextElement;
   else
      L->front = nextElement;

   L->cursor->prev = nextElement;
   ++L->length;
 }

void insertAfter(int data, List L)
 {
   if(L == NULL)
   {
      printf("List Error: called on NULL reference");
      exit(1);
   }
   if(L->index < 0)
   {
      printf("List Error: called on empty list");
      exit(1);
   }
   if(L->length < 1)
   {
      printf("List error: called on empty list");
      exit(1);
   }
   Node nextElement = newNode(data, L->cursor, L->cursor->next);
   if(L->cursor->next != NULL)
      L->cursor->next->prev = nextElement;
   else
      L->back = nextElement;

   L->cursor->next = nextElement;
   ++L->length;
 }


void deleteFront(List L) 
{
   if(L == NULL)
   {
      printf("List Error: called on NULL reference");
      exit(1);
   }
   if(L->length < 1)
   {
      printf("List Error: called on empty list"); 
      exit(1);
   }
   if(L->cursor == L->front)
   {
      L->index = -1;
      L->cursor = NULL;
   }
   Node nextElement = L->front;
   L->front = L->front->next;
   L->front->prev = NULL;
   --L->length;
   freeNode(&nextElement);
}


void deleteBack(List L)
{
   if(L == NULL)
   {
      printf("List Error: called in NULL refernce");
      exit(1);
   }
   if(L->length < 1)
   {
      printf("List Error: called on empty list");
      exit(1);
   }
   if(L->cursor == L->back) {
      L->cursor = NULL;
      --L->index;
   }
   Node nextElement = L->back;
   L->back = L->back->prev;
   L->back->next = NULL;
   --L->length;
   freeNode(&nextElement);
}


void delete(List L)
 {
   if(L == NULL)
   {
      printf("List error: called on NULL reference");
      exit(1);
   }
   if(L->length < 1)
   {
      printf("List Error: index undefined");
      exit(1);
   }
   if(L->index < 0)
   {
      printf("List Error: called on empty list");
      exit(1);
   }
   if(L->cursor == L->back)
   {
      deleteBack(L);
   }
   else if(L->cursor == L->front)
   {
      deleteFront(L);
   }
    else
    {
      Node nextElement = L->cursor;
      L->cursor->prev->next = L->cursor->next;
      L->cursor->next->prev = L->cursor->prev;
      freeNode(&nextElement);
      L->cursor = NULL;
      L->index = -1;
      --L->length;
    }
}


void printList(FILE* out, List L) {
   Node nextElement = L->front;
   while(nextElement != NULL)
   {
      printf("%d ", nextElement->data);
      nextElement = nextElement->next;
   }
}

List copyList(List L)
 {
   List tmp = newList();
   Node nextElement = L->front;
   while(nextElement != NULL) {
      append(tmp, nextElement->data);
      nextElement = nextElement->next;
   }
   return tmp;
 }

}
