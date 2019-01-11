#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include "List.h"

#define MAX_LEN 500

int main(int argc,char* argv[])
{
   
   int count = 0;
   FILE *in, *out;
   char line[MAX_LEN];

   if(argc != 3)
   {
      printf("Usage:<input file> <output file>",argv[0]);
      exit(1);
   }
 
   in = fopen(argv[1],"r");
   out = fopen(argv[2],"w");
   if(in==NULL)
   {
      printf("File error: unable to read from file",argv[1]);
      exit(1);
   }
   if(out==NULL)
   {
      printf("File Error: cannot write to file",argv[2]);
      exit(1);
   }

   
   while(fgets(line, MAX_LEN, in)!= NULL) 
   {
      count++;
   }
   
   rewind(in);

   char lines[count-1][MAX_LEN];
   int ln = -1;

   while(fgets(line, MAX_LEN, in) != NULL) 
   {
      strcpy(lines[++ln],line);
   }
   
   List list = newList();

   append(list,0);

   for(int j=1; j < count; ++j) 
   {
      char *temp = lines[j];
      int i = j - 1;
    
      moveBack(list);
  
      while(i>=0 && strcmp(temp,lines[get(list)]) <= 0) 
      {
         --i;
         movePrev(list);
      }
      
      if(index(list)>=0)
         insertAfter(list, j);
      else
         prepend(list, j);
    }
   
   moveFront(list);
   while(index(list)>=0)
   {
      fprintf(out,lines[get(list)]);
      moveNext(list);
   }


   fclose(in);
   fclose(out);
   freeList(&list);

   return(0);
}
